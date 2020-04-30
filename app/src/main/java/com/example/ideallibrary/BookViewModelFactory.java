package com.example.ideallibrary;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class BookViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private List<Integer> mParam;


    public BookViewModelFactory(Application application, List<Integer> param) {
        mApplication = application;
        mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new BookViewModel(mApplication, mParam);
    }
}