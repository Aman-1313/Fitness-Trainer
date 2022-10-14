package com.example.personalfitnesstrainer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.personalfitnesstrainer.R;
import com.example.personalfitnesstrainer.app.Services;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Homepage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavController navController;
    ImageButton imageButton;
    TextView nameText;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        username = " ";
        if (extras != null) {
            username = extras.getString("username");
        }
        setContentView(R.layout.activity_homepage);
        nameText = findViewById(R.id.nameText);
        nameText.setText("Hello,\n       "+ Services.getProfileDatabase().get(username).getFirstName());
        bottomNavigationView =findViewById(R.id.bottomNav);
        navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        imageButton = findViewById(R.id.profile_pic);
        String uri = "@drawable/"+Services.getProfileDatabase().get(username).getAvatarName();
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        imageButton.setImageDrawable(res);

        String finalUsername = username;
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAvatar(finalUsername);
            }
        });
    }
    // this is to access current username from other activity classes
    public String getuser(){
        return username;
    }
    public void changeAccountDetails(String username){
        Intent intent = new Intent(this, UpdateAccount.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
    public void changeAvatar(String username){
        Intent intent = new Intent(this, Avatar.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }


    public void newActivityEvent(String s) {
        Intent intent = new Intent(this, Timepicker.class);
        intent.putExtra("date", s);
        intent.putExtra("username", username);
        startActivity(intent);
    }

}