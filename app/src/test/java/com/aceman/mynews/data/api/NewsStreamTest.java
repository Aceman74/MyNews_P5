/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.data.api;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Created by Lionel JOFFRAY - on 03/04/2019.
 */
public class NewsStreamTest {


    NewYorkTimesService mNewYorkTimesService;

    @Before
    public void setUp() {
        mNewYorkTimesService = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);
    }

    @Test
    public void streamGetTopStoriesTest() {

         mNewYorkTimesService.streamGetTopStories()
                 .test()
                 .assertSubscribed()
                 .assertComplete()
                 .assertNoErrors()
                 .assertNoTimeout();
    }

    @Test
    public void streamGetMostPopularTest() {

        mNewYorkTimesService.streamGetMostPopular(7)
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .assertNoTimeout();
    }

    @Test
    public void streamGetBusinessTest() {

        mNewYorkTimesService.streamGetBusiness()
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .assertNoTimeout();
    }

    @Test
    public void streamGetFoodTest() {

        mNewYorkTimesService.streamGetFood()
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .assertNoTimeout();
    }

    @Test
    public void streamGetMoviesTest() {

        mNewYorkTimesService.streamGetMovies()
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .assertNoTimeout();
    }

    @Test
    public void streamGetSportsTest() {

        mNewYorkTimesService.streamGetSports()
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .assertNoTimeout();
    }

    @Test
    public void streamGetTechTest() {

        mNewYorkTimesService.streamGetTech()
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .assertNoTimeout();
    }

    @Test
    public void streamGetTravelTest() {

        mNewYorkTimesService.streamGetTravel()
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .assertNoTimeout();
    }

    @Test
    public void streamGetSearchTest() {

        mNewYorkTimesService.streamGetSearch(null,null,null,null)
                .test()
                .assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .assertNoTimeout();
    }
}