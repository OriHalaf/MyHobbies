package com.example.myhobbies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class edit_profile extends Register {

    public static final String TAG = "TAG";
    EditText profileFirstName,profileEmail;
    ImageView profileImageViwe;
    Button saveBtn;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    FirebaseUser user;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        String FirstName = data.getStringExtra("FirstName");
        String Email = data.getStringExtra("Email");

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        profileFirstName = findViewById(R.id.MyNameTextView);
        profileEmail = findViewById(R.id.MyEmailTextView);
        profileImageViwe = findViewById(R.id.imageView3);
        saveBtn = findViewById(R.id.button);
        //החלפת תמונת פרןפיל
        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener((OnSuccessListener) (uri) -> {
            Picasso.get().load((Uri) uri).into(profileImageViwe);
        });

        profileImageViwe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileFirstName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty()) {
                    Toast.makeText(edit_profile.this, "One or Many fields are empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = profileEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DocumentReference docRef = mStore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email",email);
                        edited.put("firstName",profileFirstName.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(edit_profile.this, "Profile Update", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Register.class));
                                finish();
                            }
                        });
                        Toast.makeText(edit_profile.this,"Email is changed.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(edit_profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });



        profileFirstName.setText(FirstName);
        profileEmail.setText(Email);

        Log.d(TAG,"onCreate: " + FirstName + " " + Email);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();

                UplaodImageToFirebase(imageUri);
            }
        }
    }

    public void UplaodImageToFirebase(Uri imageUri){
        //העלה תמונה לfirebase
        final StorageReference fileRef = storageReference.child("user/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener((OnSuccessListener) (taskSnapshot) -> {
            fileRef.getDownloadUrl().addOnSuccessListener(onSuccess(Uri) -> {
                Picasso.get().load(Uri).into(profileImageViwe);
            });
        }).addOnFailureListener(onFailure(e) -> {
            Toast.makeText(getApplicationContext(),"Failed.", Toast.LENGTH_SHORT).show();
        });


    }
}