package com.example.personalfitnesstrainer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.personalfitnesstrainer.R;
import com.example.personalfitnesstrainer.activities.Homepage;
import com.example.personalfitnesstrainer.business.AccessExercise;
import com.example.personalfitnesstrainer.business.AccessWeight;
import com.example.personalfitnesstrainer.objects.ScheduledExercise;
import com.example.personalfitnesstrainer.objects.WeightRecord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class Progress extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_progress, container, false);
        // Set the daily quotes
        Hashtable<String, String> quotes = new Hashtable<String, String>();
        quotes.put("Ralph Waldo Emerson","The only person you are destined to become is the person you decide to be.");
        quotes.put("Vince Lombardi Jr","Once you learn to quit, it becomes a habit.");
        quotes.put(" John F. Kennedy","Our growing softness, our increasing lack of physical fitness, is a menace to our security.");
        //initialize textviews
        TextView quote = (TextView)v.findViewById(R.id.quote_text);
        TextView author = (TextView)v.findViewById(R.id.quote_author);
        // get a random quote
        String[] authors = quotes.keySet().toArray(new String[quotes.size()]);
        String authorToday = authors[new Random().nextInt(authors.length)];
        //set the text from the list
        quote.setText(quotes.get(authorToday));
        author.setText(authorToday);
        ListView weights = (ListView) v.findViewById(R.id.list_weight_overtime);
        ListView active = (ListView) v.findViewById(R.id.list_past_act);
        //access the weight over time
        Homepage homepage = (Homepage)getActivity();
        assert homepage != null;
        String username = homepage.getuser();
        AccessWeight accessWeight = new AccessWeight();
        List<WeightRecord> weightRecords = accessWeight.getUserWeights(username);
        ArrayList<String> weightList = new ArrayList<>();
        for (int i = 0; i < weightRecords.size(); i++) {
            double w = weightRecords.get(i).getWeight();
            DateFormat obj = new SimpleDateFormat("dd MMM yyyy");
            Date date = new Date(weightRecords.get(i).getTime());
            String d = obj.format(date);
            weightList.add(d+":   "+w+ " Kgs");
        }
        //set the adaptor for the list item
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(),
                R.layout.weight_item, // what view to use for the items
                R.id.weight_title, // where to put the String of data
               weightList);
        weights.setAdapter(mAdapter);
        //access activities over time
        AccessExercise accessExercise = new AccessExercise();
        Calendar start = Calendar.getInstance();
        start.add(Calendar.DATE,-6);
        Calendar end = Calendar.getInstance();
        List<ScheduledExercise> exerciseRecord = accessExercise.getCertainDateRangeExercises(username, start, end);

        ArrayList<String> exerciseList = new ArrayList<>();
        for (int i = 0; i < exerciseRecord.size(); i++) {
            String exercise = exerciseRecord.get(i).getExerciseName();
            DateFormat obj = new SimpleDateFormat("dd MMM yyyy");
            Date date = new Date(exerciseRecord.get(i).getStart());
            String d = obj.format(date);
            if(exerciseRecord.get(i).isComplete()){
                exerciseList.add(d +":   "+exercise + "(finished)");
            }else{
                exerciseList.add(d +":   "+exercise + "(unfinished)");
            }

        }
        ArrayAdapter<String> nAdaptor = new ArrayAdapter<>(getContext(),
                R.layout.weight_item, // what view to use for the items
                R.id.weight_title, // where to put the String of data
                exerciseList);
        active.setAdapter(nAdaptor);
        return v;
    }
}