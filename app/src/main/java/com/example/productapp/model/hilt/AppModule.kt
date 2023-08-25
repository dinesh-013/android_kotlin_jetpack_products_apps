package com.example.productapp.model.hilt

import android.content.Context
import androidx.room.Room
import com.example.productapp.model.database.ProductDatabase
import com.example.productapp.model.database.ProductsDao
import com.example.productapp.model.retrofit.ProductsRepository
import com.example.productapp.model.retrofit.RetrofitApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://dummyjson.com/"

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context,ProductDatabase::class.java,"ProductDatabase").build()

    @Singleton
    @Provides
    fun injectDao(
        database: ProductDatabase
    ) = database.productDao()

    @Singleton
    @Provides
    fun injectProductsRepository(api:RetrofitApi, dao: ProductsDao) = ProductsRepository(api, dao)


    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(RetrofitApi::class.java)
    }
}