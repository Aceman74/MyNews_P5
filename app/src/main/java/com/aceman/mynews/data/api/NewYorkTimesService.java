package com.aceman.mynews.data.api;

import com.aceman.mynews.data.models.mostpopular.MostPopular;
import com.aceman.mynews.data.models.shared.SharedObservable;
import com.aceman.mynews.data.models.topstories.TopStories;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Lionel JOFFRAY - on 13/03/2019.
 * <p>
 * This Class contain all CALL with API key for NYT <br>
 * Using <b>Retrofit</> <br>
 */
public interface NewYorkTimesService {

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

    @GET("search/v2/articlesearch.json?api-key=Ev1ajeR5HJn2ghLXJUb22OAlEoYbnKXi")
    Observable<SharedObservable> streamGetSearch(@Query("begin_date") String begin, @Query("end_date") String end, @Query("q") String query, @Query("fq") String category);
    // begin_date= (YYYY/MM/DD), end_date= (YYYY/MM/DD), q= (query), fq= (news_desk:("categorie" " categorie"))
}

