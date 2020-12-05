package com.example.myapplication.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://clothing-advisor.firebaseio.com/"

enum class ApiStatus { LOADING, ERROR, DONE }

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("/users/{uid}/clothes.json")
    suspend fun getClothes(@Path("uid") userId: String,
                           @Query("auth") accessToken: String,
                           @Query("startAt") startAt: String,
                           @Query("endAt") endAt: String): Map<String, ClothesResponseItem>

    @POST("/users/{uid}/clothes.json")
    suspend fun addClothes(@Path("uid") userId: String,
                           @Query("auth") accessToken: String,
                           @Body clothes: ClothesDbModel)

    @GET("users/{uid}/categories.json")
    suspend fun getCategories(@Path("uid") userId: String,
                              @Query("auth") accessToken: String): Map<String, CategoryResponseItem>

    @POST("users/{uid}/categories.json")
    suspend fun addCategories(@Path("uid") userId: String,
                              @Query("auth") accessToken: String,
                              @Body category: CategoryDbModel)
}

object Api {
    val retrofitService : ApiService by lazy { retrofit.create(ApiService::class.java) }
}
