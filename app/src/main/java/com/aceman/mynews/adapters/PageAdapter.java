package com.aceman.mynews.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aceman.mynews.R;
import com.aceman.mynews.controllers.fragments.TopStoriesFragment;
import com.aceman.mynews.controllers.fragments.ParamPageFragment;
import com.aceman.mynews.controllers.fragments.MostPopularFragment;


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
        return(3);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                return TopStoriesFragment.newInstance();
            case 1: //Page number 2
                return MostPopularFragment.newInstance();
            case 2: //Page number 3
                return ParamPageFragment.newInstance();
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
                return "Paramètre";
            default:
                return null;
        }
    }
}

