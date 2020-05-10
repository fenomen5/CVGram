package com.barkov.ais.cvgram.tabsswipe.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.SettingsWizardActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCredentialsFragment extends ValidableFragment implements View.OnClickListener {

    Button btnRegister;
    EditText txtLogin, txtPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_credentials, container, false);

        btnRegister = view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        txtLogin = view.findViewById(R.id.txtLogin);
        txtPassword = view.findViewById(R.id.txtPassword);

        return view;
    }

    @Override
    public void onClick(View v) {

        if (!validatePersonalDetails()) {
            return;
        }
        ((SettingsWizardActivity)getActivity()).registerUser();
    }

    protected boolean validatePersonalDetails()
    {
        boolean result = true;

        setValidInput(txtLogin);
        setValidInput(txtPassword);

        if (txtLogin.getText().length() <= 2  ||
                !txtLogin.getText().toString().matches("[a-zA-Z]+")) {
            setInvalidInput(txtLogin);
            result = false;
        }

        Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher hasLetter = letter.matcher(txtPassword.getText().toString());
        Matcher hasDigit = digit.matcher(txtPassword.getText().toString());
        Matcher hasSpecial = special.matcher(txtPassword.getText().toString());

        if (txtPassword.getText().length() <= 4 ||
                !hasLetter.find() || !hasDigit.find() || !hasSpecial.find()
        ) {
            setInvalidInput(txtPassword);
            result = false;
        }

        return result;
    }

    protected void setInvalidInput(View v)
    {
        v.setBackgroundColor(getResources().getColor(R.color.colorErrorRed));
        if (v.getId() == R.id.txtPassword)  {
            Toast.makeText(getContext(), "Password should be more than 4 characters " +
                    "long and contains numbers and special symbols", Toast.LENGTH_LONG).show();
        }
        if (v.getId() == R.id.txtLogin)  {
            Toast.makeText(getContext(), "Login shold be longer than " +
                    "2 characters", Toast.LENGTH_LONG).show();
        }
    }

    protected void setValidInput(View v) {
        v.setBackgroundColor(getResources().getColor(R.color.colorBlue));
    }

    @Override
    public boolean validateInput()
    {
        return validatePersonalDetails();
    }

    @Override
    public ArrayMap setProfileFields(ArrayMap list) {

        list.put("login", txtLogin.getText());
        list.put("password", txtPassword.getText());
        return super.setProfileFields(list);
    }
}
