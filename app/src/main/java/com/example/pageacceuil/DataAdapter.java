package com.example.pageacceuil;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<MyViewHolder>{

Context context;

    private ListData listData;

    public DataAdapter(Context context,ListData listData) {
        this.context = context;
        this.listData=listData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.blocdata,parent,false));

    }

    public void setFirst(@NonNull MyViewHolder holder){
        holder.temp.setText("Température");
        holder.humi.setText("Humidité");
        holder.co2.setText("CO2");
        holder.o2.setText("02");
        holder.lux.setText("lux");
        holder.temps.setText("Heure");
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.temp.setText((listData.recup_data(position).getTemperature()) + "°");
            holder.humi.setText((listData.recup_data(position).getHumidite()) + "%");
            holder.co2.setText((listData.recup_data(position).getCO2()) + "%");
            holder.o2.setText((listData.recup_data(position).getO2()) + "%");
            holder.lux.setText((listData.recup_data(position).getLux()) + "lux");
            holder.temps.setText((listData.recup_data(position).getTemps()) + "h");
    }

    @Override
    public int getItemCount() {
        return listData.list_size();
    }
}
