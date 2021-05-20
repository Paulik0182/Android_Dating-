package com.example.lesson1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CitiesFragment extends Fragment {

    public static final String CURRENT_CITY = "CurrentCity";
    //    private int currentPosition = 0;    // Текущая позиция (выбранный город)
    private City currentCity;
    private boolean isLandscape;

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    // вызывается после создания макета фрагмента, здесь мы проинициализируем список
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         initList(view);
    }

    // создаём список городов на экране из массива в ресурсах
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view; //контейнер и получаем его
        String[] cities = getResources().getStringArray(R.array.cities);//получаем ресурсы

        // В этом цикле создаём элемент TextView,
        // заполняем его значениями,
        // и добавляем на экран.
        // Кроме того, создаём обработку касания на элемент
        for (int i = 0; i < cities.length; i++) {//проходим по ресурсу (массиву)
            String city = cities[i];
            TextView textView = new TextView(getContext());
            textView.setText(city);// задаем текст
            textView.setTextSize(30);//размер текст
            layoutView.addView(textView);//в контейнер кладем текст
            final int fi = i;
            textView.setOnClickListener(v -> {
                showImage(fi);
//                    showPortCoatOfArms(fi);
//                    showCoatOfArms(fi);
//                    currentPosition = fi;
//                    showCoatOfArms(currentPosition);
                currentCity = new City(fi, getResources().getStringArray(R.array.cities)[fi]);
                showCoatOfArms(currentCity);
            });
        }
    }
    private void showImage(int index) {
        if (isLandscape){
            showLandImage(index);
        }else {
            showPortImage(index);
        }

    }

    private void showLandImage(int index) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.image_fragment_container, ImageFragment.newInstance(index))
                .commit();
    }

    private void showPortImage(int index) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CityImageActivity.class);
        intent.putExtra(ImageFragment.ARG_INDEX, index);
        startActivity(intent);

    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putInt(CURRENT_CITY, currentPosition);
        outState.putParcelable(CURRENT_CITY, currentCity);
        super.onSaveInstanceState(outState);

    }

    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Определение, можно ли будет расположить рядом герб в другом фрагменте
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
//            currentPosition = savedInstanceState.getInt(CURRENT_CITY, 0);
            currentCity = savedInstanceState.getParcelable(CURRENT_CITY);
        } else {
            // Если восстановить не удалось, то сделаем объект с первым индексом
            currentCity = new City(0, getResources().getStringArray(R.array.cities)[0]);
        }
        // Если можно нарисовать рядом герб, то сделаем это
        if (isLandscape) {
//            showLandCoatOfArms(0);
            showLandCoatOfArms(currentCity);
                }
        if (isLandscape){
            showImage(ImageFragment.DEFAULT_INDEX);
        }
    }

    //    private void showCoatOfArms(int index) {
    private void showCoatOfArms(City currentCity) {
        if (isLandscape) {
//            showLandCoatOfArms(index);
            showLandCoatOfArms(currentCity);
        } else {
//            showPortCoatOfArms(index);
            showPortCoatOfArms(currentCity);
        }
    }

    // Показать герб в ландшафтной ориентации
//    private void showLandCoatOfArms(int index) {
    private void showLandCoatOfArms(City currentCity) {
        // Создаём новый фрагмент с текущей позицией для вывода герба
//        CoatOfArmsFragment detail = CoatOfArmsFragment.newInstance(index);
        CoatOfArmsFragment detail = CoatOfArmsFragment.newInstance(currentCity);

        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.coat_of_arms, detail);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    // Показать герб в портретной ориентации.
//    private void showPortCoatOfArms(int index) {
    private void showPortCoatOfArms(City currentCity) {
        // Откроем вторую activity
        Intent intent = new Intent();
        intent.setClass(getActivity(), CoatOfArmsActivity.class);
        // и передадим туда параметры
//        intent.putExtra(CoatOfArmsFragment.ARG_INDEX, index);
        intent.putExtra(CoatOfArmsFragment.ARG_CITY, currentCity);
        startActivity(intent);
    }
}
