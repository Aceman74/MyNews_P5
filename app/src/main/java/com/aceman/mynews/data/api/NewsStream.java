package com.aceman.mynews.data.api;

import com.aceman.mynews.data.models.mostpopular.MostPopular;
import com.aceman.mynews.data.models.shared.SharedObservable;
import com.aceman.mynews.data.models.topstories.TopStories;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aceman.mynews.ui.navigations.activities.MainActivity.mCache;

/**
 * Created by Lionel JOFFRAY - on 13/03/2019.
 * <p>
 * <b>NewsStream</> Class contain all observables for news Call an set Retrofit  <br>
 * Using <b>RX Java and Retrofit</>
 */
public class NewsStream {

    private static NewsStream INSTANCE = new NewsStream();
    Retrofit mRetrofit;

    /**
     * NewStream private constructor for Retrofit
     */
    private NewsStream() {

        /**
         * Interceptor for caching request for 2 minutes.
         */
        final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //  Create an Interceptor to  cache all request and avoid Too many request error
                Response originalResponse = chain.proceed(chain.request());
                int maxAge = 120; // read from cache for 2 minutes
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
                /**
                 int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                 return originalResponse.newBuilder()
                 .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                 .build();
                 */
            }
        };

        Dispatcher dispatcherMaxR = new Dispatcher();
        dispatcherMaxR.setMaxRequests(2);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .dispatcher(dispatcherMaxR)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(mCache)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com/svc/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static NewsStream getInstance() {
        return INSTANCE;
    }

    /**
     * Observable for Top Stories
     *
     * @return List of article
     */
    public Observable<TopStories> streamGetTopStories() {
        NewYorkTimesService newsStream = mRetrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetTopStories()
                .subscribeOn(Schedulers.io())   //  Run call on another thread
                .observeOn(AndroidSchedulers.mainThread())  //  Observe on the Main thread
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Most Popular
     *
     * @param period 7, for the last 7 day
     * @return List of article
     */
    public Observable<MostPopular> streamGetMostPopular(int period) {
        NewYorkTimesService newsStream = mRetrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetMostPopular(period)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Business
     *
     * @return List of article
     */
    public Observable<SharedObservable> streamGetBusiness() {
        NewYorkTimesService newsStream = mRetrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetBusiness()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Food
     *
     * @return List of article
     */
    public Observable<SharedObservable> streamGetFood() {
        NewYorkTimesService newsStream = mRetrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetFood()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Movies
     *
     * @return List of article
     */
    public Observable<SharedObservable> streamGetMovies() {
        NewYorkTimesService newsStream = mRetrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Sports
     *
     * @return List of article
     */
    public Observable<SharedObservable> streamGetSports() {
        NewYorkTimesService newsStream = mRetrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetSports()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Technology
     *
     * @return List of article
     */
    public Observable<SharedObservable> streamGetTech() {
        NewYorkTimesService newsStream = mRetrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetTech()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Travel
     *
     * @return List of article
     */
    public Observable<SharedObservable> streamGetTravel() {
        NewYorkTimesService newsStream = mRetrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetTravel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Top Stories
     *
     * @param begin    From date
     * @param end      To date
     * @param query    Words to search
     * @param category at least one category checked
     * @return List of article
     */
    public Observable<SharedObservable> streamGetSearch(String begin, String end, String query, String category) {
        NewYorkTimesService newsStream = mRetrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetSearch(begin, end, query, category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
