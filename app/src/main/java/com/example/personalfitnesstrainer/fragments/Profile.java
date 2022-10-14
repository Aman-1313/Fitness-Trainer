package com.example.personalfitnesstrainer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personalfitnesstrainer.R;
import com.example.personalfitnesstrainer.activities.Homepage;
import com.example.personalfitnesstrainer.app.Services;
import com.example.personalfitnesstrainer.business.AccessProfile;
import com.example.personalfitnesstrainer.business.AccessWeight;
import com.example.personalfitnesstrainer.objects.FitnessGoal;

import java.util.Calendar;

public class Profile extends Fragment {
    private static AccessProfile accessProfile = new AccessProfile();
    private static AccessWeight accessWeight = new AccessWeight();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_profile, container, false);
        Homepage homepage = (Homepage)getActivity();
        assert homepage != null;
        String username = homepage.getuser();
        updatePersonal(username,v);
        updateAccount(username,v);
        writeEmail(username,v);
        writeHeight(username,v);
        writeName(username,v);
        writeMainGoal(username,v);
        writeSecondGoal(username,v);
        writeDesiredWeight(username,v);
        writeWeight(username,v);
        return v;
    }



    private void updateAccount(String username, View v) {
        Button button = (Button) v.findViewById(R.id.update_Account);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Homepage homepage = (Homepage)getActivity();
                assert homepage!=null;
                homepage.changeAccountDetails(username);
            }
        });
    }

    private void updatePersonal(String username, View v) {
        Button button = (Button) v.findViewById(R.id.update_profile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logic class Access Profile

                //Edit the first name
                EditText nameText = (EditText)v.findViewById(R.id.name_input);
                accessProfile.setFirstName(username, nameText.getText().toString());

                //Edit the Fitness goal
                EditText amount = (EditText)v.findViewById(R.id.weight_input);
                EditText subType = (EditText)v.findViewById(R.id.second_goal_input);
                EditText type = (EditText)v.findViewById(R.id.main_goal_input);
                try {
                    FitnessGoal fitnessGoal = new FitnessGoal(type.getText().toString(),subType.getText().toString(),Double.parseDouble(amount.getText().toString()));
                    accessProfile.setGoal(username,fitnessGoal);
                }catch (Exception e){
                    Toast.makeText(getActivity(),"Wrong Weight Input",Toast.LENGTH_SHORT).show();
                }

                //Edit the User Height
                EditText height = (EditText)v.findViewById(R.id.height_input);
                try {
                    accessProfile.setHeight(username,Double.parseDouble(height.getText().toString()));
                }catch (Exception e){
                    Toast.makeText(getActivity(),"Wrong Height Input",Toast.LENGTH_SHORT).show();
                }
                EditText weight = (EditText) v.findViewById(R.id.c_weight_input);
                try {

                    accessWeight.insertNewWeightRecord(username,Calendar.getInstance().getTimeInMillis(), Double.parseDouble(weight.getText().toString()));
                }catch (Exception e){
                    Toast.makeText(getActivity(),"Wrong Weight Input",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    //update the UI layer with the newly changed data
    private void writeDesiredWeight(String username, View v) {
        EditText editText = (EditText)v.findViewById(R.id.weight_input);
        editText.setText(Double.toString(Services.getProfileDatabase().get(username).getGoal().getAmount()));
    }

    private void writeSecondGoal(String username, View v) {
        EditText editText = (EditText)v.findViewById(R.id.second_goal_input);
        editText.setText(Services.getProfileDatabase().get(username).getGoal().getSubtype());
    }

    private void writeMainGoal(String username, View v) {
        EditText editText = (EditText)v.findViewById(R.id.main_goal_input);
        editText.setText(Services.getProfileDatabase().get(username).getGoal().getType());
    }

    private void writeName(String username, View v) {
        EditText editText = (EditText)v.findViewById(R.id.name_input);
        editText.setText(Services.getProfileDatabase().get(username).getFirstName());
    }

    private void writeHeight(String username, View v) {
        EditText editText = (EditText)v.findViewById(R.id.height_input);
        editText.setText(Double.toString(Services.getProfileDatabase().get(username).getHeight()));
    }


    private void writeEmail(String username, View v) {
        EditText editText = (EditText)v.findViewById(R.id.email_input);
        editText.setText(Services.getProfileDatabase().get(username).getUsername());
    }
    private void writeWeight(String username, View v) {
        EditText editText = (EditText)v.findViewById(R.id.c_weight_input);
        editText.setText(Double.toString(accessWeight.getMostRecentWeight(username)));
    }
}
