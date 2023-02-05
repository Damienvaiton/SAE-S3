package com.example.pageacceuil;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/*

public class ESP {
    String macEsp;
    String nomEsp;
    Long tauxRafrai;
    FirebaseAcces databas=FirebaseAcces.getInstance();

    public ESP(String macEsp, String nomEsp) {
        this.macEsp = macEsp;
        this.nomEsp = nomEsp;
        this.tauxRafrai = databas.getTimeListener(macEsp) ;
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

    public Long getTauxRafrai() {
        return tauxRafrai;
    }

    public void setTauxRafrai(Long tauxRafrai) {
        this.tauxRafrai = tauxRafrai;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        //remove listener
    }
}
*/
public class ESP implements Parcelable {
    String macEsp;
    String nomEsp;
    Long tauxRafrai;
    FirebaseAcces databas=FirebaseAcces.getInstance();

    public ESP(String macEsp, String nomEsp) {
        this.macEsp = macEsp;
        this.nomEsp = nomEsp;
//        this.tauxRafrai = databas.getTimeListener(macEsp) ;
    }

    protected ESP(Parcel in) {
        macEsp = in.readString();
        nomEsp = in.readString();
        if (in.readByte() == 0) {
            tauxRafrai = null;
        } else {
            tauxRafrai = in.readLong();
        }
    }

    public static final Creator<ESP> CREATOR = new Creator<ESP>() {
        @Override
        public ESP createFromParcel(Parcel in) {
            return new ESP(in);
        }

        @Override
        public ESP[] newArray(int size) {
            return new ESP[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(macEsp);
        dest.writeString(nomEsp);
        if (tauxRafrai == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(tauxRafrai);
        }
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

    public Long getTauxRafrai() {
        return tauxRafrai;
    }

    public void setTauxRafrai(Long tauxRafrai) {
        this.tauxRafrai = tauxRafrai;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        //remove listener
    }
}