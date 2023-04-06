package com.example.saes3.ViewModel;

import android.content.Context;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.saes3.AppApplication;


public class SplashViewModel extends ViewModel {

    boolean isWifiConn = false;
    boolean isMobileConn = false;

    /**
     * Send true if mobile was connected too internet
     */
        MutableLiveData<Boolean> listenerAppReady=new MutableLiveData<>();
    /**
     * Fallse if Firebase was not available
     */
    MutableLiveData<Boolean> listenerFirebaseReady=new MutableLiveData<>(true);
    public LiveData<Boolean> getAppReady(){
        return listenerAppReady;
    }

        public void updateAppReady(boolean actif){
listenerAppReady.postValue(actif);
        }



    public LiveData<Boolean> getFirebaseReady(){
        return listenerFirebaseReady;
    }

    public void updateFirebaseReady(boolean actif){
        listenerFirebaseReady.postValue(actif);
    }


    /**
     * Check if internet is available
     */
        public void checkCo() {
            ConnectivityManager connMgr =
                    (ConnectivityManager) AppApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            for (Network network : connMgr.getAllNetworks()) {
                NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn |=networkInfo.isConnected();
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    isWifiConn |=networkInfo.isConnected();
                }
                if(isMobileConn || isWifiConn){
                    updateAppReady(true);
                }
            }
        }

    public void echecFirebase() {
        updateFirebaseReady(false);
    }
}
