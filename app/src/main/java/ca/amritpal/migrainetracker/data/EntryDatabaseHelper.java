package ca.amritpal.migrainetracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by amrit on 3/29/2017.
 */

public class EntryDatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "EntryDatabase";
    private static final int DATABASE_VERSION = 3;

    // Table Names
    private static final String TABLE_ENTRY = "entries";

    // Entry Table Columns
    private static final String KEY_ENTRY_ID = "id";
    private static final String KEY_ENTRY_DATE = "date";
    private static final String KEY_ENTRY_MORNING = "morning";
    private static final String KEY_ENTRY_AFTERNOON = "afternoon";
    private static final String KEY_ENTRY_EVENING = "evening";

    private static EntryDatabaseHelper sInstance;

    public static synchronized EntryDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new EntryDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private EntryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ENTRY_TABLE = "CREATE TABLE " + TABLE_ENTRY +
                "(" +
                KEY_ENTRY_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_ENTRY_DATE + " TEXT," +
                KEY_ENTRY_MORNING + " INTEGER, " +
                KEY_ENTRY_AFTERNOON + " INTEGER, " +
                KEY_ENTRY_EVENING + " INTEGER " +
                ")";

        //Example of foreign key use
        //KEY_POST_USER_ID_FK + " INTEGER REFERENCES " + TABLE_USERS + "," + // Define a foreign key

        db.execSQL(CREATE_ENTRY_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRY);
            onCreate(db);
        }
    }

    // Insert a post into the database
    public void addEntry(Entry entry) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();
        boolean exists = false;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ENTRY_DATE, entry.getDate());
            values.put(KEY_ENTRY_MORNING, entry.getMorningLevel());
            values.put(KEY_ENTRY_AFTERNOON, entry.getAfternoonLevel());
            values.put(KEY_ENTRY_EVENING, entry.getEveningLevel());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_ENTRY, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("SQLite", "Error while trying to add post to database");
        } finally {
            db.endTransaction();
            Log.d("SQLite", "Entry insert was successful");
        }
    }

    public void updateEntry(Entry updatedEntry) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ENTRY_MORNING, updatedEntry.getMorningLevel());
            values.put(KEY_ENTRY_AFTERNOON, updatedEntry.getAfternoonLevel());
            values.put(KEY_ENTRY_EVENING, updatedEntry.getEveningLevel());

            int rows = db.update(TABLE_ENTRY, values, KEY_ENTRY_DATE+"=?", new String[]{updatedEntry.getDate()});
            Log.d("SQLite", "Number of rows updated: "+rows);
        } catch (Exception e) {
            Log.d("SQLite","Error while trying to update existing entry");
        }
    }

    public boolean checkForEntry(String selectedDate) {
        SQLiteDatabase db = getWritableDatabase();
        boolean entryExists = false;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ENTRY_DATE, selectedDate);

            int rows = db.update(TABLE_ENTRY, values, KEY_ENTRY_DATE+"=?", new String[]{selectedDate});

            if (rows==1) {
                entryExists = true;
            }

        } catch (Exception e) {
            Log.d("SQLite","Error while checking for selected date");
        } finally {
            db.endTransaction();
        }

        return entryExists;
    }

    public Entry retrieveEntry(String date) {
        SQLiteDatabase db = getReadableDatabase();

        String ENTRY_SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s.%s = '%s'",
                TABLE_ENTRY,
                TABLE_ENTRY, KEY_ENTRY_DATE,
                date);
        Cursor cursor = db.rawQuery(ENTRY_SELECT_QUERY, null);
        Entry currEntry = null;

        try {
            if(cursor.moveToNext()) {
                do {
                    currEntry = new Entry();
                    currEntry.setDate(date);
                    int morningLevel = cursor.getInt(cursor.getColumnIndex(KEY_ENTRY_MORNING));
                    currEntry.setMorningLevel(morningLevel);
                    int afternoonLevel = cursor.getInt(cursor.getColumnIndex(KEY_ENTRY_AFTERNOON));
                    currEntry.setAfternoonLevel(afternoonLevel);
                    int eveningLevel = cursor.getInt(cursor.getColumnIndex(KEY_ENTRY_EVENING));
                    currEntry.setEveningLevel(eveningLevel);
                } while (cursor.moveToNext());
            }
            else {
                Log.d("SQLite","Entry not found");
            }
        } catch (Exception e) {
            Log.d("SQLite","Error while retrieving entry data");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return currEntry;
    }

    public void clearEntries() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_ENTRY, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("SQLite", "Error while trying to delete all entries");
        } finally {
            db.endTransaction();
        }
    }
}


