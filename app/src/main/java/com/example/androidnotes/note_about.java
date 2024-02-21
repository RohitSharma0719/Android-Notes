package com.example.androidnotes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class note_about extends AppCompatActivity {

    TextView heading;
    TextView author;
    TextView version;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.efwrfwsr);
        setContentView(R.layout.activity_note_about);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        heading=findViewById(R.id.about_head);
        author=findViewById(R.id.about_author);
        version=findViewById(R.id.about_version);
        heading.setText("Android Notes");
        author.setText("\u00a9 2022, Rohit Sharma");
        version.setText("Version 1.0");
    }
}