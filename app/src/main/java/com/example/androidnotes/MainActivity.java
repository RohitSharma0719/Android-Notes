package com.example.androidnotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
  private final ArrayList<Note> noteList =new ArrayList<>();
    private ActivityResultLauncher<Intent> activityResultLauncher;
    NotesAdapter notesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteList.clear();
        noteList.addAll(loadFile());

        recyclerView = findViewById(R.id.recycle_view);
        notesAdapter = new NotesAdapter(this, noteList);
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesAdapter.getItemCount();

        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                ,this::handleNotes);
    }
    @SuppressLint("NotifyDataSetChanged")
    private void handleNotes(ActivityResult result)
    {
        if (result == null || result.getData() == null) {
            Log.d(TAG, "handleResult: NULL ActivityResult received");
            return;
        }
        Intent data = result.getData();
        if (result.getResultCode() == RESULT_OK) {
            Note note = (Note) data.getSerializableExtra("note");
            note.setDate(System.currentTimeMillis());
            int position=(int) data.getSerializableExtra("position");
            if(position!=-5)
            {
                noteList.remove(position);
                notesAdapter.notifyDataSetChanged();
            }
            noteList.add(0, note);
            notesAdapter.notifyDataSetChanged();
            Log.d(TAG, "handleResult: NULL ActivityResult received");

        } else {
//            Toast.makeText(this, "OTHER result not OK!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        Log.d(TAG, "handleResult: NULL ActivityResult received");

        if(menuItem.getItemId()==R.id.add_note)
        {
            Intent intent = new Intent(this, NotesSecondActivity.class);
            activityResultLauncher.launch(intent);
            return true;
        }
        else if(menuItem.getItemId()==R.id.about_query)
        {
            Intent intent = new Intent(this, note_about.class);
            startActivity(intent);
            return true;
        }
        else
        {
            Log.d(TAG, "onOptionsItemSelected: ");
            return true;
        }
    }
        @Override
    protected void onResume() {

        super.onResume();
    }
    private ArrayList<Note> loadFile() {

        Log.d(TAG, "loadFile: Loading JSON File");
        ArrayList<Note> noteList = new ArrayList<>();
        try {
            InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String desc = jsonObject.getString("description");
                long date = Long.parseLong(jsonObject.getString("date"));

                Note note = new Note(name, desc);
                note.setDate(date);
                noteList.add(note);
            }

        } catch (FileNotFoundException e) {
//            Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noteList;
    }

    @Override
    protected void onPause() {
        saveNotes();
        super.onPause();
    }

    private void saveNotes() {

        Log.d(TAG, "saveNotes: Saving JSON File");

        try {
            FileOutputStream fos = getApplicationContext().
                    openFileOutput("Product.json"
                            , Context.MODE_PRIVATE);

            PrintWriter printWriter = new PrintWriter(fos);
            printWriter.print(noteList);
            printWriter.close();
            fos.close();

            Log.d(TAG, "saveNotes: JSON:\n" + noteList.toString());

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        Note m = noteList.get(pos);
        Intent intent = new Intent(this, NotesSecondActivity.class);
        intent.putExtra("note",m);
        intent.putExtra("position",pos);
        activityResultLauncher.launch(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onLongClick(View v) {
        final int pos = recyclerView.getChildLayoutPosition(v);
        Note m = noteList.get(pos);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Yes", (dialog, id) ->
        {
            if (!noteList.isEmpty()) {
                noteList.remove(pos);
                notesAdapter.notifyDataSetChanged();
                Toast.makeText(v.getContext(),"deleted '"+ m.getName()+"'", Toast.LENGTH_SHORT).show();
//                super.setTitle("Android Notes("+noteList.size()+")");
                if(noteList.size()==0)
                {
                    super.setTitle("Android Notes");
                }
                else
                {
                    super.setTitle("Android Notes("+ noteList.size()+")");
                }
            }

        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            return;
        });
        builder.setMessage("Delete Notes '"+ m.getName()+"'");
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }
    @Override
    public void onBackPressed() {
        saveNotes();
        super.onBackPressed();
    }
}