/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.data.api;

import com.aceman.mynews.TestUtils;
import com.aceman.mynews.data.models.mostpopular.MostPopular;
import com.aceman.mynews.data.models.shared.SharedObservable;
import com.aceman.mynews.data.models.topstories.TopStories;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import retrofit2.HttpException;
import retrofit2.Retrofit;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Lionel JOFFRAY - on 29/03/2019.
 * <p>
 * Test All API Call to New York Times
 */
public class NewYorkTimesServiceTest {
    private final String statusOk = "OK";
    Retrofit mRetrofit;
    private NewYorkTimesService mNewYorkTimesService;

    @Before
    public void setUp() {
        mRetrofit = TestUtils.setRetrofitForTesting();
        mNewYorkTimesService = mRetrofit.create(NewYorkTimesService.class);
    }

    @Test
    public void streamGetTopStoriesSuccessTest() {
        TopStories ts = mNewYorkTimesService.streamGetTopStories().delaySubscription(5, TimeUnit.SECONDS).blockingFirst();
        // on verifie qu'on a bien une reponse
        assertNotNull(ts);
        // on verifie qu'il y a bien des articles dans la liste des resultats
        assertTrue(ts.getTopStorieResults().size() > 0);

        assertEquals(ts.getStatus(), statusOk);  //  Test status response

        String itemType = "Article";
        assertEquals(ts.getTopStorieResults().get(0).getItemType(), itemType);   //  Test if type is article for first item
    }

    @Test
    public void streamGetMostPopularSuccessTest() {
        int period = 7;

        MostPopular mp = mNewYorkTimesService.streamGetMostPopular(period).delaySubscription(5, TimeUnit.SECONDS).blockingFirst();
        assertNotNull(mp);         // Test response
        assertTrue(mp.getPopularResults().size() > 0);      // Test if there's articles in list
        assertEquals(mp.getStatus(), statusOk);  //  Test status response
        String itemType = "Article";
        assertEquals(mp.getPopularResults().get(0).getType(), itemType);   //  Test if type is article for first item
    }

    /**
     * Should thrown an exception if period isn't 7 or 1
     */
    @Test(expected = HttpException.class)
    public void streamGetMostPopularFailTest() {
        //  Test with wrong period int (error 400)
        int period = 2;

        MostPopular mp1 = mNewYorkTimesService.streamGetMostPopular(period).delaySubscription(5, TimeUnit.SECONDS).blockingFirst();
        assertNotNull(mp1);         // Test response

    }

    @Test
    public void streamGeBusinessSuccessTest() {
        SharedObservable bu = mNewYorkTimesService.streamGetBusiness().delaySubscription(5, TimeUnit.SECONDS).blockingFirst();
        assertNotNull(bu);        // Test response
        assertTrue(bu.getSharedResponse().getSharedDocs().size() > 0);  // Test if there's articles in list
        assertEquals(bu.getStatus(), statusOk);  //  Test status response
        String sectionName = "Business Day";
        assertEquals(bu.getSharedResponse().getSharedDocs().get(0).getSectionName(), sectionName);   //  Test if section name is correct for first item
    }

    @Test
    public void streamGeFoodSuccessTest() {
        SharedObservable fo = mNewYorkTimesService.streamGetFood().delaySubscription(5, TimeUnit.SECONDS).blockingFirst();
        assertNotNull(fo);        // Test response
        assertTrue(fo.getSharedResponse().getSharedDocs().size() > 0);  // Test if there's articles in list
        assertEquals(fo.getStatus(), statusOk);  //  Test status response
        String sectionName = "Food";
        assertEquals(fo.getSharedResponse().getSharedDocs().get(0).getSectionName(), sectionName);   //  Test if section name is correct for first item
    }

    @Test
    public void streamGeMoviesSuccessTest() {
        SharedObservable mo = mNewYorkTimesService.streamGetMovies().delaySubscription(5, TimeUnit.SECONDS).blockingFirst();
        assertNotNull(mo);        // Test response
        assertTrue(mo.getSharedResponse().getSharedDocs().size() > 0);  // Test if there's articles in list
        assertEquals(mo.getStatus(), statusOk);  //  Test status response
        String sectionName = "Movies";
        assertEquals(mo.getSharedResponse().getSharedDocs().get(0).getSectionName(), sectionName);   //  Test if section name is correct for first item
    }

    @Test
    public void streamGeSportsSuccessTest() {
        SharedObservable sp = mNewYorkTimesService.streamGetSports().delaySubscription(5, TimeUnit.SECONDS).blockingFirst();
        assertNotNull(sp);        // Test response
        assertTrue(sp.getSharedResponse().getSharedDocs().size() > 0);  // Test if there's articles in list
        assertEquals(sp.getStatus(), statusOk);  //  Test status response
        String sectionName = "Sports";
        assertEquals(sp.getSharedResponse().getSharedDocs().get(0).getSectionName(), sectionName);   //  Test if section name is correct for first item
    }

    @Test
    public void streamGetTravelSuccessTest() {
        SharedObservable tr = mNewYorkTimesService.streamGetTravel().delaySubscription(5, TimeUnit.SECONDS).blockingFirst();
        assertNotNull(tr);        // Test response
        assertTrue(tr.getSharedResponse().getSharedDocs().size() > 0);  // Test if there's articles in list
        assertEquals(tr.getStatus(), statusOk);  //  Test status response
        String sectionName = "Travel";
        assertEquals(tr.getSharedResponse().getSharedDocs().get(0).getSectionName(), sectionName);   //  Test if section name is correct for first item
    }

    @Test
    public void streamGetSearchWithNullSuccessTest() {
        SharedObservable se = mNewYorkTimesService.streamGetSearch(null, null, null, null).delaySubscription(5, TimeUnit.SECONDS).blockingFirst();
        // Test response
        assertNotNull(se);
        // Test if there's articles in list
        assertTrue(se.getSharedResponse().getSharedDocs().size() > 0);  // Test if there's articles in list
        assertEquals(se.getStatus(), statusOk);  //  Test status response
    }

    @Test
    public void streamGetSearchWithNumberQuerySuccessTest() {
        //  Test number query (valid request)
        String query = "29";

        SharedObservable se1 = mNewYorkTimesService.streamGetSearch(null, null, query, null).delaySubscription(5, TimeUnit.SECONDS).blockingFirst();
        // Test response
        assertNotNull(se1);

        assertTrue(se1.getSharedResponse().getSharedDocs().size() > 0);  // Test if there's articles in list
        assertEquals(se1.getStatus(), statusOk);    //  Test status response
    }

    @Test
    public void streamGetSearchWitNormalRequestSuccessTest() {
        //  Test normal request
        String begin = "20161129";
        String end = "20181129";
        String query = "Cats";
        String category = "Movies";

        SharedObservable se2 = mNewYorkTimesService.streamGetSearch(begin, end, query, category).delaySubscription(5, TimeUnit.SECONDS).blockingFirst();
        // Test response
        assertNotNull(se2);
        assertTrue(se2.getSharedResponse().getSharedDocs().size() > 0);  // Test if there's articles in list
        assertEquals(se2.getStatus(), statusOk);    //  Test status response

    }

    /**
     * Should thrown an exception , begin date must be YYYYMMDD
     */
    @Test(expected = HttpException.class)
    public void streamGetSearchFailTest() {
        String begin;

        // Test bad begin date (return Error 400)
        begin = "Hello";

        SharedObservable se3 = mNewYorkTimesService.streamGetSearch(begin, null, null, null).delaySubscription(5, TimeUnit.SECONDS).blockingFirst();
        // Test response
        assertNotNull(se3);
    }
}