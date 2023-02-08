package com.example.pageacceuil.ViewModel;

import android.app.Activity;
import android.app.Dialog;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pageacceuil.R;

public class PopUpDialog extends Dialog {

    private final TextView titre;
    private final EditText valeur;
    private final Button yesButton;
    private final Button noButton;

    public PopUpDialog(Activity activity) {
        super(activity, com.google.android.material.R.style.Theme_AppCompat_Dialog);

        setContentView(R.layout.pop_up_add);

        this.valeur = findViewById(R.id.pop_txt_value);
        this.titre = findViewById(R.id.pop_titre);
        this.yesButton = findViewById(R.id.yesButton);
        this.noButton = findViewById(R.id.noButton);

    }


    public Button getYesButton() {
        return yesButton;
    }

    public Button getNoButton() {
        return noButton;
    }


    public float getValue() {
        return Float.valueOf(valeur.getText().toString());
    }

    public String getString() {
        return valeur.getText().toString();
    }


    public void build() {
        show();
    }

    public void build(String title, String txt, int choix) {
        if (choix == 0) {
            valeur.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            valeur.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        }
        titre.setText(title);
        valeur.setHint(txt);
        show();
    }

    public void build(String txt) {
        titre.setText(txt);
        titre.setTextSize(16);
        valeur.setVisibility(View.GONE);
        show();
    }


}




