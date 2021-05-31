package com.example.lesson1;

import androidx.annotation.NonNull;

import java.util.List;

public interface CardDataSource {

    List<Remark> getRemarkData();

    Remark getItemAt(int idx);

    int getItemsCount();

    void add(@NonNull Remark data);

    void remove(int position);
}