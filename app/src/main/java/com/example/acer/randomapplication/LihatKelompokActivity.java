package com.example.acer.randomapplication;

<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LihatKelompokActivity extends AppCompatActivity {

=======
import android.content.Context;
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
import com.example.acer.randomapplication.database.ModelKelompok;
import com.example.acer.randomapplication.database.ModelPeserta;

import java.util.List;

public class LihatKelompokActivity extends AppCompatActivity {

    List<DataPeserta> data;
    int jumlahRegu;
    int id;

    private void acak(){
        int randomVal, range = data.size() - 1;
        DataPeserta tmp;
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

    private void tampilkanPeserta(){
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.list);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayoutRecords.removeAllViews();

        int jumlahNormal = data.size() / jumlahRegu;
        int ii = 0, kelebihan;
        for(int i=1, j = data.size() % jumlahRegu; i <= jumlahRegu; i++, j--){
            kelebihan = (j>0 ? 1 : 0);

            View lists = inflater.inflate(R.layout.list_coba, null, false);
            TextView title = (TextView) lists.findViewById(R.id.title);
            title.setText("Kelompok ke-"+ i );

            LinearLayout listPeserta = (LinearLayout) lists.findViewById(R.id.listNama);
            for(int z = ii; z < ii+jumlahNormal+kelebihan; z++){
                TextView item = new TextView(this);
                item.setPadding(10, 10, 0, 0);
                item.setText( data.get(z).nama );
                listPeserta.addView(item);
            }
            ii = ii+jumlahNormal+kelebihan;

            linearLayoutRecords.addView(lists);
        }
    }

>>>>>>> eab183843e5415b524c68b8c4b967c7758798a10
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_kelompok);
<<<<<<< HEAD
    }
=======
        Bundle b = getIntent().getExtras();

        setTitle(b.getString("judul"));
        jumlahRegu = b.getInt("pembagi");
        id = b.getInt("id_kelompok");

        data = new ModelPeserta(this).ListByKelompok(id);

        tampilkanPeserta();

        Button acak = (Button) findViewById(R.id.Acak);
        acak.setOnClickListener(new acak());

        Button simpan = (Button) findViewById(R.id.Simpan);
        simpan.setOnClickListener(new simpan());
    }


>>>>>>> eab183843e5415b524c68b8c4b967c7758798a10
}
