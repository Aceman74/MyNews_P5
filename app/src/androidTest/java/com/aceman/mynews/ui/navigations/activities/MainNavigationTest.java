/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.ui.navigations.activities;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.DrawerMatchers;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.aceman.mynews.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.aceman.mynews.utils.TestUtils.atPosition;
import static com.aceman.mynews.utils.TestUtils.hasItemCount;
import static com.aceman.mynews.utils.TestUtils.waitUntil;
import static org.hamcrest.Matchers.greaterThan;

/**
 * Created by Lionel JOFFRAY - on 05/04/2019.
 */
@RunWith(AndroidJUnit4.class)
public class MainNavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {

    }

    /**
     * Test if main Activity show Top Story with recyclerview and items, and check the click on item return Webview Article.
     */
    @Test
    public void Top_Stories_Click_Item_Test() {

        onView(withId(R.id.fragment_top_stories))
                .check(matches(isDisplayed()));

        onView(withId(R.id.topstories_recycler))       //  Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.topstories_recycler))   //  Check that item display all details
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_image)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_date)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_title)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_categorie)))));

        onView(withId(R.id.topstories_recycler))   //  Recycler View click item 10
                .perform(RecyclerViewActions.scrollToPosition(10))
                .check(matches(atPosition(10, hasDescendant(withId(R.id.fragment_main_item_image)))))
                .check(matches(atPosition(10, hasDescendant(withId(R.id.fragment_main_item_date)))))
                .check(matches(atPosition(10, hasDescendant(withId(R.id.fragment_main_item_title)))))
                .check(matches(atPosition(10, hasDescendant(withId(R.id.fragment_main_item_categorie)))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(10, click()));

        onView(withId(R.id.webview))    //  Open Webview
                .perform(waitUntil(isDisplayed()))
                .check(matches(isDisplayed()));

    }

    /**
     * Test the Navigation Tab by swipping and cheking if recyclerview and items are shown for each Tab.
     */
    @Test
    public void Tab_Navigation_Swiping_Categories_Test() {


        onView(withId(R.id.fragment_top_stories))
                .check(matches(isDisplayed()));


        onView(withId(R.id.topstories_recycler))       // wait for Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_main_drawer_layout))    //  Swipe to MostPopular
                .perform(swipeLeft());

        onView(withId(R.id.mostpopular_fragment_recyclerview))       //  Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.mostpopular_fragment_recyclerview))   //  Check that item display all details
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_image)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_date)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_title)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_categorie)))));

        onView(withId(R.id.activity_main_drawer_layout))    //  Swipe to Business
                .perform(swipeLeft());


        onView(withId(R.id.business_fragment_recyclerview))       //  Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.business_fragment_recyclerview))   //  Check that item display all details
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_image)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_date)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_title)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_categorie)))));

        onView(withId(R.id.activity_main_drawer_layout))    //  Swipe to Food
                .perform(swipeLeft());

        onView(withId(R.id.food_fragment_recyclerview))       //  Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.food_fragment_recyclerview))   //  Check that item display all details
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_image)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_date)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_title)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_categorie)))));

        onView(withId(R.id.activity_main_drawer_layout))    //  Swipe to Movies
                .perform(swipeLeft());

        onView(withId(R.id.movies_fragment_recyclerview))       //  Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.movies_fragment_recyclerview))   //  Check that item display all details
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_image)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_date)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_title)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_categorie)))));

        onView(withId(R.id.activity_main_drawer_layout))    //  Swipe to Sports
                .perform(swipeLeft());

        onView(withId(R.id.sports_fragment_recyclerview))       //  Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.sports_fragment_recyclerview))   //  Check that item display all details
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_image)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_date)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_title)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_categorie)))));

        onView(withId(R.id.activity_main_drawer_layout))    //  Swipe to Technology
                .perform(swipeLeft());

        onView(withId(R.id.tech_fragment_recyclerview))       //  Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.tech_fragment_recyclerview))   //  Check that item display all details
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_image)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_date)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_title)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_categorie)))));

        onView(withId(R.id.activity_main_drawer_layout))    //  Swipe to Travel
                .perform(swipeLeft());

        onView(withId(R.id.travel_fragment_recyclerview))       //  Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.travel_fragment_recyclerview))   //  Check that item display all details
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_image)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_date)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_title)))))
                .check(matches(atPosition(0, hasDescendant(withId(R.id.fragment_main_item_categorie)))));
    }

    /**
     * Navigate by swipping to check if navigation Tab are shown
     */
    @Test
    public void Navigation_Drawer_Test() {

        onView(withId(R.id.fragment_top_stories))
                .check(matches(isDisplayed()));

        onView(withId(R.id.topstories_recycler))       // wait for Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_main_drawer_layout))    //  Open navigation drawer
                .perform(DrawerActions.open())
                .check(matches(DrawerMatchers.isOpen()))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_main_nav_view))
                .perform(waitUntil(isDisplayed()))
                .check(matches(isDisplayed()))
                .perform(NavigationViewActions.navigateTo(R.id.activity_main_drawer_business));   //  Click on Business item on navigation drawer

        onView(withId(R.id.activity_main_drawer_layout))    //  Open navigation drawer
                .perform(DrawerActions.close())
                .check(matches(DrawerMatchers.isClosed()));

        onView(withId(R.id.fragment_business))   //  Check fragment is displayed
                .check(matches(isDisplayed()));

        onView(withId(R.id.business_fragment_recyclerview))       // wait for Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_main_drawer_layout))    //  Open navigation drawer
                .perform(DrawerActions.open())
                .check(matches(DrawerMatchers.isOpen()))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_main_nav_view))
                .perform(waitUntil(isDisplayed()))
                .check(matches(isDisplayed()))
                .perform(NavigationViewActions.navigateTo(R.id.activity_main_drawer_food));   //  Click on Food item on navigation drawer

        onView(withId(R.id.activity_main_drawer_layout))    //  Open navigation drawer
                .perform(DrawerActions.close())
                .check(matches(DrawerMatchers.isClosed()));

        onView(withId(R.id.fragment_food))   //  Check fragment is displayed
                .check(matches(isDisplayed()));

        onView(withId(R.id.food_fragment_recyclerview))       // wait for Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_main_drawer_layout))    //  Open navigation drawer
                .perform(DrawerActions.open())
                .check(matches(DrawerMatchers.isOpen()))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_main_nav_view))
                .perform(waitUntil(isDisplayed()))
                .check(matches(isDisplayed()))
                .perform(NavigationViewActions.navigateTo(R.id.activity_main_drawer_movies));   //  Click on Movies item on navigation drawer

        onView(withId(R.id.activity_main_drawer_layout))    //  Open navigation drawer
                .perform(DrawerActions.close())
                .check(matches(DrawerMatchers.isClosed()));

        onView(withId(R.id.fragment_movies))   //  Check fragment is displayed
                .check(matches(isDisplayed()));

        onView(withId(R.id.movies_fragment_recyclerview))       // wait for Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_main_drawer_layout))    //  Open navigation drawer
                .perform(DrawerActions.open())
                .check(matches(DrawerMatchers.isOpen()))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_main_nav_view))
                .perform(waitUntil(isDisplayed()))
                .check(matches(isDisplayed()))
                .perform(NavigationViewActions.navigateTo(R.id.activity_main_drawer_sports));   //  Click on Sports item on navigation drawer

        onView(withId(R.id.activity_main_drawer_layout))    //  Open navigation drawer
                .perform(DrawerActions.close())
                .check(matches(DrawerMatchers.isClosed()));

        onView(withId(R.id.fragment_sports))   //  Check fragment is displayed
                .check(matches(isDisplayed()));

        onView(withId(R.id.sports_fragment_recyclerview))       // wait for Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_main_drawer_layout))    //  Open navigation drawer
                .perform(DrawerActions.open())
                .check(matches(DrawerMatchers.isOpen()))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_main_nav_view))
                .perform(waitUntil(isDisplayed()))
                .check(matches(isDisplayed()))
                .perform(NavigationViewActions.navigateTo(R.id.activity_main_drawer_tech));   //  Click on Technology item on navigation drawer

        onView(withId(R.id.activity_main_drawer_layout))    //  Open navigation drawer
                .perform(DrawerActions.close())
                .check(matches(DrawerMatchers.isClosed()));

        onView(withId(R.id.fragment_tech))   //  Check fragment is displayed
                .check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.tech_fragment_recyclerview))       // wait for Recycler View is displayed
                .perform(waitUntil(hasItemCount(greaterThan(0))))
                .check(matches(isDisplayed()));
    }

}
