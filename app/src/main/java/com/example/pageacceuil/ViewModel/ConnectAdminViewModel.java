package com.example.pageacceuil.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.FirebaseAccess;

//ATTENTION
// un VM est associé à une activity ou un fragment.
// donc si une seconde Activity a besoin de ce VM
// le système va en créer un autre et les données ne seront donc pas partagée
// car nous auront 2 VM, 1 par activity.
public class ConnectAdminViewModel extends ViewModel {

    private String[] tabID;
    public FirebaseAccess acces;
    //récupération des instances dont on a besoin dans le VM
    public ConnectAdminViewModel() {
        acces=FirebaseAccess.getInstance();
        acces.setConnetViewModel(ConnectAdminViewModel.this);
        tabID=acces.getAdminLog();
    }

    public boolean Verify(String username, String mdp) {
 if((tabID[0].equals(username)) && (tabID[1].equals(mdp))){
     return true;
 }
 return false;
        }
}

    /*

    // pour avoir l'utilisateur on observe grace au live data
    public LiveData<DataSnapshot> getUser() {

        // je déclare l'observer
        MutableLiveData<DataSnapshot> listener = new MutableLiveData<>();


        //je fais le traitement souhaité
     *//*   myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot tab = task.getResult();
                // je notifie les observateurs que les données sont dispos
                listener.postValue(tab);

              *//**//*  String[] id= new String[2];
                    id[0] = tab.child("Admin").getValue(String.class);
                    id[1] = tab.child("mdp").getValue(String.class);

                    // je notifie les observateurs que les données sont dispos
                    listener.postValue(id);*//**//*
            }
        });

        // je retourne l'observer qui est vide au début puis se "déclenche"
        // quand les données sont récupérées.
        return listener;
    }
*//*
//     coBtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            System.out.println(user);
//            System.out.println(editUser.getText().toString());
//            System.out.println(mdp);
//            System.out.println(editMdp.getText().toString());
//            if (Objects.equals(user, editUser.getText().toString().trim()) && Objects.equals(mdp, editMdp.getText().toString().trim())) {
//                Intent ac;
//                ac = new Intent(Connection_admin_page.this, Admin_page.class);
//                ac.putExtra("listeESP", tabESP);
//                ac.putExtra("hashmapEsp", ESP);
//                startActivity(ac);
//            } else {
//                Toast.makeText(getApplicationContext(), "Username ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    });
//        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//        @Override
//        public void onComplete(@NonNull Task<DataSnapshot> task) {
//            DataSnapshot tab = task.getResult();
//            user = tab.child("Admin").getValue(String.class);
//            mdp = tab.child("mdp").getValue(String.class);
//
//        }
//    });
//
//
//}
//}
}
*/