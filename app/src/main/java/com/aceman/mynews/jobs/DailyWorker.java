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
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;
import static com.aceman.mynews.ui.navigations.activities.MainActivity.CHANNEL_ID;
import static com.aceman.mynews.ui.notifications.activities.NotificationActivity.mFirstNot;

/**
 * Created by Lionel JOFFRAY - on 25/03/2019.
 * <p>
 * This Class contain the <b>Worker</> for sending Daily Notification to user <br>
 * Using <b>AndroidX Work</>
 */
public class DailyWorker extends Worker {
    private final String mSearchQuery = getInputData().getString("Query");
    private final String mCategorie = getInputData().getString("Categorie");
    private final String mPos1;
    private final String mPos2;
    private final String mPos3;
    private final String mNeg1;
    private final String mNeg2;
    private final String mAppTitle;
    private final Context mContext;
    private Disposable disposable;
    private String mBeginDate;
    private String mEndDate;
    private int mSizeResult;
    private String mToday;
    private String mYesterday;

    /**
     * Initializing variables
     *
     * @param context      get Context
     * @param workerParams get Params
     */
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

    /**
     * Worker execution
     *
     * @return result of job
     */
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

    /**
     * Result of the user API notification call
     */
    private void notificationResult() {
        mBeginDate = mYesterday;
        mEndDate = mToday;


        NewYorkTimesService newsStream = setRetrofit().create(NewYorkTimesService.class);
        disposable = NewsStream.streamGetSearch(newsStream, mBeginDate, mEndDate, mSearchQuery, mCategorie).subscribeWith(new DisposableObserver<Search>() {
            @Override
            public void onNext(Search details) {
                Log.e("NOTIFICATION Next", "On Next");
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

    /**
     * Retrofit call for worker
     *
     * @return retrofit build
     */
    private Retrofit setRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com/svc/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    /**
     * Method who trigger the Notification on user screen after 24h
     *
     * @param mSearchQuery the querry term from user
     */
    private void showNotif(String mSearchQuery) {

        if (mFirstNot == 0) {
            mFirstNot++;    //  Tips for ignoring immediate notification when set
        } else {
            if (mSizeResult > 0) {
                Intent intent = new Intent(mContext, SearchActivity.class); //  Open Search Activity with user querry if click on new notification
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

                Intent intent = new Intent(mContext, NotificationActivity.class);   //  If no result after 24h, user can click notification to change query
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

    /**
     * Setting date for matching with API call request <br>
     * by adding 0 before the month and day values from 1 to 9
     */
    private void setDate() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;  //  Month+1 cause it begin to 0 for January
        int day = cal.get(Calendar.DAY_OF_MONTH);


        String date = day + "/" + month + "/" + year;
        String formattedMonth = "" + month;
        String formattedDayOfMonth = "" + day;
        String formattedDayOfMonthYesterday = "" + (day - 1);

        if (month < 10) {
            formattedMonth = "0" + month;
        }
        if (day < 10) {
            formattedDayOfMonth = "0" + day;
        }
        mYesterday = year + "" + formattedMonth + "" + formattedDayOfMonthYesterday;    //  The job search from yesterday
        mToday = year + "" + formattedMonth + "" + formattedDayOfMonth; //  To today
    }

}
