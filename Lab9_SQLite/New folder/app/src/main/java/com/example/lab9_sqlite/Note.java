package com.example.lab9_sqlite;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Note {
    String name;
    String date;
    String place;
    int noteID;


    public Note(int noteID, String name, String date,String place) {
        this.name = name;
        this.date = date;
        this.noteID = noteID;
        this.place = place;
    }
    public boolean match(String name) {
        Pattern p = Pattern.compile(name);
        Matcher m = p.matcher(this.name);
        return m.find();
    }

}
