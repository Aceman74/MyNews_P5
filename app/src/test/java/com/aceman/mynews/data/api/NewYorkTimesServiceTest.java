/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.data.api;

import com.aceman.mynews.data.models.mostpopular.MostPopular;
import com.aceman.mynews.data.models.search.Search;
import com.aceman.mynews.data.models.shared.SharedObservable;
import com.aceman.mynews.data.models.topstories.TopStories;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import retrofit2.HttpException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Lionel JOFFRAY - on 29/03/2019.
 */
public class NewYorkTimesServiceTest {
    String statusOk = "OK";
    String badRequest = "HTTP 400 Bad Request";
    NewYorkTimesService mNewYorkTimesService;
    static String error;

    @Before
    public void setUp() {
        mNewYorkTimesService = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);
    }

    @Test
    public void streamGetTopStoriesTest() throws InterruptedException {

        Thread.sleep(2000);

        TopStories ts = mNewYorkTimesService.streamGetTopStories().delay(5, TimeUnit.SECONDS).blockingFirst();
        // on verifie qu'on a bien une reponse
        assertNotNull(ts);
        // on verifie qu'il y a bien des articles dans la liste des resultats
        assertTrue(ts.getTopStorieResults().size() > 0);

        assertEquals(ts.getStatus(), statusOk);  //  Test status response

        String itemType = "Article";
        assertEquals(ts.getTopStorieResults().get(0).getItemType(), itemType);   //  Test if type is article for first item
    }

    @Test
    public void streamGetMostPopularTest() throws InterruptedException {

        Thread.sleep(2000);

        int period = 7;

        MostPopular mp = mNewYorkTimesService.streamGetMostPopular(period).delay(5, TimeUnit.SECONDS).blockingFirst();
        assertNotNull(mp);         // Test response
        assertTrue(mp.getPopularResults().size() > 0);      // Test if there's articles in list
        assertEquals(mp.getStatus(), statusOk);  //  Test status response
        String itemType = "Article";
        assertEquals(mp.getPopularResults().get(0).getType(), itemType);   //  Test if type is article for first item

        //  Test with wrong period int (error 400)
         period = 2;
        error = null;

        Thread.sleep(2000);

        try {
            MostPopular mp1 = mNewYorkTimesService.streamGetMostPopular(period).delay(5, TimeUnit.SECONDS).blockingFirst();
            assertNotNull(mp1);         // Test response
        }catch (HttpException e){
            error = e.toString();
        }
        assertTrue(error.contains(badRequest)); //  Test status response
    }

    @Test
    public void streamGeBusinessTest() throws InterruptedException {

        Thread.sleep(2000);

        SharedObservable bu = mNewYorkTimesService.streamGetBusiness().delay(5, TimeUnit.SECONDS).blockingFirst();
        assertNotNull(bu);        // Test response
        assertTrue(bu.getSharedResponse().getSharedDocs().size() > 0);  // Test if there's articles in list
        assertEquals(bu.getStatus(), statusOk);  //  Test status response
        String sectionName = "Business Day";
        assertEquals(bu.getSharedResponse().getSharedDocs().get(0).getSectionName(), sectionName);   //  Test if section name is correct for first item
    }

    @Test
    public void streamGeFoodTest() throws InterruptedException {

        Thread.sleep(2000);

        SharedObservable fo = mNewYorkTimesService.streamGetFood().delay(5, TimeUnit.SECONDS).blockingFirst();
        assertNotNull(fo);        // Test response
        assertTrue(fo.getSharedResponse().getSharedDocs().size() > 0);  // Test if there's articles in list
        assertEquals(fo.getStatus(), statusOk);  //  Test status response
        String sectionName = "Food";
        assertEquals(fo.getSharedResponse().getSharedDocs().get(0).getSectionName(), sectionName);   //  Test if section name is correct for first item
    }

    @Test
    public void streamGeMoviesTest() throws InterruptedException {

        Thread.sleep(2000);

        SharedObservable mo = mNewYorkTimesService.streamGetMovies().delay(5, TimeUnit.SECONDS).blockingFirst();
        assertNotNull(mo);        // Test response
        assertTrue(mo.getSharedResponse().getSharedDocs().size() > 0);  // Test if there's articles in list
        assertEquals(mo.getStatus(), statusOk);  //  Test status response
        String sectionName = "Movies";
        assertEquals(mo.getSharedResponse().getSharedDocs().get(0).getSectionName(), sectionName);   //  Test if section name is correct for first item
    }

    @Test
    public void streamGeSportsTest() throws InterruptedException {

        Thread.sleep(2000);

        SharedObservable sp = mNewYorkTimesService.streamGetSports().delay(5, TimeUnit.SECONDS).blockingFirst();
        assertNotNull(sp);        // Test response
        assertTrue(sp.getSharedResponse().getSharedDocs().size() > 0);  // Test if there's articles in list
        assertEquals(sp.getStatus(), statusOk);  //  Test status response
        String sectionName = "Sports";
        assertEquals(sp.getSharedResponse().getSharedDocs().get(0).getSectionName(), sectionName);   //  Test if section name is correct for first item
    }

    @Test
    public void streamGetTravelTest() throws InterruptedException {

        Thread.sleep(2000);

        SharedObservable tr = mNewYorkTimesService.streamGetTravel().delay(5, TimeUnit.SECONDS).blockingFirst();
        assertNotNull(tr);        // Test response
        assertTrue(tr.getSharedResponse().getSharedDocs().size() > 0);  // Test if there's articles in list
        assertEquals(tr.getStatus(), statusOk);  //  Test status response
        String sectionName = "Travel";
        assertEquals(tr.getSharedResponse().getSharedDocs().get(0).getSectionName(), sectionName);   //  Test if section name is correct for first item
    }

    @Test
    public void streamGetSearchNullTest() throws InterruptedException {

        Thread.sleep(2000);

        String begin = null;
        String end = null;
        String query = null;
        String category = null;

        Search se = mNewYorkTimesService.streamGetSearch(begin, end, query, category).delay(5, TimeUnit.SECONDS).blockingFirst();
        // Test response
        assertNotNull(se);
        // Test if there's articles in list
        assertTrue(se.getSearchResponse().getDocs().size() > 0);  // Test if there's articles in list

        assertEquals(se.getStatus(), statusOk);  //  Test status response

        //  Test number query (valid request)
        begin = null;
        end = null;
        category = null;
        query = "29";
        error = null;

        Thread.sleep(2000);

        Search se1 = mNewYorkTimesService.streamGetSearch(begin, end, query, category).delay(5, TimeUnit.SECONDS).blockingFirst();
        // Test response
        assertNotNull(se1);
        assertTrue(se1.getSearchResponse().getDocs().size() > 0);  // Test if there's articles in list
        assertEquals(se1.getStatus(), statusOk);    //  Test status response

        //  Test normal request
        begin = null;
        end = "20181129";
        query = "Cats";
        category = "Movies";

        Thread.sleep(2000);

        Search se2 = mNewYorkTimesService.streamGetSearch(begin, end, query, category).delay(5, TimeUnit.SECONDS).blockingFirst();
        // Test response
        assertNotNull(se2);
        assertTrue(se2.getSearchResponse().getDocs().size() > 0);  // Test if there's articles in list
        assertEquals(se2.getStatus(), statusOk);    //  Test status response

    }
    @Test
    public void streamGetSearchFailTest() throws InterruptedException {

        Thread.sleep(2000);

        String begin;
        String end = null;
        String query = null;
        String category = null;

        // Test bad begin date (return Error 400)
        begin = "Hello";
        error = null;

        Thread.sleep(2000);

        try {
            Search se3 = mNewYorkTimesService.streamGetSearch(begin, end, query, category).delay(5, TimeUnit.SECONDS).blockingFirst();
            // Test response
            assertNotNull(se3);
        } catch (HttpException e) {
            error = e.toString();
        }
        assertTrue(error.contains(badRequest)); //  Test status response
    }
}