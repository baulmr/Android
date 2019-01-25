package com.example.acer.randomapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.acer.randomapplication.dataObject.DataKelas;
import com.example.acer.randomapplication.dataObject.DataPeserta;
import com.example.acer.randomapplication.database.ModelKelas;
import com.example.acer.randomapplication.database.ModelPeserta;
import com.example.acer.randomapplication.eventListener.OpsiDaftarPeserta;
import com.example.acer.randomapplication.eventListener.tambahPeserta;

import java.util.List;

public class DaftarPesertaActivity extends AppCompatActivity {

    int id_kelas;
    static DaftarPesertaActivity dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_peserta);
        setTitle("Peserta");

        dp = this;

        Bundle b = getIntent().getExtras();
        id_kelas = b.getInt("id_kelas");

        Button tambah = (Button) findViewById(R.id.tambah);
        tambah.setOnClickListener(new tambahPeserta(id_kelas));

        load();
    }

    public void load(){
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.list);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        linearLayoutRecords.removeAllViews();

        List<DataPeserta> data = new ModelPeserta(this).read(id_kelas);

        if (data.size() > 0) {

            for (DataPeserta obj : data) {
                View lists = inflater.inflate(R.layout.list_peserta, null, false);
                TextView nama = (TextView) lists.findViewById(R.id.nama);
                TextView email = (TextView) lists.findViewById(R.id.email);
                Button opsi = (Button) lists.findViewById(R.id.opsi);

                opsi.setOnClickListener(new OpsiDaftarPeserta(obj.id, obj.nama));

                nama.setText(obj.nama);
                email.setText(obj.email);

                linearLayoutRecords.addView(lists);
            }

        }

        else {

        }
    }

    public static DaftarPesertaActivity getInstance() {
        return dp;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Kelas.getInstance().load();
    }
}
