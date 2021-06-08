package com.example.lesson1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        //прочитать настройки, возможно чтото осталось с прошлого
        readSettings ();
        initView ();
    }

    private void initView() {//методы
        //реализовать каждую кнопку. создаем каждую кнопку
        Toolbar toolbar = initToolbar ();//нужно чтобы открывалась шторка
        initDrawer ( toolbar );
        initButtonMain ();
        initButtonFavorite ();
        initButtonSettings ();
        initButtonBack ();
    }

    // регистрация drawer
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById ( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close );
        drawer.addDrawerListener ( toggle );
        toggle.syncState ();

// Обработка навигационного меню
        NavigationView navigationView = findViewById ( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener ( new
                                                                   NavigationView.OnNavigationItemSelectedListener () {
                                                                       @Override
                                                                       public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                                                           int id = item.getItemId ();
                                                                           if (navigateFragment ( id )) {
                                                                               drawer.closeDrawer ( GravityCompat.START );
                                                                               return true;
                                                                           }
                                                                           return false;
                                                                       }
                                                                   } );
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById ( R.id.toolbar );//ищем кнопку
        setSupportActionBar ( toolbar );//назначаем
        return toolbar;
    }


    //назначаем меню
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId ();
//        switch (id){
//            case R.id.action_settings:
//                addFragment ( new SettignsFragment ()) ;
//                return true;
//            case R.id.action_main:
//                addFragment ( new MainFragment () );
//                return true;
//            case R.id.action_favorite:
//                addFragment ( new FavoriteFragment () );
//                return  true;

        if (navigateFragment ( id )) {
            return true;
        }
        return super.onOptionsItemSelected ( item );
    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_settings:
                addFragment ( new SettignsFragment () );
                return true;
            case R.id.action_main:
                addFragment ( new MainFragment () );
                return true;
            case R.id.action_favorite:
                addFragment ( new FavoriteFragment () );
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Здесь определяем меню приложения (активити)
        getMenuInflater ().inflate ( R.menu.main, menu );
        MenuItem search = menu.findItem ( R.id.action_search ); // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView (); // строка поиска
        searchText.setOnQueryTextListener ( new SearchView.OnQueryTextListener () {
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText ( MainActivity.this, query,
                        Toast.LENGTH_SHORT ).show ();
                return true;
            }

            // реагирует на нажатие каждой клавиши
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        } );
        return true;
    }


    private void initButtonMain() {//чтение настроек
        Button buttonMain = findViewById ( R.id.buttonMain );//ищем кнопку
        buttonMain.setOnClickListener ( v -> {//пишим обрабодчик
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
        } );
    }

    private Fragment getVisibleFragment(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments ();
        int countFragments = fragments.size ();//вводим переменную с количеством фрагментов
        for (int i = countFragments - 1; i >= 0; i--) {//(проходим от обратного, к нулю) проходим по стэку и находим тот который находится первым.
            Fragment fragment = fragments.get ( i );
            if (fragment.isVisible ()) //если фрагмент   то возвращаем его , если нет , то возвращаем нуль
                return fragment;
        }
        return null;
    }

    private void initButtonFavorite() {
        Button buttonBack = findViewById ( R.id.buttonFavorite );
        buttonBack.setOnClickListener ( v -> {
            FragmentManager fragmentManager = getSupportFragmentManager ();
            addFragment ( new FavoriteFragment () );
        } );
    }

    private void initButtonSettings() {
        Button buttonSettings = findViewById ( R.id.buttonSettings );
        buttonSettings.setOnClickListener ( v -> {
            addFragment ( new SettignsFragment () );
        } );
    }

    private void initButtonBack() {
        Button buttonBack = findViewById ( R.id.buttonBack );
        buttonBack.setOnClickListener ( v -> {
            FragmentManager fragmentManager = getSupportFragmentManager ();
            if (Settings.isBackAsRemove) {
                Fragment fragment = getVisibleFragment ( fragmentManager );
                if (fragment != null) {
                    fragmentManager.beginTransaction ().remove ( fragment ).commit ();
                }
            } else {
                fragmentManager.popBackStack ();
            }
        } );
    }

    private void addFragment(Fragment fragment) {
//Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager ();
// Открыть транзакцию
        FragmentTransaction transaction =
                fragmentManager.beginTransaction ();
// Удалить видимый фрагмент перед добавлением
        if (Settings.isDeleteBeforeAdd) {
            Fragment fragmentToRemove = getVisibleFragment ( fragmentManager );
            if (fragmentToRemove != null) {
                transaction.remove ( fragmentToRemove );
            }
        }
// Добавить фрагмент
        if (Settings.isAddFragment) {
            transaction.add ( R.id.fragment_container, fragment );
        } else {
            transaction.replace ( R.id.fragment_container, fragment );
        }
// Добавить транзакцию в бэкстек
        if (Settings.isBackStack) {
            transaction.addToBackStack ( null );
        }
// Закрыть транзакцию
        transaction.commit ();
    }

    // Чтение настроек
    private void readSettings() {
// Специальный класс для хранения настроек
        SharedPreferences sharedPref =
                getSharedPreferences ( Settings.SHARED_PREFERENCE_NAME, MODE_PRIVATE );
// Считываем значения настроек
        Settings.isBackStack = sharedPref.getBoolean ( Settings.IS_BACK_STACK_USED,
                false );
        Settings.isAddFragment =
                sharedPref.getBoolean ( Settings.IS_ADD_FRAGMENT_USED, true );
        Settings.isBackAsRemove =
                sharedPref.getBoolean ( Settings.IS_BACK_AS_REMOVE_FRAGMENT, true );
        Settings.isDeleteBeforeAdd =
                sharedPref.getBoolean ( Settings.IS_DELETE_FRAGMENT_BEFORE_ADD, false );
    }
}