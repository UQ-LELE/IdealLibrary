package com.example.ideallibrary.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ideallibrary.entities.Book;
import com.example.ideallibrary.entities.Favorites;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("DELETE FROM book_table")
    void deleteAllBook();

    @Query("SELECT * FROM book_table")
    LiveData<List<Book>> getAllBooks();

    @Query("SELECT * FROM book_table WHERE id IN (:favorites)")
    LiveData<List<Book>> getAllMyFavoriteBooks(List<Integer> favorites);
}
