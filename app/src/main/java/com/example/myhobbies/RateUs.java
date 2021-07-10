package com.example.myhobbies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.media.MediaCas;
import android.os.Bundle;
import android.se.omapi.Session;
import android.service.textservice.SpellCheckerService;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Properties;



import com.squareup.picasso.Picasso;

import java.util.Properties;

public class RateUs extends AppCompatActivity {

    EditText Edit_Text_Subject,Subject,ToSend;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        Edit_Text_Subject = findViewById(R.id.Edit_Text_Subject);
        Subject = findViewById(R.id.Subject);
        ToSend = findViewById(R.id.ToSend);
        btnSend = findViewById(R.id.btnSend);


        btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   sendMail();
                }

        });
    }
    public   void  sendMail(){

       String Tos = ToSend.getText().toString();
        String [] recipients = Tos.split(",");
        // exemple@test.com , exemple2@test.com
       String Sub = Subject.getText().toString();
       String message = Edit_Text_Subject.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, Sub);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose an Email client"));

    }
}