package com.example.pageacceuil.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.FirebaseAccess;

public class ConnectAdminViewModel extends ViewModel {

    private String[] tabID;
    private FirebaseAccess acces;


    public ConnectAdminViewModel() {
        acces = FirebaseAccess.getInstance();
        // acces.setConnetViewModel(ConnectAdminViewModel.this);
        tabID = acces.getAdminLog();
    }

    public boolean Verify(String username, String mdp) {
        System.out.println(tabID[0] + "edd" + tabID[1]);
        if ((tabID[0].equals(username)) && (tabID[1].equals(mdp))) {
            return true;
        }
        return false;
    }
}

