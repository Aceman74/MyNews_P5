package com.aceman.mynews.jobs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Lionel JOFFRAY - on 11/03/2019.
 */
public class MyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "News for you today!", Toast.LENGTH_SHORT).show();
    }
}
