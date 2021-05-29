package com.adhiraj.covidhelp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.adhiraj.covidhelp.Classes.User;
import com.adhiraj.covidhelp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String my_city = "";

    RelativeLayout search_bar,beds_availablility;
    LottieAnimationView drawer_opener;
    DrawerLayout drawer_layout ;
    NavigationView nav_view;

    TextView username;
    Button learn_more_button;

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    String uid ;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        search_bar = findViewById(R.id.searchBar);
        drawer_layout = findViewById(R.id.drawer);
        nav_view = (NavigationView)findViewById(R.id.nav_views);
        drawer_opener = findViewById(R.id.menu_button);
        beds_availablility = findViewById(R.id.beds_availability);
        learn_more_button = findViewById(R.id.learn_more_button);


        View headerview = nav_view.getHeaderView(0);
        username = headerview.findViewById(R.id.username);





        learn_more_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Learn_More_Activity.class));
            }
        });

//        beds_availablility.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), BedsActivity.class));
//            }
//        });

        menu = nav_view.getMenu();


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawer_layout,R.string.open_drawer,R.string.close_drawer);
        drawer_layout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        nav_view.bringToFront();
        nav_view.setNavigationItemSelectedListener(this);

//        getting user data
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        uid=user.getUid();

        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);
                if(userProfile!=null){
                    String usertype = userProfile.usertype;
                    username.setText(userProfile.username);

                    if(usertype.compareTo("admin")==0){
                        menu.findItem(R.id.admin).setVisible(true);
                    }

                }else{

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        drawer_opener.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
                drawer_opener.startAnimation(animation);

                new CountDownTimer(600, 300) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        drawer_layout.openDrawer(GravityCompat.START);
                    }
                }.start();


            }
        });


        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();

        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.dashboard: startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
                                        finish();
                                        return true;

            case R.id.admin: startActivity(new Intent(getApplicationContext(),AdminActivity.class)); return true;

            case R.id.tiffin: startActivity(new Intent(getApplicationContext(),Tiffin_data_Activity.class)); return true;

            case R.id.meds: startActivity(new Intent(getApplicationContext(),medicinesActivity.class)); return true;

            case R.id.contact: startActivity(new Intent(getApplicationContext(),ContactActivity.class)); return true;

            case R.id.actions : if(menu.findItem(R.id.logout).isVisible() == false){
                menu.findItem(R.id.logout).setVisible(true);
                menu.findItem(R.id.exit).setVisible(true);
                menu.findItem(R.id.actions).setIcon(R.drawable.downarrow);
            }else{
                menu.findItem(R.id.logout).setVisible(false);
                menu.findItem(R.id.exit).setVisible(false);
                menu.findItem(R.id.actions).setIcon(R.drawable.action);
            }
                return true;

            case R.id.logout : FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finishAffinity();
                                return true;

            case R.id.exit : drawer_layout.closeDrawer(GravityCompat.START);
                new AlertDialog.Builder(DashBoardActivity.this, android.R.style.Theme_Material_Dialog_Alert).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Exiting library")
                        .setMessage("Are You sure ?")
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(),"Welcome Back", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onBackPressed();
                            }
                        }).show(); return true;

            case R.id.about:if(menu.findItem(R.id.policy).isVisible() == false){
                menu.findItem(R.id.policy).setVisible(true);
                menu.findItem(R.id.Term_and_Condition).setVisible(true);
                menu.findItem(R.id.contact).setVisible(true);
                menu.findItem(R.id.about).setIcon(R.drawable.downarrow);
            }else{
                menu.findItem(R.id.policy).setVisible(false);
                menu.findItem(R.id.Term_and_Condition).setVisible(false);
                menu.findItem(R.id.contact).setVisible(false);
                menu.findItem(R.id.about).setIcon(R.drawable.action);
            }
                return true;

            case R.id.policy : startActivity(new Intent(getApplicationContext(), privacy_policy_Activity.class)); return true;

            case R.id.Term_and_Condition :startActivity(new Intent(getApplicationContext(),TermsActivity.class)); return true;

            default: return false;
        }
    }
}