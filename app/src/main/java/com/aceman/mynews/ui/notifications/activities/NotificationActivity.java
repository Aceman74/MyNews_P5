package com.aceman.mynews.ui.notifications.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.aceman.mynews.R;
import com.aceman.mynews.jobs.DailyWorker;
import com.aceman.mynews.utils.CategoriesCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lionel JOFFRAY.
 * <p>
 * <b>Notification Activity</> who configure the daily notification <br>
 */
public class NotificationActivity extends AppCompatActivity {
    public static int mFirstNot;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.notification_switch)
    Switch mNotificationSwitch;
    @BindView(R.id.activity_notifications_search_query)
    EditText mNotificationSearchQuery;
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
    SharedPreferences mSharedPreferences;
    int mJob;
    String mCategorieResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        this.configureToolbar();
        mCheckList = new ArrayList<>();
        loadPref(); //  If already set, the previous query is shown in edit text
        setNotificationSwitch();
        searchQueryListener();
        CategoriesCheck.setCheckListSize(mCheckList);
        CategoriesCheck.checkBoxListnener(mBusiness, mTech, mFood, mMovies, mSports, mTravel, mCheckList);
        clickListener();
    }

    /**
     * Set the toolbar
     */
    public void configureToolbar() {
        //Set the toolbar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Set the notification switch active or not, if user already use active notification
     */
    public void setNotificationSwitch() {

        if (mJob == 1) {    //  mJob is 1 if user ase active notification
            mNotificationSwitch.setChecked(true);
            mNotificationSearchQuery.setText(mSearchResult);
            mNotificationSwitch.setEnabled(true);
        }

        mNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCategorieResult = CategoriesCheck.getQueryCategories(mCheckList);
                    setNotificationOn();
                    mJob = 1;
                    Log.i("NotificationActivity", "Notification Checked!");
                } else {
                    setNotificationOff();
                    mJob = 0;
                    Log.i("NotificationActivity", "Notification Unchecked!");
                }
                savePref();
            }
        });
    }

    /**
     * Query listener, for notification query and enabling switch
     */
    public void searchQueryListener() {

        mNotificationSearchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSearchResult = mNotificationSearchQuery.getText().toString();
                checkState();
                onHitEnter();
            }
        });
    }

    /**
     * Handle hit on enter to valid query
     */
    public void onHitEnter() { //  Handle the enter key to launch search

        mNotificationSearchQuery.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    setNotificationSwitch();

                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Check if query is more than 0 and at least one category is checked
     */
    private void checkState() {
        // Checking the query and category check for enable notification
        if (mNotificationSearchQuery.getText().toString().trim().length() > 0 && mCheckList.contains(true)) {
            mNotificationSwitch.setEnabled(true);
        } else {
            mNotificationSwitch.setEnabled(false);
        }
    }

    /**
     * Enable notification <br>
     * Using <b>Androidx Worker</>
     */
    public void setNotificationOn() {
        Data notificationData = new Data.Builder()
                .putString("Query", mSearchResult)
                .putString("Categorie", mCategorieResult)
                .putInt("FirstNotification", mFirstNot)
                .build();

        PeriodicWorkRequest notifRequestDay = new PeriodicWorkRequest.Builder(DailyWorker.class, 1, TimeUnit.DAYS)
                .addTag("RequestDaliy")
                .setInputData(notificationData)
                .build();

        WorkManager.getInstance().enqueue(notifRequestDay);
        Toast.makeText(this, "Notifications set !", Toast.LENGTH_SHORT).show();
    }

    /**
     * Disable notification <br>
     * Using <b>Androidx Worker</>
     */
    public void setNotificationOff() {
        mFirstNot = 0;
        WorkManager.getInstance().cancelAllWorkByTag("RequestDaliy");
        WorkManager.getInstance().cancelAllWork();
        Toast.makeText(this, "Notifications Canceled !", Toast.LENGTH_SHORT).show();
    }

    /**
     * Save the Job as int to update fragment view on next opening
     */
    public void savePref() {
        //  Save user query and notification
        mSharedPreferences = getSharedPreferences("Notification", MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("Job", mJob)
                .putString("Query", mSearchResult)
                .apply();
    }

    /**
     * Load the Job as int to update fragment view on next opening
     */
    public void loadPref() {
        //  Load user query and notification
        mSharedPreferences = getSharedPreferences("Notification", MODE_PRIVATE);
        mJob = mSharedPreferences.getInt("Job", mJob);
        mSearchResult = mSharedPreferences.getString("Query", mSearchResult);
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Category Checkbox Listeners
     */
    void clickListener() {
        mBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkState();
            }
        });
        mFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkState();
            }
        });
        mMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkState();
            }
        });
        mSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkState();
            }
        });
        mTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkState();
            }
        });
        mTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkState();
            }
        });
    }
}
