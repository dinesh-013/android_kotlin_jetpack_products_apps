package com.example.productapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ProductsEntity::class],version = 1)
@TypeConverters(ImagesTypeConvertor::class)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao() : ProductsDao
}