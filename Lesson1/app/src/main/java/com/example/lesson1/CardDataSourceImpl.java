package com.example.lesson1;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CardDataSourceImpl implements CardDataSource {
    private final LinkedList<Remark> mData = new LinkedList<>();

    //singleTone
    private volatile static CardDataSourceImpl sInstance;

    public static CardDataSourceImpl getInstance(Resources resources) {
        CardDataSourceImpl instance = sInstance;
        if (instance == null) {
            synchronized (CardDataSourceImpl.class) {
                if (sInstance == null) {

                    instance = new CardDataSourceImpl(resources);
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private CardDataSourceImpl(Resources resources) {
        String[] remarksNames = resources.getStringArray(R.array.remarks);
        String[] remarksDescriptions = resources.getStringArray(R.array.descriptions);
        String[] remarksDates = resources.getStringArray(R.array.dates);
        for (int i = 0; i < remarksNames.length; i++) {
            mData.add(new Remark(remarksNames[i], remarksDescriptions[i], remarksDates[i]));
        }
    }

    @Override
    public List<Remark> getRemarkData() {
        return Collections.unmodifiableList(mData);
    }

    @Override
    public Remark getItemAt(int idx) {
        return mData.get(idx);
    }

    @Override
    public int getItemsCount() {
        return mData.size();
    }

    @Override
    public void add(@NonNull Remark data) {
        mData.add(data);
    }

    @Override
    public void remove(int position) {
        mData.remove(position);
    }
}
