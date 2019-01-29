package com.example.acer.randomapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.acer.randomapplication.dataObject.DataKelas;
import com.example.acer.randomapplication.dataObject.DataPeserta;
import com.example.acer.randomapplication.dataObject.DataPesertaKelompok;

import java.util.ArrayList;
import java.util.List;

public class ModelPeserta extends Handler {

    public ModelPeserta(Context context) {
        super(context);
    }

    public List<DataPeserta> read(int id_kelas) {

        List<DataPeserta> recordsList = new ArrayList<DataPeserta>();

        String sql = "SELECT * FROM tb_peserta WHERE id_kelas = "+ Integer.toString(id_kelas) +" ORDER BY nama ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                DataPeserta peserta = new DataPeserta();
                peserta.id = cursor.getInt(cursor.getColumnIndex("id"));
                peserta.nama = cursor.getString(cursor.getColumnIndex("nama"));
                peserta.email = cursor.getString(cursor.getColumnIndex("email"));

                recordsList.add(peserta);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public boolean create(DataPeserta peserta) {

        ContentValues values = new ContentValues();

        values.put("nama", peserta.nama);
        values.put("email", peserta.email);
        values.put("id_kelas", peserta.id_kelas);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("tb_peserta", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("tb_peserta", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

    public List<DataPesertaKelompok> ListByKelompok(int id_kelompok) {

        List<DataPesertaKelompok> recordsList = new ArrayList<DataPesertaKelompok>();

        String sql = "SELECT * FROM tb_peserta_kelompok " +
                "INNER JOIN tb_peserta ON tb_peserta_kelompok.id_peserta = tb_peserta.id " +
                "WHERE tb_peserta_kelompok.id_kelompok = "+ Integer.toString(id_kelompok);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                DataPesertaKelompok peserta = new DataPesertaKelompok();
                peserta.id_peserta = cursor.getInt(cursor.getColumnIndex("id"));
                peserta.nama = cursor.getString(cursor.getColumnIndex("nama"));
                peserta.no_urut = cursor.getInt(cursor.getColumnIndex("no_urut"));

                recordsList.add(peserta);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

}
