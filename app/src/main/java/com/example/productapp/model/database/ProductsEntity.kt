package com.example.productapp.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "product_table")
data class ProductsEntity(
    @ColumnInfo(name = "brand") var brand: String,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name="discountPercentage") var discountPercentage:Double?,
    @PrimaryKey@ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "images") var images: List<String>,
    @ColumnInfo(name = "price") var price: Int,
    @ColumnInfo(name="rating") var rating:Double?,
    @ColumnInfo(name = "stock") var stock: Int,
    @ColumnInfo(name = "thumbnail") var thumbnail: String,
    @ColumnInfo(name = "title") var title: String,)

class ImagesTypeConvertor {
    @TypeConverter
    fun fromString(value : String?) : List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromList(list : List<String>) : String {
        return Gson().toJson(list)

    }
}