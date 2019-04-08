package com.aceman.mynews.jobs;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.aceman.mynews.R;
import com.aceman.mynews.data.api.NewYorkTimesService;
import com.aceman.mynews.data.api.NewsStream;
import com.aceman.mynews.data.models.search.Search;
import com.aceman.mynews.ui.notifications.activities.NotificationActivity;
import com.aceman.mynews.ui.search.activities.SearchActivity;

import java.util.Calendar;

import androidx.work.Worker;
import androidx.work.WorkerParameters;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;
import static com.aceman.mynews.ui.navigations.activities.MainActivity.CHANNEL_ID;
import static com.aceman.mynews.ui.navigations.activities.MainActivity.mCache;
import static com.aceman.mynews.ui.notifications.activities.NotificationActivity.mFirstNot;

/**
 * Created by Lionel JOFFRAY - on 25/03/2019.
 */
public class DailyWorker extends Worker {
    Disposable disposable;
    String mBeginDate;
    String mEndDate;
    int mSizeResult;
    String mToday;
    String mYesterday;
    String mSearchQuery = getInputData().getString("Query");
    String mCategorie = getInputData().getString("Categorie");
    String mPos1;
    String mPos2;
    String mPos3;
    String mNeg1;
    String mNeg2;
    String mAppTitle;
    Context mContext;


    public DailyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
        mPos1 = mContext.getResources().getString(R.string.notif_pos_text_1);
        mPos2 = mContext.getResources().getString(R.string.notif_pos_text_2);
        mPos3 = mContext.getResources().getString(R.string.notif_pos_text_3);
        mAppTitle = mContext.getResources().getString(R.string.my_news);
        mNeg1 = mContext.getResources().getString(R.string.notif_neg_text_1);
        mNeg2 = mContext.getResources().getString(R.string.notif_neg_text_2);

    }

    @NonNull
    @Override
    public Result doWork() {

        try {
            setDate();
            notificationResult();
            showNotif(mSearchQuery);

            return
                    Result.success();
        } catch (Exception e) {
            return
                    Result.failure();
        }
    }

    public void notificationResult() {
        mBeginDate = mYesterday;
        mEndDate = mToday;


        NewYorkTimesService newsStream = setRetrofit().create(NewYorkTimesService.class);
        disposable = NewsStream.streamGetSearch(newsStream,mBeginDate, mEndDate, mSearchQuery, mCategorie).subscribeWith(new DisposableObserver<Search>() {
            @Override
            public void onNext(Search details) {
                Log.e("SEARCH_Next", "On Next");
                Log.d("NOTIFICATION OBSERVABLE", "from: " + mBeginDate + " to: " + mEndDate + " query: " + mSearchQuery + " categorie: " + mCategorie);
                mSizeResult = details.getSearchResponse().getDocs().size();

            }

            @Override
            public void onError(Throwable e) {
                Log.e("NOTIFICATION Error", "On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("NOTIFICATION Complete", "On Complete !!");
            }
        });
    }

    public Retrofit setRetrofit(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(mCache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com/svc/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    public void showNotif(String mSearchQuery) {

        if (mFirstNot == 0) {
            mFirstNot++;
        } else {


            if (mSizeResult > 0) {
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra("Search", mSearchQuery);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, FLAG_CANCEL_CURRENT);

                Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.newyorktimes_thumb);

                NotificationCompat.Builder notifTest = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(mAppTitle)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(mPos1 + mSizeResult + mPos2 + mSearchQuery + mPos3))
                        .setContentIntent(pendingIntent)
                        .setLargeIcon(largeIcon)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mContext);
                notificationManagerCompat.notify(1, notifTest.build());

            } else {

                Intent intent = new Intent(mContext, NotificationActivity.class);
                intent.putExtra("Search", mSearchQuery);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, FLAG_CANCEL_CURRENT);

                Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.newyorktimes_thumb);

                NotificationCompat.Builder notifTest = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(mAppTitle)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(mNeg1 + mSearchQuery + mNeg2))
                        .setContentIntent(pendingIntent)
                        .setLargeIcon(largeIcon)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mContext);
                notificationManagerCompat.notify(2, notifTest.build());
            }

        }
    }

    public void setDate() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        String date = day + "/" + (month + 1) + "/" + year;
        String formattedMonth = "" + month;
        String formattedDayOfMonth = "" + day;
        String formattedDayOfMonthYesterday = "" + (day - 1);

        if (month < 10) {
            formattedMonth = "0" + month;
        }
        if (day < 10) {
            formattedDayOfMonth = "0" + day;
        }
        mToday = year + "" + formattedMonth + "" + formattedDayOfMonth;
        mYesterday = year + "" + formattedMonth + "" + formattedDayOfMonthYesterday;
    }

}
