package com.example.lesson1;

import android.os.Parcel;
import android.os.Parcelable;

public class Remark implements Parcelable {
    private String name;
    private String description;
    private String date;

    public Remark(String name, String description, String date) {
        this.name = name;
        this.description = description;
        this.date = date;

    }

    protected Remark(Parcel in) {
        name = in.readString();
        description = in.readString();
        date = in.readString();
    }

    public static final Creator<Remark> CREATOR = new Creator<Remark>() {
        @Override
        public Remark createFromParcel(Parcel in) {
            return new Remark(in);
        }

        @Override
        public Remark[] newArray(int size) {
            return new Remark[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(date);
    }
}