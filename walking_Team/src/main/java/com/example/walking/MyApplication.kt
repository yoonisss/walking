package com.example.walking

import android.app.Application
import com.example.walking.retrofit.INetworkService
import com.example.walking.retrofit.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {

    var networkService: INetworkService
    val retrofit: Retrofit
        get() = Retrofit.Builder()
            // .baseUrl("http://192.168.0.21:8083/")
            .baseUrl("http://10.100.104.121:8083/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    init {
        networkService = retrofit.create(INetworkService::class.java)
    }

}