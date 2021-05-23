package com.example.lab9_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    final int RESULT_NOTE_ACTIVITY = 1;
    ArrayList<Note> listNote;
    NoteListViewAdapter noteListViewAdapter;
    ListView listViewNote;
    NoteDbHelper noteDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteDbHelper = new NoteDbHelper(this);


        listNote = new ArrayList<>();
        loadDbNote();


        noteListViewAdapter = new NoteListViewAdapter(listNote);
        listViewNote = findViewById(R.id.listnote);
        listViewNote.setAdapter(noteListViewAdapter);


        EditText input_search = findViewById(R.id.input_find);
        input_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==0)
                    loadDbNote();
                else {
                    ArrayList<Note> rmArray = new ArrayList<>();
                    String search = s.toString();
                    for(Iterator<Note> it = listNote.iterator(); it.hasNext();) {
                        Note rmNote = it.next();
                        if(rmNote.match(search))
                        {
                            rmArray.add(rmNote);
                            System.out.println(rmNote.name);

                        }
                    }
                    listNote.clear();
                    listNote.addAll(rmArray);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(s.length()==0)
//                {
//
//                }
//                else
//                {
//                    ArrayList<Note> rmArray = new ArrayList<>();
//                    String search = s.toString();
//                    for(Iterator<Note> it = listNote.iterator(); it.hasNext();) {
//                        Note rmNote = it.next();
//                        if(rmNote.match(search))
//                        {
//                            rmArray.add(rmNote);
//                            System.out.println(rmNote.name);
//
//                        }
//                    }
//                    listNote.clear();
//                    listNote.addAll(rmArray);
//                }
            }
        });

        //Thêm dữ liệu
        findViewById(R.id.addbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("isupdate", false);
                intent.setClass(MainActivity.this, EditNote.class);
                startActivityForResult(intent, RESULT_NOTE_ACTIVITY);


            }
        });

        //Lắng nghe bắt sự kiện một phần tử danh sách được chọn, mở Activity để soạn thảo phần tử
        listViewNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = (Note) noteListViewAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("isupdate", true);
                intent.putExtra("idnote", note.noteID);
                intent.setClass(MainActivity.this, EditNote.class);
                startActivityForResult(intent, RESULT_NOTE_ACTIVITY);
            }
        });

    }


    private void loadDbNote() {
        listNote.clear();
        listNote.addAll(noteDbHelper.getAllNotes());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_NOTE_ACTIVITY:
                //Khi đóng Activity EditNote thì nạp lại dữ liệu
                loadDbNote();
                noteListViewAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }

    }


}

