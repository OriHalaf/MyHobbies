package com.example.myhobbies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Fitness extends AppCompatActivity implements View.OnClickListener{
    ImageButton Search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);

        InitViews();
        InitButton();
    }
    private void InitViews() {
        Search = findViewById(R.id.Search);

    }

    public void InitButton() {
        Search.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Search:
                Intent a = new Intent(Fitness.this, searchpage.class);
                startActivity(a);
                break;
        }
    }
}