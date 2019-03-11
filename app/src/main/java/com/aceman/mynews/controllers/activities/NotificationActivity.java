package com.aceman.mynews.controllers.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.aceman.mynews.R;
import com.aceman.mynews.controllers.fragments.CategoriesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    CategoriesFragment mCategoriesFragment;
    @BindView(R.id.notification_switch)
    Switch mNotificationSwitch;
    @BindView(R.id.activity_notifications_search_query)
    EditText mNotificationSearchQuery;
    private PendingIntent pendingIntentNotification;
    String mSearchResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        this.configureToolbar();
        configureAlarmManager();
        setNotificationSwitch();
        mCategoriesFragment = new CategoriesFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_notification_categories, mCategoriesFragment)
                .commit();
        searchQueryListener();
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

    private void configureAlarmManager(){
        Intent alarmIntent = new Intent(NotificationActivity.this, MyAlarmReceiver.class);
        pendingIntentNotification = PendingIntent.getBroadcast(NotificationActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private void setNotificationOn() {
        AlarmManager notification = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        notification.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,0, AlarmManager.INTERVAL_DAY, pendingIntentNotification);
        Toast.makeText(this, "Notifications set !", Toast.LENGTH_SHORT).show();
    }

    private void setNotificationOff() {
        AlarmManager notification = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        notification.cancel(pendingIntentNotification);
        Toast.makeText(this, "Notifications Canceled !", Toast.LENGTH_SHORT).show();
    }
}
