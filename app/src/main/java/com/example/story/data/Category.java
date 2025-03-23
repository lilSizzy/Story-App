package com.example.story.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Category")
public class Category implements Parcelable {
    @PrimaryKey
    @NonNull
    private String CatID;
    private String CatName;

    public Category(@NonNull String CatID, String CatName) {
        this.CatID = CatID;
        this.CatName = CatName;
    }

    protected Category(Parcel in) {
        CatID = in.readString();
        CatName = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CatID);
        dest.writeString(CatName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getter & Setter

    @NonNull
    public String getCatID() {
        return CatID;
    }

    public void setCatID(@NonNull String catID) {
        CatID = catID;
    }

    public String getCatName() {
        return CatName;
    }

    public void setCatName(String catName) {
        CatName = catName;
    }
}