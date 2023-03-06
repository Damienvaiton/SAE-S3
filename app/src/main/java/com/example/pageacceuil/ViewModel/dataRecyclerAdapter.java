package com.example.pageacceuil.ViewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example.pageacceuil.Model.ListData;

public class dataRecyclerAdapter extends RecyclerView.Adapter<dataRecyclerAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Data> listData;

    public dataRecyclerAdapter(Context context, ArrayList<Data> listData) {
this.context=context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.blocdata, parent, false));

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat a = new DecimalFormat("##.###");
        holder.temp.setText(a.format(listData.get(position).getTemperature()) + "°C");
        holder.humi.setText(a.format(listData.get(position).getHumidite()) + "%");
        holder.co2.setText(a.format(listData.get(position).getCO2()) + "%");
        holder.o2.setText(a.format(listData.get(position).getO2()) + "%");
        holder.lux.setText(a.format(listData.get(position).getLight()) + "lux");
        holder.temps.setText(listData.get(position).getTemps());
    }

    @Override

    public int getItemCount() {
        return listData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        //private final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Button myButton;
        TextView temp, lux, co2, o2, humi, temps;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            temp = itemView.findViewById(R.id.temp);
            lux = itemView.findViewById(R.id.lux);
            humi = itemView.findViewById(R.id.humi);
            o2 = itemView.findViewById(R.id.o2);
            co2 = itemView.findViewById(R.id.co2);
            temps = itemView.findViewById(R.id.heure);
        }
    }
}

