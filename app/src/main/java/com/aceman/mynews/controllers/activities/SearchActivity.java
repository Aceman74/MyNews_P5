package com.aceman.mynews.controllers.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.aceman.mynews.controllers.fragments.CategoriesFragment;
import com.aceman.mynews.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_search_btn)
    Button mSearchBtn;
    @BindView(R.id.textview_from_date)
    TextView mDisplayFromDate;
    @BindView(R.id.textview_to_date)
    TextView mDisplayToDate;
    DatePickerDialog.OnDateSetListener mFromDateListener;
    DatePickerDialog mFromDate;
    DatePickerDialog mToDate;
    DatePickerDialog.OnDateSetListener mToDateListener;
    CategoriesFragment mCategoriesFragment;
    @BindView(R.id.activity_search_search_query)
    EditText mSearchQuery;
    String mSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        this.configureToolbar();
        getFromDate();
        getToDate();
        searchQueryListener();


        mCategoriesFragment = new CategoriesFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_search_categories, mCategoriesFragment)
                .commit();


    }
    public void searchQueryListener(){
        mSearchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              mSearchResult  = mSearchQuery.getText().toString();
                mSearchBtn.setEnabled(true);

            }
        });
    }
    public void getFromDate(){
        mDisplayFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                if (mFromDate == null) {

                    mFromDate = new DatePickerDialog(SearchActivity.this,
                            mFromDateListener,
                            year,
                            month,
                            day);

                    mFromDate.show();
                } else {
                    mFromDate.show();

                }
            }
        });

        mFromDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                mFromDate.updateDate(year, month, dayOfMonth);
                mDisplayFromDate.setText(date);
            }
        };
    }
    public void getToDate(){
        mDisplayToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                if (mToDate == null) {

                    mToDate = new DatePickerDialog(SearchActivity.this,
                            mToDateListener,
                            year,
                            month,
                            day);

                    mToDate.show();
                } else {
                    mToDate.show();

                }
            }
        });

        mToDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                mToDate.updateDate(year, month, dayOfMonth);
                mDisplayToDate.setText(date);
            }
        };
    }
    private void configureToolbar() {
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
