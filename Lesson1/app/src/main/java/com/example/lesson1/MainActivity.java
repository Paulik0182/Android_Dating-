package com.example.lesson1;

/**
 * Урок 9
 * 1. Сделайте фрагмент добавления и редактирования данных, если вы ещё не сделали его.
 * 2. Сделайте навигацию между фрагментами, также организуйте обмен данными между фрагментами.
 * 3. Создайте контекстное меню для изменения и удаления заметок.
 * 4. * Изучите, каким образом можно вызывать DatePicker в виде диалогового окна. Создайте текстовое поле, при нажатии на которое вызывалось бы диалоговое окно с DatePicker.
 * Урок 8
 * 1. Создайте список ваших заметок.
 * 2. Создайте карточку для элемента списка.
 * 3. Класс данных, созданный на шестом уроке, используйте для заполнения карточки списка.
 * 4. * Создайте фрагмент для редактирования данных в конкретной карточке. Этот фрагмент пока можно вызвать через основное меню.
 * <p>
 * Урок 7
 * 1. Подумайте о функционале вашего приложения заметок. Какие экраны там могут быть, помимо основного со списком заметок? Как можно использовать
 * меню и всплывающее меню в вашем приложении? Не обязательно сразу пытаться реализовать весь этот функционал, достаточно создать макеты и структуру,
 * а реализацию пока заменить на заглушки или всплывающие уведомления (Toast). Используйте подход Single Activity для отображения экранов.
 * 2. Создайте боковое навигационное меню для своего приложения и добавьте туда хотя бы один экран, например «Настройки» или «О приложении».
 * 3. * Создайте полноценный заголовок для NavigationDrawer’а. К примеру, аватарка пользователя, его имя и какая-то дополнительная информация.
 * <p>
 * Урок 6
 * 1. Создайте класс данных со структурой заметок: название заметки, описание заметки, дата создания и т. п.
 * 2. Создайте фрагмент для вывода этих данных.
 * 3. Встройте этот фрагмент в активити. У вас должен получиться экран с заметками, который мы будем улучшать с каждым новым уроком.
 * 4. Добавьте фрагмент, в котором открывается заметка. По аналогии с примером из урока: если нажать на элемент списка в портретной ориентации — открывается новое окно,
 * если нажать в ландшафтной — окно открывается рядом.
 * 5. * Разберитесь, как можно сделать, и сделайте корректировку даты создания при помощи DatePicker.
 */

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //для сохранения измененных данных заметки
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.remarkList, new RemarksListFragment());
            transaction.commit();
        }

        //при переходе экрана из landscape в portrait показывается фрагмент RemarkListFragment и очищается бэкстек
        if (savedInstanceState != null && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.remarkDetailed, RemarksListFragment.newInstance());
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            transaction.commit();
        }
        initView();
    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
    }

    // регистрация drawer
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id, item)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @SuppressLint("NonConstantResourceId")
    private boolean navigateFragment(int id, MenuItem item) {
        switch (id) {
            case R.id.action_favorite:
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about:
                AboutFragment aboutPage = AboutFragment.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.remarkDetailed, aboutPage);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
                return true;
        }
        return true;
    }
}
