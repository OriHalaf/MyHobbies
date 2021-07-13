package com.example.myhobbies;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class edit_profile  extends AppCompatActivity {
    final static int GET_IMAGE_FROM_GALLERY = 1;

    EditText FirstName,LastName;
    Button btnSave,btnImage;
    ImageView ChangeImage;
    Uri imageUri ;
    StorageReference storageRef ; // שמירת תמונה ב FIREBASE
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String _FirstName,_LastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        storageRef = FirebaseStorage.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users").child(mAuth.getUid());;
        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        ChangeImage = findViewById(R.id.ChangeImage);
        btnSave = findViewById(R.id.btnSave);
        btnImage = findViewById(R.id.btnImage);


        showAllUsers();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                if(user.getFirstName()!= FirstName.getText().toString()){
                    myRef.child("FirstName").setValue(FirstName.getText().toString());
                    myRef.child(mAuth.getUid()).setValue(user);
                }
                if(user.getLastName()!= LastName.getText().toString()){
                    myRef.child("LastName").setValue(LastName.getText().toString());
                    myRef.child(mAuth.getUid()).setValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(edit_profile.this,ScrolBorger.class);
                startActivity(i);
            }
        });
    }



    private  void showAllUsers(){
        Intent intent = getIntent();
        _FirstName = intent.getStringExtra("firstName");
        _LastName = intent.getStringExtra("lastName");

        FirstName.setText(_FirstName);
        LastName.setText(_LastName);

    }

    public void Update(View view){

    }

    private boolean FirstNameChange() {
        if(!_FirstName.equals(FirstName.getText().toString())){
            myRef.child(_FirstName).child("firstName").setValue(FirstName.getText().toString());
            return true;
        }
        else {
            return false;
        }

    }

    private boolean LastNameChange() {
        if(!_LastName.equals(LastName.getText().toString())){
            myRef.child(_LastName).child("lastName").setValue(LastName.getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == GET_IMAGE_FROM_GALLERY){
//            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(getApplicationContext(),"You did permission to the Gallery",Toast.LENGTH_LONG).show();
//                getImageFromGallery();
//            }
//            else {
//                Toast.makeText(getApplicationContext(),"You did not bring permission to the Gallery",Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode){
//            case GET_IMAGE_FROM_GALLERY:
//                if(resultCode == RESULT_OK){
//                    imageUri = data.getData();
//                }
//                break;
//
//        }
//    }
//
//
//    // האלעת תמונה מתוך תיקיה בטלפון
//    private void getImageFromGallery() {
//        if(ContextCompat.checkSelfPermission(edit_profile.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//            Toast.makeText(edit_profile.this,"You did permission to the camera",Toast.LENGTH_LONG).show();
//            Intent Upload = new Intent();
//            Upload.setType("image/*");
//            Upload.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Upload,GET_IMAGE_FROM_GALLERY);
//
//        }
//        else {
//            requestStoragerPermission();
//        }
//    }
//    private void requestStoragerPermission() {
//        if(ActivityCompat.shouldShowRequestPermissionRationale(edit_profile.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
//            new AlertDialog.Builder(this).
//                    setTitle("Permission needed").
//                    setMessage("This Permission is for use the CAMERA :)").
//                    setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            ActivityCompat.requestPermissions(edit_profile.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
//                                    ,GET_IMAGE_FROM_GALLERY);
//
//                        }
//                    })
//                    .setNegativeButton("cencel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    })
//                    .create().show();
//        }
//        else{
//
//        }
//    }
//
//
//
//
//    public void UplaodImage(){
//        if (imageUri != null)
//        {
//            storageRef = FirebaseStorage.getInstance().getReference().child("MyImages/")
//                    .child(Email.getText().toString()+"."+getFileExtention(imageUri));
//            storageRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            url = uri.toString();
//                            InItButtonSignUp();
//                        }
//                    });
//                }
//            });
//        }else{
//            Toast.makeText(getApplicationContext(),"No Image",Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private String getFileExtention(Uri imageUri){
//        ContentResolver contentResolver = getContentResolver();
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
//    }
//
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
}