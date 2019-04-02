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
 */
public class NewsStream {

    public static Observable<TopStories> streamGetTopStories() {
        NewYorkTimesService newsStream = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetTopStories()
                .subscribeOn(Schedulers.io())   //  Run call on another thread
                .observeOn(AndroidSchedulers.mainThread())  //  Observe on the Main thread
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<MostPopular> streamGetMostPopular(int period) {
        NewYorkTimesService newsStream = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetMostPopular(period)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<SharedObservable> streamGetBusiness() {
        NewYorkTimesService newsStream = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetBusiness()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<SharedObservable> streamGetFood() {
        NewYorkTimesService newsStream = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetFood()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<SharedObservable> streamGetMovies() {
        NewYorkTimesService newsStream = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<SharedObservable> streamGetSports() {
        NewYorkTimesService newsStream = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetSports()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<SharedObservable> streamGetTech() {
        NewYorkTimesService newsStream = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetTech()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<SharedObservable> streamGetTravel() {
        NewYorkTimesService newsStream = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetTravel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<Search> streamGetSearch(String begin, String end, String query, String categorie) {
        NewYorkTimesService newsStream = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);
        return newsStream.streamGetSearch(begin, end, query, categorie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
