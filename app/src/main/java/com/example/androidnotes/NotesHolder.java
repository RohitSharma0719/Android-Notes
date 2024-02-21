package com.example.androidnotes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesHolder extends RecyclerView.ViewHolder {
    TextView empName;
    TextView empDept;
    TextView note_date;
    public NotesHolder(@NonNull View itemView) {
        super(itemView);
        empName = itemView.findViewById(R.id.emp_ID);
        empDept = itemView.findViewById(R.id.description);
        note_date=itemView.findViewById(R.id.Note_date);
    }
}
