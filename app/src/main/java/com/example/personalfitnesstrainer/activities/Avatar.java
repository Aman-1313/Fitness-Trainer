package com.example.personalfitnesstrainer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.personalfitnesstrainer.R;
import com.example.personalfitnesstrainer.app.Services;
import com.example.personalfitnesstrainer.business.AccessProfile;

public class Avatar extends AppCompatActivity {

    ImageButton b1;
    ImageButton b2;
    ImageButton b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHomepage(b1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHomepage(b2);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHomepage(b3);
            }
        });
    }

    private void goHomepage(ImageButton b) {
        Bundle extras = getIntent().getExtras();
        AccessProfile p = new AccessProfile();
        String username = " ";
        if (extras != null) {
            username = extras.getString("username");
            p.setAvatarName(username,b.getTag().toString());
            //Services.getProfileDatabase().get(username).setAvatarName(b.getTag().toString());
            Intent intent = new Intent(this, Homepage.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }else
            System.out.println("null");
    }
}