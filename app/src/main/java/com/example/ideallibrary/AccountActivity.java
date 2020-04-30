package com.example.ideallibrary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ideallibrary.entities.User;
import com.example.ideallibrary.repository.SharedPreferencesRepository;
import com.example.ideallibrary.utilities.Constants;
import com.google.gson.Gson;

public class AccountActivity extends AppCompatActivity {

    public static final String TAG = "Mon Compte";

    //UI components
    private TextView txtAccountName;
    private Button btnModifyPassword;

    //vars
    private User user;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar_account);
        setSupportActionBar(toolbar);
        setTitle(TAG.toUpperCase());

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        user = getUser();
        txtAccountName = findViewById(R.id.txt_account_name);
        txtAccountName.setText(user.getFirstName());

        btnModifyPassword = findViewById(R.id.btn_modify_password);
        btnModifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogModifyPassword();
            }
        });
    }

    private User getUser() {

        String userJson = SharedPreferencesRepository.getSharedPreferences(context, Constants.SHARED_USER);
        Gson gson = new Gson();
        user = gson.fromJson(userJson, User.class);

        return user;
    }

    private void  openDialogModifyPassword(){
        ModifyPasswordDialog modifyPasswordDialog = new ModifyPasswordDialog();
        modifyPasswordDialog.show(getSupportFragmentManager(), "password dialog");
    }
}
