package com.example.covidhelp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.covidhelp.R;

public class ContactActivity extends AppCompatActivity {

    Button mail_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mail_us = findViewById(R.id.mail_us_button);

        mail_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailaddress = "adhiraj818@gmail.com";
                String subject = "User query";
                String message = "type you query here";
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL,new String[]{emailaddress});
                i.putExtra(Intent.EXTRA_SUBJECT,subject);
                i.putExtra(Intent.EXTRA_TEXT,message);
                i.setType("message/rfc822");
                startActivity(Intent.createChooser(i,"choose an email client"));
            }
        });

    }
}