package com.example.pageacceuil.ViewModel;

import androidx.annotation.Nullable;

import com.example.pageacceuil.Model.Data;


public class ClassTransitoireViewModel {
    private GraphViewModel graphViewModel;
    private SettingsAdminViewModel settingsAdminViewModel;
    private SettingsEtuViewModel settingsEtuViewModel;


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

    public GraphViewModel getGraphViewModel() {
        return graphViewModel;
    }

    public void setGraphViewModel(GraphViewModel graphViewModel) {
        this.graphViewModel = graphViewModel;
    }

    public SettingsAdminViewModel getSettingsAdminViewModel() {
        return settingsAdminViewModel;
    }

    public void setSettingsAdminViewModel(SettingsAdminViewModel settingsAdminViewModel) {
        this.settingsAdminViewModel = settingsAdminViewModel;
    }
    public void updateRefresh(String s){
        if(graphViewModel!=null){
            graphViewModel.updateRefresh(s);
        }
        if(settingsAdminViewModel!=null){
            settingsAdminViewModel.updateRefresh(s);
        }
        if(settingsEtuViewModel!=null){
            settingsEtuViewModel.updateRefresh(s);
        }
    }
    public void updateData(Data data){
        if(graphViewModel!=null){
            graphViewModel.updateData(data);
        }
    }
    public void ajoutESP(String esp, @Nullable String nom){
        System.out.println("ajout ESP");
        if(accueilViewModel!=null){
            accueilViewModel.addESP(esp,nom);
        }
        if(settingsAdminViewModel!=null){
            settingsAdminViewModel.addESP(esp,nom);
        }
    }
    public void suppESP(String esp){
        if(accueilViewModel!=null){
            accueilViewModel.deleteESP(esp);
        }
        if(settingsAdminViewModel!=null){
            settingsAdminViewModel.deleteESP(esp);
        }
    }
    public void creationESP(int pos){
        if(accueilViewModel!=null){
            accueilViewModel.creaESP(pos);
        }
       /* if(settingsAdminViewModel!=null){
            settingsAdminViewModel.creaESP(pos);
        }*/
    }


}
