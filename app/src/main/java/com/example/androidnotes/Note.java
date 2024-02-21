package com.example.androidnotes;

import android.util.JsonWriter;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

public class Note implements Serializable {

    private  String name;
    private  String description;
    private  long date=0;
    public Note(String name, String description) {
        this.name = name;
        this.description = description;
    }
   void setDate(long date)
   {
       this.date=date;
   }
    String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    long getDate(){
        return date;
    }
    @NonNull
    public String toString() {

        try {
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(sw);
            jsonWriter.setIndent("  ");
            jsonWriter.beginObject();
            jsonWriter.name("name").value(getName());
            jsonWriter.name("description").value(getDescription());
            jsonWriter.name("date").value(getDate());
            jsonWriter.endObject();
            jsonWriter.close();
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
