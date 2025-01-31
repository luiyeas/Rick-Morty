package com.lnavarro.rickmorty.data.network

import com.lnavarro.rickmorty.data.model.response.CharacterResponseModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RickAndMortyApiClient {

    @GET("/api/character/")
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): CharacterResponseModel

}