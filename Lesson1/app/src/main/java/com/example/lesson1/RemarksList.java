package com.example.lesson1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

public class RemarksList extends Fragment {

    private static final String CURRENT_REMARK = "CurrentRemark";
    private Remark currentRemark;
    private boolean isLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_remarks_list, container, false);
//        return view;
        return inflater.inflate(R.layout.fragment_remarks_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    @SuppressLint("NonConstantResourceId")
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] dates = getResources().getStringArray(R.array.dates);
        String[] remarks = getResources().getStringArray(R.array.remarks);
        for (int i = 0; i < remarks.length; i++) {
            String remark = remarks[i];
            String date = dates[i];
            LinearLayoutCompat subLayoutView = new LinearLayoutCompat(Objects.requireNonNull(getContext()));
            subLayoutView.setOrientation(LinearLayoutCompat.VERTICAL);
            TextView textviewName = new TextView(getContext());
            TextView textviewDate = new TextView(getContext());
            int subLayoutViewId = subLayoutView.getId();

            // оформление view
            int remarkColor = Color.parseColor("#FFFAF096");

            textviewDate.setText(date);
            textviewDate.setTextColor(Color.GRAY);
            textviewDate.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textviewDate.setBackgroundColor(remarkColor);

            textviewName.setText(remark);
            textviewName.setTextSize(25);
            textviewName.setTextColor(Color.BLACK);
            textviewName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textviewName.setBackgroundColor(remarkColor);

            // добавление view в layouts
            subLayoutView.addView(textviewName);
            subLayoutView.addView(textviewDate);
            layoutView.addView(subLayoutView);

            // вызов menu
            subLayoutView.setOnLongClickListener(v -> {
                Activity activity = requireActivity();
                PopupMenu popupMenu = new PopupMenu(activity, v);
                Menu menu = popupMenu.getMenu();
                activity.getMenuInflater().inflate(R.menu.popup, menu);
                popupMenu.setOnMenuItemClickListener(item -> {
                    int id = item.getItemId();
                    switch (id) {
                        case R.id.favorite_popup:
                            Toast.makeText(getContext(), "В Избранное", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.delete_popup:
                            Toast.makeText(getContext(), "Удалить", Toast.LENGTH_SHORT).show();
                            return true;
                    }
                    return true;
                });
                popupMenu.show();
                return true;
            });

            // обработка нажатия на заметку
            final int index = i;
            subLayoutView.setOnClickListener(v -> {
                currentRemark = new Remark(getResources().getStringArray(R.array.remarks)[index], getResources().getStringArray(R.array.descriptions)[index], getResources().getStringArray(R.array.dates)[index]);
                showRemark(currentRemark);
            });
        }
    }

    // метод вызывает один из двух методов в зависимости от ориентации экрана
    private void showRemark(Remark remark) {
        if (isLandscape) {
            showRemarkLandscape(remark);
        } else {
            showRemarkPortrait(remark);
        }
    }

    private void showRemarkPortrait(Remark remark) {
        // создаём новый фрагмент
        RemarksDetailedFragment notesDetailed = RemarksDetailedFragment.newInstance(remark);
        // выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.remarkDetailed, notesDetailed);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

        // переходим на RemarkssDetailedActivity, т.к. к ней привязан фрагмент с деталями заметки
        Intent intent = new Intent();
        intent.setClass(getActivity(), RemarksDetailedActivity.class);
        // передаем с интентом экземпляр заметки, по которой было нажатие
        intent.putExtra(RemarksDetailedFragment.ARG_REMARK, remark);
//        startActivity(intent);
    }

    private void showRemarkLandscape(Remark remark) {
        // создаём новый фрагмент с текущей позицией
        RemarksDetailedFragment remarksDetailed = RemarksDetailedFragment.newInstance(remark);
        // выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.remarkDetailed, remarksDetailed);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        //  показываем сохраненную заметку
        if (savedInstanceState != null) {
            currentRemark = savedInstanceState.getParcelable(CURRENT_REMARK);
        } else {
            // если не появлась заметка - показываем самую первую
            currentRemark = new Remark(getResources().getStringArray(R.array.remarks)[0], getResources().getStringArray(R.array.descriptions)[0], getResources().getStringArray(R.array.dates)[0]);
        }
        if (isLandscape) {
            showRemarkLandscape(currentRemark);
        }
    }

    // сохраняем текущую отображаемую заметку
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_REMARK, currentRemark);
        super.onSaveInstanceState(outState);
    }

}
