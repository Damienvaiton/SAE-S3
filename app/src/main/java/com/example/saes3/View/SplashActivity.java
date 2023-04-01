package com.example.saes3.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.saes3.AppApplication;
import com.example.saes3.R;
import com.example.saes3.ViewModel.GraphViewModel;
import com.example.saes3.ViewModel.SplashViewModel;

public class SplashActivity extends AppCompatActivity {
    SplashViewModel splashViewModel=null;

    boolean ready=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        splashViewModel.getAppReady().observe(this, aBoolean -> ready=aBoolean);



    }

    @Override
    protected void onStart() {
        super.onStart();
        splashViewModel.checkCo();
        Handler handlerTest=new Handler();
        handlerTest.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ready) {
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
                    pop.setPositiveButton("Fait", (dialog, which) -> {
                        splashViewModel.checkCo();
                        if (ready) {
                            dialog.cancel();
                            transi();

                        } else {
                            pop.show();
                        }
                    });
                    pop.show();
                }
            }
        },1000);

    }

    public void transi() {
        startActivity(new Intent(this, AccueilActivity.class));
        finish();
    }

}
