package com.example.lesson1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView Screen;
    private Button C, Divide, Seven, Eight, Nine, Multiply, Four, Five, Six, Plus, One, Two, Three, Minus, Zero, Point, Percent, Equally, DelCharacter;
    private String input, Answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Screen = findViewById(R.id.screen);
        C = findViewById(R.id.c);
        Divide = findViewById(R.id.divide);
        Seven = findViewById(R.id.seven);
        Eight = findViewById(R.id.eight);
        Nine = findViewById(R.id.nine);
        Multiply = findViewById(R.id.multiply);
        Four = findViewById(R.id.four);
        Five = findViewById(R.id.five);
        Six = findViewById(R.id.six);
        Plus = findViewById(R.id.plus);
        One = findViewById(R.id.one);
        Two = findViewById(R.id.two);
        Three = findViewById(R.id.three);
        Minus = findViewById(R.id.minus);
        Zero = findViewById(R.id.zero);
        Point = findViewById(R.id.point);
        Percent = findViewById(R.id.percent);
        Equally = findViewById(R.id.equally);
        DelCharacter = findViewById(R.id.delCharacter);
    }

    public void ClickButton(View view) {
        Button button = (Button) view;
        String data = button.getText().toString();
        switch (data) {
            case "C":
                input = "";
                break;
            case "A":
                input += Answer;
                break;
            case "x":
                Solve();
                input += "x";
                break;
            case "=":
                Solve();
                Answer += input;
                break;
            case "del":
                String newText = input.substring(0, input.length() - 1);
                input = newText;
                break;
            default:
                if (input == null) {
                    input = "";
                }
                if (data.equals("+") || data.equals("-") || data.equals("/")) {
                    Solve();
                }
                input += data;
        }
        Screen.setText(input);
    }

    private void Solve() {
        if (input.split("x").length == 2) {
            String numder[] = input.split("x");
            try {
                double multiply = Double.parseDouble(numder[0]) * Double.parseDouble(numder[1]);
                input = multiply+"";
            } catch (Exception e) {
            }
        }

        if (input.split("/").length == 2) {
            String numder[] = input.split("/");
            try {
                double divide = Double.parseDouble(numder[0]) / Double.parseDouble(numder[1]);
                input = divide+"";
            } catch (Exception e) {
            }
        }

        if (input.split("plus").length == 2) {
            String numder[] = input.split("plus");
            try {
                double plus = Double.parseDouble(numder[0]) + Double.parseDouble(numder[1]);
                input = plus+"";
            } catch (Exception e) {
            }
        }

        if (input.split("-").length>1) {
            String numder[] = input.split("-");
            if (numder[0] == "" && numder.length == 2) {
                numder[0] = 0 + "";
            }
            try {
                double minus=0;
                if (numder.length==2){
                    minus= Double.parseDouble(numder[0]) - Double.parseDouble(numder[1]);
                }
                else if (numder.length==3){
                    minus= -Double.parseDouble(numder[1]) - Double.parseDouble(numder[2]);
                }
                input = minus+"";
            } catch (Exception e) {
            }
        }
//разделитель целого числа. что бы можнобыло поставить запятую после целого числа
        String n[] = input.split(".");
        if (n.length > 1) {
            if (n[1].equals("0")) {
                input = n[0];
            }
        }
        Screen.setText(input);


    }
}