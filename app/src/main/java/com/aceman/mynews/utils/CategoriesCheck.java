/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.utils;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;


/**
 * Created by Lionel JOFFRAY - on 25/03/2019.
 * <p>
 * Checkbox category check for using in Search and Notification, return a list of Boolean for each category
 */
public class CategoriesCheck {

    /**
     * Set the list size for checkboxes
     *
     * @param mCheckList list of boolean
     */
    public static void setCheckListSize(List<Boolean> mCheckList) {
        for (int i = 0; i < 6; i++) {
            mCheckList.add(false);
        }
    }

    /**
     * Listener for categories
     *
     * @param mBusiness  category
     * @param mTech      category
     * @param mFood      category
     * @param mMovies    category
     * @param mSports    category
     * @param mTravel    category
     * @param mCheckList category
     */
    public static void checkBoxListnener(final CheckBox mBusiness, CheckBox mTech, CheckBox mFood, CheckBox mMovies, CheckBox mSports, CheckBox mTravel, final List<Boolean> mCheckList) {

        mBusiness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckList.set(0, true);
                } else {
                    mCheckList.set(0, false);
                }
            }
        });
        mTech.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckList.set(1, true);
                } else {
                    mCheckList.set(1, false);
                }
            }
        });
        mFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckList.set(2, true);
                } else {
                    mCheckList.set(2, false);
                }
            }
        });
        mMovies.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckList.set(3, true);
                } else {
                    mCheckList.set(3, false);
                }
            }
        });
        mSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckList.set(4, true);
                } else {
                    mCheckList.set(4, false);
                }
            }
        });
        mTravel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckList.set(5, true);
                } else {
                    mCheckList.set(5, false);
                }
            }
        });
    }

    /**
     * Add the category to String for matching the Call Request
     *
     * @param mCheckList the category checklist full off boolean
     * @return the category(ies) on String
     */
    public static String getQueryCategories(List<Boolean> mCheckList) {
        String cat1 = "";
        String cat2 = "";
        String cat3 = "";
        String cat4 = "";
        String cat5 = "";
        String cat6 = "";
        if (mCheckList.get(0)) {
            cat1 = "\"Business Day\"";
        }
        if (mCheckList.get(1)) {
            cat2 = "\"Technology\"";
        }
        if (mCheckList.get(2)) {
            cat3 = "\"Food\"";
        }
        if (mCheckList.get(3)) {
            cat4 = "\"Movies\"";
        }
        if (mCheckList.get(4)) {
            cat5 = "\"Sports\"";
        }
        if (mCheckList.get(5)) {
            cat6 = "\"Travel\"";
        }
        String categorie = "news_desk:(" + cat1 + cat2 + cat3 + cat4 + cat5 + cat6 + " )";

        if (cat1.equals("") && cat2.equals("") && cat3.equals("") && cat4.equals("") && cat5.equals("") && cat6.equals("")) {
            categorie = null;
        }
        return categorie;
    }
}
