package com.aceman.mynews.ui.search.activities;

import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import java.util.List;


/**
 * Created by Lionel JOFFRAY - on 25/03/2019.
 */
public class CategoriesCheck  {




    static void setCheckListSize(List<Boolean> mCheckList) {
        for (int i = 0; i < 6; i++) {
            mCheckList.add(false);
        }
    }

    public static List<Boolean> checkBoxListnener(CheckBox mBusiness, CheckBox mTech, CheckBox mFood, CheckBox mMovies, CheckBox mSports, CheckBox mTravel, final List<Boolean> mCheckList) {

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
}
