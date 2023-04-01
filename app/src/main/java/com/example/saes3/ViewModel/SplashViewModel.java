package com.example.saes3.ViewModel;

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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.saes3.AppApplication;
import com.example.saes3.R;

public class SplashViewModel extends ViewModel {

        MutableLiveData<Boolean> getWifiCo=new MutableLiveData<>();
    MutableLiveData<Boolean> getMobileCo=new MutableLiveData<>();

    public LiveData<Boolean> getWifiStatus(){
        return getWifiCo;
    }
    public LiveData<Boolean> getMobileStatus(){
        return getMobileCo;
    }
        public void updateWifiCo(boolean actif){
getWifiCo.postValue(actif);
        }
        public void updateMobileCo(boolean actif){
getMobileCo.postValue(actif);
        }


        public void checkCo() {
            ConnectivityManager connMgr =
                    (ConnectivityManager) AppApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            for (Network network : connMgr.getAllNetworks()) {
                NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                   updateWifiCo(networkInfo.isConnected());
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    updateMobileCo(networkInfo.isConnected());
                }
            }
        }

    }
