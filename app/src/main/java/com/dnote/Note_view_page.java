package com.dnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Note_view_page extends AppCompatActivity {

    public String Id = "";
    public long Id_l;

    private TextView titleView, noteView;

    DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view_page);
        getSupportActionBar().setTitle("Note");                    // Set page title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);     // Set back Button on Title bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);     // Enable the back Button on Title bar

        databaseAdapter = new DatabaseAdapter(this);
        SQLiteDatabase sqLiteDatabase = databaseAdapter.getReadableDatabase();

        titleView = (TextView) findViewById(R.id.titleViewId);
        noteView = (TextView) findViewById(R.id.noteViewId);


        // Receiving Id for indentifing the note to open
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {

            long id = bundle.getLong("Id");
            Id_l = id;
            Id = String.valueOf(id);

            showTheNote();     // Calling the function for showing the note wanted

        }


    }



    // For adding menu to this page
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.note_view_page_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


    // This is for performing actions according to menu option clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // This item is for redirecting to Edit page
        if(item.getItemId() == R.id.editNote_itemId){

            // Redirecting to edit note page
            Intent intent = new Intent(Note_view_page.this, edit_note_page.class);
            intent.putExtra("Id", Id_l);    // Passing Id for indentifing note needed to be edited
            startActivity(intent);
            finish();

            return true;
        }

        // This item is for deleting currently open note
        if(item.getItemId() == R.id.deleteNote_itemId){

            delete();   // Calling Delete function for deleting note

            return true;
        }

        // This item is for the Title bar back button action
        if(item.getItemId() == android.R.id.home){

            // Redirecting to main page
            Intent intent = new Intent(Note_view_page.this, MainActivity.class);
            startActivity(intent);
            finish();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }





    // This is function for showing the note wanted
    public void showTheNote(){

        Cursor cursorR = databaseAdapter.getANote(Id);    // Calling getANote function with the Id parameter from DatabaseAdapter for getting the note wanted

        cursorR.moveToFirst();

        if(cursorR != null){

            String Title1 = cursorR.getString(1);
            String Note1 = cursorR.getString(2);

            titleView.setText(Title1);   // Showing note title in title textview
            noteView.setText(Note1);     // Showing note in note textview

        }

        else{
            Toast.makeText(getApplicationContext(),"Note Is Not Received From Database",Toast.LENGTH_SHORT).show();
        }

    }



    // This is for when back button is pressed
    @Override
    public void onBackPressed() {

        // Redirecting to main page on back button pressed
        Intent intent = new Intent(Note_view_page.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


    // This is function for Deleting current note
    // It shows a alert dialog for confirming delete
    public void delete(){

        AlertDialog.Builder alartDialogBuilder;
        alartDialogBuilder = new AlertDialog.Builder(Note_view_page.this);

        alartDialogBuilder.setIcon(R.drawable.warning);
        alartDialogBuilder.setTitle(R.string.AlertTitle);
        alartDialogBuilder.setMessage(R.string.AlertMessage2);


        // Delete the note if Yes is clicked
        alartDialogBuilder.setPositiveButton(R.string.PositiveButtonTxt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                databaseAdapter.deleteNote(Id);     // Calling delete function with Id parameter from DatabaseAdapter
                Toast.makeText(Note_view_page.this,R.string.ToastTxt2,Toast.LENGTH_SHORT).show();

                // Redirecting to main page / home page after note is deleted
                Intent intent = new Intent(Note_view_page.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });

        alartDialogBuilder.setNegativeButton(R.string.NegativeButtonTxt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(Note_view_page.this,R.string.ToastTxt5,Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
        });

        AlertDialog alertDialog = alartDialogBuilder.create();
        alertDialog.show();

    }




}


