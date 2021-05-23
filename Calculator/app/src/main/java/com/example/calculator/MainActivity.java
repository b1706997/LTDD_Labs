package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class Number {
    public String present;
    public Float value;
    public Number(CharSequence string) {
        this.present = ""+string;
        this.value = Float.parseFloat(this.present);
    }
    public Number(Float value,Float value2,CharSequence op) {
//            System.out.println(value);
//            System.out.println(value2);

        Float value3;
            switch(op.charAt(0)) {
            case '+':
                value3 = new Float(value+value2);
                break;
            case '-':
                value3 = new Float(value-value2);
                break;
            case 'x':
                value3=new Float(value*value2);
                break;
            default :
                value3=new Float(value/value2);
                break;
        }
//        Integer a = Integer.parseInt(value.toString());
        Integer a = value3.intValue();
        this.present = a.toString();
        this.value = value3;
    }
    public void add(CharSequence string) {
        if(!this.present.equals("0"))
            this.present=this.present+string;
        else
            this.present = ""+string;
        this.value=Float.parseFloat(this.present);
    }

    public void zero() {
        this.present = "0";
        this.value = Float.parseFloat("0");
    }
}

class Calculator {
    // Attributes
    public List<Number> numberList = new ArrayList<>();
    public List<CharSequence> op = new ArrayList<>();
    public int current;
    public TextView screen1;
    public TextView screen2;
    // Constructor
    public Calculator(TextView screen1,TextView screen2) {
        this.screen1 = screen1;
        this.screen2 = screen2;
        this.current = 0;
        this.numberList.add(new Number("0"));
    }
    public void operator(CharSequence op) {
        if(this.op.size()!=0) {
//            screen2.setText(numberList.get(0).present+this.op.get(0)+numberList.get(1).present+"=");
            Number result = new Number(numberList.get(0).value,numberList.get(1).value,this.op.get(0));
            screen2.setText(result.present+op);
            this.numberList.set(0, result);

            numberList.remove(this.numberList.get(1));
            current = 1;
            numberList.add(new Number("0"));
            this.op.remove(this.op.get(0));

            screen1.setText(this.numberList.get(current).present);
        }
        else
        {
            current++;
            numberList.add(new Number("0"));
            screen1.setText(numberList.get(current).present);
            screen2.setText(numberList.get(0).present+op);
        }
        this.op.add(op);


        // try numberList[0] + numberList[1]
//        current++;
//        numberList.add(new Number("0"));
//        screen1.setText(numberList.get(current).present);
//
//        try {
//            screen2.setText(numberList.get(current).present+op);
//        }
//        catch(Exception e) {
//
//        }
//
//
//
//        switch(op.charAt(0)) {
//            case '+':
//
//                break;
//            case '-':
//
//                break;
//            case '*':
//                break;
//            case '/':
//                break;
//        }
    }
    public void ce() {
        try {
            numberList.get(current).zero();
            screen1.setText(numberList.get(current).present);
        }
        catch(Exception e) {

        }
    }
    public void c() {
        screen2.setText("");
        this.current = 0;
        this.numberList = new ArrayList<>();
        this.op = new ArrayList<>();
        this.numberList.add(new Number("0"));
        screen1.setText(this.numberList.get(current).present);

    }
    public void input(CharSequence string) {
        numberList.get(current).add(string);

                screen1.setText(numberList.get(current).present);
        try {
            System.out.println(this.numberList.get(0).value);
            System.out.println(this.numberList.get(1).value);
        }
        catch(Exception e) {

        }
//
//        try {
//            numberList.get(current).add(string);
//        }
//        catch(Exception e) {
//            // empty array or null element
//            numberList.add(new Number(string));
//        }
//        screen1.setText(numberList.get(current).present);
//        numberList[current] = new Number(string);

        //numberList[current].add(string);
//        if(numberList.isEmpty()) {
//            numberList.add(new Number(string));
//        }
//        else {
//
//            numberList.get(current).add(string);
//        }
    }
    public void result() {
        Number result = new Number(numberList.get(0).value,numberList.get(1).value,op.get(0));
        numberList = new ArrayList<>();
        numberList.add(result);
        current = 0;
        op = new ArrayList<>();
        screen1.setText(numberList.get(current).present);
        screen2.setText("");
    }


}
public class MainActivity extends AppCompatActivity {
    private TextView screen;
    private TextView screen2;
    private Calculator cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.screen = (TextView) findViewById(R.id.screen);
        this.screen2 = (TextView) findViewById(R.id.screen2);
        this.cal = new Calculator(this.screen,this.screen2);
    }
    public void onClickBtn(View view) {
        Button button = (Button) view;
        switch(view.getId()) {
            case R.id.back:
                String string = ""+ screen.getText();
                string=string.substring(0, string.length() - 1);
                screen.setText(string);
                break;
            case R.id.ce:
                cal.ce();
                break;
            case R.id.c:
                cal.c();
                break;
            case R.id.divide:
            case R.id.multiply:
            case R.id.plus:
            case R.id.minus:
                // Equation
                cal.operator(button.getText());
                break;
            case R.id.equal:
                // Result
                cal.result();
                break;
            default:
                // Numbers
                cal.input(button.getText());
                break;
        }
    }


}