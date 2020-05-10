package com.barkov.ais.cvgram.tabsswipe.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.barkov.ais.cvgram.EmailHelper;
import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.SettingsWizardActivity;

import java.util.Random;

public class UserPersonalFragment extends ValidableFragment implements View.OnClickListener {

    Button btnAccountStep1;
    EditText txtName, txtEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        btnAccountStep1 = view.findViewById(R.id.btnAccountStep1);
        btnAccountStep1.setOnClickListener(this);

        txtName = view.findViewById(R.id.txtSearchParamClubName);
        txtEmail = view.findViewById(R.id.txtEmail);

        return view;
    }

    @Override
    public void onClick(View v) {

        if (!validatePersonalDetails()) {
            return;
        }

        EmailHelper emailHelper = new EmailHelper();
        int code = new Random().nextInt(8999) + 1000;
        emailHelper.send(
                txtEmail.getText().toString(),
                "CVGram email check",
                "Your verification code is " + code
        );
        ((SettingsWizardActivity)getActivity()).setVerificationCode(code);
        ((SettingsWizardActivity)getActivity()).nextTab();
    }

    protected boolean validatePersonalDetails()
    {
        boolean result = true;

        setValidInput(txtName);
        setValidInput(txtEmail);

        if (txtName.getText().length() <= 2  ||
                !txtName.getText().toString().matches("[a-zA-Z]+")) {
            setInvalidInput(txtName);
            result = false;
        }

        if (txtEmail.getText().length() <= 2 ||
                !txtEmail.getText().toString()
                        .matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            setInvalidInput(txtEmail);
            result = false;
        }

        return result;
    }

    protected void setInvalidInput(View v)
    {
        v.setBackgroundColor(getResources().getColor(R.color.colorErrorRed));
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

        int code = new Random().nextInt(8999) + 1000;
        list.put("name", txtName.getText());
        list.put("email", txtEmail.getText());
        list.put("phone", "02045" + code );

        Log.d("dbg_userpersoanl", list.toString());
        return super.setProfileFields(list);
    }
}
