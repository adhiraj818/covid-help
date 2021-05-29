package com.adhiraj.covidhelp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.adhiraj.covidhelp.Classes.RecyclerViewAdapterTiffin;
import com.adhiraj.covidhelp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class medicinesActivity extends AppCompatActivity {


    FirebaseFirestore fStore;

    private ArrayList<String> mproviderNames = new ArrayList<String>();
    private ArrayList<String> maddress = new ArrayList<String>();
    private ArrayList<String> mcontact = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);

        fStore = FirebaseFirestore.getInstance();

        getData("medicine");

    }

    private void getData(String tiffin) {

        fStore.collection(tiffin).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String providername , address , contact;

                        providername = document.getId();
                        address = document.getString("place");
                        contact = document.getString("contact");

                        Log.d("tiffin_name", providername+"  "+address+"   "+contact);

                        mproviderNames.add(providername);
                        maddress.add(address);
                        mcontact.add(contact);

                        initRecyclerView();

                    }
                }

            }

        });

    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterTiffin adapter = new RecyclerViewAdapterTiffin(this, mproviderNames, maddress,mcontact);
        recyclerView.setAdapter(adapter);

    }


}