package com.example.productapp.model.retrofit

import com.example.productapp.model.Products
import com.example.productapp.model.database.ProductsDao
import com.example.productapp.model.database.ProductsEntity
import javax.inject.Inject

class ProductsRepository@Inject constructor( private val api : RetrofitApi, private val
                dao: ProductsDao)  {


    suspend fun insertProduct(productEntity : ProductsEntity) {
        dao.insertProduct(productEntity)

    }


    suspend fun getProductsFromDb() : List<ProductsEntity> {
        return dao.getProductFromDb()
    }

    suspend fun getProducts(): Products {
       return api.getProducts()
    }
}




