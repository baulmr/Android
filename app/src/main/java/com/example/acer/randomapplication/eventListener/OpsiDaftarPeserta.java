package com.example.acer.randomapplication.eventListener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.acer.randomapplication.DaftarPesertaActivity;
import com.example.acer.randomapplication.database.ModelKelas;
import com.example.acer.randomapplication.database.ModelPeserta;

public class OpsiDaftarPeserta implements View.OnClickListener {

    int id;
    String nama;

    public OpsiDaftarPeserta(int id, String nama){
        this.id = id;
        this.nama = nama;
    }

    @Override
    public void onClick(View v) {
        final CharSequence[] items = { "Hapus Peserta" };
        final Context context = v.getRootView().getContext();
        final int id = this.id;
        final View view = v;

        new AlertDialog.Builder(context).setTitle("Opsi untuk "+ nama)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item){
                            case 0 :
                                boolean deleteSuccessful = new ModelPeserta(context).delete(id);
                                if (deleteSuccessful){
                                    Toast.makeText(context, "Berhasil menghapus kelas.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Gagal menghapus peserta.", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }

                        DaftarPesertaActivity.getInstance().load();

                        dialog.dismiss();

                    }
                }).show();
    }
}
