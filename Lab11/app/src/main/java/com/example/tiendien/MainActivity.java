package com.example.tiendien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tiendien.classes.SuDung;
import com.example.tiendien.classes.User;
import com.example.tiendien.models.KhachHangModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // adapter
        ArrayList<String> listItems = new ArrayList<String>();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);
        // model
        TextView label_current = findViewById(R.id.label_current);
        //KhachHangModel model = new ViewModelProvider(this).get(KhachHangModel.class);

        KhachHangModel model = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(KhachHangModel.class);
        model.getIsShowing().observe(this,isShowing->{
            if(isShowing) {
                model.getCurrentSuDung().observe(this,khachhang->{
                    if(khachhang==null){
                        // new customer
                        label_current.setText(label_current.getText()+"Chưa có lịch sử");
                        adapter.clear();
                    }
                    else
                    {
                        adapter.clear();
                        for (SuDung suDung : khachhang.listSuDung) {
                            adapter.add(new String("Ngày: "+suDung.ngay+"\nChỉ số điện: "+suDung.chisodien+"\nTiền điện: "+suDung.tiendien ));
                        }
                    }
                });
            }
            else {
                adapter.clear();
            }
        });


        // OnChange listener
        EditText editText = findViewById(R.id.input_makhachhang);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")) {
                    // empty string
                    model.setIsShowing(false);

                }
                else {
                    model.setCurrentSuDung(s.toString());
                    model.setIsShowing(true);
                    label_current.setText("Mã khách hàng: "+s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }

    public void btn_add(View view) {
        EditText makhachhang_input = findViewById(R.id.input_makhachhang);
        EditText chisodien_input = findViewById(R.id.input_chisodien);
        EditText ngay_input = findViewById(R.id.input_ngay);
        String ngay = ngay_input.getText().toString();
        int chisodien = Integer.parseInt(chisodien_input.getText().toString());
        String makhachhang = makhachhang_input.getText().toString();
        KhachHangModel model = new ViewModelProvider(this).get(KhachHangModel.class);
        model.addKhachHang(makhachhang,ngay,chisodien);


    }
}