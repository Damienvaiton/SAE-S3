package com.example.saes3.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saes3.Model.ListData;
import com.example.saes3.R;

import java.text.DecimalFormat;

public class dataRecyclerAdapter extends RecyclerView.Adapter<dataRecyclerAdapter.ViewHolder> {
    private Context context;
    private ListData listData;

    public dataRecyclerAdapter(Context context) {
        this.context = context;
        listData = ListData.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.blocdata, parent, false));

    }

    /**
     * fill all the fields with the ArrayList of data
     * fields : temp, humidity, co2,o2,lux,temps
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat a = new DecimalFormat("##.###");
        holder.temp.setText(a.format(listData.recup_data(position).getTemperature()) + "°C");
        holder.humi.setText(a.format(listData.recup_data(position).getHumidite()) + "%");
        holder.co2.setText(a.format(listData.recup_data(position).getCO2()) + "%");
        holder.o2.setText(a.format(listData.recup_data(position).getO2()) + "%");
        holder.lux.setText(a.format(listData.recup_data(position).getLight()) + "L");
        holder.temps.setText(listData.recup_data(position).getTemps());
    }

    @Override
    public int getItemCount() {
        return listData.list_size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

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

