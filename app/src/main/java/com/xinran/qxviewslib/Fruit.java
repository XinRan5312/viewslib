package com.xinran.qxviewslib;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by qixinh on 16/7/21.
 */
public class Fruit implements Parcelable {
    private String name;
    private Integer size;

    public Fruit(String name, Integer size) {
        this.name = name;
        this.size = size;
    }

    public Fruit(Parcel source) {
        readFromParcel(source);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getsize() {
        return size;
    }

    public void setsize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "[name: " + name + ", size: " + size + "]";
    }


    //AIDL needs CREATOR static final android.os.Parcelable.Creator<T> to call service
    public static final Creator<Fruit> CREATOR = new Creator<Fruit>() {
        @Override
        public Fruit createFromParcel(Parcel source) {
            return new Fruit(source);
        }

        @Override
        public Fruit[] newArray(int size) {
            return new Fruit[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //write and read must in the same sequence
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(size);
    }

    public void readFromParcel(Parcel source) {
        name = source.readString();
        size = source.readInt();
    }
}
