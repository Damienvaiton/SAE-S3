package com.example.pageacceuil;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    Button myButton;
    TextView temp,lux,co2,o2,humi,temps;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        temp=itemView.findViewById(R.id.temp);
        lux=itemView.findViewById(R.id.lux);
        humi=itemView.findViewById(R.id.humi);
        o2=itemView.findViewById(R.id.o2);
        co2=itemView.findViewById(R.id.co2);
        temps=itemView.findViewById(R.id.heure);
    }

  //  public MyViewHolder(Context applicationContext, ListData listData) {
       // super();
    //}
}
