package com.example.productapp.model

import com.example.productapp.model.database.ProductsEntity

data class Products(
    val limit: Int,
    val products: List<ProductsEntity>,
    val skip: Int,
    val total: Int
)