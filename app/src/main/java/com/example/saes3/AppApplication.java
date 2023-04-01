package com.example.saes3;

import android.app.Application;
import android.content.Context;

import com.example.saes3.Model.FirebaseAccess;


public class AppApplication extends Application {
    public static Context context;

        public void onCreate() {
            super.onCreate();
            AppApplication.context= getApplicationContext();
            FirebaseAccess database = FirebaseAccess.getInstance();
        }

        public static Context getAppContext() {
            return AppApplication.context;
        }


// ...
// Initialize Firebase Auth

        // je peux initialiser des données ici.
        //lancé une seule fois à l'init de l'app
        // pour repasser ici il faut killer l'application et la relancer
        // si on la passe en background et qu'on la relance on ne repasse pas ici

}
