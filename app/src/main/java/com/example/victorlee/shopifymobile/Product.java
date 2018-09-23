package com.example.victorlee.shopifymobile;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;

/**
 * Created by Victor Lee on 9/21/2018.
 */

@Getter
public class Product implements Parcelable {

    private String title;
    private List<String> tags;
    private int amountOfInventory;
    private String imageSrc;

    public Product(String title, List<String> tags, int amountOfInventory, String imageSrc) {
        this.title = title;
        this.tags = tags;
        this.amountOfInventory = amountOfInventory;
        this.imageSrc = imageSrc;
    }

    public Product(Parcel in) {
        this.title = in.readString();
        this.tags = in.createStringArrayList();
        this.amountOfInventory = in.readInt();
        this.imageSrc = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeStringList(tags);
        dest.writeInt(amountOfInventory);
        dest.writeString(imageSrc);
    }
}
