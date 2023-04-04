package com.example.saes3;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.saes3.Model.FirebaseAccess;

import java.lang.ref.WeakReference;


public class AppApplication extends Application implements Application.ActivityLifecycleCallbacks {
    public static Context context;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private static AppApplication sInstance;
    private static WeakReference<Activity> mCurrentActivityRef;
        public void onCreate() {
            super.onCreate();
            AppApplication.context= getApplicationContext();
            FirebaseAccess database = FirebaseAccess.getInstance();
            listenerCo();
            FirebaseAccess.getInstance().testFirebase();
            sInstance = this;
            registerActivityLifecycleCallbacks(this);
        }

        public static Context getAppContext() {
            return AppApplication.context;
        }

    public void listenerCo() {
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                // network available
            }

            @Override
            public void onLost(Network network) {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder pop = new AlertDialog.Builder(AppApplication.getCurrentActivity());
                        pop.setMessage("Connexion à internet perdue, reconnectez vous");
                        pop.setCancelable(false);
                        pop.setPositiveButton("Fait", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        pop.show();
                    }
                });
            }
        };

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }
    }


    public static AppApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mCurrentActivityRef = new WeakReference<>(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        mCurrentActivityRef = new WeakReference<>(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mCurrentActivityRef = new WeakReference<>(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    public static Activity getCurrentActivity() {
        if (mCurrentActivityRef != null) {
            return mCurrentActivityRef.get();
        }
        return null;
    }
}
// ...
// Initialize Firebase Auth

        // je peux initialiser des données ici.
        //lancé une seule fois à l'init de l'app
        // pour repasser ici il faut killer l'application et la relancer
        // si on la passe en background et qu'on la relance on ne repasse pas ici

