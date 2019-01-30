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

import com.example.acer.randomapplication.dataObject.DataPeserta;
import com.example.acer.randomapplication.dataObject.DataPesertaKelompok;
import com.example.acer.randomapplication.database.ModelKelompok;
import com.example.acer.randomapplication.database.ModelPeserta;


import java.util.List;

public class LihatKelompokActivity extends AppCompatActivity {

    List<DataPesertaKelompok> data;
    int jumlahRegu;
    int id;
    int id_kelas;

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

            boolean success = new ModelKelompok(context).updateList(id, data);
            if(success){
                Toast.makeText(context, "Berhasil menyimpan perubahan.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Gagal menyimpan perubahan.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class sesuaikan implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            setData();
            acak();
            tampilkanPeserta();
        }
    }

    private void setData(){
        data.clear();
        List<DataPeserta> peserta = new ModelPeserta(this).read(id_kelas);
        for (DataPeserta obj : peserta) {
            DataPesertaKelompok baru = new DataPesertaKelompok();
            baru.id_peserta = obj.id;
            baru.nama = obj.nama;
            data.add(baru);
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

    private void tampilkanDefault(){
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.list);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayoutRecords.removeAllViews();
        int pos = 0;
        int panjangData = data.size();
        for(int i=1; i <= jumlahRegu; i++){
            View lists = inflater.inflate(R.layout.list_lihatkel, null, false);
            TextView title = (TextView) lists.findViewById(R.id.title);
            title.setText("Kelompok ke-"+ i );
            LinearLayout listPeserta = (LinearLayout) lists.findViewById(R.id.listNama);

            for(int z = pos; z < panjangData; z++, pos++){

                if(data.get(z).no_urut != i)
                    break;


                TextView item = new TextView(this);
                item.setPadding(10, 10, 0, 0);
                item.setText( data.get(z).nama );
                item.setTextColor(Color.WHITE);
                listPeserta.addView(item);


            }
            linearLayoutRecords.addView(lists);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_kelompok);

        Bundle b = getIntent().getExtras();

        setTitle(b.getString("judul"));
        jumlahRegu = b.getInt("pembagi");
        id = b.getInt("id_kelompok");
        id_kelas = b.getInt("id_kelas");

        data = new ModelPeserta(this).ListByKelompok(id);

        tampilkanDefault();

        Button acak = (Button) findViewById(R.id.Acak);
        acak.setOnClickListener(new acak());

        Button simpan = (Button) findViewById(R.id.Simpan);
        simpan.setOnClickListener(new simpan());

        Button sesuikan = (Button) findViewById(R.id.Sesuaikan);
        sesuikan.setOnClickListener(new sesuaikan());
    }

}
