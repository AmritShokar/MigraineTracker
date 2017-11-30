package ca.amritpal.migrainetracker.data.helpers;

import android.util.Log;

import ca.amritpal.migrainetracker.data.models.SelectedTrigger;

/**
 * Created by amrit on 29/11/17.
 */

public class TriggerConversion {

    public TriggerConversion() {}

    public static int[] StringToIntArray(SelectedTrigger selectedTrigger) {
        String triggerString = selectedTrigger.getIds();
        // Remove square brackets from trigger id string
        triggerString = triggerString.substring(1, triggerString.length()-1);
        String[] triggerKeysS = triggerString.split(",");
        int[] triggerKeys = new int[triggerKeysS.length];
        for(int i=0; i<triggerKeys.length; i++) {
            Log.d("TriggerConversion", "Trigger Key: "+triggerKeysS[i]);
            triggerKeys[i] = Integer.parseInt(triggerKeysS[i]);
        }
        return triggerKeys;
    }
}
