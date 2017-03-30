package ca.amritpal.migrainetracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by amrit on 3/29/2017.
 */

public class EntryDatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "EntryDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_ENTRY = "entries";

    // Entry Table Columns
    private static final String KEY_ENTRY_ID = "id";
    private static final String KEY_ENTRY_DATE = "date";
    private static final String KEY_ENTRY_MOOD = "mood";

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
                KEY_ENTRY_MOOD + " INTEGER " +
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

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ENTRY_DATE, entry.getDate());
            values.put(KEY_ENTRY_MOOD, entry.getMoodLevel());

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

}
