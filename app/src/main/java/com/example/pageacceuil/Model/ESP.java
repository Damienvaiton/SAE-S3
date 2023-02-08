package com.example.pageacceuil.Model;


public class ESP {
    String macEsp;
    String nomEsp;
    String tauxRafrai;
    FirebaseAccess database = FirebaseAccess.getInstance();

    private static volatile ESP instance;


    public static ESP getInstance() {

        ESP result = instance;
        if (result != null) {
            return result;
        }
        return null;
    }
    public ESP(String macEsp, String nomEsp) {
        this.macEsp = macEsp;
        this.nomEsp = nomEsp;
        database.getTimeListener(this) ;
        instance=this;
    }

    public String getMacEsp() {
        return macEsp;
    }

    public void setMacEsp(String macEsp) {
        this.macEsp = macEsp;
    }

    public String getNomEsp() {
        return nomEsp;
    }

    public void setNomEsp(String nomEsp) {
        this.nomEsp = nomEsp;
    }

    public String getTauxRafrai() {
        return tauxRafrai;
    }

    public void setTauxRafrai(String tauxRafrai) {
        this.tauxRafrai = tauxRafrai;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        //remove listener
    }
}
