package com.dnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class Add_note_page extends AppCompatActivity {


    private EditText titleEdittext, noteEdittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_page);
        getSupportActionBar().setTitle("Add Note");              // Set page title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);    // Set back Button on Title bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);    // Enable the back Button on Title bar


        titleEdittext = (EditText) findViewById(R.id.titleEditId);
        noteEdittext = (EditText) findViewById(R.id.noteEditId);


    }

    // For adding menu to this page
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.add_note_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


    // This is for performing actions according to menu option clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // This item is for Saving note
        if(item.getItemId() == R.id.saveNote_itemId){

            saveNote();    // Calling saveNote function

            return true;
        }


        // This item is for the Title bar back button action
        if(item.getItemId() == android.R.id.home){

            saveNoteAndGoToHome();      // Calling saveNoteAndGoToHome function

            return true;
        }


        return super.onOptionsItemSelected(item);
    }



    // This function is for save note and show it on note view page
    public void saveNote(){

        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);

        String title = titleEdittext.getText().toString();
        String note = noteEdittext.getText().toString();


        long rowId = databaseAdapter.insertNote(title, note);    // Insert note function from DatabaseAdapter for inserting new note
        if(rowId==-1)
        {
            Toast.makeText(getApplicationContext(), "Note Is Not Saved",Toast.LENGTH_SHORT).show();

        }
        else
        {

            Toast.makeText(getApplicationContext(), "Note Is Saved" ,Toast.LENGTH_SHORT).show();

        }

        // Redirecting to note view page
        Intent intent = new Intent(Add_note_page.this, Note_view_page.class);
        intent.putExtra("Id", rowId);     // passing Id to note view page for indentifing the note to show
        startActivity(intent);

        finish();

    }


    // This function is for save note and go back to main page/ home page
    public void saveNoteAndGoToHome(){

        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);

        String title = titleEdittext.getText().toString();
        String note = noteEdittext.getText().toString();


        long rowId = databaseAdapter.insertNote(title, note);    // Insert note function from DatabaseAdapter for inserting new note
        if(rowId==-1)
        {
            Toast.makeText(getApplicationContext(), "Note Is Not Saved",Toast.LENGTH_SHORT).show();

        }
        else
        {

            Toast.makeText(getApplicationContext(), "Note Is Saved" ,Toast.LENGTH_SHORT).show();

        }

        // Redirecting to main page
        Intent intent = new Intent(Add_note_page.this, MainActivity.class);
        startActivity(intent);
        finish();

    }



    // This is for when back button is pressed
    @Override
    public void onBackPressed() {

        saveNoteAndGoToHome();       // Calling saveNoteAndGoToHome function

    }


}


