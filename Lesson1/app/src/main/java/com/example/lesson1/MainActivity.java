package com.example.lesson1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView screen;
    private Button c;
    private Button divide;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button multiply;
    private Button four;
    private Button five;
    private Button six;
    private Button plus;
    private Button one;
    private Button two;
    private Button three;
    private Button minus;
    private Button zero;
    private Button point;
    private Button percent;
    private Button equally;
    private Button delCharacter;
    private Button night;
    private String input;
    private String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        screen = findViewById(R.id.screen);
        c = findViewById(R.id.c);
        divide = findViewById(R.id.divide);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        multiply = findViewById(R.id.multiply);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        plus = findViewById(R.id.plus);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        minus = findViewById(R.id.minus);
        zero = findViewById(R.id.zero);
        point = findViewById(R.id.point);
        percent = findViewById(R.id.percent);
        equally = findViewById(R.id.equally);
        delCharacter = findViewById(R.id.delCharacter);
        night = findViewById(R.id.night);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.night:
                Intent intent = new Intent(this, ActivitySettings.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void ClickButton(View view) {

        final TextView textView = findViewById(R.id.screen);
        final EditText editText = findViewById(R.id.screen);
        String name = editText.getText().toString();

        final Button BUTTON;
        final String DATA;
        BUTTON = (Button) view;
        DATA = BUTTON.getText().toString();
        switch (DATA) {
            case "C":
                input = "";
                break;
            case "A":
                input += answer;
                break;
            case "x":
                Solve();
                input += "x";
                break;
            case "=":
                Solve();
                answer += input;
                break;
            case "%":
                Solve();
                input += "%";
                break;
            case "del":
                String newText = input.substring(0, input.length() - 1);
                input = newText;
                break;
            default:
                if (input == null) {
                    input = "";
                }
                if (DATA.equals("+") || DATA.equals("-") || DATA.equals("/")) {
                    Solve();
                }
                input += DATA;
        }
        screen.setText(input);
    }

    private void Solve() {
        if (input.split("x").length == 2) {
            String numder[] = input.split("x");
            try {
                double multiply = Double.parseDouble(numder[0]) * Double.parseDouble(numder[1]);
                input = multiply + "";
            } catch (Exception e) {
            }
        }

        if (input.split("/").length == 2) {
            String numder[] = input.split("/");
            try {
                double divide = Double.parseDouble(numder[0]) / Double.parseDouble(numder[1]);
                input = divide + "";
            } catch (Exception e) {
            }
        }

        if (input.split("\\+").length == 2) {
            String numder[] = input.split("\\+");
            try {
                double plus = Double.parseDouble(numder[0]) + Double.parseDouble(numder[1]);
                input = plus + "";
            } catch (Exception e) {
            }
        }

        if (input.split("-").length > 1) {
            String numder[] = input.split("-");
            if (numder[0] == "" && numder.length == 2) {
                numder[0] = 0 + "";
            }
            try {
                double minus = 0;
                if (numder.length == 2) {
                    minus = Double.parseDouble(numder[0]) - Double.parseDouble(numder[1]);
                } else if (numder.length == 3) {
                    minus = -Double.parseDouble(numder[1]) - Double.parseDouble(numder[2]);
                }
                input = minus + "";
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
        screen.setText(input);
    }

    public void percentEvent(View view) {
        try {
            double percent = Double.parseDouble(screen.getText().toString()) / 100;
            screen.setText(percent + "");
        } catch (Exception e) {
        }
    }
}
