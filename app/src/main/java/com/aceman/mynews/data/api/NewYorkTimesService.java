package com.aceman.mynews.data.api;

import com.aceman.mynews.data.models.mostpopular.MostPopular;
import com.aceman.mynews.data.models.search.Search;
import com.aceman.mynews.data.models.shared.SharedObservable;
import com.aceman.mynews.data.models.topstories.TopStories;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Lionel JOFFRAY - on 13/03/2019.
 */
public interface NewYorkTimesService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @GET("topstories/v2/home.json?api-key=Ev1ajeR5HJn2ghLXJUb22OAlEoYbnKXi")
        // TopStories call
    Observable<TopStories> streamGetTopStories();

    @GET("mostpopular/v2/viewed/{period}.json?api-key=Ev1ajeR5HJn2ghLXJUb22OAlEoYbnKXi")
        //  MostPopular call
    Observable<MostPopular> streamGetMostPopular(@Path("period") int period);

    @GET("search/v2/articlesearch.json?fq=section_name:(\"Business Day\")&sort=newest&api-key=Ev1ajeR5HJn2ghLXJUb22OAlEoYbnKXi")
    Observable<SharedObservable> streamGetBusiness();

    @GET("search/v2/articlesearch.json?fq=section_name:(\"Technology\")&sort=newest&api-key=Ev1ajeR5HJn2ghLXJUb22OAlEoYbnKXi")
    Observable<SharedObservable> streamGetTech();

    @GET("search/v2/articlesearch.json?fq=section_name:(\"Food\")&sort=newest&api-key=Ev1ajeR5HJn2ghLXJUb22OAlEoYbnKXi")
    Observable<SharedObservable> streamGetFood();

    @GET("search/v2/articlesearch.json?fq=section_name:(\"Movies\")&sort=newest&api-key=Ev1ajeR5HJn2ghLXJUb22OAlEoYbnKXi")
    Observable<SharedObservable> streamGetMovies();

    @GET("search/v2/articlesearch.json?fq=section_name:(\"Sports\")&sort=newest&api-key=Ev1ajeR5HJn2ghLXJUb22OAlEoYbnKXi")
    Observable<SharedObservable> streamGetSports();

    @GET("search/v2/articlesearch.json?fq=section_name:(\"Travel\")&sort=newest&api-key=Ev1ajeR5HJn2ghLXJUb22OAlEoYbnKXi")
    Observable<SharedObservable> streamGetTravel();
    // begin_date= (YYYY/MM/DD), end_date= (YYYY/MM/DD), q= (query), fq= (news_desk:("categorie" " categorie"))

    @GET("search/v2/articlesearch.json?api-key=Ev1ajeR5HJn2ghLXJUb22OAlEoYbnKXi")
    Observable<Search> streamGetSearch(@Query("begin_date") String begin, @Query("end_date") String end, @Query("q") String query, @Query("fq") String category);
}

