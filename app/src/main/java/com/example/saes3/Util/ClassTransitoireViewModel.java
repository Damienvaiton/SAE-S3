package com.example.saes3.Util;

import android.os.Handler;

import androidx.annotation.Nullable;

import com.example.saes3.Model.Data;
import com.example.saes3.ViewModel.AccueilViewModel;
import com.example.saes3.ViewModel.GraphViewModel;
import com.example.saes3.ViewModel.SettingsAdminViewModel;
import com.example.saes3.ViewModel.SettingsEtuViewModel;
import com.example.saes3.ViewModel.SplashViewModel;

public class ClassTransitoireViewModel {

    private GraphViewModel graphViewModel;
    private SettingsAdminViewModel settingsAdminViewModel;
    private SettingsEtuViewModel settingsEtuViewModel;
    private SplashViewModel splashViewModel;
    private AccueilViewModel accueilViewModel;

    private static ClassTransitoireViewModel instance;

    public static ClassTransitoireViewModel getInstance() {
        ClassTransitoireViewModel result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ClassTransitoireViewModel.class) {
            if (instance == null) {
                instance = new ClassTransitoireViewModel();
            }
            return instance;
        }
    }

    public void setAccueilViewModel(AccueilViewModel accueilViewModel) {
        this.accueilViewModel = accueilViewModel;
    }
    public void setSettingsEtuViewModel(SettingsEtuViewModel settingsEtuViewModel) {
        this.settingsEtuViewModel=settingsEtuViewModel;
    }

    public void setGraphViewModel(GraphViewModel graphViewModel) {
        this.graphViewModel = graphViewModel;
    }

    public void setSettingsAdminViewModel(SettingsAdminViewModel settingsAdminViewModel) {
        this.settingsAdminViewModel = settingsAdminViewModel;
    }

    public void updateRefresh(String s) {
        if (graphViewModel != null) {
            graphViewModel.updateRefresh(s);
        }
        if (settingsAdminViewModel != null) {
            settingsAdminViewModel.updateRefresh(s);
        }
        if(settingsEtuViewModel!=null){
            settingsEtuViewModel.updateRefresh(s);
        }
    }

    public void updateData(Data data) {
        if (graphViewModel != null) {
            graphViewModel.updateData(data);
        }

    }
    public void ajoutESP(String esp, @Nullable String nom){
        if(accueilViewModel!=null){
            accueilViewModel.addESP(esp,nom);
        }
        if (settingsAdminViewModel != null) {
            settingsAdminViewModel.addESP(esp, nom);
        }
    }
    public void suppESP(String esp){
        if(accueilViewModel!=null){
            accueilViewModel.deleteESP(esp);
        }
        if (settingsAdminViewModel != null) {
            settingsAdminViewModel.deleteESP(esp);
        }
    }

    public void echecFirebase() {
        if (splashViewModel != null) {
            splashViewModel.echecFirebase();
        }

    }
}
