package com.example.myhobbies;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;

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
        private Instant Glide;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }
//        //public void setDetails(String FirstName, String userImage){
//            TextView First_Name = (TextView) mView.findViewById(R.id.name);
//            ImageView user_Image = (ImageView) mView.findViewById(R.id.profileImage);
//            First_Name.setText(FirstName);
//            Glide.with(getApplicationContext()).load(userImage).into(user_Image);
//        }//
    }
}
