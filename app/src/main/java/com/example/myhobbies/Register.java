package com.example.myhobbies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class Register extends Activity implements View.OnClickListener{

   private StorageReference storageRef ; // שמירת תמונה ב FIREBASE
    ImageView MyImage;

    EditText Email,ConfirmPass,Pass,FirstName,LastName;
    Button btnToLogin, UplaodImage;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        storageRef = FirebaseStorage.getInstance().getReference();

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
        MyImage = findViewById(R.id.MyImage);
        InitViews();
        btnToLogin.setOnClickListener(this);
        UplaodImage.setOnClickListener(this);


    }

    private void InitViews() {
        btnToLogin = findViewById(R.id.btnToLogin);
        UplaodImage = findViewById(R.id.UplaodImage);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnToLogin:
                InItButtonSignUp();
                break;
            case R.id.UplaodImage:
                UplaodImage();

        }
    }
    // העלאת תמונה
    public void UplaodImage(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        int requestCode;
        startActivityForResult(intent, requestCode= 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            onCaptureImageResult(data);
        }
    }
    private void onCaptureImageResult(Intent data){
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);
        byte by[] = bytes.toByteArray();

        MyImage.setImageBitmap(thumbnail);
        uploadToFireBase(by);
    }
    private void uploadToFireBase(byte [] by){
        StorageReference sr = storageRef.child("MyImages/a.jpg");
        sr.putBytes(by).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText( Register.this,""+"Successfully UpLoad",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( Register.this,""+"Dosn`t UpLoad",Toast.LENGTH_SHORT).show();
            }
        });
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