package com.example.kevinkombate.kryptnation.api


import com.example.kevinkombate.kryptnation.model.Balance
import com.example.kevinkombate.kryptnation.model.Result
import com.example.kevinkombate.kryptnation.model.Transaction
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EtherscanApiService {

    @GET("api")
    fun singleAccountBalance( @Query("apikey") apiKey: String,
                              @Query("module") module: String,
                              @Query("action") action: String,
                              @Query("tag") tag: String,
                              @Query("address") address: String): Call<Result<String>>

    @GET("api")
    fun transactions( @Query("apikey") apiKey: String,
                      @Query("module") module: String,
                      @Query("action") action: String,
                      @Query("tag") tag: String,
                      @Query("address") address: String,
                      @Query("page") page: Int,
                      @Query("offset") offset: Int,
                      @Query("sort") sort: String): Call<Result<List<Transaction>>>

}