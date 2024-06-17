package com.example.coroutineapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsApi {

    @GET("/products")
    suspend fun getProducts() : Response<List<ProductListItem>>

    @GET("/products/{id}")
    suspend fun getProduct(@Path("id") id: Int) : Response<ProductListItem>
}