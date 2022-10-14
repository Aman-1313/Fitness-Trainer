package com.example.personalfitnesstrainer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalfitnesstrainer.R;
import com.example.personalfitnesstrainer.app.Services;
import com.example.personalfitnesstrainer.business.AccessProfile;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        Services.init(getApplicationContext());

        AppCompatButton button = (AppCompatButton) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccessProfile p = new AccessProfile();
                boolean success = p.loginVerifier(username.getText().toString(),password.getText().toString());
                if (success){
                    enterHomePage(username.getText().toString());
                }
                else
                    Toast.makeText(MainActivity.this, "USER NOT AVAILABLE", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void enterHomePage(String username){
        Intent intent = new Intent(this, Homepage.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}