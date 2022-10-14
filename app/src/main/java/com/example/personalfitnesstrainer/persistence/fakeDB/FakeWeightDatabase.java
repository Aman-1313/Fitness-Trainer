package com.example.personalfitnesstrainer.persistence.fakeDB;

import com.example.personalfitnesstrainer.objects.WeightRecord;
import com.example.personalfitnesstrainer.persistence.WeightDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class FakeWeightDatabase implements WeightDatabase {
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

    private final TreeMap<CompositeKey, WeightRecord> data;

    public FakeWeightDatabase() {
        data = new TreeMap<>();
    }

    @Override
    public void add(String user, long time, double weight) {
        data.put(new CompositeKey(user, time), new WeightRecord(user, time, weight));
    }

    @Override
    public WeightRecord getWeight(String user, long time) {
        CompositeKey k = new CompositeKey(user, time);
        if (data.containsKey(k))
            return data.get(k);
        else
            return null;
    }

    @Override
    public WeightRecord remove(String user, long time) {
        CompositeKey k = new CompositeKey(user, time);
        return data.remove(k);
    }

    @Override
    public List<WeightRecord> getUserWeights(String user) {
        ArrayList<WeightRecord> result = new ArrayList<>();

        for (WeightRecord w : data.values()) {
            if (w.getUsername().equals(user))
                result.add(w);
        }

        return result;
    }

    @Override
    public WeightRecord getMostRecent(String user) {
        return data.lastEntry().getValue();
    }
}
