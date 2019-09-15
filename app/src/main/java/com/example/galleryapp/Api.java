package com.example.galleryapp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

import java.util.List;

public interface Api {
    String BASE_URL ="https://api.flickr.com/";
    //Method to get data from flickr Api
    //*  @GET("/services/rest/?method=flickr.photos.getRecent&per_page=20&page=1&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
    //Method to get data from flickr Api variably
 @GET()
  Call<Pojo>setPhot(@Url String url);
}

