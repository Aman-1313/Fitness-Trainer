package com.example.personalfitnesstrainer.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.personalfitnesstrainer.R;
import com.example.personalfitnesstrainer.app.Services;
import com.example.personalfitnesstrainer.business.AccessProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateAccount extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);
        button = (Button) findViewById(R.id.set_new_details);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goProfileFragment();
            }
        });
    }

    private void goProfileFragment() {
        Bundle extras = getIntent().getExtras();
        String username = " ";
        EditText currentPassword = (EditText) findViewById(R.id.current_password);
        EditText newEmail = (EditText) findViewById(R.id.new_email);
        EditText newPassword = (EditText) findViewById(R.id.new_password);
        if (extras!=null){
            username = extras.getString("username");
            AccessProfile accessProfile = new AccessProfile();
            if (accessProfile.checkUserNameFormat(newEmail.getText().toString())){
                if (Services.getProfileDatabase().get(username).checkPassword(currentPassword.getText().toString())){
                    accessProfile.setPassword(username, newPassword.getText().toString());
                    accessProfile.setUserEmail(username,newEmail.getText().toString());
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }else {
                    Toast.makeText(UpdateAccount.this, "Password is not correct", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(UpdateAccount.this, "Email is not correct", Toast.LENGTH_SHORT).show();
            }
        }
    }
}