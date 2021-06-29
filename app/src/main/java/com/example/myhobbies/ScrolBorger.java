package com.example.myhobbies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;


public class ScrolBorger extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ImageView Fitness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrol_borger);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        // תמונה לחיצה להיכנס לדף וצריך להכין את זה לכל הדפים שבדף קטגוריות
        Fitness = (ImageView) findViewById(R.id.Fitness);
        Fitness.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentCategory = new Intent (ScrolBorger.this,Fitness.class);
                startActivity(intentCategory);            }
        });

    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_add_category:
                Intent intentCategory = new Intent (ScrolBorger.this,add_new_category.class);
                startActivity(intentCategory);
                break;

            case R.id.nav_my_profile:
                Intent intentProfile = new Intent (ScrolBorger.this,Profile.class);
                startActivity(intentProfile);
                break;

            case R.id.nav_edit_profile:
                Intent intentEditProfile = new Intent (ScrolBorger.this,edit_profile.class);
                startActivity(intentEditProfile);
                break;

            case R.id.nav_share:
                Intent intentShare = new Intent (ScrolBorger.this,Share.class);
                startActivity(intentShare);
                break;

            case R.id.nav_rate:
                Intent intentRateUs = new Intent (ScrolBorger.this,RateUs.class);
                startActivity(intentRateUs);
                break;

            case R.id.nav_log_out:
                Intent intentLogout = new Intent (ScrolBorger.this,MainActivity.class);
                startActivity(intentLogout);
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }

}