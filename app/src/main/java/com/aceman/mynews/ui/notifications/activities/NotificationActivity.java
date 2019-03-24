package com.aceman.mynews.ui.notifications.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.aceman.mynews.R;
import com.aceman.mynews.jobs.SampleBootReceiver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.notification_switch)
    Switch mNotificationSwitch;
    @BindView(R.id.activity_notifications_search_query)
    EditText mNotificationSearchQuery;
    private PendingIntent pendingIntentNotification;
    String mSearchResult;
    @BindView(R.id.checkbox_business)
    CheckBox mBusiness;
    @BindView(R.id.checkbox_tech)
    CheckBox mTech;
    @BindView(R.id.checkbox_food)
    CheckBox mFood;
    @BindView(R.id.checkbox_movies)
    CheckBox mMovies;
    @BindView(R.id.checkbox_sports)
    CheckBox mSports;
    @BindView(R.id.checkbox_travel)
    CheckBox mTravel;
    List<Boolean> mCheckList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        this.configureToolbar();
        mCheckList = new ArrayList<>();
        setNotificationSwitch();
        searchQueryListener();
        setCheckListSize();
        checkBoxListnener();
    }

    void setCheckListSize() {
        for (int i = 0; i < 6; i++) {
            mCheckList.add(false);
        }
    }

    public List<Boolean> checkBoxListnener() {

        mBusiness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i("Categories", "Business is Checked!");
                    mCheckList.set(0, true);
                } else {
                    Log.i("Categories", "Business is Unchecked!");
                    mCheckList.set(0, false);
                }
            }
        });
        mTech.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i("Categories", "Cars is Checked!");
                    mCheckList.set(1, true);
                } else {
                    Log.i("Categories", "Cars is Unchecked!");
                    mCheckList.set(1, false);
                }
            }
        });
        mFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i("Categories", "Food is Checked!");
                    mCheckList.set(2, true);
                } else {
                    Log.i("Categories", "Food is Unchecked!");
                    mCheckList.set(2, false);
                }
            }
        });
        mMovies.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i("Categories", "Movies is Checked!");
                    mCheckList.set(3, true);
                } else {
                    Log.i("Categories", "Movies is Unchecked!");
                    mCheckList.set(3, false);
                }
            }
        });
        mSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i("Categories", "Sports is Checked!");
                    mCheckList.set(4, true);
                } else {
                    Log.i("Categories", "Sports is Unchecked!");
                    mCheckList.set(4, false);
                }
            }
        });
        mTravel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i("Categories", "Travel is Checked!");
                    mCheckList.set(5, true);
                } else {
                    Log.i("Categories", "Travel is Unchecked!");
                    mCheckList.set(5, false);
                }
            }
        });
        return mCheckList;
    }


    private void configureToolbar(){
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }
    public void setNotificationSwitch(){


        mNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setNotificationOn();
                    Log.i("NotificationActivity","Notification Checked!");
                }else{
                    setNotificationOff();
                    Log.i("NotificationActivity","Notification Unchecked!");
                }
            }
        });
    }

    public void searchQueryListener(){
        mNotificationSearchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSearchResult  = mNotificationSearchQuery.getText().toString();
            }
        });
    }


    private void setNotificationOn() {

        enableReceiver();
        AlarmManager notification = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        notification.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+10*1000, AlarmManager.INTERVAL_HALF_DAY, pendingIntentNotification);
        Toast.makeText(this, "Notifications set !", Toast.LENGTH_SHORT).show();
    }

    private void setNotificationOff() {
        disableReceiver();
        AlarmManager notification = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        notification.cancel(pendingIntentNotification);
        Toast.makeText(this, "Notifications Canceled !", Toast.LENGTH_SHORT).show();
    }

    private void enableReceiver() {
        ComponentName receiver = new ComponentName(getApplicationContext(), SampleBootReceiver.class);
        PackageManager pm = getApplicationContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void disableReceiver() {
        ComponentName receiver = new ComponentName(getApplicationContext(), SampleBootReceiver.class);
        PackageManager pm = getApplicationContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
