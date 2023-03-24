package com.example.pageacceuil.Model;


public class ESP {
    private String macEsp;
    private String nomEsp;

    private static ESP instance;


    public static ESP getInstance() {

        ESP result = instance;
        if (result != null) {
            return result;
        }
        return null;
    }
    public ESP(String macEsp, String nomEsp) {
        instance=this;
        redefinition(macEsp,nomEsp);
    }

    public void redefinition(String macEsp, String nomEsp){
        this.macEsp = macEsp;
        this.nomEsp = nomEsp;
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
