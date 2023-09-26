package com.example.rick_morty


import retrofit2.http.GET

interface CharacterApi {

    @GET("character")
    suspend fun getAllCharacters(): CharacterResponce

}