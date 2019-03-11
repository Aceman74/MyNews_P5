package com.aceman.mynews.controllers.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.aceman.mynews.R;

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
        checkBoxListnener();

        // Inflate the layout for this fragment
        return catView;
    }

    public void checkBoxListnener(){
        mBusiness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("Categories","Business is Checked!");
                }else{
                    Log.i("Categories","Business is Unchecked!");
                }
            }
        });
        mCars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("Categories","Cars is Checked!");
                }else{
                    Log.i("Categories","Cars is Unchecked!");
                }
            }
        });
        mFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("Categories","Food is Checked!");
                }else{
                    Log.i("Categories","Food is Unchecked!");
                }
            }
        });
        mMovies.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("Categories","Movies is Checked!");
                }else{
                    Log.i("Categories","Movies is Unchecked!");
                }
            }
        });
        mSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("Categories","Sports is Checked!");
                }else{
                    Log.i("Categories","Sports is Unchecked!");
                }
            }
        });
        mTravel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("Categories","Travel is Checked!");
                }else{
                    Log.i("Categories","Travel is Unchecked!");
                }
            }
        });
    }
}
