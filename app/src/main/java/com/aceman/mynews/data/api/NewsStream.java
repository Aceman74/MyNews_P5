package com.aceman.mynews.data.api;

import com.aceman.mynews.data.models.mostpopular.MostPopular;
import com.aceman.mynews.data.models.search.Search;
import com.aceman.mynews.data.models.shared.SharedObservable;
import com.aceman.mynews.data.models.topstories.TopStories;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Lionel JOFFRAY - on 13/03/2019.
 * <p>
 * <b>NewsStream</> Class contain all observables for news Call <br>
 * Using <b>RX Java</>
 */
public class NewsStream {
    /**
     * Observable for Top Stories
     *
     * @param service set Retrofit
     * @return List of article
     * @see RetrofitSet
     */
    public static Observable<TopStories> streamGetTopStories(NewYorkTimesService service) {
        return service.streamGetTopStories()
                .subscribeOn(Schedulers.io())   //  Run call on another thread
                .observeOn(AndroidSchedulers.mainThread())  //  Observe on the Main thread
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Most Popular
     *
     * @param service set Retrofit
     * @param period  7, for the last 7 day
     * @return List of article
     * @see RetrofitSet
     */
    public static Observable<MostPopular> streamGetMostPopular(NewYorkTimesService service, int period) {
        return service.streamGetMostPopular(period)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Business
     *
     * @param service set Retrofit
     * @return List of article
     * @see RetrofitSet
     */
    public static Observable<SharedObservable> streamGetBusiness(NewYorkTimesService service) {
        return service.streamGetBusiness()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Food
     *
     * @param service set Retrofit
     * @return List of article
     * @see RetrofitSet
     */
    public static Observable<SharedObservable> streamGetFood(NewYorkTimesService service) {
        return service.streamGetFood()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Movies
     *
     * @param service set Retrofit
     * @return List of article
     * @see RetrofitSet
     */
    public static Observable<SharedObservable> streamGetMovies(NewYorkTimesService service) {
        return service.streamGetMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Sports
     *
     * @param service set Retrofit
     * @return List of article
     * @see RetrofitSet
     */
    public static Observable<SharedObservable> streamGetSports(NewYorkTimesService service) {
        return service.streamGetSports()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Technology
     *
     * @param service set Retrofit
     * @return List of article
     * @see RetrofitSet
     */
    public static Observable<SharedObservable> streamGetTech(NewYorkTimesService service) {
        return service.streamGetTech()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Travel
     *
     * @param service set Retrofit
     * @return List of article
     * @see RetrofitSet
     */
    public static Observable<SharedObservable> streamGetTravel(NewYorkTimesService service) {
        return service.streamGetTravel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Observable for Top Stories
     *
     * @param service  set Retrofit
     * @param begin    From date
     * @param end      To date
     * @param query    Words to search
     * @param category at least one category checked
     * @return List of article
     * @see RetrofitSet
     */
    public static Observable<Search> streamGetSearch(NewYorkTimesService service, String begin, String end, String query, String category) {
        return service.streamGetSearch(begin, end, query, category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
