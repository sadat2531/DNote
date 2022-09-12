package com.dnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listview;

    ArrayList<Note_type> arrayList;
    DatabaseAdapter databaseAdapter;
    custom_Listview_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Notes");  // Set page title

        databaseAdapter = new DatabaseAdapter(this);
        SQLiteDatabase sqLiteDatabase = databaseAdapter.getReadableDatabase();

        listview = (ListView) findViewById(R.id.listviewId);


        arrayList = new ArrayList<>();


        loadNoteInListview();   // Calling this function for load all notes to the list view of main page


        // This on item click listener is added for show the saved note which is clicked
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Going to note view page for showing full note
                Intent intent = new Intent(MainActivity.this, Note_view_page.class);
                intent.putExtra("Id", id);   // passing Id to note view page for indentifing the note to open
                startActivity(intent);
                finish(); // finish the current activity

            }
        });



    }

    // This is for adding menu to this page
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


    // This is for performing actions according to menu option clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // This item is for the Add Note button
        if(item.getItemId() == R.id.addNote_itemId){

            // Redirecting to Add Note page
            Intent intent = new Intent(MainActivity.this, Add_note_page.class);
            startActivity(intent);
            finish();

            return true;
        }

        // This item is for showing About App page
        if(item.getItemId() == R.id.aboutApp_itemId){

            // Redirecting to About App page
            Intent intent = new Intent(MainActivity.this, About_app.class);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // This function is for showing all notes in list view of main page/ home page
    private void loadNoteInListview() {

        arrayList = databaseAdapter.getAllNote();                         // Get a Arraylist from getAllNote function of DatabaseAdapter
        adapter = new custom_Listview_adapter(this, arrayList);    // Pass the arraylist using parameter to custom_listview_adapter and receive view
        listview.setAdapter(adapter);                                     // Set the view to the listview
        adapter.notifyDataSetChanged();

    }



    // This function is for showing a confirmation dialog box before exiting the app
    public void exit(){

        AlertDialog.Builder alartDialogBuilder;
        alartDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        alartDialogBuilder.setIcon(R.drawable.warning);
        alartDialogBuilder.setTitle(R.string.AlertTitle);
        alartDialogBuilder.setMessage(R.string.AlertMessage);
        alartDialogBuilder.setPositiveButton(R.string.PositiveButtonTxt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(MainActivity.this,R.string.ToastTxt4,Toast.LENGTH_SHORT).show();
                dialog.cancel();
                finish();

            }
        });

        alartDialogBuilder.setNegativeButton(R.string.NegativeButtonTxt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(MainActivity.this,R.string.ToastTxt5,Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
        });

        AlertDialog alertDialog = alartDialogBuilder.create();
        alertDialog.show();

    }



    // This is for when back button is pressed
    @Override
    public void onBackPressed() {

        exit();

    }


}


