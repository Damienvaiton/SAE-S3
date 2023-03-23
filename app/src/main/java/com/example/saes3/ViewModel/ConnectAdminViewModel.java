package com.example.saes3.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.saes3.Model.FirebaseAccess;

public class ConnectAdminViewModel extends ViewModel {
    /**
     * Tab of string who contains username in [0] and password in [1]
     */
    private String[] tabID;
    /**
     * Instance of FirebaseAcces
     */
    private FirebaseAccess acces;


    /**
     * Constructor who initialize and fill tabID by calling 'getAdminLog' of FirebaseAccess
     */
    public ConnectAdminViewModel() {
        acces = FirebaseAccess.getInstance();
        tabID = acces.getAdminLog();
    }

    /**
     * @param username Text in EditText on the view
     * @param mdp      Text in EditText on the view
     * @return a boolean in connexion is valid or not
     * Verify if the username and password are the same in database and this wrote by users
     */
    public boolean Verify(String username, String mdp) {
        System.out.println(tabID[0] + "edd" + tabID[1]);
        if ((tabID[0].equals(username)) && (tabID[1].equals(mdp))) {
            return true;
        }
        return false;
    }
}

