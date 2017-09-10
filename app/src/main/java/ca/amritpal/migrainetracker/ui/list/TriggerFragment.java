package ca.amritpal.migrainetracker.ui.list;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;

import ca.amritpal.migrainetracker.R;
import ca.amritpal.migrainetracker.data.EntryDatabaseHelper;
import ca.amritpal.migrainetracker.data.adapters.TriggerCursorAdapter;
import ca.amritpal.migrainetracker.data.helpers.InsertTriggerIntentService;
import ca.amritpal.migrainetracker.data.models.SelectedTrigger;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TriggerFragment.OnFinishedTriggerSelectionListener} interface
 * to handle interaction events.
 * Use the {@link TriggerFragment} factory method to
 * create an instance of this fragment.
 */
public class TriggerFragment extends Fragment {

    private OnFinishedTriggerSelectionListener mListener;
    private ListView triggerItems;
    private String date;

    public TriggerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.trigger_fragment, container, false);

        // Get selected date from bundle
        date = getArguments().getString("date");

        // TodoDatabaseHandler is a SQLiteOpenHelper class connecting to SQLite
        EntryDatabaseHelper helper = EntryDatabaseHelper.getInstance(getContext());
        // Get access to the underlying writeable database
        SQLiteDatabase db = helper.getWritableDatabase();
        // Query for items from the database and get a cursor back
        Cursor triggerCursor = db.rawQuery("SELECT  * FROM trigger", null);

        // Find ListView to populate
        triggerItems = (ListView) view.findViewById(R.id.trigger_selection_list);
        // Setup cursor adapter using cursor from last step
        TriggerCursorAdapter triggerAdapter = new TriggerCursorAdapter(getContext(), triggerCursor);
        // Attach cursor adapter to the ListView
        triggerItems.setAdapter(triggerAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTriggerSelection();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFinishedTriggerSelectionListener) {
            mListener = (OnFinishedTriggerSelectionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Create collection to hold ids of selected triggers
        Collection triggersChecked = new ArrayList<Integer>();

        // Insert selected trigger ids into collection
        for(int i=0; i<triggerItems.getCount(); i++) {
            View v = triggerItems.getChildAt(i);

            CheckBox triggerCheckBox = (CheckBox) v.findViewById(R.id.trigger_selection_checkbox);
            TextView triggerLabel = (TextView) v.findViewById(R.id.trigger_selection_label);

            if(triggerCheckBox.isChecked()) {
                int trigId = (Integer) triggerLabel.getTag();
                triggersChecked.add(trigId);
            }
        }
        Gson gson = new Gson();
        Log.d("TriggerFragment",gson.toJson(triggersChecked));
        // Collection serialized for Object persistence
        String triggersCheckedJson = gson.toJson(triggersChecked);
//        SelectedTrigger triggersForDate = new SelectedTrigger(date, triggersCheckedJson);

        // Create Intent to store trigger object
        Intent i = new Intent(getContext(), InsertTriggerIntentService.class);
//        i.putExtra("tester","Test SelectedTrigger Intent Service Success");
        i.putExtra("triggers",triggersCheckedJson);
        i.putExtra("date", date);
        getActivity().startService(i);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFinishedTriggerSelectionListener {
        // TODO: Update argument type and name
        void onTriggerSelection();
    }
}
