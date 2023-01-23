package com.example.pageacceuil;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

public class MyViewHolder extends RecyclerView.ViewHolder {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button myButton;
    TextView temp,lux,co2,o2,humi,temps;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        myButton = itemView.findViewById(R.id.btn);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
             /*   System.out.println("yo"+position +" ");
                Pop_up deletePopup = new Pop_up(this.);
                deletePopup.build("Supprimer l'entrée? " + position);
                deletePopup.getYesButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference myRef = database.getReference("SAE_S3_BD/ESP32/Mesure/" + position );
                        myRef.child(choixESP).removeValue(position);
                        spinner.getAdapter().notify();
                        //  choixESP=spinner.g
                        deletePopup.dismiss();
                    }
                });
                deletePopup.getNoButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deletePopup.dismiss();
                    }
                });
                break;
            }*/
            }
        });

        //  public MyViewHolder(Context applicationContext, ListData listData) {
        // super();
        //}
    }
}
