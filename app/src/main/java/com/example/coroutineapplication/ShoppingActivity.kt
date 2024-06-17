package com.example.coroutineapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShoppingActivity : AppCompatActivity() {
    lateinit var shoppingViewModel: ShoppingViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var shoppingAdapter: ShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shopping)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this,2)

        val repository = (application as StoreApplication).productRepository
        shoppingViewModel = ViewModelProvider(this,ShoppingViewModelFactory(repository))[ShoppingViewModel::class.java]
        shoppingViewModel.getProducts()
        shoppingViewModel.products.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    shoppingAdapter = ShoppingAdapter(it.data!!)
                    recyclerView.adapter = shoppingAdapter
                }

                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        }

        shoppingViewModel.product.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    Log.d("!!!!!!!!!", it.data?.id.toString())
                }

                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        }
    }
}