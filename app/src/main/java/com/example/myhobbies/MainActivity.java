package com.example.myhobbies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText Email_2,Pass_2;
    Button Login,Register,ForgotPass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseAuth mAuth;

    //  כאשר לא עושים יציאה הוא ילך ישר לדף הבית ולא להתחברות
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,ScrolBorger.class));
        }
    }

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
        ForgotPass = findViewById(R.id.ForgotPass);
    }

    public void InitButton(){
        Register.setOnClickListener(this);
        Login.setOnClickListener(this);
        ForgotPass.setOnClickListener(this);
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
            case R.id.ForgotPass:
                Intent f = new Intent(MainActivity.this,ForgotPass.class);
                startActivity(f);
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

