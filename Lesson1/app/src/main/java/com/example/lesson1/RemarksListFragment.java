package com.example.lesson1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Date;

public class RemarksListFragment extends Fragment {

    private static final String CURRENT_REMARK = "CurrentRemark";
    private static final String ARG_REMARK_LIST = "RemarkList";
    private Remark currentRemark;
    private boolean isLandscape;

    protected int mLastSelectedPosition = -1;

    public CardDataSource mDataSource;
    public ViewHolderAdapter mViewHolderAdapter;
    public RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //создаем RecyclerView и передаем в макет fragment_remark_list
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_remarks_list, container, false);
        mRecyclerView.setHasFixedSize(true);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        DividerItemDecoration decorator = new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL);
        decorator.setDrawable(getResources().getDrawable(R.drawable.decoration));
        mRecyclerView.addItemDecoration(decorator);

        //создаем layout manager для RecyclerView и связываем их
        LinearLayoutManager layoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mDataSource = CardDataSourceImpl.getInstance(getResources());
        //создаем adapter для RecyclerView и связываем их
        mViewHolderAdapter = new ViewHolderAdapter(this, this, mDataSource);
        mViewHolderAdapter.setOnClickListener((v, position) -> {
            final int index = position;
            currentRemark = mDataSource.getItemAt(index);
            showRemark(currentRemark);
        });
        mRecyclerView.setAdapter(mViewHolderAdapter);

        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return mRecyclerView;
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
        // создаём новый фрагмент с текущей позицией
        RemarksDetailedFragment remarksDetailedFragment = RemarksDetailedFragment.newInstance(remark);
        // выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.remarkDetailed, remarksDetailedFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

        Intent intent = new Intent();
        //заменил RemarkDetailedActivity на RemarkDetailedFragment для отвезки фрагмента RemarkDetailed от 2-ой активити
        intent.setClass(getActivity(), RemarksDetailedFragment.class);
        intent.putExtra(RemarksDetailedFragment.ARG_REMARK, remark);
    }

    private void showRemarkLandscape(Remark remark) {
        // создаём новый фрагмент с текущей позицией
        RemarksDetailedFragment remarksDetailedFragment = RemarksDetailedFragment.newInstance(remark);
        // выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.remarkDetailed, remarksDetailedFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

        Intent intent = new Intent();
        //заменил RemarkDetailedActivity на RemarkDetailedFragment для отвезки фрагмента RemarkDetailed от 2-ой активити
        intent.setClass(getActivity(), RemarksDetailedFragment.class);
        intent.putExtra(RemarksDetailedFragment.ARG_REMARK, remark);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        // если фрагмент уже появлялся - показываем сохраненную заметку
        if (savedInstanceState != null) {
            currentRemark = savedInstanceState.getParcelable(CURRENT_REMARK);
        } else {
            // если не появлялся - показываем самую первую
            if (mDataSource.getRemarkData() != null) {
                currentRemark = new Remark(getResources().getStringArray(R.array.remarks)[0],
                        getResources().getStringArray(R.array.descriptions)[0],
                        getResources().getStringArray(R.array.dates)[0]);
            }
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.action_favorite:
                Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_add:
                Remark newRemark = new Remark("New Remark", "", new Date().toString());
                mDataSource.add(newRemark);
                int position = mDataSource.getItemsCount() - 1;
                mViewHolderAdapter.notifyItemInserted(position);
                mRecyclerView.scrollToPosition(position);
                return true;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu_main, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.context_edit) {
            if (mLastSelectedPosition != -1) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.remarkList, RemarkEditFragment.newInstance(mLastSelectedPosition));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        } else if (item.getItemId() == R.id.context_delete) {
            if (mLastSelectedPosition != -1) {
                mDataSource.remove(mLastSelectedPosition);
                mViewHolderAdapter.notifyItemRemoved(mLastSelectedPosition);
            }
        } else {
            return super.onContextItemSelected(item);
        }
        return true;
    }

    public static RemarksListFragment newInstance() {
        RemarksListFragment fragment = new RemarksListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //интерфейс внутри класса для обработки нажатия
    public interface OnClickListener {
        void onItemClick(View v, int position);
    }

    void setLastSelectedPosition(int lastSelectedPosition) {
        mLastSelectedPosition = lastSelectedPosition;
    }
}
