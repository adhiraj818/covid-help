package com.adhiraj.covidhelp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.adhiraj.covidhelp.Classes.User;
import com.adhiraj.covidhelp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextView register_text , forgot_password;
    EditText email,password;
    Button login_button;
    LottieAnimationView progress_bar;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    String uid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//       firebase get instance for authentication
         mAuth=FirebaseAuth.getInstance();

        register_text = findViewById(R.id.register_text);
        forgot_password = findViewById(R.id.forgot_password);
        login_button = findViewById(R.id.login);


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        progress_bar = findViewById(R.id.progress_bar);


        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                login_button.startAnimation(animation);
                loginUser();

            }
        });

        register_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserAdminActivity.class));
            }
        });

    }

    private void loginUser() {
        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();

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
            this.password.setError("* Incorrect Password");
            this.password.requestFocus();
            return ;
        }
        progress_bar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            user = FirebaseAuth.getInstance().getCurrentUser();
                            databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                            uid=user.getUid();

                            if(user.isEmailVerified()){
                                databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        User userProfile = snapshot.getValue(User.class);
                                        if(userProfile!=null){
                                            boolean validity = userProfile.valid;
                                            if(validity == true){

                                                startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
                                                finish();
                                            }else{
                                            Toast.makeText(getApplicationContext(),"You are under varification process \n kindly wait...",Toast.LENGTH_LONG).show();
                                            }

                                        }else{
                                            Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();
                                    }
                                });


                            }else{
                                user.sendEmailVerification();
                                Toast.makeText(getApplicationContext(),"please Varify your email",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Please check your internet connetcion", Toast.LENGTH_LONG).show();
                        }
                        progress_bar.setVisibility(View.INVISIBLE);
                    }
                });

    }
}