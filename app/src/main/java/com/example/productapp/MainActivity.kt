package com.example.productapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.productapp.ui.theme.ProductAppTheme
import com.example.productapp.view.ProductDetailsScreen
import com.example.productapp.view.ProductsScreen
import com.example.productapp.view.ScreensEnum
import com.example.productapp.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductAppTheme {
                ProductAppNavigation()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Composable
    private fun ProductAppNavigation() {
        val viewModel = hiltViewModel<ProductsViewModel>()
        val navController = rememberNavController()
        NavHost(navController, startDestination = ScreensEnum.ProductsScreen.name) {
            composable(route = ScreensEnum.ProductsScreen.name) {
                //val viewModel = hiltViewModel<ProductsViewModel>()
                ProductsScreen(navController = navController, viewModel = viewModel)
            }
            val productScreen = ScreensEnum.ProductDetailsScreen.name
            composable("$productScreen/{productId}", arguments =
            listOf(navArgument("productId"){
                type = NavType.StringType
            })
            ){ backStackEntry ->
                backStackEntry.arguments?.getString("productId").let {
                    ProductDetailsScreen(navController=navController,viewModel.productStateOne.value, productId = it.toString())
                }

            }

        }
    }
}


