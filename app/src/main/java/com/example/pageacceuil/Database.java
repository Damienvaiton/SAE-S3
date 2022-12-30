package com.example.pageacceuil;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final String idESP;
    private final DatabaseReference tableESP;



    public Database(String idESP, DatabaseReference tableESP) {
        this.idESP = idESP;
        this.tableESP = tableESP;
    }

}
