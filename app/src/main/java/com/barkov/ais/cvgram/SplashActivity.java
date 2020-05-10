package com.barkov.ais.cvgram;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startLoginActivity();
            }
        }, 4000);
    }

    /**
     * Run Login activity
     */
    protected void startLoginActivity()
    {
        startActivity(LoginActivity.class);
    }

    /**
     * Start selected activity
     * @param activityClassName
     */
    protected void startActivity(Class activityClassName)
    {
        Intent intent = new Intent(this, activityClassName);
        //Intent intent = new Intent(this, JobSeekerDashboardActivity.class);
        //Intent intent = new Intent(this, EmployerActivity.class);
        startActivity(intent);
        finish();
    }
}
