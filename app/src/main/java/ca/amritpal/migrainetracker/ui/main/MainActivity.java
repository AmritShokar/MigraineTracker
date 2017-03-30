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

public class MainActivity extends AppCompatActivity implements CalendarFragment.OnEditJournalEntryListener, JournalFragment.OnFinishedJournalEntryListener{

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
    public void onEditJournal(int day, int month, int year) {
        Log.d("FragmentInteraction","Calendar test succeeded");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Log.d("FragmentTransaction", "Journal fragment start: "+day+"-"+month+"-"+year);
        Bundle bundle = new Bundle();
        bundle.putInt("day", day);
        bundle.putInt("month", month);
        bundle.putInt("year", year);
        JournalFragment journalFragment = new JournalFragment();
        journalFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.main_layout_container, journalFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onFinishedEntry(Uri uri) {
        Log.d("FragmentInteraction","Journal test succeeded");
    }
}
