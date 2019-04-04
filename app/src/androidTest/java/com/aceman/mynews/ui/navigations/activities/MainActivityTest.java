/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.ui.navigations.activities;


import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.not;

import com.aceman.mynews.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



/**
 * Created by Lionel JOFFRAY - on 04/04/2019.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void onClickSearchTopRightTest() {

        Intents.init();
        onView(withId(R.id.menu_activity_main_search))
                .perform(ViewActions.click());
        Intents.release();

        onView(withId(R.id.firstlayout))
                .check(matches(isDisplayed()));

        onView(withId(R.id.layout_search_categories))   //  Check if categories are shown
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_search_btn))
                .check(matches(not(isEnabled())));       //  Test if search btn is disabled

        onView(withId(R.id.activity_search_search_query))       //  add query
                .perform(ViewActions.typeText("cats"),ViewActions.closeSoftKeyboard());

        onView(withId(R.id.checkbox_movies))    //  Category checked, search btn enable
                .perform(ViewActions.click());

        onView(withId(R.id.activity_search_btn))        //  Search btn clicked
                .perform(ViewActions.click());

        onView(withId(R.id.activity_search_btn))        //  Search btn gone
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void onClickNotificationTopRightTest() {

        Intents.init();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.notfication_menu))
                .perform(ViewActions.click());
        Intents.release();

        onView(withId(R.id.notification_linear_layout))
                .check(matches(isDisplayed()));

        onView(withId(R.id.checkbox_movies))   //  Check if categories are shown
                .check(matches(isDisplayed()));

        onView(withId(R.id.notification_switch))
                .check(matches(not(isEnabled())));       //  Test if notification btn is disabled

        onView(withId(R.id.activity_notifications_search_query))       //  add query
                .perform(ViewActions.typeText("French"),ViewActions.closeSoftKeyboard());

        onView(withId(R.id.checkbox_travel))    //  Category checked, search btn enable
                .perform(ViewActions.click());

        onView(withId(R.id.notification_switch))        //  Notification btn clicked (enable notification)
                .perform(ViewActions.click());

        onView(withId(R.id.notification_switch))        //  Notification btn clicked (disable notification)
                .perform(ViewActions.click());
    }

    @Test
    public void onClickHelpTopRightTest() {

        Intents.init();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.help))    //  Click on help menu right
                .perform(ViewActions.click());
        Intents.release();

        onView(withId(R.id.textview_help_drawer))    //  Check if displayed
                .check(matches(isDisplayed()));
    }

    @Test
    public void onClickAboutTopRightTest() {

        Intents.init();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.about))    //  Click on about menu right
                .perform(ViewActions.click());
        Intents.release();

        onView(withId(R.id.text_copyright_textview))    //  Check if displayed
                .check(matches(isDisplayed()));

        onView(withId(R.id.dialogButton))    //  Close by click
                .perform(ViewActions.click());
    }
}