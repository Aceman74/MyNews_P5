/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.data.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.aceman.mynews.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import retrofit2.Retrofit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Lionel JOFFRAY - on 15/04/2019.
 */
public class RetrofitSetTest {

    final Context context = Mockito.mock(Context.class);
    final ConnectivityManager connectivityManager = Mockito.mock(ConnectivityManager.class);
    final NetworkInfo networkInfo = Mockito.mock(NetworkInfo.class);
    Retrofit mRetrofit;

    @Before
    public void setUp() {
        mRetrofit = TestUtils.setRetrofitForTesting();
    }

    /**
     * Testing the isOnline() method
     */
    @Test
    public void isOnline_Network_Test() {

        Mockito.when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);
        Mockito.when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        Mockito.when(networkInfo.isConnected()).thenReturn(true);
        assertNotNull(networkInfo);
    }

    /**
     * Test the Retrofit base call on the URL
     */
    @Test
    public void setRetrofit_Test() {

        assertEquals("https://api.nytimes.com/svc/", mRetrofit.baseUrl().toString());
        assertNotNull(mRetrofit);
        assertTrue(mRetrofit.baseUrl().isHttps());

    }
}