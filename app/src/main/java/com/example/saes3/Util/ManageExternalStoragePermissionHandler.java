package com.example.saes3.Util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ManageExternalStoragePermissionHandler {
    protected static final int REQUEST_MANAGE_EXTERNAL_STORAGE = 1001;
    private Context mContext;
    private AppCompatActivity mActivity;

    public ManageExternalStoragePermissionHandler(Context context, AppCompatActivity activity) {
        mContext = context;
        mActivity = activity;
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int readPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED;
        }
    }

    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                intent.setData(uri);
                mActivity.startActivityForResult(intent, REQUEST_MANAGE_EXTERNAL_STORAGE);
            } catch (Exception e) {
                // Si une exception se produit, affichez une boîte de dialogue pour rediriger l'utilisateur vers les paramètres de l'application pour accorder l'autorisation.
                showPermissionSettingDialog();
            }
        } else {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_MANAGE_EXTERNAL_STORAGE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_MANAGE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Les autorisations ont été accordées, vous pouvez maintenant accéder au stockage externe
            } else {
                // Les autorisations ont été refusées, vous pouvez afficher un message à l'utilisateur ou prendre d'autres mesures
            }
        }
    }

    private void showPermissionSettingDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("Autorisation requise")
                .setMessage("L'autorisation de gestion du stockage externe est nécessaire pour exporter le fichier. Veuillez accorder cette autorisationpour continuer.")
                .setPositiveButton("Paramètres", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                    intent.setData(uri);
                    mActivity.startActivity(intent);
                })
                .setNegativeButton("Annuler", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}
