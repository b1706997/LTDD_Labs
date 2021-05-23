package com.example.lab9_sqlite;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class NoteListViewAdapter extends BaseAdapter {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    final ArrayList<Note> listNote;

    NoteListViewAdapter(ArrayList<Note> listNote) {
        this.listNote = listNote;
    }

    @Override
    public int getCount() {
        //Trả về tổng số phần tử, nó được gọi bởi ListView
        return listNote.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
        //có chỉ số position trong listNote
        return listNote.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Trả về một ID của phần
        return listNote.get(position).noteID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới

        View viewNote;
        if (convertView == null) {
            viewNote = View.inflate(parent.getContext(), R.layout.note_view, null);
        } else viewNote = convertView;

        //Bind sữ liệu phần tử vào View
        Note note = (Note) getItem(position);

        ((TextView) viewNote.findViewById(R.id.namenote)).setText(String.format("%s", note.name));
        ((TextView) viewNote.findViewById(R.id.placenote)).setText(String.format("%s", note.place));
        ((TextView) viewNote.findViewById(R.id.datenote)).setText(String.format("%s", note.date));


        return viewNote;
    }
}
