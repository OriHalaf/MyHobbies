package com.example.myhobbies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText Email_2,Pass_2;
    Button Login,Register;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Email_2 = findViewById(R.id.Email_2);
        Pass_2=findViewById(R.id.Pass_2);
        InitView();
        InitButton();
    }

    private void InitView() {

        Login = findViewById(R.id.Login);
        Register = findViewById(R.id.Register);
    }

    public void InitButton(){
        Register.setOnClickListener(this);
        Login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.Register:
                Intent a = new Intent(MainActivity.this,Register.class);
                startActivity(a);
                break;
            case R.id.Login:
                SignUpUser();
                break;
        }
    }

    private void SignUpUser() {
        mAuth.signInWithEmailAndPassword(Email_2.getText().toString(),Pass_2.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            startActivity(new Intent(MainActivity.this,HomePage.class));
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(),task.getResult().toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

