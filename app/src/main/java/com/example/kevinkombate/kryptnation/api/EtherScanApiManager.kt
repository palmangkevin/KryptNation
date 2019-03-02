package com.example.kevinkombate.kryptnation.api

import com.example.kevinkombate.kryptnation.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EtherScanApiManager {

    private val apiKey = "H7W179MC2ANBR558RDKTSGAMQAJ8BM5DCU"

    private val retrofit: Retrofit by lazy {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }
        Retrofit.Builder()
                .baseUrl("https://api.etherscan.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
    }
    private val etherscanApiService = retrofit.create(EtherscanApiService::class.java)

    fun singleAccountBalance(address: String)
            = etherscanApiService.singleAccountBalance(
            address = address,
            apiKey = apiKey,
            action = "balance",
            module = "account",
            tag = "latest")

    fun transactions(address: String)
            = etherscanApiService.transactions(apiKey = apiKey,
            action = "txlist",
            module = "account",
            tag = "latest",
            address = address,
            page = 1,
            offset = 100,
            sort = "desc")

}