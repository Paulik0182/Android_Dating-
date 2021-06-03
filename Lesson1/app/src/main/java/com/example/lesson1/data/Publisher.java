package com.example.lesson1.data;

import com.example.lesson1.Remark;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private final List<Observer> observers;   // Все обозреватели

    public Publisher() {
        observers = new ArrayList<> ();
    }

    // Подписать
    public void subscribe(Observer observer) {
        observers.add ( observer );
    }

    // Отписать
    public void unsubscribe(Observer observer) {
        observers.remove ( observer );
    }

    // Разослать событие
    public void notifySingle(Remark remark) {
        for (Observer observer : observers) {
            observer.updateRemark ( remark );
            unsubscribe ( observer );
        }
    }

}
