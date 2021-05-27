package com.example.lesson1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import static android.text.TextUtils.replace;

public class CityImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_image);

        //проверка ориентации Activity
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish(); //разобратся (  finish()  ), могут спрасить на собеседовании. Жизненый цыкл прирвется сразу.
            // Если onCreate вызва finish то не вызовится ничего, не резьюм, не старт и т.д.

            return;
        }
        if (savedInstanceState == null){
            ImageFragment fragment = new ImageFragment();
            fragment.setArguments(getIntent().getExtras());//взяли бандел нашего активити и положили его во фрагмент

            //добавляем наш фрагмент в фктивити, как сделать транзакцию
            getSupportFragmentManager() //делаем так, когда пользуемся апКомпактАктивити, а им пользуемся всегда
            .beginTransaction() //открываем транзакцию
            .replace(R.id.fragment_container, fragment)//способ добавления фрагмента в активити (это второй способ)
                    .commit();

        }

    }
}