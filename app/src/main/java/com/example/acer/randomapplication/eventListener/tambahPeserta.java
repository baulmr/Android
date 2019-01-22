package com.example.acer.randomapplication.eventListener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acer.randomapplication.DaftarPesertaActivity;
import com.example.acer.randomapplication.R;
import com.example.acer.randomapplication.dataObject.DataPeserta;
import com.example.acer.randomapplication.database.ModelKelas;
import com.example.acer.randomapplication.database.ModelPeserta;

public class tambahPeserta implements View.OnClickListener {

    int id_kelas;

    public tambahPeserta(int id_kelas){
        this.id_kelas = id_kelas;
    }

    @Override
    public void onClick(View v) {
        final Context context = v.getRootView().getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View formElementsView = inflater.inflate(R.layout.modals_input_peserta, null, false);
        final View formTitle = inflater.inflate(R.layout.modals_title_peserta, null, false);

        final EditText nama = (EditText) formElementsView.findViewById(R.id.nama);
        final EditText email = (EditText) formElementsView.findViewById(R.id.email);
        final int id_kelas = this.id_kelas;

        new AlertDialog.Builder(context)
                .setCustomTitle(formTitle)
                .setView(formElementsView)
                .setCustomTitle(formTitle)
                .setPositiveButton("Simpan",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DataPeserta peserta = new DataPeserta();
                                peserta.nama = nama.getText().toString();;
                                peserta.email = email.getText().toString();;
                                peserta.id_kelas = id_kelas;

                                boolean createSuccessful = new ModelPeserta(context).create(peserta);

                                if(createSuccessful){
                                    Toast.makeText(context, "Berhasil menambahkan peserta.", Toast.LENGTH_SHORT).show();
                                    ((DaftarPesertaActivity) context).load();
                                }else{
                                    Toast.makeText(context, "Gagal menambahkan kelas.", Toast.LENGTH_SHORT).show();
                                }

                                dialog.cancel();
                            }

                        }).show();
    }
}
