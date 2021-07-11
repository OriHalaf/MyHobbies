package com.example.myhobbies;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    EditText MyNameTextView;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        MyNameTextView = findViewById(R.id.MyNameTextView);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        imageView = findViewById(R.id.imageView7);
        myRef = database.getReference("Users").child(mAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                MyNameTextView.setText(  user.getFirstName() +" "+ user.getLastName());
                if(user.getProfile() != null){
                    Picasso.get().load(user.getProfile()).into(imageView);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            //hello
            }
        });

    }

}