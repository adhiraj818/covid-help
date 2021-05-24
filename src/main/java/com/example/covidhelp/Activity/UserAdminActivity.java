package com.example.covidhelp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.covidhelp.R;

public class UserAdminActivity extends AppCompatActivity {

    Button user_button ,  admin_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        user_button = findViewById(R.id.user);
        admin_button = findViewById(R.id.admin);

        admin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                admin_button.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(),AdminSignupActivity.class));
            }
        });

        user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                user_button.startAnimation(animation);
                startActivity( new Intent(getApplicationContext(), UserSignupActivity.class));

            }
        });


    }
}