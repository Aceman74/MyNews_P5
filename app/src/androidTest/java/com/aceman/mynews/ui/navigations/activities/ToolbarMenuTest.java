/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.ui.navigations.activities;


import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;

import com.aceman.mynews.R;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static com.aceman.mynews.utils.TestUtils.atPosition;
import static com.aceman.mynews.utils.TestUtils.hasItemCount;
import static com.aceman.mynews.utils.TestUtils.waitUntil;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;


/**
 * Created by Lionel JOFFRAY - on 04/04/2019.
 */
@RunWith(AndroidJUnit4.class)
public class ToolbarMenuTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
    }

    @Test
    public void Search_Bike_Query_Sports_Category_Test() {

        Intents.init();
        onView(withId(R.id.menu_activity_main_search))
                .perform(click());
        Intents.release();

        onView(withId(R.id.firstlayout))
                .check(matches(isDisplayed()));

        onView(withId(R.id.layout_search_categories))   //  Check if categories are shown
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_search_btn))
                .check(matches(not(isEnabled())));       //  Test if search btn is disabled

        onView(withId(R.id.activity_search_search_query))       //  add query
                .perform(ViewActions.typeText("bike"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.checkbox_sports))    //  Category checked, search btn enable
                .perform(click());

        onView(withId(R.id.activity_search_btn))        //  Search btn clicked
                .perform(click());

        onView(withId(R.id.checkbox_travel))        //  Categories are gone
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.search_fragment_recyclerview))       //  Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.search_fragment_recyclerview))   //  Check that item display all details
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_image)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_date)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_title)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_categorie)))));

        onView(withId(R.id.search_fragment_recyclerview))   //  Recycler View click item 2
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.webview))    //  Open Webview
                .perform(waitUntil(isDisplayed()))
                .check(matches(isDisplayed()));

        onWebView()
                .withElement(findElement(Locator.CSS_SELECTOR, "title"));   //  Wait for webview to show completely
    }

    @Test
    public void Search_Avengers_Query_Movies_Category_With_Date_Test() {

        Intents.init();
        onView(withId(R.id.menu_activity_main_search))
                .perform(click());
        Intents.release();

        onView(withId(R.id.firstlayout))
                .check(matches(isDisplayed()));

        onView(withId(R.id.layout_search_categories))   //  Check if categories are shown
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_search_btn))
                .check(matches(not(isEnabled())));       //  Test if search btn is disabled

        onView(withId(R.id.activity_search_search_query))       //  add query
                .perform(ViewActions.typeText("Avengers"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.textview_from_date))
                .perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2018, 10, 1))
                .perform(pressBack());

        onView(withId(R.id.textview_to_date))
                .perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2018, 10, 1))
                .perform(pressBack());

        onView(withId(R.id.checkbox_movies))    //  Category checked, search btn enable
                .perform(click());

        onView(withId(R.id.activity_search_btn))        //  Search btn clicked
                .perform(click());

        onView(withId(R.id.checkbox_movies))        //  Categories are gone
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.search_fragment_recyclerview))       //  Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.search_fragment_recyclerview))   //  Check that item display all details
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_image)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_date)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_title)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_categorie)))));

        onView(withId(R.id.search_fragment_recyclerview))   //  Recycler View click item 2
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.webview))    //  Open Webview
                .perform(waitUntil(isDisplayed()))
                .check(matches(isDisplayed()));

        onWebView()
                .withElement(findElement(Locator.CSS_SELECTOR, "title"));   //  Wait for webview (find item on page)
    }

    @Test
    public void Switch_Notification_Test() {

        Intents.init();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.notfication_menu))
                .perform(click());
        Intents.release();

        onView(withId(R.id.notification_linear_layout))
                .check(matches(isDisplayed()));

        onView(withId(R.id.checkbox_movies))   //  Check if categories are shown
                .check(matches(isDisplayed()));

        onView(withId(R.id.notification_switch))
                .check(matches(not(isEnabled())));       //  Test if notification btn is disabled

        onView(withId(R.id.activity_notifications_search_query))       //  add query
                .perform(ViewActions.typeText("French"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.checkbox_travel))    //  Category checked, search btn enable
                .perform(click());

        onView(withId(R.id.notification_switch))        //  Notification btn clicked (enable notification)
                .perform(click());

        onView(withId(R.id.notification_switch))        //  Notification btn clicked (disable notification)
                .perform(click());
    }

    @Test
    public void Click_Help_Test() {

        Intents.init();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.help))    //  Click on help menu right
                .perform(click());
        Intents.release();

        onView(withId(R.id.textview_help_drawer))    //  Check if displayed
                .check(matches(isDisplayed()));
    }

    @Test
    public void Click_About_Test() {

        Intents.init();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.about))    //  Click on about menu right
                .perform(click());
        Intents.release();

        onView(withId(R.id.text_copyright_textview))    //  Check if displayed
                .check(matches(isDisplayed()));

        onView(withId(R.id.dialogButton))    //  Close by click
                .perform(click());
    }
}