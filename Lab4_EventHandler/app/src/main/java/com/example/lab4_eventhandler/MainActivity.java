package com.example.lab4_eventhandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //4
    ImageView img_hinh;
    Button btn_cam;
    Button btn_lam;
    //5
    class MauTim implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            img_hinh.setBackgroundColor(getResources().getColor(R.color.tim));
        }
    }
    @Override
    public void onClick(View v) {
        if(v==btn_cam) {
            img_hinh.setBackgroundColor(getResources().getColor(R.color.cam));
        }
        if(v==btn_lam){
            img_hinh.setBackgroundColor(getResources().getColor(R.color.lam));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.img_hinh=findViewById(R.id.img_hinh);
        this.btn_lam=findViewById(R.id.btn_lam);
        this.btn_cam=findViewById(R.id.btn_cam);
        //2
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(v==findViewById(R.id.btn_vang)) {
                    findViewById(R.id.img_hinh).setBackgroundColor(getResources().getColor(R.color.vang));
                }
            }
        };
        findViewById(R.id.btn_vang).setOnClickListener(listener);
        //3
        btn_cam.setOnClickListener(this);
        btn_lam.setOnClickListener(this);
        //4
        findViewById(R.id.btn_xanhla).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_hinh.setBackgroundColor(getResources().getColor(R.color.xanhla));
            }
        });
        //5
        findViewById(R.id.btn_tim).setOnClickListener(new MauTim());
    }
    // 1
    public void click_do(View view) {
         findViewById(R.id.img_hinh).setBackgroundColor(getResources().getColor(R.color.maudo));
    }

}