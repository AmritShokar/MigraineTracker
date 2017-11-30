package ca.amritpal.migrainetracker.data.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import ca.amritpal.migrainetracker.R;

/**
 * Created by Amrit on 2017-04-24.
 */

public class TriggerCursorAdapter extends CursorAdapter {

    int [] triggerIds;
    boolean triggerExists = false;

    public TriggerCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    public TriggerCursorAdapter(Context context, Cursor cursor, int[] triggerIds, boolean triggerExists) {
        super(context, cursor, 0);
        this.triggerIds = triggerIds;
        this.triggerExists = triggerExists;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.trigger_selection_list_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        CheckBox mTriggerCheckbox = (CheckBox) view.findViewById(R.id.trigger_selection_checkbox);
        TextView mTriggerLabel = (TextView) view.findViewById(R.id.trigger_selection_label);

        // Extract properties from cursor
        String triggerType = cursor.getString(cursor.getColumnIndexOrThrow("type"));
        int triggerId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

        // Populate fields with extracted properties
        mTriggerLabel.setText(triggerType);
        mTriggerLabel.setTag(triggerId);

        //Log.d("TriggerFragment", "Outside Loop: "+triggerItems.getChildCount());
        //for(int i = 0; i < triggerItems.getChildCount(); i++) {
            //Log.d("TriggerFragment", "Inside Loop");
            //View item = triggerItems.getChildAt(i);
            //CheckBox checkBox = (CheckBox) item.findViewById(R.id.trigger_selection_checkbox);
            //Log.d("TriggerFragment", "CheckBox Id: "+checkBox.getId());
        Log.d("TriggerCursorAdapter", "Current list item trigger: "+triggerId);
        if(triggerExists) {
            mTriggerCheckbox.setChecked(false);
            for (int trig : triggerIds) {
                if (trig == triggerId) {
                    Log.d("TriggerCursorAdapter", "triggerId: "+trig+" "+triggerId);
                    mTriggerCheckbox.setChecked(true);
                    //Log.d("TriggerFragment", "Selected Trigger match");
                    break;
                }
            }
        }
        //}
    }
}
