package com.example.pageacceuil.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.FirebaseAccess;

import java.util.ArrayList;

public class GraphViewModel extends ViewModel {
FirebaseAccess acess=FirebaseAccess.getInstance();
    public LiveData<Data> getData() {
        MutableLiveData<Data> listener = new MutableLiveData<>();
        acess.setPrechargeDonnee("d");
        listener.postValue(data);

    }
}
