package com.example.story.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Book",
        foreignKeys = @ForeignKey(entity = Category.class,
                parentColumns = "CatID", childColumns = "CatID"))
public class  Book implements Parcelable {
    private String CatID; // Foreign Key
    @PrimaryKey
    @NonNull
    private String BookID;
    private String BookName;
    private String Image;
    private String Author;
    private boolean FavoriteBook;
    private boolean NewBook;


    public Book(String CatID, String BookID) {
        this.CatID = CatID;
        this.BookID = BookID;
        this.BookName = BookName;
        this.Image = Image;
        this.Author = Author;
        this.FavoriteBook = FavoriteBook;
        this.NewBook = NewBook;
    }

    protected Book(Parcel in) {
        CatID = in.readString();
        BookID = in.readString();
        BookName = in.readString();
        Image = in.readString();
        Author = in.readString();
        FavoriteBook = in.readByte() != 0;
        NewBook = in.readByte() != 0;
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CatID);
        dest.writeString(BookID);
        dest.writeString(BookName);
        dest.writeString(Image);
        dest.writeString(Author);
        dest.writeByte((byte) (FavoriteBook ? 1 : 0));
        dest.writeByte((byte) (NewBook ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getter & Setter

    public String getCatID() {
        return CatID;
    }

    public void setCatID(String catID) {
        CatID = catID;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        BookID = bookID;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public boolean isFavoriteBook() {
        return FavoriteBook;
    }

    public void setFavoriteBook(boolean favoriteBook) {
        FavoriteBook = favoriteBook;
    }

    public boolean isNewBook() {
        return NewBook;
    }

    public void setNewBook(boolean newBook) {
        NewBook = newBook;
    }
}

