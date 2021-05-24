package com.adhiraj.covidhelp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adhiraj.covidhelp.Classes.AdminUser;
import com.adhiraj.covidhelp.Classes.User;
import com.adhiraj.covidhelp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class AdminSignupActivity extends AppCompatActivity {

    TextView login_text;
    EditText username , email , password , re_enter_password;
    Button signupButton;
    private FirebaseAuth mAuth;
    boolean valid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        //        firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        login_text = findViewById(R.id.login_text);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        re_enter_password = findViewById(R.id.re_enter_password);

        signupButton = findViewById(R.id.sign_up);

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                signupButton.startAnimation(animation);
                registerUser();
            }
        });





    }

    private void registerUser() {

        final String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        String re_enter_password = this.re_enter_password.getText().toString().trim();
        final String username = this.username.getText().toString().trim();
        final String usertype = "admin";
        final boolean valid = this.valid;

        if(username.isEmpty()){
            this.username.setError("* required");
            this.username.requestFocus();
            return ;
        }

        if(email.isEmpty()){
            this.email.setError("* required");
            this.email.requestFocus();
            return ;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.email.setError("* please provide valid Email address");
            this.email.requestFocus();
            return ;
        }
        if(password.isEmpty()){
            this.password.setError("* required");
            this.password.requestFocus();
            return ;
        }
        if(password.length()<6){
            this.password.setError("* Too small password");
            this.password.requestFocus();
            return ;
        }

        if(re_enter_password.isEmpty()){
            this.re_enter_password.setError("* required");
            this.re_enter_password.requestFocus();
            return ;
        }

        if(password.compareTo(re_enter_password) != 0){
            this.password.setError("* attention here");
            this.password.requestFocus();

            this.re_enter_password.setError("* re enter password");
            this.re_enter_password.requestFocus();
            return ;
        }
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            final AdminUser admin_user = new AdminUser(username,email);

                            final User user = new User(usertype,username,email,valid);

                            FirebaseDatabase.getInstance().getReference("Admin_Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(admin_user);


                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        if(valid==false){
                                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                            firebaseUser.sendEmailVerification();
                                            startActivity(new Intent(getApplicationContext(),VarficationActivity.class));
                                        }else{
                                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                        }
                                    }else{
                                        Toast.makeText(getApplicationContext(),"unable to register \n Retry in couple of minutes",Toast.LENGTH_LONG).show();
                                    }


                                }
                            });

                        }else{
                            Toast.makeText(getApplicationContext(),"unable to register \n Retry in couple of minutes",Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }
}