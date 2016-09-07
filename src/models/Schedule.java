package models;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Paul on 9/8/2016.
 */
public class Schedule {
    public TreeMap<String, List<String>> week;
    public TreeMap<String, List<String>> weekend;

    public Schedule(TreeMap<String, List<String>> week, TreeMap<String, List<String>> weekend) {
        this.week = week;
        this.weekend = weekend;
    }

    public Schedule() {
    }
}
