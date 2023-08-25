package com.example.productapp.view

enum class ScreensEnum {

    ProductsScreen,
    ProductDetailsScreen;
    companion object{
        fun fromRoute(route:String?) : ScreensEnum

        = when(route?.substringBefore("/")) {
            ProductsScreen.name -> ProductsScreen
            ProductDetailsScreen.name -> ProductDetailsScreen
            null -> ProductsScreen
            else -> throw IllegalArgumentException("Route $route is not recognised")
        }



    }
}