package com.example.productapp.model.retrofit

import com.example.productapp.model.Products
import retrofit2.http.GET

interface RetrofitApi {

    @GET("/products/")
    suspend fun getProducts() : Products
}