package com.example.meetapp.data.services

import com.example.meetapp.data.models.presenter.Presenter
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DataEventService {

    @GET("palestrantes?limit=200")
    fun getAllPresenters(
        @Query(value = "d_rdhid", encoded = true) key : String
    ) : Call<JsonObject>

    companion object{

        private val dataEventService : DataEventService by lazy{

            val retrofitService = Retrofit.Builder()
                .baseUrl("https://api.doity.com.br/public/aplicativos/v2/eventos/24043/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofitService.create(DataEventService::class.java)

        }

        fun getInstance() : DataEventService{
            return dataEventService
        }
    }
}