package com.example.androidnotes;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class NotesAdapter extends RecyclerView.Adapter<NotesHolder>{
    private  ArrayList<Note> newNoteList;
    private final MainActivity mainAct;
    public NotesAdapter(MainActivity mainAct, ArrayList<Note> newEmpList)
    {
        this.mainAct=mainAct;
        this.newNoteList=newEmpList;
    }
    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

//        mainAct.setTitle("Android Notes("+newEmpList.size()+")");
        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new NotesHolder(itemView);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, int position) {
            Note e = newNoteList.get(position);
            String temp_noteHeading,temp_noteDescription;
            if(e.getName().length()>80)
            {
                temp_noteHeading=e.getName().substring(0,79)+"...";
            }
            else
            {
                temp_noteHeading=e.getName();
            }
            holder.empName.setText(temp_noteHeading);
        if(e.getDescription().length()>80)
        {
            temp_noteDescription=e.getDescription().substring(0,79)+"...";
        }
        else
        {
            temp_noteDescription=e.getDescription();
        }
            holder.empDept.setText(temp_noteDescription);
        Date date=new Date(e.getDate());
        DateFormat formatDate;
//        format = new SimpleDateFormat("EEE MMM dd, K:mm a").setTimeZone(TimeZone.getTimeZone("CST6CDT"));
        formatDate = new SimpleDateFormat("EEE MMM dd, K:mm a");
        formatDate.setTimeZone(TimeZone.getTimeZone("CST6CDT"));
        String formatted_DateTime = formatDate.format(date);
        holder.note_date.setText(formatted_DateTime);
//        holder.note_date.setText(DateFormat.SimpleDateFormat("EEE MMM dd, K:mm a").setTimeZone(TimeZone.getTimeZone("CST6CDT")));
////            holder.note_date.setText(new Date().toString());
//            holder.note_date.setText(Calendar.getInstance().getTimeInMillis());
            if(newNoteList.size()>0)
            {
                mainAct.setTitle("Android Notes("+newNoteList.size()+")");
            }
            else
            {
                mainAct.setTitle("Android Notes");
            }

    }

    @Override
    public int getItemCount() {
//        super.getItemCount();
        return newNoteList.size();
    }
}
