package com.example.productapp.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.productapp.model.database.ProductsEntity
import com.example.productapp.viewmodel.ProductsViewModel

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun ProductsScreen(viewModel :ProductsViewModel = hiltViewModel(), navController: NavController) {
   val product: List<ProductsEntity> = viewModel.productStateOne.value
    LazyColumn{    items(product)
            { product->
                ProductsInfo(navController, product) }}

}
@Composable
fun ProductsInfo(navController: NavController, state: ProductsEntity) {
    Card( elevation = 8.dp, modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, start = 8.dp, end = 8.dp)
        .clickable {
            navController.navigate(route = ScreensEnum.ProductDetailsScreen.name + "/${state.id}")
        }
           )    {
        Row{

            Image(painter = rememberAsyncImagePainter(state.images[0]),
                contentDescription = null,modifier = Modifier
                    .height(110.dp)
                    .width(110.dp)
                    .padding(4.dp), contentScale = ContentScale.Crop)

            Column(modifier = Modifier.padding(10.dp)) {

                Text(text = state.brand, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(10.dp))

                Text(text= state.description, fontFamily = FontFamily.Serif, maxLines = 2,
                    color = Color.Black)
            }
            }
        }
    }






