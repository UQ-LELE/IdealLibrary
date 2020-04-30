package com.example.ideallibrary;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ideallibrary.entities.Book;
import com.example.ideallibrary.entities.Favorite;
import com.example.ideallibrary.entities.Favorites;
import com.example.ideallibrary.repository.BookRepository;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private BookRepository repository;
    private LiveData<List<Book>> allBooks;
    private LiveData<List<Book>> allFavorites;


    public BookViewModel(@NonNull Application application, List<Integer> favorites) {
        super(application);
        repository = new BookRepository(application);
        allBooks = repository.getAllBooks();
        allFavorites = repository.getAllMyFavoriteBooks(favorites);
    }

    public void insert(Book book){
        repository.insert(book);
    }

    public void update(Book book){
        repository.update(book);
    }

    public void delete(Book book){
        repository.delete(book);
    }

    public void deleteAllBooks(){
        repository.deleteAllBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }

    public LiveData<List<Book>> getAllMyFavoriteBooks() {
        return allFavorites;

    }

}
