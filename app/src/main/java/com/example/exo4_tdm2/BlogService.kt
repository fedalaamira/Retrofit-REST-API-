package com.example.exo4_tdm2



import retrofit2.Call
import retrofit2.http.*


/**
 *Created by Fedala Amira.

 */

interface BlogService {

    @GET("/posts/{id}")
    fun getPost(@Path("id") id: String): Call<post>
    @GET("/posts?userId=1")
    fun getPosts(): Call<List<post>>
    @POST("/posts")
    fun sendPost(@Body post: post): Call<post>
    @DELETE("/posts/{id}")
    fun deletePost(@Path("id") id: String): Call<post>
}