package com.example.myhobbies;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class photograff_page extends AppCompatActivity {
    TextView linkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photograff_page);
        linkTextView = findViewById(R.id.Photografflink);
    }
}