package com.adhiraj.covidhelp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.adhiraj.covidhelp.Classes.RecyclerViewAdapter;
import com.adhiraj.covidhelp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BedsActivity extends AppCompatActivity {

    FirebaseFirestore fStore;

    private ArrayList<String> mcity = new ArrayList<String>();
    private ArrayList<String> mhospNames = new ArrayList<String>();
    private ArrayList<String> mbeds_count = new ArrayList<String>();
    private ArrayList<String> moxygen = new ArrayList<String>();
    private ArrayList<String> mcontact = new ArrayList<String>();
    private ArrayList<String> mdate = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beds);
//      initialize firestore
        fStore = FirebaseFirestore.getInstance();
        if(!DashBoardActivity.my_city.isEmpty()){
            String clicked_city = DashBoardActivity.my_city;
            getData(clicked_city);
        }else{
            Toast.makeText(getApplicationContext(),"No city is Selected\n Please Select a City", Toast.LENGTH_LONG).show();
        }

    }

    private void getData(String city_name) {



        fStore.collection(city_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String hospname , beds_count , oxygen , contact , date;

                        hospname = document.getId();
                        beds_count = document.getString("beds_count");
                        oxygen = document.getString("oxygen");
                        contact = document.getString("contact");
                        date = document.getString("date");

                        mhospNames.add(hospname);
                        mbeds_count.add(beds_count);
                        moxygen.add(oxygen);
                        mcontact.add(contact);
                        mdate.add(date);

                        initRecyclerView();

                    }
                }else {
                    Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                    Log.d("Firebase_error", "Error getting documents: ", task.getException());
                }
            }
        });

    }

    private void initRecyclerView() {
//        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mhospNames, mbeds_count,moxygen,mcontact,mdate);
        recyclerView.setAdapter(adapter);
    }
}