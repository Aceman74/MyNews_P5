package com.aceman.mynews.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aceman.mynews.R;
import com.aceman.mynews.controllers.fragments.CarsFragment;
import com.aceman.mynews.controllers.fragments.FoodFragment;
import com.aceman.mynews.controllers.fragments.MoviesFragment;
import com.aceman.mynews.controllers.fragments.SportsFragment;
import com.aceman.mynews.controllers.fragments.TopStoriesFragment;
import com.aceman.mynews.controllers.fragments.BusinessFragment;
import com.aceman.mynews.controllers.fragments.MostPopularFragment;
import com.aceman.mynews.controllers.fragments.TravelFragment;

import butterknife.ButterKnife;


/**
 * Created by Lionel JOFFRAY - on 05/03/2019.
 */
public class PageAdapter extends FragmentPagerAdapter {


    Context mContext;

    //Default Constructor
    public PageAdapter(FragmentManager mgr, Context context) {
        super(mgr);
        mContext = context;
    }

    @Override
    public int getCount() {
        return(7);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                return TopStoriesFragment.newInstance();
            case 1: //Page number 2
                return MostPopularFragment.newInstance();
            case 2: //Page number 3
                return BusinessFragment.newInstance();
            case 3: //Page number 4
                return CarsFragment.newInstance();
            case 4: //Page number 5
                return FoodFragment.newInstance();
            case 5: //Page number 6
                return MoviesFragment.newInstance();
            case 6: //Page number 7
                return SportsFragment.newInstance();
            case 7: //Page number 8
                return TravelFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){

            case 0: //Page number 1
                return mContext.getString(R.string.top_stories_tab);
            case 1: //Page number 2
                return mContext.getString(R.string.most_polular_tab);
            case 2: //Page number 3
                return mContext.getString(R.string.business_tab);
            case 3: //Page number 4
                return mContext.getString(R.string.cars_tab);
            case 4: //Page number 5
                return mContext.getString(R.string.food_tab);
            case 5: //Page number 6
                return mContext.getString(R.string.movies_tab);
            case 6: //Page number 7
                return mContext.getString(R.string.sport_tab);
            case 7: //Page number 8
                return mContext.getString(R.string.travel_tab);
            default:
                return null;
        }
    }
}

