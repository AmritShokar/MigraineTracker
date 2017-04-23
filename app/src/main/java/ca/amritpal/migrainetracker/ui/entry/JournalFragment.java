package ca.amritpal.migrainetracker.ui.entry;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ca.amritpal.migrainetracker.R;
import ca.amritpal.migrainetracker.data.Entry;
import ca.amritpal.migrainetracker.data.EntryDatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JournalFragment.OnFinishedJournalEntryListener} interface
 * to handle interaction events.
 * Use the {@link JournalFragment} factory method to
 * create an instance of this fragment.
 */
public class JournalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private OnFinishedJournalEntryListener mListener;
    private TextView mDateView;
    private SeekBar mMorningSlider;
    private SeekBar mAfternoonSlider;
    private SeekBar mEveningSlider;
    private String newDateFormat;
    private int daySelect;
    private int monthSelect;
    private int yearSelect;
    private boolean entryExists;

    public JournalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.journal_fragment, container, false);

        entryExists = getArguments().getBoolean("exists");
        daySelect = getArguments().getInt("day");
        monthSelect = getArguments().getInt("month");
        yearSelect = getArguments().getInt("year");
        newDateFormat = daySelect+"-"+monthSelect+"-"+yearSelect;

        mDateView = (TextView) view.findViewById(R.id.journal_entry_date);
        //Calendar currDate = Calendar.getInstance();
        Calendar currDate = new GregorianCalendar(yearSelect, monthSelect, daySelect);
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd, yyyy");
        String currDateText = sdf.format(currDate.getTime());
        mDateView.setText(currDateText);

        mMorningSlider = (SeekBar) view.findViewById(R.id.journal_morning_slider);
        mAfternoonSlider = (SeekBar) view.findViewById(R.id.journal_afternoon_slider);
        mEveningSlider = (SeekBar) view.findViewById(R.id.journal_evening_slider);

        if (entryExists) {
            setAttributes();
        }

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFinishedEntry(uri);
        }
    }

    public void setAttributes() {
        EntryDatabaseHelper helper = EntryDatabaseHelper.getInstance(getContext());
        Entry entryData = helper.retrieveEntry(newDateFormat);

        Log.d("setAttributes()","getMorningLevel: "+entryData.getMorningLevel()+" for "+newDateFormat);

        mMorningSlider.setProgress(entryData.getMorningLevel());
        mAfternoonSlider.setProgress(entryData.getAfternoonLevel());
        mEveningSlider.setProgress(entryData.getEveningLevel());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFinishedJournalEntryListener) {
            mListener = (OnFinishedJournalEntryListener) context;
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
    public void onStop() {
        super.onStop();
        EntryDatabaseHelper helper = EntryDatabaseHelper.getInstance(getContext());
        Entry entry = new Entry(newDateFormat, mMorningSlider.getProgress(), mAfternoonSlider.getProgress(),
                mEveningSlider.getProgress());

        if(entryExists) {
            //Log.d("Journal","New Mood Level: "+entry.getMoodLevel());
            helper.updateEntry(entry);
            Log.d("Lifecycle","Journal entry edit");
        } else {
            helper.addEntry(entry);
            Log.d("Lifecycle","Journal entry new");
        }

        Log.d("Lifecycle","Journal Fragment stopped");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    public interface OnFinishedJournalEntryListener {
        // TODO: Update argument type and name
        void onFinishedEntry(Uri uri);
    }
}
