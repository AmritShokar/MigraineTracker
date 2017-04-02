package ca.amritpal.migrainetracker.data;

/**
 * Created by amrit on 3/29/2017.
 */

public class Entry {

    private String date;
    private int morningPainLevel;
    private int afternoonPainLevel;
    private int eveningPainLevel;

    public Entry() {}

    public Entry(String date, int morningPainLevel, int afternoonPainLevel, int eveningPainLevel) {
        this.date = date;
        this.morningPainLevel = morningPainLevel;
        this.afternoonPainLevel = afternoonPainLevel;
        this.eveningPainLevel = eveningPainLevel;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setMorningLevel(int newMorningLevel) {
        this.morningPainLevel = newMorningLevel;
    }
    public int getMorningLevel() {
        return this.morningPainLevel;
    }

    public void setAfternoonLevel(int newAfternoonLevel) {
        this.afternoonPainLevel = newAfternoonLevel;
    }
    public int getAfternoonLevel() {
        return this.afternoonPainLevel;
    }

    public void setEveningLevel(int newEveningLevel) {
        this.eveningPainLevel = newEveningLevel;
    }
    public int getEveningLevel() {
        return this.eveningPainLevel;
    }
}
