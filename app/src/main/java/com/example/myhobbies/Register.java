package com.example.myhobbies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends Activity implements View.OnClickListener{

    EditText Email,ConfirmPass,Pass,FirstName,LastName;
    Button btnToLogin;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        Email = findViewById(R.id.Email);
        ConfirmPass = findViewById(R.id.ConfirmPass);
        Pass = findViewById(R.id.Pass);
        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
//        sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
//        editor = sharedPreferences.edit();

        InitViews();
        btnToLogin.setOnClickListener(this);

    }

    private void InitViews() {
        btnToLogin = findViewById(R.id.btnToLogin);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnToLogin:
                InItButtonSignUp();
                break;
        }
    }

    private void InItButtonSignUp() {
        if(!Pass.getText().toString().equals(ConfirmPass.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Password Not correct in ConfirmPass",Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(Email.getText().toString(), Pass.getText().toString())
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Users user = new Users(FirstName.getText().toString(),LastName.getText().toString());
                            myRef.child(mAuth.getUid()).setValue(user);
                            startActivity(new Intent(Register.this,MainActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),task.getResult().toString(),Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }
}