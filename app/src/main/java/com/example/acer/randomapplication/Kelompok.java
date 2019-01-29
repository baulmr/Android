package com.example.acer.randomapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.randomapplication.dataObject.DataKelompok;
import com.example.acer.randomapplication.database.ModelKelompok;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Kelompok extends Fragment {

    View v;
    Context c;

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
                        Intent intent = new Intent(getActivity(), LihatKelompokActivity.class);
                        intent.putExtra("judul", nama);
                        intent.putExtra("pembagi", jumlahRegu);
                        intent.putExtra("id_kelompok", id);
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

    public Kelompok() {
        // Required empty public constructor
    }

    public void load(){
        LinearLayout linearLayoutRecords = (LinearLayout) v.findViewById(R.id.ListKelompok);
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        linearLayoutRecords.removeAllViews();

        List<DataKelompok> data = new ModelKelompok(c).daftarKelompok();

        if (data.size() > 0) {

            for (DataKelompok obj : data) {
                View lists = inflater.inflate(R.layout.list_kelompok, null, false);

                TextView namaKelompok = (TextView) lists.findViewById(R.id.nama_kelompok);
                TextView namaKelas = (TextView) lists.findViewById(R.id.Namakelas);
                TextView jumlahRegu = (TextView) lists.findViewById(R.id.jumkelompok);
                Button pilih = (Button) lists.findViewById(R.id.Pilih);

                namaKelompok.setText(obj.nama);
                namaKelas.setText(obj.nama_kelas);
                jumlahRegu.setText(Integer.toString(obj.jumlahKelompok)+ " Regu");
                pilih.setOnClickListener(new opsiDaftarKelompok(obj.id, obj.nama, obj.id_kelas));

                linearLayoutRecords.addView(lists);
            }

        }

        else {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kelompok, container, false);
        getActivity().setTitle("Kelompok");

        this.v = view;
        this.c = view.getRootView().getContext();
        load();

        return view;


    }

}
