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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.ideallibrary.entities.FilterPreferences;
import com.example.ideallibrary.entities.User;
import com.example.ideallibrary.repository.SharedPreferencesRepository;
import com.example.ideallibrary.utilities.Constants;

import com.example.ideallibrary.utilities.Fun;
import com.google.gson.Gson;


public class LoginFragment extends Fragment {


    // UI Components
    private TextView mTxtWelcome;
    private EditText mEdtPassword;
    private Button mBtnConnexion;
    private ProgressBar mProgressBarConnexion;

    // vars
    private Context mContext;
    private String mPassword;
    private User user;
    private FilterPreferences mFilterPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = getActivity();

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initWidget(view);

        mProgressBarConnexion.setVisibility(View.INVISIBLE);

        user = getUser();
        mFilterPreferences = getUserFilterPreferences();

        mTxtWelcome.setText("Bonjour " + user.getFirstName());

        mBtnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBarConnexion.setVisibility(View.VISIBLE);
                mPassword = mEdtPassword.getText().toString().trim();
                if (!mPassword.isEmpty() && mPassword != null) {
                    if (mPassword.equalsIgnoreCase(user.getPassword())) {
                        mProgressBarConnexion.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(mContext, LibraryActivity.class);
                        startActivity(intent);
                    } else {
                        mProgressBarConnexion.setVisibility(View.INVISIBLE);
                        Fun.showToastMessage(mContext, "Mot de passe incorrect", Constants.TOAST_ERROR );
                    }
                } else {
                    mProgressBarConnexion.setVisibility(View.INVISIBLE);
                    Fun.showToastMessage(mContext, "Veuillez saisir votre mot de passe", Constants.TOAST_ERROR );
                }
            }
        });
        return view;
    }

    private void initWidget(View view) {
        mTxtWelcome = view.findViewById(R.id.txt_welcome_connexion);
        mEdtPassword = view.findViewById(R.id.edt_password);
        mBtnConnexion = view.findViewById(R.id.btn_connexion);
        mProgressBarConnexion = view.findViewById(R.id.progressBar_connexion);
    }

    private User getUser() {

        String userJson = SharedPreferencesRepository.getSharedPreferences(mContext, Constants.SHARED_USER);
        Gson gson = new Gson();
        user = gson.fromJson(userJson, User.class);

        return user;
    }

    private FilterPreferences getUserFilterPreferences(){
        FilterPreferences filterPreferences = new FilterPreferences();

        return  filterPreferences;
    }
}
