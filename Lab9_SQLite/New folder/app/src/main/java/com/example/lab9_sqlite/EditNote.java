package com.example.lab9_sqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditNote extends AppCompatActivity {
        boolean isupdate;
        int idnote;
        DatePicker editDate;
        EditText editPlace;
        EditText editName;
        Note note;

        NoteDbHelper noteDbHelper;

        @SuppressLint("WrongViewCast")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_note);

            noteDbHelper = new NoteDbHelper(this);

            Intent intent = getIntent();
            isupdate = intent.getBooleanExtra("isupdate", false);
            if (isupdate) {
                //Activity hoạt động biên tập dữ liệu Sản phẩm đã

                //Đọc sản phẩm

                idnote = intent.getIntExtra("idnote", 0);
                note = noteDbHelper.getNoteByID(idnote);


                findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noteDbHelper.deleteNoteByID(idnote);
                        finish();
                    }
                });


            } else {
                //Activity nhâp dữ liệu thêm Sản phẩm mới

                note = new Note(0, "", "","");
                findViewById(R.id.deleteBtn).setVisibility(View.GONE);
                ((Button) findViewById(R.id.save)).setText("Tạo ghi chú mới");
            }

            //Update to View
            editName = findViewById(R.id.namenote);
            editPlace = findViewById(R.id.placenote);
            editDate = findViewById(R.id.datenote);



            editName.setText(note.name);
            editPlace.setText(note.place + "");
            //String[] a = note.date.split("/");
            //editDate.updateDate( Integer.valueOf(a[2]),Integer.valueOf(a[1]),Integer.valueOf(a[0]));

            findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note.name = editName.getText().toString();
                    note.date = editDate.getDayOfMonth() + "/" + editDate.getMonth() + "/" + editDate.getYear();
                    note.place = editPlace.getText().toString();

                    if (isupdate) {
                        //Cập nhật
                        noteDbHelper.updateNote(note);
                    } else {
                        //Tạo
                        noteDbHelper.insertNote(note);
                    }
                    finish();
                }
            });


        }
    }

