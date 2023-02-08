package com.example.pageacceuil;

import java.util.ArrayList;

public class GroupeESP {
    private String nomGroupe;
    private ArrayList<ESP> espList;

    public GroupeESP(String nom) {
        nomGroupe = nom;
        espList = new ArrayList<>();
    }

    public GroupeESP(String nom, ArrayList<ESP> espList) {
        nomGroupe = nom;
        this.espList = espList;
    }

    public void addESP(ESP esp) {
        espList.add(esp);
    }

    public String getNomGroupe() {
        return nomGroupe;
    }

    public ArrayList<ESP> getEspList() {
        return espList;
    }

    public void modifyNomGroupe(String nom) {
        nomGroupe = nom;
    }

    public void supprimerESP(ESP esp) {
        espList.remove(esp);
    }

    public void supprimerESPNom(String nomESP) {
        for (ESP i : espList) {
            if (i.getNomEsp().equals(nomESP)) {
                espList.remove(i);
            }
        }
    }

    public void supprimerESPmac(String macESP) {
        for (ESP i : espList) {
            if (i.getMacEsp().equals(macESP)) {
                espList.remove(i);
            }
        }
    }

    public ESP getESP(String nomESP) {
        for (ESP i : espList) {
            if (i.getNomEsp().equals(nomESP)) {
                return i;
            }
        }
        return null;
    }

    public ESP getESPmac(String macESP) {
        for (ESP i : espList) {
            if (i.getMacEsp().equals(macESP)) {
                return i;
            }
        }
        return null;
    }

    public void modifTauxRafrechissement(long taux) {
        for (ESP i : espList) {
            i.setTauxRafrai(taux);
        }
    }

    public long getTauxRafrechissement(String macESP) {
        return getESPmac(macESP).getTauxRafrai();
    }
}










