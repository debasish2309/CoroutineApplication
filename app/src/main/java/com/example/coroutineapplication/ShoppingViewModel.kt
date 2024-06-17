package com.example.coroutineapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repository: ProductRepository): ViewModel() {

    private val _products= MutableLiveData<NetworkResult<List<ProductListItem>>>()
    val products : LiveData<NetworkResult<List<ProductListItem>>>
        get() = _products

    private val _product= MutableLiveData<NetworkResult<ProductListItem>>()
    val product : LiveData<NetworkResult<ProductListItem>>
        get() = _product

    fun getProducts() {
        viewModelScope.launch {
            val result = repository.getProducts()
            _products.postValue(result)

            val result1 = async { repository.getProducts() }
            Log.d("!!!!!!!!!->", result1.await().data?.get(0)?.id.toString())
            val result2 = async { result1.await().data?.get(0)?.let { repository.getProduct(it.id) } }
            Log.d("!!!!!!!!!->>",result2.await()?.data?.id.toString())
            _product.postValue(result2.await())
        }
    }
}