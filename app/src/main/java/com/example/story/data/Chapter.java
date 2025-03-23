package com.example.story.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Chapter",
        foreignKeys = @ForeignKey(entity = Book.class, parentColumns = "BookID", childColumns = "BookID"))
public class Chapter implements Parcelable {
    private String BookID; // Foreign Key
    @PrimaryKey
    @NonNull
    private String ChapterID;
    private String Title;
    private String Content;

    public Chapter(String BookID, String ChapterID, String Title, String Content) {
        this.BookID = BookID;
        this.ChapterID = ChapterID;
        this.Title = Title;
        this.Content = Content;
    }

    protected Chapter(Parcel in) {
        BookID = in.readString();
        ChapterID = in.readString();
        Title = in.readString();
        Content = in.readString();
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(BookID);
        dest.writeString(ChapterID);
        dest.writeString(Title);
        dest.writeString(Content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getter & Setter

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        BookID = bookID;
    }

    @NonNull
    public String getChapterID() {
        return ChapterID;
    }

    public void setChapterID(@NonNull String chapterID) {
        ChapterID = chapterID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
