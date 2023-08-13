package com.example.triviaapp2.network

import com.example.triviaapp2.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

// used to get the data that we need (like a DAO :) )
@Singleton
interface QuestionApi {
    @GET("world.json")
    suspend fun getAllQuestions(): Question
}