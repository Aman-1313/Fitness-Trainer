package com.example.personalfitnesstrainer.persistence.fakeDB;

import com.example.personalfitnesstrainer.objects.ScheduledExercise;
import com.example.personalfitnesstrainer.persistence.ActivityDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class FakeActivityDatabase implements ActivityDatabase {
    private static class CompositeKey implements Comparable<CompositeKey> {
        String user;
        long time;

        CompositeKey(String u, long t) {
            user = u;
            time = t;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof CompositeKey){
                return ((CompositeKey) o).user.equals(this.user) && ((CompositeKey) o).time == this.time;
            }
            else {
                return false;
            }
        }

        @Override
        public int compareTo(CompositeKey o) {
            if (this.user.compareTo(o.user) != 0)
                return this.user.compareTo(o.user);
            else
                return Long.compare(this.time, o.time);
        }
    }

    private final TreeMap<CompositeKey, ScheduledExercise> data;

    public FakeActivityDatabase() {
        data = new TreeMap<>();
    }

    @Override
    public void add(String user, long start, long end, boolean isComplete, String exerciseName) {
        data.put(new CompositeKey(user, start), new ScheduledExercise(user, start, end, isComplete, exerciseName));
    }

    @Override
    public ScheduledExercise get(String user, long start) {
        return data.get(new CompositeKey(user, start));
    }

    @Override
    public ScheduledExercise remove(String user, long start) {
        return data.remove(new CompositeKey(user, start));
    }

    @Override
    public ScheduledExercise changeIsComplete(String user, long start, boolean isComplete) {
        CompositeKey k = new CompositeKey(user, start);
        //if (data.containsKey(k))
        //    data.get(k).setComplete(isComplete);
        if (data.get(k) != null)
            data.get(k).setComplete(isComplete);
        return data.get(k);
    }

    @Override
    public List<ScheduledExercise> getUserActivities(String user) {
        ArrayList<ScheduledExercise> result = new ArrayList<>();

        for (ScheduledExercise u : data.values()) {
            if (u.getUser().equals(user))
                result.add(u);
        }

        return result;
    }

    @Override
    public List<ScheduledExercise> getUserActivities(String user, long earliest, long latest) {
        ArrayList<ScheduledExercise> result = new ArrayList<>();

        for (ScheduledExercise u : data.values()) {
            if (u.getUser().equals(user)
                    && u.getStart() >= earliest
                    && u.getStart() <= latest)
            {
                result.add(u);
            }
        }

        return result;
    }
}
