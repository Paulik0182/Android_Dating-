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

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
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

        //создаем RecyclerView и передаем в макет fragment_notes_list
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_remarks_list, container, false);
        recyclerView.setHasFixedSize(true);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        DividerItemDecoration decorator = new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL);
        decorator.setDrawable(getResources().getDrawable(R.drawable.decoration));
        recyclerView.addItemDecoration(decorator);

        //создаем layout manager для RecyclerView и связываем их
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //создаем adapter для RecyclerView и связываем их
        ViewHolderAdapter viewHolderAdapter = new ViewHolderAdapter(inflater, new CardDataSourceImpl(getResources()));
        viewHolderAdapter.setOnClickListener((v, position) -> {
            final int index = position;

            currentRemark = new Remark(getResources().getStringArray(R.array.remarks)[index], getResources().getStringArray(R.array.descriptions)[index], getResources().getStringArray(R.array.dates)[index]);
            showRemark(currentRemark);
        });
        recyclerView.setAdapter(viewHolderAdapter);

        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static RemarksList newInstance() {
        RemarksList fragment = new RemarksList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //определяем класс ViewHolderAdapter ВНУТРИ класса списка (RemarksList)
    //в этом классе также реализуем слушатели нажатия
    private class ViewHolderAdapter extends RecyclerView.Adapter<ViewHolder> {
        private final LayoutInflater mInflater;
        private final CardDataSource mDataSource;
        private OnClickListener mOnClickListener;

        public ViewHolderAdapter(LayoutInflater mInflater, CardDataSource mDataSource) {
            this.mInflater = mInflater;
            this.mDataSource = mDataSource;
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = mInflater.inflate(R.layout.list_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CardData cardData = mDataSource.getItemAt(position);
            holder.populate(cardData);

            //клик по View
            holder.itemView.setOnClickListener(v -> {
                if (mOnClickListener != null) {
                    mOnClickListener.onItemClick(v, position);
                }
            });

            // вызов popup menu долгим нажатием
            holder.itemView.setOnLongClickListener(v -> {
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
                        case R.id.rename_popup:
                            Toast.makeText(getContext(), "Переименовать", Toast.LENGTH_SHORT).show();
                            return true;
                    }
                    return true;
                });
                popupMenu.show();
                return true;
            });
        }

        @Override
        public int getItemCount() {
            return mDataSource.getItemsCount();
        }
    }

    //определяем класс ViewHolder ВНУТРИ класса списка (RemarktesList)
    //определяем в нем элементы UI (View), которые будут в нашем RecyclerView
    //ViewHolder хранит соответствия между элементом списка и элементами UI
    private static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView text;
        public final AppCompatImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.list_item_text);
            image = itemView.findViewById(R.id.list_item_img);
        }

        //класс populate связывает данные карточки (CardView) и View в элементе CardView макета
        public void populate(CardData data) {
            text.setText(data.text);
            image.setImageResource(data.imageResourceId);
        }
    }

    //интерфейс ВНУТРИ класса для обработки нажатия
    private interface OnClickListener {
        void onItemClick(View v, int position);
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
        RemarksDetailedFragment remarksDetailed = RemarksDetailedFragment.newInstance(remark);
        // выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack("remarkList");
        fragmentTransaction.replace(R.id.remarkDetailed, remarksDetailed);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
        Intent intent = new Intent();
        // передаем с интентом экземпляр заметки, по которой было нажатие
        //заменил RemarkDetailedActivity на RemarkDetailedFragment для отвезки фрагмента RemarkDetailed от 2-ой активити
        intent.setClass(getActivity(), RemarksDetailedFragment.class);
        intent.putExtra(RemarksDetailedFragment.ARG_REMARK, remark);
    }

    private void showRemarkLandscape(Remark remark) {
        // создаём новый фрагмент с текущей позицией
        RemarksDetailedFragment remarksDetailed = RemarksDetailedFragment.newInstance(remark);
        // выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.remarkDetailed, remarksDetailed);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        // если фрагмент уже появлялся - показываем сохраненную заметку
        if (savedInstanceState != null) {
            currentRemark = savedInstanceState.getParcelable(CURRENT_REMARK);
        } else {
            // если не появлась заметка - показываем самую первую
            currentRemark = new Remark(getResources().getStringArray(R.array.remarks)[0],
                    getResources().getStringArray(R.array.descriptions)[0],
                    getResources().getStringArray(R.array.dates)[0]);
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
