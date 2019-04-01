/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.data.api;

import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.aceman.mynews.data.models.topstories.TopStorieResult;
import com.aceman.mynews.data.models.topstories.TopStories;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Observable;

import io.reactivex.observers.TestObserver;

/**
 * Created by Lionel JOFFRAY - on 29/03/2019.
 */
public class NewYorkTimesServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock NewYorkTimesService mNewYorkTimesService;

    @InjectMocks
    TopStories mTopStories;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void streamGetTopStoriesTest() {

        mNewYorkTimesService.streamGetTopStories();

    }

    @Test
    public void streamGetMostPopularTest() {
    }

    @Test
    public void streamGetTravelTest() {
    }

    @Test
    public void streamGetSearchTest() {
    }
}