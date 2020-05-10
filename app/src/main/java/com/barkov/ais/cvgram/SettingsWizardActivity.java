package com.barkov.ais.cvgram;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.barkov.ais.cvgram.entity.User;
import com.barkov.ais.cvgram.services.RegisterServiceProvider;
import com.barkov.ais.cvgram.tabsswipe.adapter.SectionsPageAdapter;

import com.barkov.ais.cvgram.tabsswipe.adapter.SectionsPageAdapter;
import com.barkov.ais.cvgram.tabsswipe.fragment.EmailConfirmationFragment;
import com.barkov.ais.cvgram.tabsswipe.fragment.SelectUserTypeFragment;
import com.barkov.ais.cvgram.tabsswipe.fragment.UserCredentialsFragment;
import com.barkov.ais.cvgram.tabsswipe.fragment.UserPersonalFragment;
import com.barkov.ais.cvgram.tabsswipe.fragment.ValidableFragment;
import com.barkov.ais.cvgram.tabsswipe.pager.ctrViewPager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class SettingsWizardActivity extends FragmentActivity implements OnTaskCompleted{

    private int mCode;
    SectionsPageAdapter mSectionsPageAdapter;
    TabLayout  mTableLayout;
    private ctrViewPager mviewPager;
    int startPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_wizard);
        Log.d("dbg", "onCreate: starting");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mviewPager = findViewById(R.id.container);
        setupViewPager(mviewPager);

        mTableLayout = findViewById(R.id.tabs);
        mTableLayout.setupWithViewPager(mviewPager,true);

        mCode = 0;
        //createProfile();
    }

    private void setupViewPager(ViewPager viewPager)
    {
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mSectionsPageAdapter.addFragment(new UserPersonalFragment(), "Personal");
        mSectionsPageAdapter.addFragment(new EmailConfirmationFragment(), "Email");
        mSectionsPageAdapter.addFragment(new SelectUserTypeFragment(), "Type");
        mSectionsPageAdapter.addFragment(new UserCredentialsFragment(), "Creds");
        viewPager.setAdapter(mSectionsPageAdapter);
    }

    public void nextTab()
    {
        TabLayout layout = findViewById(R.id.tabs);

        int position = layout.getSelectedTabPosition();
        if (position + 1 < layout.getTabCount()) {
            layout.getTabAt(position + 1).select();
        }

    }

    public void setVerificationCode (int code)
    {
        this.mCode = code;
    }

    public int getVerificationCode ()
    {
        return this.mCode;
    }

    public void setTabsState(final boolean state)
    {
        mviewPager.setSwipeState(state);

        TabLayout layout = findViewById(R.id.tabs);
        final TabLayout lt = layout;
        layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public int registerUser()
    {
        int result = validateProfileSettings();

        if (result >= 0) {
            TabLayout layout = findViewById(R.id.tabs);
            layout.getTabAt(result).select();
        }

        createProfile();
        
        return result;
    }

    protected int validateProfileSettings()
    {
        for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
            if (!((ValidableFragment)getSupportFragmentManager()
                    .getFragments().get(i)).validateInput()
            ) {
                return i;
            }
        }

        return 0;
    }

    protected void createProfile()
    {
        ArrayMap profile = new ArrayMap();

        for (int i=0; i< getSupportFragmentManager().getFragments().size(); i++) {

            ((ValidableFragment)getSupportFragmentManager()
                    .getFragments().get(i)).setProfileFields(profile);
        }

        User user = new User(profile.get("name").toString(),
                profile.get("email").toString(),
                profile.get("phone").toString(),
                Integer.valueOf(profile.get("userType").toString()),
                profile.get("login").toString(),
                profile.get("password").toString()
        );

        RegisterServiceProvider provider = new RegisterServiceProvider(this);
        try {
            provider.register(user, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(HashMap result)
    {
        if (result.get("success").equals("true")) {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        displayInvalidRegister();
    }

    private void displayInvalidRegister()
    {
        Toast.makeText(this, "Unable to register user", Toast.LENGTH_LONG).show();
    }
}
