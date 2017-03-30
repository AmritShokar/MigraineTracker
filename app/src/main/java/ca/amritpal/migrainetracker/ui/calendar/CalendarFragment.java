package ca.amritpal.migrainetracker.ui.calendar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import ca.amritpal.migrainetracker.R;
import ca.amritpal.migrainetracker.ui.entry.JournalFragment;

import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarFragment.OnEditJournalEntryListener} interface
 * to handle interaction events.
 * Use the {@link CalendarFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private CalendarView mCalendarView;
    private Button mEditJournalButton;

    private OnEditJournalEntryListener mListener;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mCalendarView = (CalendarView) View.findViewById(R.id.calendar_view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mCalendarFragmentView = inflater.inflate(R.layout.main_calendar_fragment, container, false);

        mCalendarView = (CalendarView) mCalendarFragmentView.findViewById(R.id.calendar_view);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast selectedDateToast = Toast.makeText(getActivity(), "DayOfMonth: "+dayOfMonth, Toast.LENGTH_SHORT);
                selectedDateToast.show();
                checkSelectedDate();
                //Log.d("Calendar", "Date Selected");
            }
        });

        mEditJournalButton = (Button) mCalendarFragmentView.findViewById(R.id.edit_journal_button);

        mEditJournalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(Uri.parse("http://test.com"));
            }
        });

        // Inflate the layout for this fragment
        return mCalendarFragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onEditJournal(uri);
        }
        else {
            Log.d("mListener","mListener set to null");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEditJournalEntryListener) {
            mListener = (OnEditJournalEntryListener) context;
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void checkSelectedDate() {

        mCalendarView.get

        long selectedDateLong = mCalendarView.getDate();
        Date selectedDate = new Date(selectedDateLong);

        int day = selectedDate.getDay();
        int month = selectedDate.getMonth();
        int year = selectedDate.getYear();

        Log.d("Calendar",day+"-"+month+"-"+year);

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
    public interface OnEditJournalEntryListener {
        // TODO: Update argument type and name
        void onEditJournal(Uri uri);
    }
}
