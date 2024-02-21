package com.example.androidnotes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NotesSecondActivity extends AppCompatActivity {

    private static final String TAG = "NotesSecondActivity";
    EditText Note_title;
    EditText Note_content;
    TextView noteDate;
    private Note p;
    private int position=-5;
    private String temp_Note_title="-1",temp_Note_content="-1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Note_title=findViewById(R.id.note_title);
        Note_content=findViewById(R.id.notes_typed);
        Intent intent = getIntent();
        if (intent.hasExtra("note")) {
            p = (Note) intent.getSerializableExtra("note");
            if (p != null)
                Note_title.setText(p.getName());
                Note_content.setText(p.getDescription());
                temp_Note_title=Note_title.getText().toString();
            temp_Note_content=Note_content.getText().toString();
        }else {
            Log.d(TAG, "onCreate: ");
        }
        if(intent.hasExtra("position"))
        {
            position=(int)intent.getSerializableExtra("position");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.save_note,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        Log.d(TAG, "handleResult: NULL ActivityResult received");

        if(menuItem.getItemId()==R.id.save_note) {
            saveNotes(null);
            return true;
        }
        else
            return true;
    }
    public void doReturn(View v) {
        Note_title=findViewById(R.id.note_title);
        Note_content=findViewById(R.id.notes_typed);
        noteDate=findViewById(R.id.Note_date);
        if(temp_Note_title.equals(Note_title.getText().toString()) && temp_Note_content.equals(Note_content.getText().toString()))
        {
            finish();
        }
        else
        {
            Intent intent = new Intent();
            p=new Note(Note_title.getText().toString(),Note_content.getText().toString());
            intent.putExtra("note", p);
            intent.putExtra("position",position);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
    public void saveNotes(View v) {
        if (Note_title.getText().toString().length() == 0 && Note_content.getText().toString().length() == 0) {
            finish();
        }
        if(temp_Note_title.equals(Note_title.getText().toString()) && temp_Note_content.equals(Note_content.getText().toString()))
        {
            finish();
        }
        if(Note_title.getText().toString().length() == 0 && Note_content.getText().toString().length() != 0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Ok", (dialog, id) -> {
                    Toast.makeText(this, "Note not saved!", Toast.LENGTH_LONG).show();
                    finish();});
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {

                return;
            });
            builder.setMessage("No Title, Can not save!");
            builder.setTitle("Warning");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            Note_title = findViewById(R.id.note_title);
            Note_content = findViewById(R.id.notes_typed);
            Intent intent = new Intent();
//            p = new Note(Note_title.getText().toString(), Note_content.getText().toString());
            p = new Note(Note_title.getText().toString(), Note_content.getText().toString());

            intent.putExtra("note", p);
            intent.putExtra("position", position);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void doReturntoMenu()
    {
        finish();
    }
    @Override
    public void onBackPressed() {
        Note_title = findViewById(R.id.note_title);
        Note_content = findViewById(R.id.notes_typed);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Note_title.getText().toString().length() == 0 && Note_content.getText().toString().length() == 0) {
            super.onBackPressed();
        }
        else if(Note_title.getText().toString().length() == 0 && Note_content.getText().toString().length() != 0)
        {

            builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Ok", (dialog, id) ->{
                    Toast.makeText(this, "Note not saved!", Toast.LENGTH_LONG).show();
                    super.onBackPressed();});
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {

                return;
            });
            builder.setMessage("No Title, Can not save!");
            builder.setTitle("Warning");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            if (temp_Note_title.equals(Note_title.getText().toString()) && temp_Note_content.equals(Note_content.getText().toString())) {
                super.onBackPressed();
            } else {

                builder.setPositiveButton("Yes", (dialog, id) ->
                {
                    doReturn(null);
                    super.onBackPressed();
                });
                builder.setNegativeButton("No", (dialogInterface, i) -> doReturntoMenu());
                builder.setMessage("Save Notes?");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }
}