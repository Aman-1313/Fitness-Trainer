package com.example.personalfitnesstrainer.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.personalfitnesstrainer.R;
import com.example.personalfitnesstrainer.activities.Homepage;
import com.example.personalfitnesstrainer.app.Services;

public class Home extends Fragment   {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //Get activity for the username
        Homepage homepage = (Homepage)getActivity();
        assert homepage != null;
        String username = homepage.getuser();

        //set USer Profile Image
        ImageButton imageButton = view.findViewById(R.id.profile_pic);
        String uri = "@drawable/"+ Services.getProfileDatabase().get(username).getAvatarName();
        int imageResource = getResources().getIdentifier(uri, null, homepage.getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        imageButton.setImageDrawable(res);
        //

        //Set Changing Avatar functionality
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homepage.changeAvatar(username);
            }
        });
        //Set Name Text Of the user
        TextView nameText = view.findViewById(R.id.nameText);
        nameText.setText("Hello,\n       "+Services.getProfileDatabase().get(username).getFirstName());

        return view;
    }
}
