package com.example.myhobbies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter_LifecycleAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class searchpage extends AppCompatActivity {

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
        FirebaseRecyclerAdapter<Users , UsersViewHolders> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolders>(
//                Users.class,
//                R.layout.activity_list_layout,
//                UsersViewHolders.class,
//                mUserDatabase
        ) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolders holder, int position, @NonNull Users model) {

            }

            @NonNull
            @Override
            public UsersViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
    }

    public class UsersViewHolders extends RecyclerView.ViewHolder{
        View mView;
        public UsersViewHolders(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }
    }
}