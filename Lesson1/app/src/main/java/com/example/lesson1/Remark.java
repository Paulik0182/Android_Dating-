package com.example.lesson1;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import javax.annotation.Nullable;

public class Remark implements Parcelable {
    @Nullable
    private String mId;
    @NonNull
    private String name;
    private String description;
    private String date;

    public Remark(@NonNull String name, String description, String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    @Nullable
    public String getId() {
        return mId;
    }

    public void setId(@Nullable String id) {
        mId = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    protected Remark(Parcel in) {
        name = in.readString ();
        description = in.readString ();
        date = in.readString ();
    }

    public static final Creator<Remark> CREATOR = new Creator<Remark> () {
        @Override
        public Remark createFromParcel(Parcel in) {
            return new Remark ( in );
        }

        @Override
        public Remark[] newArray(int size) {
            return new Remark[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString ( name );
        dest.writeString ( description );
        dest.writeString ( date );
    }
}