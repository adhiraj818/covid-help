package com.adhiraj.covidhelp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adhiraj.covidhelp.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    EditText city , beds_count , contact , oxygen , date , hosp_name;
    Button submit , next;

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
        next = findViewById(R.id.next);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                submit.startAnimation(animation);
                submitData();

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                next.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), Tiffin_Activity.class));

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
             return;
         }
         if(hosp_name.isEmpty()){
             this.hosp_name.setError("* required");
             this.hosp_name.requestFocus();
             return;
         }
         if(oxygen.isEmpty()){
             this.oxygen.setError("* required");
             this.oxygen.requestFocus();
             return;
         }
         if(beds_count.isEmpty()){
             this.beds_count.setError("* required");
             this.beds_count.requestFocus();
             return;
         }
         if(contact.isEmpty()){
             this.contact.setError("* required");
             this.contact.requestFocus();
             return;
         }
         if(contact.length()!=10 ){
             this.contact.setError("* please enter valid phone no.");
             this.contact.requestFocus();
             return;
         }
         if(date.isEmpty()){
             this.date.setError("* required");
             this.date.requestFocus();
             return;
         }

        Toast.makeText(getApplicationContext(),"Please wait Your data is uploading ", Toast.LENGTH_LONG).show();

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

        startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
        finish();
    }

}