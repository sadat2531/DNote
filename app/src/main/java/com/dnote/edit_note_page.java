package com.dnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class edit_note_page extends AppCompatActivity {

    public String Id ="";
    public long Id_l;

    private EditText edit_titleEdittext, edit_noteEdittext;

    DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note_page);
        getSupportActionBar().setTitle("Edit Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        edit_titleEdittext = (EditText) findViewById(R.id.edit_titleEditId);
        edit_noteEdittext = (EditText) findViewById(R.id.edit_noteEditId);


        // Receiving Id for indentifing the note to open for editing
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {

            long id = bundle.getLong("Id");
            Id_l = id;
            Id = String.valueOf(id);

            showTheNote();    // Show wanted note on edittext for editing

        }


    }


    // For adding menu to this page
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_note_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    // This is for performing actions according to menu option clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        // This item for saving edited note
        if(item.getItemId() == R.id.saveEditedNote_itemId){

            save_edited_note();   // Save edited note function for saving edited note

            return true;
        }

        // This item is for exiting editing page
        if(item.getItemId() == android.R.id.home){

            save_edited_note();    // Saving edited note before exiting the page using save edited note function

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    // Function for showing the note on edittext for enable editing
    public void showTheNote(){

        Cursor cursorRe = databaseAdapter.getANote(Id);    // Calling getANote function with the Id parameter from DatabaseAdapter for getting the note wanted

        cursorRe.moveToFirst();

        if(cursorRe != null){

            String Title2 = cursorRe.getString(1);
            String Note2 = cursorRe.getString(2);

            edit_titleEdittext.setText(Title2);    // Showing note title in title edittext for enable editing
            edit_noteEdittext.setText(Note2);      // Showing note in note edittext for enable editing

        }
        else{
            Toast.makeText(getApplicationContext(),"Note Is Not Received From Database",Toast.LENGTH_SHORT).show();
        }


    }



    // This function is for saving edited note
    public void save_edited_note(){

        String editedTitle = edit_titleEdittext.getText().toString();
        String editedNote = edit_noteEdittext.getText().toString();

        databaseAdapter.updateNote(Id, editedTitle, editedNote);     // Calling update note function from DatabaseAdapter with three parameter

        Toast.makeText(getApplicationContext(),"Note Is Updated", Toast.LENGTH_SHORT).show();

        // Redirecting to note view page after saving the note
        Intent intent = new Intent(edit_note_page.this, Note_view_page.class);
        intent.putExtra("Id", Id_l);    // Passing Id for indentifing note to show
        startActivity(intent);

        finish();

    }




    // This is for when back button is pressed
    @Override
    public void onBackPressed() {

        save_edited_note();    // Save edited note and return to note view page

    }

}


