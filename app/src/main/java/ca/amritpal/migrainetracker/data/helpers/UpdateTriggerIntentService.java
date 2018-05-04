package ca.amritpal.migrainetracker.data.helpers;

import android.app.IntentService;
import android.content.Intent;

import ca.amritpal.migrainetracker.data.EntryDatabaseHelper;
import ca.amritpal.migrainetracker.data.models.SelectedTrigger;

/**
 * Created by Amrit on 2017-04-26.
 */

public class UpdateTriggerIntentService extends IntentService {
    public static final String UPDATE_TRIGGER_FILTER = "ca.migrainetracker.amrit.migrainetracker.UPDATE_TRIGGER_FILTER";

    public UpdateTriggerIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        String tester = intent.getStringExtra("tester");
//        Log.d("InsertTriggerService", tester);
        EntryDatabaseHelper helper = EntryDatabaseHelper.getInstance(getApplicationContext());
        //SQLiteDatabase db = handler.getWritableDatabase();

        String selectedTriggerDate = intent.getStringExtra("date");
        String selectedTriggerIds = intent.getStringExtra("triggers");
        SelectedTrigger selectedTrigger = new SelectedTrigger(selectedTriggerDate, selectedTriggerIds);

        helper.updateSelectedTrigger(selectedTrigger);
    }
}
