package com.example.myhobbies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ScrolBorger extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ImageView Fitness,Surf,Paint,Food,Author,Camera;
    FirebaseAuth mFirebaseUser;
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

        mFirebaseUser = FirebaseAuth.getInstance();
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        // תמונה לחיצה להיכנס לדף וצריך להכין את זה לכל הדפים שבדף קטגוריות
        Fitness = (ImageView) findViewById(R.id.Fitness);
        Surf = (ImageView) findViewById(R.id.Surf);
        Paint = (ImageView) findViewById(R.id.Paint);
        Food = (ImageView) findViewById(R.id.Food);
        Author = (ImageView) findViewById(R.id.Author);
        Camera = (ImageView) findViewById(R.id.Camera);

        Fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Fit = new Intent(ScrolBorger.this,fitness_page.class);
                startActivity(Fit);

            }
        });
        Surf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent surf = new Intent(ScrolBorger.this,surf_page.class);
                startActivity(surf);
            }
        });
        Paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paint = new Intent(ScrolBorger.this,paint.class);
                startActivity(paint);
            }
        });

        Food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent food = new Intent(ScrolBorger.this,DfPage.class);
                startActivity(food);
            }
        });
        Author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent author = new Intent(ScrolBorger.this,Author.class);
                startActivity(author);
            }
        });
        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(ScrolBorger.this,photograff_page.class);
                startActivity(camera);
            }
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
                ClickLogout();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ClickLogout() {
        Logout(this);
        mFirebaseUser.signOut();
    }

    public static void Logout(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to log out ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }
}