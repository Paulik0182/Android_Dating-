package com.example.lesson1;

import java.util.List;

public interface CardDataSource {
    List<CardData> getCardData();
    CardData getItemAt(int idx);
    int getItemsCount();
}