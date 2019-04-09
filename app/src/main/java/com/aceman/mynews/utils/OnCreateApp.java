/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.utils;

import android.app.Application;
import android.content.Context;


/**
 * Created by Lionel JOFFRAY - on 30/03/2019.
 * <p>
 * * This Class is used for OnCreate App <br>
 * * Using <b>LeakCanary</>
 */
public class OnCreateApp extends Application {
    public static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        /**
         if (LeakCanary.isInAnalyzerProcess(this)) {
         // This process is dedicated to LeakCanary for heap analysis.
         // You should not init your app in this process.
         return;
         }
         LeakCanary.install(this);
         // Normal app init code...
         */
    }
}