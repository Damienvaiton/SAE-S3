package com.example.saes3.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.example.saes3.R;

public class GuideInstaView extends AppCompatActivity {
Button scrollGraph;
Button scrollEsp;
Button scrollAcueil;
ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_insta_view);
        scrollView=  findViewById(R.id.scrollView);
        scrollAcueil=findViewById(R.id.btn_scroll_home);
        scrollEsp=findViewById(R.id.btn_scroll_esp);
        scrollEsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 600);
            }
        });
        scrollAcueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        scrollView.smoothScrollTo(0, 2425);
                    }
                });
        scrollGraph=findViewById(R.id.btn_scroll_graph);
        scrollGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 3500);
            }
        });
    }
}