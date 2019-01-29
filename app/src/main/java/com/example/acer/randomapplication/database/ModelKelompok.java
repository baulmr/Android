package com.example.acer.randomapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.acer.randomapplication.dataObject.DataKelompok;
import com.example.acer.randomapplication.dataObject.DataPeserta;
import com.example.acer.randomapplication.dataObject.DataPesertaKelompok;

import java.util.ArrayList;
import java.util.List;

public class ModelKelompok extends Handler {
    public ModelKelompok(Context context) {
        super(context);
    }

    public List<DataKelompok> daftarKelompok(){

        List<DataKelompok> recordsList = new ArrayList<DataKelompok>();

        String sql = "SELECT tb_kelompok.*, tb_kelas.nama AS nama_kelas FROM tb_kelompok " +
                "INNER JOIN tb_kelas ON tb_kelompok.id_kelas = tb_kelas.id " +
                "ORDER BY tb_kelompok.id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                DataKelompok kelompok = new DataKelompok();
                kelompok.id = cursor.getInt(cursor.getColumnIndex("id"));
                kelompok.nama = cursor.getString(cursor.getColumnIndex("nama"));
                kelompok.id_kelas = cursor.getInt(cursor.getColumnIndex("id_kelas"));
                kelompok.nama_kelas = cursor.getString(cursor.getColumnIndex("nama_kelas"));
                kelompok.jumlahKelompok = cursor.getInt(cursor.getColumnIndex("jumlah_kelompok"));

                recordsList.add(kelompok);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public List<DataKelompok> daftarKelompok_byKelas(int id_kelas){

        List<DataKelompok> recordsList = new ArrayList<DataKelompok>();

        String sql = "SELECT * FROM tb_kelompok WHERE id_kelas = '"+ Integer.toString(id_kelas) +"' ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                DataKelompok kelompok = new DataKelompok();
                kelompok.id = cursor.getInt(cursor.getColumnIndex("id"));
                kelompok.nama = cursor.getString(cursor.getColumnIndex("nama"));
                kelompok.id_kelas = id_kelas;
                kelompok.jumlahKelompok = cursor.getInt(cursor.getColumnIndex("jumlah_kelompok"));

                recordsList.add(kelompok);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("tb_kelompok", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

    public boolean create(DataKelompok kelompok, List<DataPesertaKelompok> data){

        ContentValues values = new ContentValues();

        values.put("nama", kelompok.nama);
        values.put("id_kelas", kelompok.id_kelas);
        values.put("jumlah_kelompok", kelompok.jumlahKelompok);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("tb_kelompok", null, values) > 0;

        String qry = "SELECT * FROM tb_kelompok";
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToLast();
        db.close();

        createSuccessful = createSuccessful && updateList(cursor.getInt(cursor.getColumnIndex("id")), data);

        return createSuccessful;
    }

    public boolean updateList(int id, List<DataPesertaKelompok> data){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tb_peserta_kelompok", "id_kelompok ='" + id + "'", null);

        boolean add = true;

        for (DataPesertaKelompok obj : data) {
            ContentValues values = new ContentValues();

            values.put("id_peserta", obj.id_peserta);
            values.put("id_kelompok", id);

            add = add && db.insert("tb_peserta_kelompok", null, values) > 0;

        }

        db.close();
        return add;
    }

}
