package com.example.ideallibrary.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "book_table")
public class Book implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String authorFirstName;

    private String authorName;

    private String yearString;

    private int yearInt;

    private String genre;

    private String country;

    private String description;

    private int nbPages;

    private boolean readFinish;

    public Book(String title, String authorFristName, String authorName, String yearString, int yearInt, String genre, String country, String description, int nbPages, boolean readFinish) {
        this.title = title;
        this.authorFirstName = authorFristName;
        this.authorName = authorName;
        this.yearString = yearString;
        this.yearInt = yearInt;
        this.genre = genre;
        this.country = country;
        this.description = description;
        this.nbPages = nbPages;
        this.readFinish = readFinish;
    }

    public Book() {

    }
    protected Book(Parcel in) {
        id = in.readInt();
        title = in.readString();
        authorFirstName = in.readString();
        authorName = in.readString();
        yearString = in.readString();
        yearInt = in.readInt();
        genre = in.readString();
        country = in.readString();
        description = in.readString();
        nbPages = in.readInt();
        readFinish = in.readInt() == 1;

    }
    public void setId(int id) {
        this.id = id;
    }

    //getter
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getYearString() {
        return yearString;
    }

    public int getYearInt() {
        return yearInt;
    }

    public String getGenre() {
        return genre;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public int getNbPages() {
        return nbPages;
    }

    public boolean isReadFinish() {
        return readFinish;
    }

    //setter

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorFirstName(String author) {
        this.authorFirstName = authorFirstName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setYearString(String yearString) {
        this.yearString = yearString;
    }

    public void setYearInt(int yearInt) {
        this.yearInt = yearInt;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNbPages(int nbPages) {
        this.nbPages = nbPages;
    }

    public void setReadFinish(boolean readFinish) {
        this.readFinish = readFinish;
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
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(authorFirstName);
        parcel.writeString(authorName);
        parcel.writeString(yearString);
        parcel.writeInt(yearInt);
        parcel.writeString(genre);
        parcel.writeString(country);
        parcel.writeString(description);
        parcel.writeInt(nbPages);
        parcel.writeInt(isReadFinish() ? 1 : 0);
    }


}
