package com.example.acer.randomapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.randomapplication.dataObject.DataKelompok;
import com.example.acer.randomapplication.database.ModelKelompok;

import java.util.List;

public class KelompokKelasActivity extends AppCompatActivity {

    int id_kelas;

    private void buatKelompok(String nama, int jumlahBagi){
        Intent intent = new Intent(KelompokKelasActivity.this, BuatKelompok.class);
        intent.putExtra("judul", nama);
        intent.putExtra("pembagi", jumlahBagi);
        intent.putExtra("id_kelas", id_kelas);
        startActivity(intent);
    }

    class opsiDaftarKelompok implements View.OnClickListener{

        int id, jumlahRegu;
        String nama;

        public opsiDaftarKelompok(int id, String nama, int jumlahRegu){
            this.id = id;
            this.nama = nama;
            this.jumlahRegu = jumlahRegu;
        }

        @Override
        public void onClick(View v) {
            final CharSequence[] items = { "Lihat Anggota", "Hapus" };
            final Context context = v.getRootView().getContext();
            final int id = this.id;
            final View view = v;

            new AlertDialog.Builder(context).setTitle("Opsi untuk kelompok "+ nama)
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item){
                                case 0 :
                                    Intent intent = new Intent(KelompokKelasActivity.this, LihatKelompokActivity.class);
                                    intent.putExtra("judul", nama);
                                    intent.putExtra("pembagi", jumlahRegu);
                                    intent.putExtra("id_kelompok", id);
                                    intent.putExtra("id_kelas", id_kelas);
                                    startActivity(intent);
                                    break ;
                                case 1 :
                                    boolean deleteSuccessful = new ModelKelompok(context).delete(id);
                                    if (deleteSuccessful){
                                        Toast.makeText(context, "Berhasil menghapus kelompok.", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(context, "Gagal menghapus kelompok.", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                            }
                            load();
                            dialog.dismiss();

                        }
                    }).show();
        }
    }
    class tambahKelompok implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            final Context context = v.getRootView().getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View formElementsView = inflater.inflate(R.layout.modals_input_kelompok, null, false);

            final EditText judul = (EditText) formElementsView.findViewById(R.id.nama);
            final EditText pembagi = (EditText) formElementsView.findViewById(R.id.jumlah);

            new AlertDialog.Builder(context)
                    .setView(formElementsView)
                    .setCustomTitle(Modals.getTitle(inflater, "Tambah Kelompok"))
                    .setPositiveButton("Buat",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String nama = judul.getText().toString();
                                    int jumlahBagi = (pembagi.getText().toString().isEmpty() ? 0 : Integer.parseInt(pembagi.getText().toString()));

                                    if(nama != "" && jumlahBagi > 0){
                                        buatKelompok(nama, jumlahBagi);
                                    } else {
                                        Toast.makeText(context, "Data input tidak valid !.", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();

                                }

                            }).show();
        }
    }

    public void load(){
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.list);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        linearLayoutRecords.removeAllViews();

        List<DataKelompok> data = new ModelKelompok(this).daftarKelompok_byKelas(id_kelas);

        if (data.size() > 0) {

            for (DataKelompok obj : data) {
                View lists = inflater.inflate(R.layout.list_kelompok_kelas, null, false);

                TextView namaKelompok = (TextView) lists.findViewById(R.id.nama_kelompok);
                TextView jumlahRegu = (TextView) lists.findViewById(R.id.jumkelompok);
                Button pilih = (Button) lists.findViewById(R.id.Pilih);

                namaKelompok.setText(obj.nama);
                jumlahRegu.setText(Integer.toString(obj.jumlahKelompok)+ " Regu");
                pilih.setOnClickListener(new opsiDaftarKelompok(obj.id, obj.nama, obj.jumlahKelompok));

                linearLayoutRecords.addView(lists);
            }

        }

        else {
            linearLayoutRecords.addView(inflater.inflate(R.layout.list_kosong, null, false));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelompok_kelas);
        Bundle b = getIntent().getExtras();
        id_kelas = b.getInt("id_kelas");
        setTitle("Daftar Kelompok "+ b.getString("nama_kelas"));

        ini = this;

        Button tambah = (Button) findViewById(R.id.TambahKelompok);
        tambah.setOnClickListener(new tambahKelompok());

        load();
    }

    static KelompokKelasActivity ini;

    public static KelompokKelasActivity getInstance() {
        return ini;
    }
}
