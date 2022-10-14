package com.example.personalfitnesstrainer.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import com.example.personalfitnesstrainer.R;
import com.example.personalfitnesstrainer.activities.Homepage;

import com.example.personalfitnesstrainer.business.AccessExercise;

import com.example.personalfitnesstrainer.objects.ScheduledExercise;

import java.util.ArrayList;

import java.util.List;


public class Plan extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);




        //Initialize the Calendar
        CalendarView calendarView = (CalendarView)v.findViewById(R.id.event_calendar);
        Homepage homepage = (Homepage)getActivity();
        assert homepage != null;
        String username = homepage.getuser();

        //set current date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        calendarView.setMinDate(calendarView.getDate());
        Calendar start = Calendar.getInstance();
        start.setTime(new Date(calendarView.getDate()));
        start.set(Calendar.HOUR_OF_DAY,0);
        start.set(Calendar.MINUTE,0);
        start.set(Calendar.SECOND,0);
        Calendar end = Calendar.getInstance();
        Date endDate = new Date(calendarView.getDate());
        end.setTime(endDate);
        end.add(Calendar.HOUR,24);
        setList (v,username, start, end);
        final String[] selectedDate = {sdf.format(calendarView.getDate())};
        //Add calender onchange listener
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String newDate = sdf.format(new Date(year-1900,month,day));
                selectedDate[0] = newDate;
                start.setTime(new Date(year-1900,month,day));
                start.set(Calendar.HOUR_OF_DAY,0);
                start.set(Calendar.MINUTE,0);
                end.setTime(new Date(year-1900,month,day+1));
                end.set(Calendar.HOUR_OF_DAY,0);
                end.set(Calendar.MINUTE,0);
                setList(v, username, start, end);
            }
        });




        // Add a new activity event
        Button buttonAdd = (Button) v.findViewById(R.id.add_new_button);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homepage.newActivityEvent(selectedDate[0]);
            }
        });

        return v;
    }

    private void setBar(ProgressBar progressBar, List<ScheduledExercise> oneDayActivities) {
        if (oneDayActivities.size()!=0) {
            int progress = 0;
            for (int i = 0; i < oneDayActivities.size(); i++) {
                if (oneDayActivities.get(i).isComplete()) {
                    progress++;
                }
            }
            int percentage = (progress* 100 ) / oneDayActivities.size();
            progressBar.setProgress(percentage);
        }else progressBar.setProgress(0);
    }
    private void setList(View v, String username, Calendar start, Calendar end){
        AccessExercise accessExercise = new AccessExercise();
        final List<ScheduledExercise>[] oneDayActivities = new List[]{accessExercise.getCertainDateRangeExercises(username, start, end)};
        ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.determinateBar);
        setBar(progressBar, oneDayActivities[0]);
        if(oneDayActivities[0] !=null){
            ArrayList<String> taskList = new ArrayList<>();
            ListView mTaskListView = (ListView) v.findViewById(R.id.list_todo);
            for (int i = 0; i < oneDayActivities[0].size(); i++) {
                taskList.add((i+1)+")  "+oneDayActivities[0].get(i).getExerciseName() );
            }

            ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(),
                    R.layout.todo_item, // what view to use for the items
                    R.id.task_title, // where to put the String of data
                    taskList); // where to get all the data
            mTaskListView.setAdapter(mAdapter);
            Button button11 = (Button)v.findViewById(R.id.auto_assign);
            button11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AccessExercise accessExercise = new AccessExercise();
                    if(accessExercise.getCertainDateRangeExercises(username,start, end).size() ==0){
                    accessExercise.assignExerciseOnDay(username, start);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(),"Success\n Click Date Again To Update",Toast.LENGTH_LONG).show();
                    }else Toast.makeText(getActivity(),"Found Existing Exercise Event",Toast.LENGTH_SHORT).show();
                }
            });

            mTaskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                public boolean onItemLongClick(AdapterView<?> parent, View view, int
                        position, long id) {

                    // it will get the position of selected item from the ListView

                    final int selected_item = position;

                    long start1 = oneDayActivities[0].get(selected_item).getStart();
                    Calendar newCal = Calendar.getInstance();
                    newCal.setTimeInMillis(start1);
                    accessExercise.removeExercise(username,newCal);
                    taskList.remove(selected_item);
                    mAdapter.notifyDataSetChanged();
                    oneDayActivities[0] = accessExercise.getCertainDateRangeExercises(username,start,end);
                    setBar(progressBar, oneDayActivities[0]);
                    return true;
                }
            });


            mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view,
                                        final int position, long id) {
                    final int selected_item = position;
                    long start1 = oneDayActivities[0].get(selected_item).getStart();
                    Calendar newCal = Calendar.getInstance();
                    newCal.setTimeInMillis(start1);
                    accessExercise.setExerciseIsComplete(username,newCal);
                    oneDayActivities[0] = accessExercise.getCertainDateRangeExercises(username,start,end);
                    setBar(progressBar, oneDayActivities[0]);
                }
            });
        }

    }
}
//
