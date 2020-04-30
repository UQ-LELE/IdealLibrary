package com.example.ideallibrary;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ideallibrary.R;
import com.example.ideallibrary.entities.User;
import com.example.ideallibrary.repository.SharedPreferencesRepository;
import com.example.ideallibrary.utilities.Constants;
import com.example.ideallibrary.utilities.Fun;
import com.google.gson.Gson;

public class ModifyPasswordDialog extends AppCompatDialogFragment {

    // UI components
    private EditText edtOldPassword;
    private EditText edtNewPassword;
    private EditText edtConfirmPassord;


    // vars
    User user;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_modify_password, null);

        edtOldPassword = view.findViewById(R.id.edt_old_password);
        edtNewPassword = view.findViewById(R.id.edt_new_password);
        edtConfirmPassord = view.findViewById(R.id.edt_confirm_password);

        builder.setView(view)
                .setTitle("Modifier mon mot de passe :")
                .setPositiveButton("Valider", null)
                .setNegativeButton("Annuler", null);

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String oldPassword = edtOldPassword.getText().toString().trim();
                if(checkOldPassword(oldPassword)){
                    if(edtNewPassword.getText().toString().trim().equals(edtConfirmPassord.getText().toString().trim())){
                       saveNewPassword(edtConfirmPassord.getText().toString().trim());
                        dialog.dismiss();
                        Fun.showToastMessage(getActivity(), "Nouveau mot de passe enregistré", Constants.TOAST_VALIDATION);
                    }else{
                        Fun.showToastMessage(getActivity(), "Confirmation du mot de passe incorrect", Constants.TOAST_ERROR);
                    }
                }else{
                    Fun.showToastMessage(getActivity(), "Mot de passe incorrect", Constants.TOAST_ERROR);
                }
            }
        });
        return dialog;
    }

    public boolean checkOldPassword(String password){
        boolean isPassword = false;
        user = getUser();
        if(password.equals(user.getPassword())){
            isPassword = true;
        }
        return isPassword;
    }

    private User getUser() {

        String userJson = SharedPreferencesRepository.getSharedPreferences(getActivity(), Constants.SHARED_USER);
        Gson gson = new Gson();
        user = gson.fromJson(userJson, User.class);

        return user;
    }

    private void saveNewPassword(String newPassword){
            user.setPassword(newPassword);
            Gson gson = new Gson();
            String userJson = gson.toJson(user);
            SharedPreferencesRepository.insertSharedPreferences(getActivity(), Constants.SHARED_USER, userJson);
    }
}
