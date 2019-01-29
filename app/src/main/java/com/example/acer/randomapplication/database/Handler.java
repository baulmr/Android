package com.example.acer.randomapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Handler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "StudentDatabase";

    public Handler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        sql = "CREATE TABLE tb_kelas " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nama TEXT ) ";
        db.execSQL(sql);

        sql = "CREATE TABLE tb_peserta " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nama TEXT, " +
                "email TEXT, " +
                "id_kelas INTEGER, "+
                "FOREIGN KEY(id_kelas) REFERENCES tb_kelas(id) ON DELETE CASCADE  ) ";
        db.execSQL(sql);

        sql = "CREATE TABLE tb_kelompok " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_kelas INTEGER, "+
                "nama TEXT, " +
                "jumlah_kelompok INTEGER, "+
                "FOREIGN KEY(id_kelas) REFERENCES tb_kelas(id) ON DELETE CASCADE  ) ";
        db.execSQL(sql);

        sql = "CREATE TABLE tb_peserta_kelompok " +
                "( id_peserta INTEGER, " +
                "id_kelompok INTEGER, " +
                "no_urut INTEGER, " +
                "FOREIGN KEY(id_peserta) REFERENCES tb_peserta(id) ON DELETE CASCADE, " +
                "FOREIGN KEY(id_kelompok) REFERENCES tb_kelompok(id) ON DELETE CASCADE  ) ";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql;
        sql = "DROP TABLE IF EXISTS tb_peserta_kelompok";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS tb_kelompok";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS tb_peserta";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS tb_kelas";
        db.execSQL(sql);

        onCreate(db);
    }

}
