import android.app.Application;

import Model.FirebaseAccess;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAccess database=FirebaseAccess.getInstance();

        // je peux initialiser des données ici.
        //lancé une seule fois à l'init de l'app
        // pour repasser ici il faut killer l'application et la relancer
        // si on la passe en background et qu'on la relance on ne repasse pas ici
    }
}
