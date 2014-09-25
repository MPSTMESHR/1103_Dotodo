
package com.android.dotodo;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * Simple reminder database access helper class. 
 * Defines the basic CRUD operations (Create, Read, Update, Delete)
 * for the example, and gives the ability to list all reminders as well as
 * retrieve or modify a specific reminder.
 * 
 */
public class RemindersDbAdapter {

	//
	// Databsae Related Constants
	//
	private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "reminders";
    private static final String DATABASE_TABLE1 = "notes";
    
    private static final int DATABASE_VERSION = 3;
    
	public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
   
    public static final String KEY_DATE_TIME = "reminder_date_time"; 
    public static final String KEY_ROWID = "_id";
    
    
    private static final String TAG = "ReminderDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context context;
    /**
     * Database creation SQL statement
     */
    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " ("
            		+ KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_TITLE + " text not null, " 
                    + KEY_BODY + " text not null, "
                 
                    + KEY_DATE_TIME + " text not null);"; 
    
    
    
    public static final String KEY_BODY1 = "body1";
    public static final String KEY_ROWID1 = "_id1";
    
    
    private static final String DATABASE_CREATE1 =
            "create table " + DATABASE_TABLE1 + " ("
            		+ KEY_ROWID1 + " integer primary key autoincrement, "
                   
                    
                 
                    + KEY_BODY1 + " text not null);"; 
    

   
    private static class DatabaseHelper extends SQLiteOpenHelper {

    	public DatabaseHelper(Context context, String name, CursorFactory factory, int version){
			super(context, name, factory, version);
		}

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE1);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE1);
            
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
     public RemindersDbAdapter(Context context) {
		// TODO Auto-generated constructor stub
    	this.context = context;
    
    }
		
	
    /**
     * Open the database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public RemindersDbAdapter open(){
    	mDbHelper = new DatabaseHelper(context, this.DATABASE_NAME, null, this.DATABASE_VERSION);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
    
    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new reminder using the title, body and reminder date time provided. 
     * If the reminder is  successfully created return the new rowId
     * for that reminder, otherwise return a -1 to indicate failure.
     * 
     * @param title the title of the reminder
     * @param body the body of the reminder
     * @param reminderDateTime the date and time the reminder should remind the user
     * @return rowId or -1 if failed
     */
    public long createReminder(String title, String body, String reminderDateTime) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_BODY, body);
        initialValues.put(KEY_DATE_TIME, reminderDateTime); 

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    
    public long createNote(String body1) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_BODY1, body1);
        
        return mDb.insert(DATABASE_TABLE1, null, initialValues);
    }

    
    /**
     * Delete the reminder with the given rowId
     * 
     * @param rowId id of reminder to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteReminder(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean deleteNote(long rowId1) {

        return mDb.delete(DATABASE_TABLE1, KEY_ROWID1 + "=" + rowId1, null) > 0;
    }

    
    /**
     * Return a Cursor over the list of all reminders in the database
     * 
     * @return Cursor over all reminders
     */
    public Cursor fetchAllReminders() {

        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                KEY_BODY, KEY_DATE_TIME}, null, null, null, null, null);
    }


    public Cursor fetchAllNote() {

        return mDb.query(DATABASE_TABLE1, new String[] {KEY_ROWID1,KEY_BODY1}, null, null, null, null, null);
    }


    /**
     * Return a Cursor positioned at the reminder that matches the given rowId
     * 
     * @param rowId id of reminder to retrieve
     * @return Cursor positioned to matching reminder, if found
     * @throws SQLException if reminder could not be found/retrieved
     */
    public Cursor fetchReminder(long rowId) throws SQLException {

        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                        KEY_TITLE, KEY_BODY, KEY_DATE_TIME}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchNote(long rowId) throws SQLException {

        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE1, new String[] {KEY_ROWID1,
                        KEY_BODY1}, KEY_ROWID1 + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    
    public boolean updateReminder(long rowId, String title, String body, String reminderDateTime) {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_BODY, body);
        args.put(KEY_DATE_TIME, reminderDateTime);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean updateNote(long rowId1, String body1) {
        ContentValues args = new ContentValues();
        args.put(KEY_BODY1, body1);
        
        return mDb.update(DATABASE_TABLE1, args, KEY_ROWID1 + "=" + rowId1, null) > 0;
    }







}



