package com.example.coroutineapplication

class ProductRepository(private val productsApi: ProductsApi) {

    suspend fun getProducts() : NetworkResult<List<ProductListItem>> {
        val response = productsApi.getProducts()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if(responseBody != null) {
                NetworkResult.Success(responseBody)
            } else {
                NetworkResult.Error("Something went wrong")
            }
        } else {
            NetworkResult.Error("Something went wrong")
        }
    }

    suspend fun getProduct(id: Int): NetworkResult<ProductListItem> {
        val response = productsApi.getProduct(id)

        return if (response.isSuccessful) {
            val responseBody = response.body()
            if(responseBody != null) {
                NetworkResult.Success(responseBody)
            } else {
                NetworkResult.Error("Something went wrong")
            }
        } else {
            NetworkResult.Error("Something went wrong")
        }
    }
}