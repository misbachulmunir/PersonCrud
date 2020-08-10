package com.kubangkangkung.voleyproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kubangkangkung.voleyproject.Activity.DetailActiviity;
import com.kubangkangkung.voleyproject.Model.ModelPerson;
import com.kubangkangkung.voleyproject.R;

import org.parceler.Parcels;

import java.util.List;

public class AdapterPerson extends RecyclerView.Adapter<AdapterPerson.HolderData> {
    public static final String DATA_PERSON = "DATA PERSON";
    public static final String DATA_EXTRA = "DATA EXTRA";
    private Context context;
    List<ModelPerson> lisdata;

    public AdapterPerson(Context context, List<ModelPerson> lisdata) {
        this.context = context;
        this.lisdata = lisdata;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_person,parent,false);
        HolderData holder=new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, final int position) {
        ModelPerson dm = lisdata.get(position);
        holder.teknama.setText(dm.getNama());
        holder.tekstelepon.setText(dm.getPhone());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent pindah = new Intent(context,DetailActiviity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable(DATA_PERSON, Parcels.wrap(lisdata.get(position)));
                pindah.putExtra(DATA_EXTRA,bundle);
                context.startActivity(pindah);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lisdata.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView teknama,tekstelepon;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            teknama =itemView.findViewById(R.id.id_namanya);
            tekstelepon =itemView.findViewById(R.id.id_phonena);
        }
    }
}
