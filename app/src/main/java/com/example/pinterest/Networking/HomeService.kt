package com.example.pinterest.Networking

import dev.matyaqubov.pinterest.service.model.Search
import retrofit2.Call
import retrofit2.http.*

interface HomeService {

    //$ curl https://api.unsplash.com/search/photos?query=minimal
    //https://api.unsplash.com/search/photos?page=1&query=office
    //https://api.unsplash.com/search/photos?query=london&client_id=r_PBgcCBLgDICSpQXjRHOPe7e0N0kK4frHbnE60dryE
    //topics/nature/photos
    //photos/random?count=50
    @GET("photos/random?count=50")
    fun listPhotos(): Call<ArrayList<ResponseItem>>

    @GET("photos")
    fun getPhotos(@Query("page") page: Int, @Query("per_page") per_page: Int = 20): Call<ArrayList<ResponseItem>>

    @GET("topics/nature/photos")
    fun listPhotosNature():Call<ArrayList<ResponseItem>>

    @GET("photos/random?count=60")
    fun listPhotosForYou():Call<ArrayList<ResponseItem>>

    @GET("topics/animals/photos")
    fun listPhotosAnimals():Call<ArrayList<ResponseItem>>

    @GET("search/photos")
    fun getSearchResult(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = 20
    ): Call<Search>

    @DELETE("photos/{id}")
    fun deletePhotos(@Path("id")id:Int):Call<ResponseItem>

}