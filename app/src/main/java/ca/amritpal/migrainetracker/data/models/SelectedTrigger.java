package ca.amritpal.migrainetracker.data.models;

/**
 * Created by Amrit on 2017-04-25.
 */

public class SelectedTrigger {
    String date;
    String ids;

    public SelectedTrigger() {}

    public SelectedTrigger(String date, String ids) {
        this.date = date;
        this.ids = ids;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getDate() {
        return date;
    }
    public String getIds() {
        return ids;
    }
}
