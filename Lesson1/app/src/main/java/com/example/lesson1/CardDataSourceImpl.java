package com.example.lesson1;

import android.content.res.Resources;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CardDataSourceImpl implements CardDataSource {
    private final LinkedList<CardData> mData = new LinkedList<>();

    public CardDataSourceImpl(Resources resources) {
        String[] remarks = resources.getStringArray(R.array.remarks);
        for (int i = 0; i < remarks.length; i++) {
            mData.add(new CardData(remarks[i], R.drawable.drawing));
        }
    }

    @Override
    public List<CardData> getCardData() {
        return Collections.unmodifiableList(mData);
    }

    @Override
    public CardData getItemAt(int idx) {
        return mData.get(idx);
    }

    @Override
    public int getItemsCount() {
        return mData.size();
    }
}