package ca.amritpal.migrainetracker.data;

/**
 * Created by amrit on 3/29/2017.
 */

public class Entry {

    private String date;
    private int moodLevel;

    public Entry(String date, int moodLevel) {
        this.date = date;
        this.moodLevel = moodLevel;
    }

    public void setMoodLevel(int newMoodLevel) {
        this.moodLevel = newMoodLevel;
    }
    public int getMoodLevel() {
        return this.moodLevel;
    }
    public String getDate() {
        return date;
    }

}
