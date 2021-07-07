package com.example.myhobbies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter_LifecycleAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class searchpage extends AppCompatActivity {
//
    EditText SearchLine;
    ImageButton BtnSearchView;
    RecyclerView recyclerList;
    DatabaseReference mUserDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);

        SearchLine = findViewById(R.id.SearchLine);
        BtnSearchView = findViewById(R.id.BtnSearchView);
        recyclerList = findViewById(R.id.recyclerList);
        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        BtnSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUserSearch();
            }
        });
    }
    private void FirebaseUserSearch(){
//        FirebaseRecyclerAdapter <Users,UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(
//                Users.class,
//                R.layout.activity_list_layout,
//                UsersViewHolder.class,
//                mUserDatabase
//        ) {
//            @Override
//            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {
//                holder.setDetails(model.getFirstName() , model.getProfile());
//            }
//
//            @NonNull
//            @Override
//            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//        };
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setDetails(String FirstName, String userImage){
            TextView First_Name = (TextView) mView.findViewById(R.id.name);
            ImageView user_Image = (ImageView) mView.findViewById(R.id.profileImage);
            First_Name.setText(FirstName);
            Glide.with(getApplicationContext()).load(userImage).into(user_Image);
        }
    }
}
