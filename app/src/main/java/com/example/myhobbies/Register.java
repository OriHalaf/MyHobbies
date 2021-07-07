package com.example.myhobbies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
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

    final static int GET_IMAGE_FROM_GALLERY = 1;
    private static final int  Permission_CAMERA = 2;
    private static final int CAPTURE_IMAGE = 3 ;
    StorageReference storageRef ; // שמירת תמונה ב FIREBASE
    ImageView MyImage;

    EditText Email,ConfirmPass,Pass,FirstName,LastName;
    Button btnToLogin, UplaodImage,TakePicture;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Uri imageUri ;
    String url;
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
        TakePicture.setOnClickListener(this);
    }

    private void InitViews() {
        btnToLogin = findViewById(R.id.btnToLogin);
        UplaodImage = findViewById(R.id.UplaodImage);
        TakePicture = findViewById(R.id.TakePicture);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnToLogin:
                UplaodImage();
                break;
            case R.id.UplaodImage:
                getImageFromGallery();
                break;
            case R.id.TakePicture:
                takePicture();
                break;
        }
    }

    // האלעת תמונה על ידי הפעלת המצלמה
    private void takePicture() {
        if(ContextCompat.checkSelfPermission(Register.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

           Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
           startActivityForResult(i,CAPTURE_IMAGE);
        }
        else {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this).
                    setTitle("Permission needed").
                    setMessage("This Permission is for use the CAMERA :)").
                    setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(Register.this,new String[]{Manifest.permission.CAMERA}
                                    ,Permission_CAMERA);

                        }
                    })
                    .setNegativeButton("cencel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},Permission_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Permission_CAMERA){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"You did permission to the camera",Toast.LENGTH_LONG).show();
                takePicture();
            }
            else {
                Toast.makeText(getApplicationContext(),"You did not bring permission to the camera",Toast.LENGTH_LONG).show();
            }
        }
        if(requestCode == GET_IMAGE_FROM_GALLERY){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"You did permission to the Gallery",Toast.LENGTH_LONG).show();
                takePicture();
            }
            else {
                Toast.makeText(getApplicationContext(),"You did not bring permission to the Gallery",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case GET_IMAGE_FROM_GALLERY:
                if(resultCode == RESULT_OK){
                    imageUri = data.getData();
                }
                break;
            case CAPTURE_IMAGE:
                if(resultCode == RESULT_OK){
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imageUri = getImageUri(Register.this,photo);
                    storageRef.putFile(imageUri);
                    MyImage.setImageBitmap(photo);

                }

        }
    }


    // האלעת תמונה מתוך תיקיה בטלפון
    private void getImageFromGallery() {
        if(ContextCompat.checkSelfPermission(Register.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(Register.this,"You did permission to the camera",Toast.LENGTH_LONG).show();
            Intent Upload = new Intent();
            Upload.setType("image/*");
            Upload.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Upload,GET_IMAGE_FROM_GALLERY);
        }
        else {
            requestStoragerPermission();
        }
    }
    //
//
    private void requestStoragerPermission() {

        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this).
                    setTitle("Permission needed").
                    setMessage("This Permission is for use the CAMERA :)").
                    setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(Register.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                                    ,GET_IMAGE_FROM_GALLERY);

                        }
                    })
                    .setNegativeButton("cencel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},Permission_CAMERA);
        }
    }

    public void UplaodImage(){
        if (imageUri != null)
        {
            storageRef = FirebaseStorage.getInstance().getReference().child("MyImages/")
                    .child(Email.getText().toString()+"."+getFileExtention(imageUri));
            storageRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString();
                            InItButtonSignUp();
                        }
                    });
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"No Image",Toast.LENGTH_LONG).show();
        }
    }

    private String getFileExtention(Uri imageUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

//    private void uploadToFireBase(byte [] by){
//        StorageReference sr = storageRef.child("MyImages/" +mAuth.getUid() +".jpg");
//        sr.putBytes(by).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText( Register.this,""+"Successfully UpLoad",Toast.LENGTH_SHORT).show();
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText( Register.this,""+"Dosn`t UpLoad",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }



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
                            Users user = new Users(FirstName.getText().toString(),LastName.getText().toString(),Email.getText().toString(),url);
                            myRef.child(mAuth.getUid()).setValue(user);
                            // Intent i = new Intent(Register.this,MainActivity.class);
                            //  i.putExtra("profilePic",imageUri);
                            startActivity(new Intent(Register.this,MainActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),task.getResult().toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }
}



