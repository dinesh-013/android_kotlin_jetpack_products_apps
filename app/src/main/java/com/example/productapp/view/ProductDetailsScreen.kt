package com.example.productapp.view

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.productapp.model.database.ProductsEntity
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.gowtham.ratingbar.RatingBar
import kotlinx.coroutines.delay
import java.lang.Thread.yield


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductDetailsScreen(navController: NavController,
                         product :List<ProductsEntity>, productId : String) {

    val pageCount = product[productId.toInt()-1].images.size
    val pagerState = rememberPagerState(initialPage = 1)
    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2000)
            tween<Float>(600)
            pagerState.animateScrollToPage(
            page = (pagerState.currentPage + 1) % pageCount)
        }
    }
    Column(modifier = Modifier.fillMaxSize()
                                .background(Color.Black.copy(0.8f))) {

            Box(modifier = Modifier.fillMaxWidth()
                                    .height(300.dp)) {
                HorizontalPager(count = pageCount, state = pagerState, modifier =Modifier.fillMaxWidth()
                    .height(300.dp) ) { page ->
                    val image =
                        rememberAsyncImagePainter(model = product[productId.toInt() - 1].images[page])
                    Image(
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(250.dp)
                    )
                }
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.TopStart)
                        .background(Color.Transparent).padding(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.size(52.dp),
                        tint = Color.Black
                    )
                }
                Text(
                    text = product[productId.toInt() - 1].title,
                    modifier = Modifier.align(Alignment.BottomStart)
                        .padding(bottom = 8.dp, start = 15.dp, top = 20.dp, end = 48.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )}
                DetailBody(product, productId)
            }

    }
@Composable
private fun DetailBody(product: List<ProductsEntity>,productId: String) {
    Column(modifier = Modifier.padding(12.dp)) {


        ProductDetailsText(data = product[productId.toInt()-1].brand, title = "Brand")
        ProductDetailsText(data = product[productId.toInt()-1].category, "Category")
        ProductDetailsText(data = product[productId.toInt()-1].price.toString(), title = "Price")


            Spacer(modifier = Modifier.width(10.dp))

        ProductDetailsText(data = product[productId.toInt()-1].description, title = "Description")

            Spacer(modifier = Modifier.height(10.dp))

        ProductDetailsText(data = product[productId.toInt()-1].rating.toString(), title = "Rating")

            Spacer(modifier = Modifier.height(10.dp))
        product[productId.toInt()-1].rating?.let {

            RatingBar(value = it.toFloat(),
                onValueChange = {
                },
                onRatingChanged = {}
            )
        }
    }

}


@Composable
private fun ProductDetailsText(data: String?, title: String?) {
    Text(text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold,color = Color(0xFFf26e00),
            fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
        ) {
            append("$title:  ")
        }
        withStyle(style = SpanStyle(color = Color.LightGray,fontSize = 17.sp))
            {
                append(data!!)
        }

            }, Modifier.padding(4.dp))


}
