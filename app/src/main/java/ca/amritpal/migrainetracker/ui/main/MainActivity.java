package ca.amritpal.migrainetracker.ui.main;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.stetho.Stetho;

import ca.amritpal.migrainetracker.R;
import ca.amritpal.migrainetracker.data.EntryDatabaseHelper;
import ca.amritpal.migrainetracker.ui.calendar.CalendarFragment;
import ca.amritpal.migrainetracker.ui.entry.JournalFragment;
import ca.amritpal.migrainetracker.ui.list.TriggerFragment;

public class MainActivity extends AppCompatActivity implements CalendarFragment.OnEditJournalEntryListener, JournalFragment.OnFinishedJournalEntryListener,
        TriggerFragment.OnFinishedTriggerSelectionListener {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        //EntryDatabaseHelper helper = EntryDatabaseHelper.getInstance(getApplicationContext());
        //helper.clearEntries();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.main_layout_container, new CalendarFragment());
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LifeCycle","OnStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle","OnPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle","OnResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle","OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle","OnDestroy");
    }

    @Override
    public void onEditJournal(int day, int month, int year, boolean entryExists) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Log.d("FragmentTransaction", "Journal fragment start: "+day+"-"+month+"-"+year);
        Bundle bundle = new Bundle();
        bundle.putInt("day", day);
        bundle.putInt("month", month);
        bundle.putInt("year", year);
        bundle.putBoolean("exists", entryExists);
        JournalFragment journalFragment = new JournalFragment();
        journalFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.main_layout_container, journalFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Log.d("FragmentInteraction","Calendar test succeeded");
    }

    @Override
    public void onFinishedEntry(String date) {
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        TriggerFragment triggerFragment = new TriggerFragment();
        triggerFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout_container, triggerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Log.d("FragmentInteraction","Journal test succeeded");
    }

    @Override
    public void onTriggerSelection() {
        Log.d("FragmentInteraction","SelectedTrigger test succeeded");
    }
}
