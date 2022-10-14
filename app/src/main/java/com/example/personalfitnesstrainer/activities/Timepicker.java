package com.example.personalfitnesstrainer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.personalfitnesstrainer.R;
import com.example.personalfitnesstrainer.business.AccessExercise;
import com.example.personalfitnesstrainer.fragments.Plan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Timepicker extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button addButton;
    TimePicker start;
    TimePicker end;
    Spinner spinner;
    String username = "";
    String exerciseName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timepicker);
        addButton = findViewById(R.id.add_event);
        start = findViewById(R.id.time_here);
        end = findViewById(R.id.time_end);
        Bundle extras = getIntent().getExtras();
        String date = " ";

        if (extras != null) {
            date = extras.getString("date");
            username = extras.getString("username");
        }


        //Select an exercise name using a spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.exercise_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //onclicklistener
        spinner.setOnItemSelectedListener(this);

        final int[] startHour = {start.getHour()};
        final int[] startMinute1 = {start.getMinute()};
        final int[] endHour = {end.getHour()};
        final int[] endMinute1 = {end.getMinute()};
        start.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                startHour[0] = hourOfDay;
                startMinute1[0] = minute;
            }
        });

        end.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                endHour[0] = hourOfDay;
                endMinute1[0] = minute;
            }
        });
        String finalDate = date;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date1 = null;
                try {
                    date1=new SimpleDateFormat("dd/MM/yyyy").parse(finalDate);
                } catch (ParseException e) {
                    System.out.println(e);
                }
                Calendar startCal = Calendar.getInstance();
                startCal.set(date1.getYear()+1900,date1.getMonth(),date1.getDate(),startHour[0],startMinute1[0]);
                Calendar endCal = Calendar.getInstance();
                endCal.set(date1.getYear()+1900,date1.getMonth(),date1.getDate(),endHour[0],endMinute1[0]);
                AccessExercise accessExercise = new AccessExercise();
                if(accessExercise.addExercise(username,exerciseName,startCal,endCal)){
                    Toast.makeText(Timepicker.this,"Activity Added Successfully",Toast.LENGTH_SHORT).show();
                    goBack();
                }else Toast.makeText(Timepicker.this,"End Time Smaller Than Start Time",Toast.LENGTH_SHORT).show();


            }
        });
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        exerciseName = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    private void goBack() {
        Intent intent = new Intent(this, Homepage.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}