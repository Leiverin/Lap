package com.poly.lap.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String id;
    private String name;
    private double price;
    private String actor;
    private String image;

    public Book(String id, String name, double price, String actor, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.actor = actor;
        this.image = image;
    }

    public Book(String name, double price, String actor, String image) {
        this.name = name;
        this.price = price;
        this.actor = actor;
        this.image = image;
    }

    protected Book(Parcel in) {
        id = in.readString();
        name = in.readString();
        price = in.readDouble();
        actor = in.readString();
        image = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeDouble(price);
        parcel.writeString(actor);
        parcel.writeString(image);
    }
}
