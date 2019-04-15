package com.aceman.mynews.ui.navigations.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.aceman.mynews.R;
import com.aceman.mynews.ui.navigations.adapter.PageAdapter;
import com.aceman.mynews.ui.notifications.activities.NotificationActivity;
import com.aceman.mynews.ui.search.activities.SearchActivity;
import com.aceman.mynews.utils.CopyrightDialog;
import com.aceman.mynews.utils.HelpDialog;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cache;

/**
 * Created by Lionel JOFFRAY.
 * <p>
 * <b>Main Activity</> who configure a lot of method: <br>
 * - Configure ViewPager <br>
 * - Configure Navigation Drawer <br>
 * - Configure ViewPager <br>
 * - Configure Notification Channel <br>
 * - Configure SSL update for Android API < 19 <br>
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String CHANNEL_ID = "29";
    public static Cache mCache;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_main_drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.activity_main_viewpager)
    ViewPager pager;
    @BindView(R.id.activity_main_tabs)
    TabLayout tabs;
    @BindView(R.id.activity_main_nav_view)
    NavigationView mNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sslUpdate();    //  handle SSLHandshakeException for older API
        configureCache();
        configureToolBar();
        configureDrawerLayout();
        configureViewPagerAndTabs();    //  cause frame skip
        configureNavigationView();
        new onLaunchMainActivity().execute("notification and glide cache");
    }

    /**
     * Setting the cache size for App
     */
    private void configureCache() {
        int cacheSize = 5 * 1024 * 1024; // 5 MB
        mCache = new Cache(getCacheDir(), cacheSize);   //  For API requests
    }

    /**
     * onDestroy override to clear Glide and pager memory
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        pager.setAdapter(null);
        Glide.get(getApplicationContext()).clearMemory();
    }

    /**
     * onBackPressed override for handle navigation drawer closing
     */
    @Override
    public void onBackPressed() {
        //  Handle back click to close menu of navdrawer
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Set the toolbar
     */
    private void configureToolBar() {
        setSupportActionBar(toolbar);
    }

    /**
     * Creation of menu in Toolbar
     *
     * @param menu layout for the menu
     * @return the menu (top right)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    /**
     * Configure click on the previous created menu
     *
     * @param item item in menu
     * @return true if clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_notification:
                Intent newNotification = new Intent(this, NotificationActivity.class);
                Log.d("MainActivity", "Click Notification");
                startActivity(newNotification);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.menu_activity_main_search:
                Intent newSearch = new Intent(this, SearchActivity.class);
                Log.d("MainActivity", "Click Search");
                startActivity(newSearch);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.menu_activity_main_help:
                Log.d("MainActivity", "Click Help");
                HelpDialog.openHelp(this);
                return true;
            case R.id.menu_activity_main_about:
                CopyrightDialog.openCopyright(this);
                Log.d("MainActivity", "Click About");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Set the tabs navigation
     */
    private void configureViewPagerAndTabs() {
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(), getApplicationContext()));
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    /**
     * Set the nav drawer "hamburger"
     */
    private void configureDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * Set listener for Nav drawer
     */
    private void configureNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Configure the menu of Navigation drawer Click
     *
     * @param item the news item
     * @return the fragment of the news
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //  Navigation Drawer item settings
        int id = item.getItemId();

        switch (id) {
            case R.id.activity_main_drawer_topstories:
                pager.setCurrentItem(0);
                break;
            case R.id.activity_main_drawer_mostpopulate:
                pager.setCurrentItem(1);
                break;
            case R.id.activity_main_drawer_business:
                pager.setCurrentItem(2);
                break;
            case R.id.activity_main_drawer_food:
                pager.setCurrentItem(3);
                break;
            case R.id.activity_main_drawer_movies:
                pager.setCurrentItem(4);
                break;
            case R.id.activity_main_drawer_sports:
                pager.setCurrentItem(5);
                break;
            case R.id.activity_main_drawer_tech:
                pager.setCurrentItem(6);
                break;
            case R.id.activity_main_drawer_travel:
                pager.setCurrentItem(7);
                break;
            case R.id.activity_main_drawer_search:
                Intent search = new Intent(this, SearchActivity.class);
                this.startActivity(search);
                break;
            case R.id.activity_main_drawer_notification:
                Intent notification = new Intent(this, NotificationActivity.class);
                this.startActivity(notification);
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Create the notification Channel for daily notification
     */
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * Method for older Android Version who update SSL security
     */
    private void sslUpdate() {
        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Call the Create Notification Channel and clear Glide cache Asynchronously
     */
    class onLaunchMainActivity extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            createNotificationChannel();
            Glide.get(getApplicationContext()).clearDiskCache();
            Log.d("Async", "Executed!");
            return null;
        }
    }
}