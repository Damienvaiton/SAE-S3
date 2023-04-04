package com.example.saes3.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saes3.Model.Data;
import com.example.saes3.Model.ListData;
import com.example.saes3.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class dataRecyclerAdapter extends RecyclerView.Adapter<dataRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Data> listData;
    //private int originalHeight;

    public dataRecyclerAdapter(Context context, ArrayList<Data> listData) {
        this.context = context;
        this.listData=listData;
        //  listData = ListData.getInstance();
      //  originalHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, originalHeight));
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
        holder.temp.setText(a.format(listData.get(position).getTemperature()) + "°C");
        holder.humi.setText(a.format(listData.get(position).getHumidite()) + "%");
        holder.co2.setText(a.format(listData.get(position).getCO2()) + "%");
        holder.o2.setText(a.format(listData.get(position).getO2()) + "%");
        holder.lux.setText(a.format(listData.get(position).getLight()) + "L");
        holder.temps.setText(listData.get(position).getTemps());
    }
       /* holder.itemView.setOnTouchListener(new View.OnTouchListener() {

            private float startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.itemView.setNestedScrollingEnabled(false);
                    System.out.println("yo");
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        // Record the starting Y position of the touch
                        startY = event.getY();
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        // Check if the swipe was large enough to expand the RecyclerView
                        float endY = event.getY();
                        System.out.println("yoded");
                        float distance = Math.abs(endY - startY);
                        if (distance > 5) {
                            System.out.println("lzlzllz");
                            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                            holder.itemView.setLayoutParams(layoutParams);
                        }
                    }
                    return false;
                }
            });  */


    @Override
    public int getItemCount() {
        return listData.size();
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

