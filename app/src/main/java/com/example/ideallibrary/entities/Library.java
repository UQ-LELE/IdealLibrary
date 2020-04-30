package com.example.ideallibrary.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity (tableName = "library_table")
public class Library {

    @PrimaryKey (autoGenerate = true)
    private int idLibrary;

    private String title;

    private int image;

    private List<Integer> idBooks;

    public int getIdLibrary() {
        return idLibrary;
    }

    public void setIdLibrary(int idLibrary) {
        this.idLibrary = idLibrary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public List<Integer> getIdBooks() {
        return idBooks;
    }

    public void setIdBooks(List<Integer> idBooks) {
        this.idBooks = idBooks;
    }

    public Library(String title, int image) {
        this.title = title;
        this.image = image;
    }
}
