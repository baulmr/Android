package com.example.acer.randomapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.acer.randomapplication.dataObject.DataKelas;

import java.util.ArrayList;
import java.util.List;

public class ModelKelas extends Handler {

    public ModelKelas(Context context) {
        super(context);
    }

    private int jumlah_peserta(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM tb_peserta WHERE id_kelas = "+ Integer.toString(id);
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;
    }

    public List<DataKelas> read() {

        List<DataKelas> recordsList = new ArrayList<DataKelas>();

        String sql = "SELECT * FROM tb_kelas ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                DataKelas kelas = new DataKelas();
                kelas.id = cursor.getInt(cursor.getColumnIndex("id"));
                kelas.nama = cursor.getString(cursor.getColumnIndex("nama"));
                kelas.jumlahPeserta = jumlah_peserta(kelas.id);

                recordsList.add(kelas);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public boolean create(String kelas) {

        ContentValues values = new ContentValues();

        values.put("nama", kelas);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("tb_kelas", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("tb_kelas", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

}
