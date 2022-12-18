package com.uroosa.dieperson.api

import com.uroosa.dieperson.models.request.UserRequest
import com.uroosa.dieperson.models.response.UserResponse
import retrofit2.Response
import retrofit2.http.*

/*Retrofit interface */

interface UserApi {
    /*it's a post request, So We Add Body Notation*/
    @POST("users")
    suspend fun createUser(@Body userRequest: UserRequest): Response<UserResponse>

    @GET("users")
    suspend fun getUser(): Response<List<UserResponse>>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") userId: String): Response<UserResponse>

    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") userId: String,
        @Body userRequest: UserRequest
    ): Response<UserResponse>

}