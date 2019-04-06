package com.aceman.mynews.ui.search.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aceman.mynews.R;
import com.aceman.mynews.ui.search.fragments.SearchFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    @BindView(R.id.layout_search_categories)
    LinearLayout searchCatgorie;
    @BindView(R.id.time_layout)
    LinearLayout timeLayout;
    @BindView(R.id.btn_layout)
    LinearLayout btnLayout;
    @BindView(R.id.checkbox_business)
    CheckBox mBusiness;
    @BindView(R.id.checkbox_tech)
    CheckBox mTech;
    @BindView(R.id.checkbox_food)
    CheckBox mFood;
    @BindView(R.id.checkbox_movies)
    CheckBox mMovies;
    @BindView(R.id.checkbox_sports)
    CheckBox mSports;
    @BindView(R.id.checkbox_travel)
    CheckBox mTravel;
    DatePickerDialog.OnDateSetListener mFromDateListener;
    DatePickerDialog mFromDate;
    String mFromDateString;
    DatePickerDialog mToDate;
    String mToDateString;
    DatePickerDialog.OnDateSetListener mToDateListener;
    SearchFragment mSearchFragment;
    @BindView(R.id.activity_search_search_query)
    EditText mSearchQuery;
    String mSearchResult;
    String mCategorieResult;
    List<Boolean> mCheckList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setVisibilitySearchLayout();
        mCheckList = new ArrayList<>();
        this.configureToolbar();
        getFromDate();
        getToDate();

        Intent intent = getIntent();
        mSearchResult = intent.getStringExtra("Search");
        CategoriesCheck.setCheckListSize(mCheckList);
        CategoriesCheck.checkBoxListnener(mBusiness, mTech, mFood, mMovies, mSports, mTravel, mCheckList);
        searchQueryListener();
        clickListener();
    }

    public void setVisibilitySearchLayout() {
        mSearchQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                searchCatgorie.setVisibility(View.VISIBLE);
                timeLayout.setVisibility(View.VISIBLE);
                btnLayout.setVisibility(View.VISIBLE);
            }
        });
    }


    public void searchFragmentLaunch() {
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFragment();
            }
        });
    }

    public void searchQueryListener() {

        mSearchBtn.setAlpha(0.5f);

        mSearchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnLayout.setVisibility(View.VISIBLE);
                searchCatgorie.setVisibility(View.VISIBLE);
                timeLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSearchResult = mSearchQuery.getText().toString();
                checkState();
                onHitEnter();

            }
        });
    }

    private void checkState() {
        if (mSearchQuery.getText().toString().trim().length() > 0 && mCheckList.contains(true)) {
            mSearchBtn.setEnabled(true);
            mSearchBtn.setAlpha(1);
            searchFragmentLaunch();

        } else {
            mSearchBtn.setEnabled(false);
            mSearchBtn.setAlpha(0.5f);
        }
    }

    public void onHitEnter() { //  Handle the enter key

        mSearchQuery.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    searchFragment();
                    return true;
                }
                return false;
            }
        });
    }

    public void searchFragment() {

        btnLayout.setVisibility(View.GONE);
        searchCatgorie.setVisibility(View.GONE);
        timeLayout.setVisibility(View.GONE);

        mCategorieResult = CategoriesCheck.getQueryCategories(mCheckList);
        Bundle searchStrings = new Bundle();
        searchStrings.putString("fromDatePicker", mFromDateString);
        searchStrings.putString("toDatePicker", mToDateString);
        searchStrings.putString("query", mSearchResult);
        searchStrings.putString("categories", mCategorieResult);
        mSearchFragment = new SearchFragment();
        mSearchFragment.setArguments(searchStrings);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.firstlayout, mSearchFragment)
                .addToBackStack(SearchActivity.class.getSimpleName())
                .commit();
        Log.d("Search Tags", "from: " + mFromDateString + " to: " + mToDateString + " query: " + mSearchResult + " categorie: " + mCategorieResult);

    }


    public void getFromDate() {
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
                String formattedMonth = "" + month;
                String formattedDayOfMonth = "" + dayOfMonth;

                if (month < 10) {
                    formattedMonth = "0" + month;
                }
                if (dayOfMonth < 10) {
                    formattedDayOfMonth = "0" + dayOfMonth;
                }
                mFromDateString = year + "" + formattedMonth + "" + formattedDayOfMonth;
            }

        };
    }


    public void getToDate() {
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
                String formattedMonth = "" + month;
                String formattedDayOfMonth = "" + dayOfMonth;

                if (month < 10) {
                    formattedMonth = "0" + month;
                }
                if (dayOfMonth < 10) {
                    formattedDayOfMonth = "0" + dayOfMonth;
                }
                mToDateString = year + "" + formattedMonth + "" + formattedDayOfMonth;
            }
        };
    }

    public void configureToolbar() {
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    void clickListener() {
        mBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkState();
            }
        });
        mFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkState();
            }
        });
        mMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkState();
            }
        });
        mSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkState();
            }
        });
        mTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkState();
            }
        });
        mTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkState();
            }
        });
    }
}
