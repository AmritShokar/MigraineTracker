package ca.amritpal.migrainetracker.data.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
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

    public TriggerCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
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

        // Populate fields with extracted properties
        mTriggerLabel.setText(triggerType);
    }
}
