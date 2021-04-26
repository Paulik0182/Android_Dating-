package com.example.lesson1;
/**
 * 1. Создать проект со следующими пользовательскими элементами: TextView, EditText, Button, Switch, CheckBox, ToggleButton.
 * Для работы использовать LinearLayout. Использовать различные шрифты, цвета, размеры, прочие атрибуты.
 * 2. Создать ещё один макет (если не получается, то проект) с несколькими EditText, где использовать атрибут inputType:
 * text, textPersonName, phone, number, textPassword, textEmailAddress и другие значения.
 * 3. Добавить в проект элемент CalendarView.
 * 4. * Разобраться, что такое параметр ems.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;
    private boolean startStop = true;
    private Button button2;
    private int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout1 = findViewById(R.id.linearLayout1);
        layout2 = findViewById(R.id.linearLayout2);
        layout3 = findViewById(R.id.linearLayout3);
        button2 = findViewById(R.id.button2);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> Toast.makeText(MainActivity.this, "Привет", Toast.LENGTH_SHORT).show());
    }

    @SuppressLint("SetTextI18n")
    public void onClickStart(View view) {
        if (!startStop) {
            button2.setText("STOP");
            startStop = true;
            new Thread(() -> {
                while (startStop) {
                    counter++;
                    switch (counter) {
                        case 1:
                            layout1.setBackgroundColor(getResources().getColor(R.color.green));
                            layout2.setBackgroundColor(getResources().getColor(R.color.yellow));
                            layout3.setBackgroundColor(getResources().getColor(R.color.red));
                            break;
                        case 2:
                            layout1.setBackgroundColor(getResources().getColor(R.color.red));
                            layout2.setBackgroundColor(getResources().getColor(R.color.green));
                            layout3.setBackgroundColor(getResources().getColor(R.color.yellow));
                            break;
                        case 3:
                            layout1.setBackgroundColor(getResources().getColor(R.color.yellow));
                            layout2.setBackgroundColor(getResources().getColor(R.color.red));
                            layout3.setBackgroundColor(getResources().getColor(R.color.green));
                            counter = 0;
                            break;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            startStop = false;
            button2.setText("START");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startStop = false;
    }
}