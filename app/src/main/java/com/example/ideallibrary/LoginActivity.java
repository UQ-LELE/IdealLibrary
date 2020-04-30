package com.example.ideallibrary;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ideallibrary.repository.SharedPreferencesRepository;
import com.example.ideallibrary.utilities.Constants;
import com.example.ideallibrary.utilities.Fun;

public class LoginActivity extends AppCompatActivity {

    Context mContext;

    FragmentManager mFragmentManager;

    LoginFragment mLoginFragment;
    CreateUserFragment mCreateUserFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;

        initFragments();

        String userJson = SharedPreferencesRepository.getSharedPreferences(mContext, Constants.SHARED_USER);


        if(!userJson.isEmpty()){
            Fun.loadFragment(mFragmentManager,R.id.framelayout_login, mLoginFragment);
        } else {
            Fun.loadFragment(mFragmentManager,R.id.framelayout_login, mCreateUserFragment);
        }

    }

    private void initFragments() {

        mLoginFragment = new LoginFragment();
        mCreateUserFragment = new CreateUserFragment();

        mFragmentManager = getFragmentManager();
    }
}
