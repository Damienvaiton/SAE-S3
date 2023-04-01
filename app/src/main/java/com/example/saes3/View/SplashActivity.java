package com.example.saes3.View;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.saes3.AppApplication;
import com.example.saes3.R;
import com.example.saes3.ViewModel.SplashViewModel;

public class SplashActivity extends AppCompatActivity {

    ImageView splash;

    TextView text;
    SplashViewModel splashViewModel=null;
    boolean isWifiConn = false;
    boolean isMobileConn = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        splash = findViewById(R.id.imageplante);
        text = findViewById(R.id.nameAPP);

        splashViewModel.getMobileStatus().observe(this, aBoolean -> isMobileConn=aBoolean);
        splashViewModel.getWifiStatus().observe(this, aBoolean -> isWifiConn=aBoolean);


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animimgsplashscreen);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.animtxtsplashscreen);
        splash.startAnimation(animation);
        text.startAnimation(animation2);


    }

    @Override
    protected void onStart() {
        super.onStart();
        splashViewModel.checkCo();
        if (isWifiConn || isMobileConn) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    transi();
                }
            }, 1000);
        } else {
            AlertDialog.Builder pop = new AlertDialog.Builder(AppApplication.getCurrentActivity());
            pop.setMessage("Merci de vous connectez à internet");
            pop.setPositiveButton("Fait", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (isMobileConn || isWifiConn) {
                        dialog.cancel();
                        transi();

                    } else {
                        pop.show();
                    }
                }
            });
            pop.show();
        }
    }

    public void transi() {
        startActivity(new Intent(this, AccueilActivity.class));
        finish();
    }

}
