package com.example.lesson1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView Screen;
    private Button C, Divide, Seven, Eight, Nine, Multiply, Four, Five, Six, Plus, One, Two, Three, Minus, Zero, Point, Percent, Equally;
    private String input, Answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Screen=findViewById(R.id.screen);
        C=findViewById(R.id.c);
        Divide=findViewById(R.id.divide);
        Seven=findViewById(R.id.seven);
        Eight=findViewById(R.id.eight);
        Nine=findViewById(R.id.nine);
        Multiply=findViewById(R.id.multiply);
        Four=findViewById(R.id.four);
        Five=findViewById(R.id.five);
        Six=findViewById(R.id.six);
        Plus=findViewById(R.id.plus);
        One=findViewById(R.id.one);
        Two=findViewById(R.id.two);
        Three=findViewById(R.id.three);
        Minus=findViewById(R.id.minus);
        Zero=findViewById(R.id.zero);
        Point=findViewById(R.id.point);
        Percent=findViewById(R.id.percent);
        Equally=findViewById(R.id.equally);

    }
}