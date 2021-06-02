package com.example.lesson1;

import androidx.annotation.NonNull;

import java.util.List;

public interface CardDataSource {

    interface DataSourceListener {
        void onItemAdded(int idx);

        void onItemRemoved(int idx);

        void onItemUpdated(int idx);

        void onDataSetChanged();
    }

    void addDataSourceListener(DataSourceListener listener);

    void removeDataSourceListener(DataSourceListener listener);

    List<Remark> getRemarkData();

    Remark getItemAt(int idx);

    int getItemsCount();

    void add(@NonNull Remark data);

    void remove(int position);

    void update(@NonNull Remark data);
}