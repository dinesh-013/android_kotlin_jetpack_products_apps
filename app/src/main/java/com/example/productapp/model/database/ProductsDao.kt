package com.example.productapp.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface  ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productEntity: ProductsEntity)

    @Query("SELECT (SELECT COUNT(*) FROM product_table) == 0")
    fun databaseIsEmpty(): Int

    @Query("SELECT * FROM product_table")
    suspend fun getProductFromDb() : List<ProductsEntity>

}