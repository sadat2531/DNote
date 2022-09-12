package com.dnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class About_app extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        getSupportActionBar().setTitle("About app");                // Set page Title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      // Set back Button on Title bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);      // Enable the back Button on Title bar


    }

    // This is for performing actions when the Title bar back button is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item){


        // This item is for the Title bar back button action
        if(item.getItemId() == android.R.id.home){

            // Redirecting to main page
            Intent intent = new Intent(About_app.this, MainActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // This is for when back button is pressed
    @Override
    public void onBackPressed() {

        // Redirecting to main page
        Intent intent = new Intent(About_app.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}