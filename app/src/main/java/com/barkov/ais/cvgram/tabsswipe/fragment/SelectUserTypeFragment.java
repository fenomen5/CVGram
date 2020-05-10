package com.barkov.ais.cvgram.tabsswipe.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.SettingsWizardActivity;
import com.barkov.ais.cvgram.dataadapter.UserTypesAdapter;
import com.barkov.ais.cvgram.entity.UserType;

public class SelectUserTypeFragment extends ValidableFragment {

    Spinner spinner;
    Button btnNext;
    UserTypesAdapter userTypesAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_usertype_select, container, false);

        spinner = view.findViewById(R.id.spinner);

        userTypesAdapter = new UserTypesAdapter(getContext());
        spinner.setAdapter(userTypesAdapter);

        btnNext = view.findViewById(R.id.btnLocationNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateValues()) {
                    ((SettingsWizardActivity) getActivity()).nextTab();
                }
            }
        });

        return view;
    }

    protected boolean validateValues()
    {
        return true;
    }

    @Override
    public boolean validateInput()
    {
        return validateValues();
    }

    @Override
    public ArrayMap setProfileFields(ArrayMap list) {

        int userTypeId = ((UserType)spinner.getSelectedItem()).getId();

        list.put("userType", String.valueOf(userTypeId));
        return super.setProfileFields(list);
    }
}
