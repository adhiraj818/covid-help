package com.adhiraj.covidhelp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.adhiraj.covidhelp.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Tiffin_Activity extends AppCompatActivity {

    EditText place , provider , contact ;
    Button submit;
    Spinner service;
    FirebaseFirestore fStore;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiffin_);

        submit = findViewById(R.id.submit);
        place = findViewById(R.id.place);
        service = findViewById(R.id.serivce);
        provider = findViewById(R.id.name);
        contact = findViewById(R.id.contact);

        fStore = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                submit.startAnimation(animation);

                submitMore();

            }
        });
    }

    private void submitMore() {
        String place = this.place.getText().toString().trim();
        String service = this.service.getSelectedItem().toString();
        String provider = this.provider.getText().toString().trim();
        String contact = this.contact.getText().toString().trim();



        if(service.compareTo("Select a service") == 0){
            Toast.makeText(getApplicationContext(), "please select a service", Toast.LENGTH_SHORT).show();
            this.service.requestFocus();
            return ;
        }
        if(place.isEmpty()){
            this.place.setError("*required");
            this.place.requestFocus();
            return ;
        }
        if(provider.isEmpty()){
            this.provider.setError("*required");
            this.provider.requestFocus();
            return ;
        }
        if(contact.isEmpty()){
            this.contact.setError("*required");
            this.contact.requestFocus();
            return ;
        }
        if(contact.length()!=10){
            this.contact.setError("* please enter valid details");
            this.contact.requestFocus();
            return ;
        }

        Toast.makeText(getApplicationContext(),"Please wait Your data is uploading ", Toast.LENGTH_LONG).show();

        DocumentReference documentReference = fStore.collection(service.toLowerCase()).document(provider);
        Map<String,Object> data = new HashMap<>();

        data.put("place", place);
        data.put("contact", contact);

        documentReference.set(data);

        startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
        finish();



    }
}