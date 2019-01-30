package com.example.acer.randomapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.randomapplication.dataObject.DataKelompok;
import com.example.acer.randomapplication.dataObject.DataPeserta;
import com.example.acer.randomapplication.dataObject.DataPesertaKelompok;
import com.example.acer.randomapplication.database.ModelKelompok;
import com.example.acer.randomapplication.database.ModelPeserta;

import java.util.ArrayList;
import java.util.List;

public class BuatKelompok extends AppCompatActivity {

    List<DataPesertaKelompok> data = new ArrayList<DataPesertaKelompok>();
    int jumlahRegu;
    int id_kelas;
    String judul;

    private void setData(){
        List<DataPeserta> peserta = new ModelPeserta(this).read(id_kelas);
        for (DataPeserta obj : peserta) {
            DataPesertaKelompok baru = new DataPesertaKelompok();
            baru.id_peserta = obj.id;
            baru.nama = obj.nama;
            data.add(baru);
        }
    }
    private void acak(){
        int randomVal, range = data.size() - 1;
        DataPesertaKelompok tmp;
        for(int i=0; i<data.size(); i++){
            randomVal = (int)(Math.random() * range);
            tmp = data.get(randomVal);
            data.set(randomVal, data.get(i));
            data.set(i, tmp);
        }
    }
    private void tampilkanPeserta(){
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.list);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayoutRecords.removeAllViews();

        int jumlahNormal = data.size() / jumlahRegu;
        int ii = 0, kelebihan;
        for(int i=1, j = data.size() % jumlahRegu; i <= jumlahRegu; i++, j--){
            kelebihan = (j>0 ? 1 : 0);

            View lists = inflater.inflate(R.layout.list_lihatkel, null, false);
            TextView title = (TextView) lists.findViewById(R.id.title);
            title.setText("Kelompok ke-"+ i );

            LinearLayout listPeserta = (LinearLayout) lists.findViewById(R.id.listNama);
            for(int z = ii; z < ii+jumlahNormal+kelebihan; z++){
                TextView item = new TextView(this);
                item.setPadding(10, 10, 0, 0);
                item.setText( data.get(z).nama );
                item.setTextColor(Color.WHITE);
                data.get(z).no_urut = i;
                listPeserta.addView(item);
            }
            ii = ii+jumlahNormal+kelebihan;
            linearLayoutRecords.addView(lists);
        }
    }

    class acak implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            acak();
            tampilkanPeserta();
        }
    }

    class simpan implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Context context = v.getRootView().getContext();
            DataKelompok kelompok = new DataKelompok();
            kelompok.id_kelas = id_kelas;
            kelompok.nama = judul;
            kelompok.jumlahKelompok = jumlahRegu;
            boolean success = new ModelKelompok(context).create(kelompok, data);
            if(success){
                onBackPressed();
                KelompokKelasActivity.getInstance().load();
            } else {
                Toast.makeText(context, "Gagal menyimpan kelompok.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_kelompok);

        Bundle b = getIntent().getExtras();
        judul = b.getString("judul");
        jumlahRegu = b.getInt("pembagi");
        id_kelas = b.getInt("id_kelas");
        setTitle(judul+"*");
        setData();
        acak(); tampilkanPeserta();

        Button acak = (Button) findViewById(R.id.Acak);
        acak.setOnClickListener(new acak());

        Button simpan = (Button) findViewById(R.id.Simpan);
        simpan.setOnClickListener(new simpan());

    }
}
