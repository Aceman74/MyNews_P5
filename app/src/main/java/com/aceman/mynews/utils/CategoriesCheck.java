/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.utils;

import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;


/**
 * Created by Lionel JOFFRAY - on 25/03/2019.
 *
 * Checkbox category check for using in Search and Notification, return a list of Boolean for each category
 */
public class CategoriesCheck {


    public static void setCheckListSize(List<Boolean> mCheckList) {
        for (int i = 0; i < 6; i++) {
            mCheckList.add(false);
        }
    }


    public static void checkBoxListnener(CheckBox mBusiness, CheckBox mTech, CheckBox mFood, CheckBox mMovies, CheckBox mSports, CheckBox mTravel, final List<Boolean> mCheckList) {

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
                    Log.i("Categories", "Technology is Checked!");
                    mCheckList.set(1, true);
                } else {
                    Log.i("Categories", "Technology is Unchecked!");
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
    }

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
