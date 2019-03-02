package com.example.kevinkombate.kryptnation.model

data class Result<T>(
        val status: String,
        val message: String,
        val result: T
)