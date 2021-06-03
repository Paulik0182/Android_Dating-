package com.example.lesson1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //прочитать настройки, возможно чтото осталось с прошлого
        readSettings();
        initView();
    }
    private void initView() {
        //реализовать каждую кнопку. создаем каждую кнопку
        initButtonMain();
        initButtonFavorite();
        initButtonSettings();
        initButtonBack();
    }
    private void initButtonMain() {//чтение настроек
        Button buttonMain = findViewById(R.id.buttonMain);//ищем кнопку
        buttonMain.setOnClickListener(v ->  {//пишим обрабодчик
            addFragment ( new MainFragment () );

//            FragmentManager fragmentManager = getSupportFragmentManager ();
//            if (Settings.isBackAsRemove){
//                Fragment fragment = getVisibleFragment ( fragmentManager );
//                if (fragment != null){//если фрагмент существует , то удоляем видимый фрагмент, удалить предыдущий фрагмент.
//                    fragmentManager.beginTransaction ().remove ( fragment ).commit ();
//                }else {
//                    fragmentManager.popBackStack ();//иначе выдаем то, что есть
//                }
//            }
        });
    }
    private Fragment getVisibleFragment(FragmentManager fragmentManager){
        List<Fragment> fragments = fragmentManager.getFragments();
        int countFragments = fragments.size();//вводим переменную с количеством фрагментов
        for(int i = countFragments - 1; i >= 0; i--){//(проходим от обратного, к нулю) проходим по стэку и находим тот который находится первым.
            Fragment fragment = fragments.get(i);
            if(fragment.isVisible()) //если фрагмент   то возвращаем его , если нет , то возвращаем нуль
                return fragment;
        }
        return null;
    }

    private void initButtonFavorite() {
        Button buttonBack = findViewById(R.id.buttonFavorite);
        buttonBack.setOnClickListener(v ->  {
            FragmentManager fragmentManager = getSupportFragmentManager ();
            addFragment ( new FavoriteFragment () );
        });
    }

    private void initButtonSettings() {
        Button buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(v ->  {
            addFragment ( new SettignsFragment () );
        });
    }

    private void initButtonBack() {
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v ->  {
            FragmentManager fragmentManager = getSupportFragmentManager();
                if (Settings.isBackAsRemove){
                    Fragment fragment = getVisibleFragment(fragmentManager);
                    if (fragment != null) {
                        fragmentManager.beginTransaction().remove(fragment).commit();
                    }
                } else {
                    fragmentManager.popBackStack ();
                }
        });
    }

    private void addFragment(Fragment fragment){
//Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
// Открыть транзакцию
        FragmentTransaction transaction =
                fragmentManager.beginTransaction();
// Удалить видимый фрагмент перед добавлением
        if (Settings.isDeleteBeforeAdd){
            Fragment fragmentToRemove = getVisibleFragment(fragmentManager);
            if (fragmentToRemove != null) {
                transaction.remove(fragmentToRemove);
            }
        }
// Добавить фрагмент
        if (Settings.isAddFragment) {
            transaction.add(R.id.fragment_container, fragment);
        } else {
            transaction.replace(R.id.fragment_container, fragment);
        }
// Добавить транзакцию в бэкстек
        if (Settings.isBackStack){
            transaction.addToBackStack(null);
        }
// Закрыть транзакцию
        transaction.commit();
    }
    // Чтение настроек
    private void readSettings(){
// Специальный класс для хранения настроек
        SharedPreferences sharedPref =
                getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
// Считываем значения настроек
        Settings.isBackStack = sharedPref.getBoolean(Settings.IS_BACK_STACK_USED,
                false);
        Settings.isAddFragment =
                sharedPref.getBoolean(Settings.IS_ADD_FRAGMENT_USED, true);
        Settings.isBackAsRemove =
                sharedPref.getBoolean(Settings.IS_BACK_AS_REMOVE_FRAGMENT, true);
        Settings.isDeleteBeforeAdd =
                sharedPref.getBoolean(Settings.IS_DELETE_FRAGMENT_BEFORE_ADD, false);
    }
}