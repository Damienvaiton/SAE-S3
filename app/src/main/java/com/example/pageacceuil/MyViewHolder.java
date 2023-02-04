package com.example.pageacceuil;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

public class MyViewHolder extends RecyclerView.ViewHolder {

    //private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //Button myButton;
    TextView temp, lux, co2, o2, humi, temps;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        temp = itemView.findViewById(R.id.temp);
        lux = itemView.findViewById(R.id.lux);
        humi = itemView.findViewById(R.id.humi);
        o2 = itemView.findViewById(R.id.o2);
        co2 = itemView.findViewById(R.id.co2);
        temps = itemView.findViewById(R.id.heure);
    }
}


