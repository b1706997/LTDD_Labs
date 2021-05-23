package com.example.lab7_progresstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    public ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lv = (ListView) findViewById(R.id.lv);
        String[] empty_array = new String[] {};
        // Create a List from String Array elements
        final List<String> itemList= new ArrayList<String>(Arrays.asList(empty_array));
        // Create an ArrayAdapter from List

//        this.arrayAdapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, this.itemList);

//        this.arrayAdapter = new ArrayAdapter<String>
//                (this, R.layout.item,R.id.item_id, this.itemList);

        // DataBind ListView with items from ArrayAdapter
        this.arrayAdapter = new UsersAdapter(this,itemList);
        lv.setAdapter(arrayAdapter);

    }
    public void click_ok(View view) {
        EditText a = findViewById(R.id.txtName);
        String name = a.getText().toString();
        RadioGroup radio = findViewById(R.id.radioGroup);
        int selectedId = radio.getCheckedRadioButtonId();
        RadioButton gioitinhButton = findViewById(selectedId);
        String gioitinh = gioitinhButton.getText().toString();
        Spinner comboBox = findViewById(R.id.spinner);
        String lop = comboBox.getSelectedItem().toString();
        CheckBox check1 =  findViewById(R.id.checkBox);
        CheckBox check2 =  findViewById(R.id.checkBox2);
        CheckBox check3 =  findViewById(R.id.checkBox3);
        CheckBox check4 =  findViewById(R.id.checkBox4);
        String ngonngu = "";
        if(check1.isChecked()) {
            ngonngu+= check1.getText()+" ";
        }
        if(check2.isChecked()) {
            ngonngu+= check2.getText()+" ";
        }
        if(check3.isChecked()) {
            ngonngu+= check3.getText()+" ";
        }
        if(check4.isChecked()) {
            ngonngu+= check4.getText()+" ";
        }

        String text = "Tên: " + name+
                "\n Giới tính: "+gioitinh+
                "\n Lớp: "+lop+
                "\n Ngôn ngữ: "+ngonngu;
//        Toast.makeText(getApplicationContext(),  text, Toast.LENGTH_LONG).show();
        this.arrayAdapter.add(text);
    }



    public class UsersAdapter extends ArrayAdapter<String> {
        public UsersAdapter(Context context, List<String> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            String data = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.item_id);
            // Populate the data into the template view using the data object
            tvName.setText(data);
            // Return the completed view to render on screen
            Button btn = (Button) convertView.findViewById(R.id.removeButton);
            btn.setTag(position);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();
                    remove(getItem(position));
                }
            });
            return convertView;
        }
    }


}