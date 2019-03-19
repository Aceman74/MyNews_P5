package com.aceman.mynews.ui.navigations.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.aceman.mynews.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {
    CheckBox mBusiness;
    CheckBox mCars;
    CheckBox mFood;
    CheckBox mMovies;
    CheckBox mSports;
    CheckBox mTravel;
  public static final List<Boolean> checkList = new ArrayList<>();


    public CategoriesFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View catView = inflater.inflate(R.layout.fragment_categories, container, false);
        mBusiness = catView.findViewById(R.id.checkbox_business);
        mCars = catView.findViewById(R.id.checkbox_cars);
        mFood = catView.findViewById(R.id.checkbox_food);
        mMovies = catView.findViewById(R.id.checkbox_movies);
        mSports = catView.findViewById(R.id.checkbox_sports);
        mTravel = catView.findViewById(R.id.checkbox_travel);
        setCheckListSize();
        checkBoxListnener();

        // Inflate the layout for this fragment
        return catView;
    }
void setCheckListSize(){
    for (int i = 0; i < 6; i++) {
        checkList.add(false);
    }
}

    public List<Boolean>  checkBoxListnener(){

        mBusiness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("Categories","Business is Checked!");
                    checkList.set(0,true);
                }else{
                    Log.i("Categories","Business is Unchecked!");
                    checkList.set(0,false);
                }
            }
        });
        mCars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("Categories","Cars is Checked!");
                    checkList.set(1,true);
                }else{
                    Log.i("Categories","Cars is Unchecked!");
                    checkList.set(1,false);
                }
            }
        });
        mFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("Categories","Food is Checked!");
                    checkList.set(2,true);
                }else{
                    Log.i("Categories","Food is Unchecked!");
                    checkList.set(2,false);
                }
            }
        });
        mMovies.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("Categories","Movies is Checked!");
                    checkList.set(3,true);
                }else{
                    Log.i("Categories","Movies is Unchecked!");
                    checkList.set(3,false);
                }
            }
        });
        mSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("Categories","Sports is Checked!");
                    checkList.set(4,true);
                }else{
                    Log.i("Categories","Sports is Unchecked!");
                    checkList.set(4,false);
                }
            }
        });
        mTravel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("Categories","Travel is Checked!");
                    checkList.set(5,true);
                }else{
                    Log.i("Categories","Travel is Unchecked!");
                    checkList.set(5,false);
                }
            }
        });
        return checkList;
    }
}
