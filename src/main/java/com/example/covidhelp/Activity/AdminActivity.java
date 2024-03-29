package com.example.covidhelp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.covidhelp.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    EditText city , beds_count , contact , oxygen , date , hosp_name;
    Button submit;

    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
//  initialize firestore
        fStore = FirebaseFirestore.getInstance();

        city = findViewById(R.id.city);
        beds_count = findViewById(R.id.bed_count);
        contact = findViewById(R.id.contact);
        oxygen = findViewById(R.id.oxygen);
        date = findViewById(R.id.date);
        hosp_name = findViewById(R.id.place);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitData();

            }
        });



    }

    private void submitData() {

        String city = this.city.getText().toString().trim();
        String beds_count = this.beds_count.getText().toString().trim();
        String contact = this.contact.getText().toString().trim();
        String date = this.date.getText().toString().trim();
        String oxygen = this.oxygen.getText().toString().trim();
        String hosp_name = this.hosp_name.getText().toString().trim();

         if(city.isEmpty()){
             this.city.setError("* required");
             this.city.requestFocus();
         }
         if(hosp_name.isEmpty()){
             this.hosp_name.setError("* required");
             this.hosp_name.requestFocus();
         }
         if(oxygen.isEmpty()){
             this.oxygen.setError("* required");
             this.oxygen.requestFocus();
         }
         if(beds_count.isEmpty()){
             this.beds_count.setError("* required");
             this.beds_count.requestFocus();
         }
         if(contact.isEmpty()){
             this.contact.setError("* required");
             this.contact.requestFocus();
         }
         if(contact.length()!=10 ){
             this.contact.setError("* please enter valid phone no.");
             this.contact.requestFocus();
         }
         if(date.isEmpty()){
             this.date.setError("* required");
             this.date.requestFocus();
         }

        DocumentReference documentReference = fStore.collection(city.toLowerCase()).document(hosp_name);

        Map<String,Object> data = new HashMap<>();

        data.put("beds_count", beds_count);
        data.put("oxygen", oxygen);
        data.put("contact", contact);
        data.put("date", date);

        documentReference.set(data);

        DocumentReference documentReference1 = fStore.collection("city").document(city.toLowerCase());
        Map<String,String> farzi_data = new HashMap<>();
        farzi_data.put("null_value","null_value");
        documentReference1.set(farzi_data);

    }

}