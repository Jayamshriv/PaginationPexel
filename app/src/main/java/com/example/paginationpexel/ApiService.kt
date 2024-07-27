package com.example.paginationpexel

import com.example.paginationpexel.dto.PexelDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

//    /authHeader : String = Constants.API_KEY
    @GET("v1/search/")
    suspend fun getPhotos(
        @Query("query") query : String? = "nature",
        @Query("per_page") per_page : Int?,
        @Query("page") page : Int?,
        @Header("Authorization") authHeader : String? = Constants.API_KEY
    ) : PexelDto

}