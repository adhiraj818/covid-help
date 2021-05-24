package com.example.covidhelp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.covidhelp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    SearchView search_bar;
    ListView search_list;
    ArrayList<String> cities = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        initialise firestore
        firestore = FirebaseFirestore.getInstance();


        search_bar = findViewById(R.id.searchBar);
        search_list = findViewById(R.id.search_list);
//        firestore cities data
        firestore.collection("city").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
           if(task.isSuccessful()){
               for(QueryDocumentSnapshot document : task.getResult()){
                   cities.add(document.getId());
               }
           }

            }
        });

//        cities.add("Kanpur");
//        cities.add("Lucknow");
//        cities.add("Mangalore");
//        cities.add("Bangalore");

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,cities);
        search_list.setAdapter(arrayAdapter);

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {



                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });

        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String clicked_city = adapterView.getItemAtPosition(i).toString();

                Log.d("user_city", clicked_city);
                DashBoardActivity.my_city = clicked_city;
                startActivity(new Intent(getApplicationContext(),BedsActivity.class));
                finish();
                
            }
        });

    }
}