package com.example.saes3.Model;


public class ESP {
    private static volatile ESP instance;
    private String macEsp;
    private String nomEsp;

    public ESP(String macEsp, String nomEsp) {
        this.macEsp = macEsp;
        this.nomEsp = nomEsp;
        instance = this;
    }

    public static ESP getInstance() {

        ESP result = instance;
        if (result != null) {
            return result;
        }
        return null;
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

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        FirebaseAccess.getInstance().deleteListener();
        //remove listener
    }
}
