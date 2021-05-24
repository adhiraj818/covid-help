package com.adhiraj.covidhelp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.adhiraj.covidhelp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email;
    Button reset_password;
    LottieAnimationView progress_bar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.email);
        reset_password = findViewById(R.id.reset_password);
        progress_bar = findViewById(R.id.progress_bar);

        auth = FirebaseAuth.getInstance();

        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                reset_password.startAnimation(animation);
                resetPassword();
            }
        });

    }

    private void resetPassword() {

        String email = this.email.getText().toString().trim();

        if(email.isEmpty()){
            this.email.setError("* required");
            this.email.requestFocus();
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.email.setError("* please provide valid Email address");
            this.email.requestFocus();
            return ;
        }

        progress_bar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"please check your mail to reset your password", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Something went wrong please try after some time", Toast.LENGTH_LONG).show();
                }
                progress_bar.setVisibility(View.INVISIBLE);
            }
        });
    }
}