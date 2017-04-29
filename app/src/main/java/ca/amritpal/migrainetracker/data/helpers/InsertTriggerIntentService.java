package ca.amritpal.migrainetracker.data.helpers;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Amrit on 2017-04-26.
 */

public class InsertTriggerIntentService extends IntentService {
    public static final String INSERT_TRIGGER_FILTER = "ca.migrainetracker.amrit.migrainetracker.INSERT_TRIGGER_FILTER";

    public InsertTriggerIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String tester = intent.getStringExtra("tester");
        Log.d("InsertTriggerService", tester);
    }
}
