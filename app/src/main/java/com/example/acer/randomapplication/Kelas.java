package com.example.acer.randomapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    class tambahKelas implements View.OnClickListener{
        @Override
        public void onClick(final View view) {
            final Context context = view.getRootView().getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View formElementsView = inflater.inflate(R.layout.modals_input_kelas, null, false);
            final EditText nama = (EditText) formElementsView.findViewById(R.id.nama);

            new AlertDialog.Builder(context)
                    .setView(formElementsView)
                    .setTitle("Kelas Baru")
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

                                    dialog.cancel();
                                }

                            }).show();
        }
    }
    class OpsiDaftarKelas implements View.OnClickListener{
        int id;
        public OpsiDaftarKelas(int id){
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            final CharSequence[] items = { "Detail", "Hapus" };
            final Context context = v.getRootView().getContext();
            final int id = this.id;
            final View view = v;

            new AlertDialog.Builder(context).setTitle("Student Record")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            if(item == 1){
                                boolean deleteSuccessful = new ModelKelas(context).delete(id);

                                if (deleteSuccessful){
                                    Toast.makeText(context, "Berhasil menghapus kelas.", Toast.LENGTH_SHORT).show();
                                    load();
                                }else{
                                    Toast.makeText(context, "Gagal menghapus kelas.", Toast.LENGTH_SHORT).show();
                                }
                            }

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
                Button opsi = (Button) lists.findViewById(R.id.opsi);

                opsi.setOnClickListener(new OpsiDaftarKelas(obj.id));

                nama.setText(obj.nama);

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

        this.v = view;
        load();
        return view;
    }



}
