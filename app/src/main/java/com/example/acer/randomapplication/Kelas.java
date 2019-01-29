package com.example.acer.randomapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.randomapplication.dataObject.DataKelas;
import com.example.acer.randomapplication.database.ModelKelas;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Kelas extends Fragment {
    View v;

<<<<<<< HEAD
=======
    public void coba(final View view, int id_kelas){
        final Context context = view.getRootView().getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View formElementsView = inflater.inflate(R.layout.modals_input_kelompok, null, false);
        final View formTitle = inflater.inflate(R.layout.modals_title_kelompok, null, false);

        final EditText judul = (EditText) formElementsView.findViewById(R.id.nama);
        final EditText pembagi = (EditText) formElementsView.findViewById(R.id.jumlah);
        final int kelas = id_kelas;

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setCustomTitle(formTitle)
                .setPositiveButton("Buat",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String nama = judul.getText().toString();
                                int jumlahBagi = (pembagi.getText().toString().isEmpty() ? 0 : Integer.parseInt(pembagi.getText().toString()));

                                if(nama != "" && jumlahBagi > 0){
                                    Intent intent = new Intent(getActivity(), LihatKelompokActivity.class);
                                    intent.putExtra("judul", nama);
                                    intent.putExtra("pembagi", jumlahBagi);
                                    intent.putExtra("id_kelas", kelas);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(context, "Data input tidak valid !.", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();

                            }

                        }).show();
    }

>>>>>>> eab183843e5415b524c68b8c4b967c7758798a10
    class tambahKelas implements View.OnClickListener{
        @Override
        public void onClick(final View view) {
            final Context context = view.getRootView().getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View formElementsView = inflater.inflate(R.layout.modals_input_kelas, null, false);
            final View formTitle = inflater.inflate(R.layout.modals_title_kelas, null, false);

            final EditText nama = (EditText) formElementsView.findViewById(R.id.nama);

            new AlertDialog.Builder(context)
                    .setView(formElementsView)
                    .setCustomTitle(formTitle)
                    .setPositiveButton("Simpan",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String namanya = nama.getText().toString();
                                    boolean createSuccessful = new ModelKelas(context).create(namanya);

                                    if(createSuccessful){
                                        Toast.makeText(context, "Kelas "+ namanya +" telah ditambahkan.", Toast.LENGTH_SHORT).show();
                                        load();
                                    }else{
                                        Toast.makeText(context, "Gagal menambahkan kelas.", Toast.LENGTH_SHORT).show();
                                    }
<<<<<<< HEAD

                                    dialog.cancel();
                                }

                            }).show();
        }
    }
    class OpsiDaftarKelas implements View.OnClickListener{
        int id;
        String namaKelas;
        public OpsiDaftarKelas(int id, String nama){
            this.id = id;
            namaKelas = nama;
        }
        @Override
        public void onClick(View v) {
            final CharSequence[] items = { "Lihat Peserta", "Lihat Kelompok", "Hapus Kelas" };
            final Context context = v.getRootView().getContext();
            final int id = this.id;
            final View view = v;

=======

                                    dialog.cancel();
                                }

                            }).show();
        }
    }
    class OpsiDaftarKelas implements View.OnClickListener{
        int id;
        String namaKelas;
        public OpsiDaftarKelas(int id, String nama){
            this.id = id;
            namaKelas = nama;
        }
        @Override
        public void onClick(View v) {
            final CharSequence[] items = { "Lihat Peserta", "Lihat Kelompok", "Hapus Kelas" };
            final Context context = v.getRootView().getContext();
            final int id = this.id;
            final View view = v;

>>>>>>> eab183843e5415b524c68b8c4b967c7758798a10
            new AlertDialog.Builder(context).setTitle("Opsi untuk kelas "+ namaKelas)
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            Intent intent;
                            switch (item){
                                case 0 :
                                    intent = new Intent(getActivity(), DaftarPesertaActivity.class);
                                    intent.putExtra("id_kelas", id);
                                    startActivity(intent);
                                    break ;
                                case 1 :
<<<<<<< HEAD
=======
//                                    coba(view, id);
>>>>>>> eab183843e5415b524c68b8c4b967c7758798a10
                                    break;
                                case 2 :
                                    boolean deleteSuccessful = new ModelKelas(context).delete(id);
                                    if (deleteSuccessful){
                                        Toast.makeText(context, "Berhasil menghapus kelas.", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(context, "Gagal menghapus kelas.", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                            }
                            load();
                            dialog.dismiss();

                        }
                    }).show();
        }
    }

    public Kelas() {
        // Required empty public constructor
    }

    public void load(){
        Context context = v.getRootView().getContext();
        LinearLayout linearLayoutRecords = (LinearLayout) v.findViewById(R.id.list);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        linearLayoutRecords.removeAllViews();

        List<DataKelas> data = new ModelKelas(context).read();

        if (data.size() > 0) {

            for (DataKelas obj : data) {
                View lists = inflater.inflate(R.layout.list_kelas, null, false);
                TextView nama = (TextView) lists.findViewById(R.id.nama);
                TextView jumlahP = (TextView) lists.findViewById(R.id.jumlah_peserta);
                Button opsi = (Button) lists.findViewById(R.id.opsi);

                opsi.setOnClickListener(new OpsiDaftarKelas(obj.id, obj.nama));

                nama.setText(obj.nama);
                jumlahP.setText(obj.jumlahPeserta + " Peserta");

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
        View view = inflater.inflate(R.layout.fragment_kelas, container, false);
        getActivity().setTitle("Kelas");

        Button tambah = (Button) view.findViewById(R.id.tambah);
        tambah.setOnClickListener(new tambahKelas());

<<<<<<< HEAD
        kelas = this;       this.v = view;
=======
        kelas = this;
        this.v = view;

>>>>>>> eab183843e5415b524c68b8c4b967c7758798a10
        load();
        return view;
    }

    static Kelas kelas;

    public static Kelas getInstance() {
        return kelas;
    }

}
