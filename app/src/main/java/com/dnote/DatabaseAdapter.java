package com.dnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseAdapter extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "Notes.db";
    private static final String TABLE_NAME = "Note";
    private static final String ID = "_id";
    private static final String TITLE = "Title";
    private static final String NOTE = "Note";
    private static final int VERSION_NUMBER = 3;
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"( "+ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+TITLE+" TEXT,"+NOTE+" TEXT);";
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    // Create Database
    public DatabaseAdapter(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    // Create table in Database
    @Override
    public void onCreate(SQLiteDatabase db) {

        try{

            db.execSQL(CREATE_TABLE);    // Executing SQL command for creating table
            Toast.makeText(context, "Database Is Created",Toast.LENGTH_SHORT).show();

        }
        catch (Exception e)
        {
            Toast.makeText(context, "Exception : "+e,Toast.LENGTH_SHORT).show();
        }

    }

    // Upgrade Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try{
            Toast.makeText(context, "onUpgrade is called",Toast.LENGTH_SHORT).show();
            db.execSQL(DROP_TABLE);        // Delete currently available table from Database
            onCreate(db);                  // Calling onCreate function
        }

        catch (Exception e)
        {
            Toast.makeText(context, "Exception : "+e,Toast.LENGTH_SHORT).show();
        }

    }


    // Function for inserting New note in table of Database
    public long insertNote(String title, String note){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();   // Creating new contentValues
        contentValues.put(TITLE, title);                    // Adding Title to contentValues
        contentValues.put(NOTE, note);                      // Adding Note to contentValues

        long rowId = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);   // Executing insert command
        return rowId;   // Returning Id for Indentifing if note is successfully added

    }


    // Function for Updating previously saved Note
    public boolean updateNote(String id, String title, String note){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();              // Creating contentValues

        Integer Id = Integer.valueOf(id);       // Converting String id to Integer Id As the ID in the table is an Integer value

        contentValues.put(ID, Id);            // Adding Id note to update
        contentValues.put(TITLE, title);      // Adding edited title
        contentValues.put(NOTE, note);        // Adding edited note

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID + " = ?", new String[]{id});        // Executing update table command
        return true;

    }

    // Function for deleting note from table
    public Integer deleteNote(String id){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, ID+ " = ?", new String[]{id});     // Executing Delete command for deleting note from table and returning database after delete note from table

    }

    // Function for getting All note from the Table
    public ArrayList<Note_type> getAllNote(){

        ArrayList<Note_type> arrayList = new ArrayList<>();            // Create a Arraylist of Note_type
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL, null);     // Getting All note data from table in a cursor

        while (cursor.moveToNext())      // Move the cursor next until the list is finish and execute commands
        {
            int Id = cursor.getInt(0);              // Get the Id from cursor
            String Title = cursor.getString(1);     // Get the Title from cursor
            String Note = cursor.getString(2);      // Get the Note from cursor
            Note_type note = new Note_type(Id, Title, Note);     // Creating a Note_type named note using Id, Title and Note

            arrayList.add(note);      // Adding Note_type note to the ArrayList

        }
        return arrayList;     // After all note is added return the arraylist for further processing

    }

    // This is for getting only a single note data from table depending on id received as parameter
    public Cursor getANote(String id){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " +ID+" = '"+id+"'";      // Query
        Cursor cursorS = sqLiteDatabase.rawQuery(SELECT_BY_ID,null);                 // Executing Query and keeping received data in a cursor

        return cursorS;     // Returning the cursor

    }




}



