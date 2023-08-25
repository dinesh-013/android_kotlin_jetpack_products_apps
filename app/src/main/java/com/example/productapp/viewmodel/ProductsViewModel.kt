package com.example.productapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.productapp.HiltApplication
import com.example.productapp.model.database.ProductsEntity
import com.example.productapp.model.retrofit.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class ProductsViewModel @Inject constructor
    ( app : Application, private val repo: ProductsRepository) : AndroidViewModel(app) {

    val productStateOne: MutableState<List<ProductsEntity>> = mutableStateOf(emptyList())

    init {
        showProduct()
        }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<HiltApplication>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


    private fun showProduct() {
        val internet = hasInternetConnection()
            if (internet) {
                Log.d("tag", " internet is working")


                viewModelScope.launch(Dispatchers.IO) {


                    val productsFromApi = repo.getProducts().products
                    val productsFromDatabase = repo.getProductsFromDb()
                    if (productsFromDatabase.isEmpty()){
                        Log.d("tag", " No data in database, net is working and showing data from retrofit")

                        productStateOne.value = productsFromApi

                        for (element in productsFromApi.indices) {
                            repo.insertProduct(
                                ProductsEntity(
                                    brand = productsFromApi[element].brand,
                                    category = productsFromApi[element].category,
                                    description = productsFromApi[element].description,
                                    discountPercentage = productsFromApi[element].discountPercentage,
                                    id = productsFromApi[element].id,
                                    images = productsFromApi[element].images,
                                    price = productsFromApi[element].price,
                                    rating = productsFromApi[element].rating,
                                    stock = productsFromApi[element].stock,
                                    thumbnail = productsFromApi[element].thumbnail,
                                    title = productsFromApi[element].title
                                )
                            )
                        }

                    }

                    else {
                        Log.d("tag", " showing data from database")
                        productStateOne.value = productsFromDatabase
                    }

//

                }
            }
            else {
                Log.d("tag", " internet is down")

                viewModelScope.launch(Dispatchers.IO) {
                    if(repo.getProductsFromDb().isEmpty()) {
                        Toast.makeText(getApplication(), "Internet is down and there is no data in database",
                        Toast.LENGTH_SHORT).show()

                    }
                    else {
                        productStateOne.value = repo.getProductsFromDb()

                    }

                }
            }
        }
    }
