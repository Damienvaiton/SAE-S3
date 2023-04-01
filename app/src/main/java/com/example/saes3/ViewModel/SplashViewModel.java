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

        MutableLiveData<Boolean> listenerAppReady=new MutableLiveData<>();

    public LiveData<Boolean> getAppReady(){
        return listenerAppReady;
    }

        public void updateAppReady(boolean actif){
listenerAppReady.postValue(actif);
        }



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

    }
