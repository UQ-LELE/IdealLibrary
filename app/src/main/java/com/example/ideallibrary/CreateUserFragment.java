package com.example.ideallibrary;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.ideallibrary.entities.User;
import com.example.ideallibrary.repository.SharedPreferencesRepository;
import com.example.ideallibrary.utilities.Constants;
import com.example.ideallibrary.utilities.Fun;
import com.google.gson.Gson;

public class CreateUserFragment extends Fragment {

    Context mContext;

    EditText mEdtFirstName;
    EditText mEdtPassword;
    Button mBtnCreate;
    ProgressBar mProgressBarCreation;

    String mFirstName;
    String mPassword;

    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mContext = getActivity();

        View view = inflater.inflate(R.layout.fragment_create_user, container, false);

        initWidget(view);

        mProgressBarCreation.setVisibility(View.INVISIBLE);

        mBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBarCreation.setVisibility(View.VISIBLE);

                mFirstName = mEdtFirstName.getText().toString().trim();
                mPassword = mEdtPassword.getText().toString().trim();

                if (!mFirstName.isEmpty() && mFirstName != null && !mPassword.isEmpty() && mPassword != null) {

                    user = new User(mFirstName, mPassword);
                    saveUser(user);

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                } else {
                    mProgressBarCreation.setVisibility(View.INVISIBLE);
                    Fun.showToastMessage(mContext, "Veuillez remplir tous les champs", Constants.TOAST_ERROR);
                }
            }
        });

        return view;
    }

    private void initWidget(View view) {
        mEdtFirstName = view.findViewById(R.id.edt_create_first_name);
        mEdtPassword = view.findViewById(R.id.edt_create_password);
        mBtnCreate = view.findViewById(R.id.btn_create_account);
        mProgressBarCreation = view.findViewById(R.id.progressBar_create_account);
    }

    private void saveUser(User user) {
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        SharedPreferencesRepository.insertSharedPreferences(mContext, Constants.SHARED_USER, userJson);
    }
}

