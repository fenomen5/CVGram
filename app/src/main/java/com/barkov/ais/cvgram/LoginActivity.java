package com.barkov.ais.cvgram;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.barkov.ais.cvgram.entity.UserType;
import com.barkov.ais.cvgram.services.AuthServiceProvider;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener, OnTaskCompleted{

    EditText txtLogin, txtPassword;
    Button btnLogin, btnRegister, btnAbout;
    TextView lblLoginForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(this);

        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);

        lblLoginForm = findViewById(R.id.lblLoginForm);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnLogin) {
            processLogin(txtLogin.getText().toString(), txtPassword.getText().toString());
        }
        if (v.getId() == R.id.btnRegister) {
            startRegisterActivity();
        }
        if (v.getId() == R.id.btnAbout) {
            startAboutActivity();
        }

    }

    /**
     * Login user
     * @param login
     * @param password
     * @return
     */
    protected boolean processLogin(String login, final String password)
    {
        AuthServiceProvider authProvider = new AuthServiceProvider(this);

        if (!authProvider.isOnline()) {
            Log.d("dbg", "No internet connection");
            authProvider.displayNoInternetConnection();
            return false;
        }

        try {
            authProvider.login(login, password, this);
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }

        return  true;
    }

    /**
     * Display invalid login message
     */
    protected void displayInvalidLogin()
    {
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.wrong_login);
        lblLoginForm.setText("login or password are incorrect");
        lblLoginForm.setTextColor(getResources().getColor(R.color.colorErrorRed));
        lblLoginForm.startAnimation(animShake);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lblLoginForm.setText("Login");
                lblLoginForm.setTextColor(getResources().getColor(R.color.title_white));
            }
        },4000);

    }

    @Override
    public void onTaskCompleted(HashMap result)
    {
        if (result.get("success").equals("true")) {
            Intent intent;
            if (result.get("user_type").equals(""+UserType.JOBSEEKER_TYPE)) {
                intent = new Intent(this, JobSeekerDashboardActivity.class);
            } else {
                intent = new Intent(this, EmployerActivity.class);
            }

            startActivity(intent);
            finish();
            return;
        }

        displayInvalidLogin();
    }

    /**
     * Go to Register screen
     */
    private void startRegisterActivity()
    {
        Intent intent = new Intent(this, SettingsWizardActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    /**
     * Go to About screen
     */
    private void startAboutActivity()
    {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
        return;
    }
}
