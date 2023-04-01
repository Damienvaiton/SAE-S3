package com.example.saes3.View;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
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

    boolean ready=false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        splashViewModel.getAppReady().observe(this, aBoolean -> ready=aBoolean);

        splash = findViewById(R.id.imageplante);
        text = findViewById(R.id.nameAPP);





        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animimgsplashscreen);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.animtxtsplashscreen);


        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        animation2.setStartOffset(1000);


        AnimationSet animationSet = new AnimationSet(true);

        animationSet.addAnimation(animation);
        animationSet.addAnimation(scaleAnimation);


        splash.startAnimation(animationSet);
        text.startAnimation(animation2);


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
pop.setCancelable(false);

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
        },3000);

    }

    public void transi() {
        startActivity(new Intent(this, AccueilActivity.class));
        finish();
    }

}
