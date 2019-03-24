package com.aceman.mynews.ui.navigations.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aceman.mynews.R;
import com.aceman.mynews.ui.notifications.activities.NotificationActivity;
import com.aceman.mynews.ui.search.activities.SearchActivity;
import com.aceman.mynews.ui.navigations.adapter.PageAdapter;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.State;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
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
    @State
    int mFragmentTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Icepick.restoreInstanceState(this, savedInstanceState);
        sslUpdate();
        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureViewPagerAndTabs();

        configureNavigationView();

    }

    private void sslUpdate() {
        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // 1 - Configure Toolbar
    private void configureToolBar() {
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_notification:
                Intent newNotification = new Intent(this, NotificationActivity.class);
                Log.d("MainActivity","Click Notification");
                startActivity(newNotification);
                return true;
            case R.id.menu_activity_main_search:
                Intent newSearch = new Intent(this, SearchActivity.class);
                Log.d("MainActivity","Click Search");
                startActivity(newSearch);
                return true;
            case R.id.menu_activity_main_help:
                Log.d("MainActivity","Click Help");
                return true;
            case R.id.menu_activity_main_about:
                Toast.makeText(this, "Aceman 2019 Copyright.", Toast.LENGTH_LONG).show();
                Log.d("MainActivity","Click About");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    private void configureViewPagerAndTabs(){
        //Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(),getApplicationContext()));
        // 2 - Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // 3 - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

    }



        private void configureNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        // 6 - Show fragment after user clicked on a menu item
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
            case R.id.activity_main_drawer_tech:
                pager.setCurrentItem(3);
                break;
            case R.id.activity_main_drawer_food:
                pager.setCurrentItem(4);
                break;
            case R.id.activity_main_drawer_movies:
                pager.setCurrentItem(5);
                break;
            case R.id.activity_main_drawer_sports:
                pager.setCurrentItem(6);
                break;
            case R.id.activity_main_drawer_travel:
                pager.setCurrentItem(7);
                break;
            case R.id.activity_main_drawer_search:
               Intent search = new Intent(this,SearchActivity.class);
               this.startActivity(search);
                break;
            case R.id.activity_main_drawer_notification:
                Intent notification = new Intent(this,NotificationActivity.class);
                this.startActivity(notification);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


}