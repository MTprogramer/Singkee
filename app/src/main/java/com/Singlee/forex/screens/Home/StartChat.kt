package com.Singlee.forex.screens.Home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.Singlee.forex.R
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.graph.HomeRoutes
import com.Singlee.forex.screens.Home.ViewModels.UserViewModel
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.button_blue
import com.Singlee.forex.ui.theme.extra_light
import com.Singlee.forex.ui.theme.sans
import com.Singlee.forex.ui.theme.titleColor


@Composable
fun StartChat(navController: NavHostController, userViewModel: UserViewModel)
{
    val imagesState by userViewModel.rendomUser.collectAsState(initial = Response.Empty)

    val imageUrlList = remember { mutableStateListOf<String>("","","") }

    LaunchedEffect(Unit) {
        userViewModel.getRendomImages()
    }
    LaunchedEffect(imagesState) {
        when(imagesState)
        {
            Response.Empty -> {}
            is Response.Error -> {
                Log.d("images","error")
            }
            Response.Loading -> {
                Log.d("images","error")
            }
            is Response.Success -> {
                val data = (imagesState as Response.Success).data
                imageUrlList.clear()
                imageUrlList.addAll(data)
                Log.d("images","success")
            }
        }
    }

    Column(Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = "We are here to help you",
            fontFamily = sans,
            fontWeight = FontWeight.SemiBold,
            color = titleColor,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 25.dp, bottom = 10.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .background(extra_light, RoundedCornerShape(20.dp))
        )
        {
            Column(
                modifier = Modifier
                    .padding(horizontal = 25.dp, vertical = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Send us a message",
                    color = Color.White ,
                    fontFamily = sans,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    resentSender(x = 0 , imageUrlList[0])
                    resentSender(x = -18, s = imageUrlList[1])
                    resentSender(x = -36, s = imageUrlList[2])

                    Column(
                        modifier = Modifier.offset(x = -(15).dp)
                    )
                    {
                        Text(
                            text = "Our Usually reply time",
                            color = titleColor ,
                            fontFamily = sans,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.char_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(17.dp)
                                    .padding(end = 5.dp),
                                contentScale = ContentScale.FillWidth
                            )
                            Text(
                                text = "Few Minuets",
                                color = blue ,
                                fontFamily = sans,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }

                Button(
                    onClick = {navController.navigate(HomeRoutes.ChatScreenRoute.route)} ,
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .background(button_blue, RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(button_blue)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Text(
                            text = "Start a conversation",
                            color = Color.White ,
                            fontFamily = sans,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Image(
                            painter = painterResource(id = R.drawable.sent_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .padding(start = 10.dp),
                            contentScale = ContentScale.FillWidth,
                        )
                    }
                }

            }
        }
    }
}

//@Preview
//@Composable
//fun OverlappingHorizontalImages() {
//
//    Row(
//        horizontalArrangement = Arrangement.Center,
//    ) {
//        // Image 3 (right)
//        Card(
//            modifier = Modifier
//                .size(47.dp)
//                .offset(x = 20.dp, y = 0.dp), // Adjust offsets for desired overlap
//            shape = CircleShape,
//            border = BorderStroke(2.dp, Color.White ),
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_launcher_background),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize(),
//                colorFilter = ColorFilter.tint(Color.Green)
//            )
//        }
//
//        // Image 2 (middle)
//        Card(
//            modifier = Modifier.size(47.dp),
//            shape = CircleShape,
//            border = BorderStroke(2.dp, Color.White ),
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_launcher_background),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize(),
//                colorFilter = ColorFilter.tint(Color.Cyan)
//            )
//        }
//
//        // Image 1 (left)
//        Card(
//            modifier = Modifier
//                .size(47.dp)
//                .offset(x = (-20).dp, y = 0.dp), // Adjust offsets for desired overlap
//            shape = CircleShape,
//            border = BorderStroke(2.dp, Color.White ),
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_launcher_background),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize(),
//                colorFilter = ColorFilter.tint(Color.Red)
//            )
//        }
//    }
//}

@Composable
fun resentSender(x: Int, s: String)
{
    Card(
        modifier = Modifier
            .size(50.dp)
            .offset(x = x.dp, y = 0.dp), // Adjust offsets for desired overlap
        shape = CircleShape,
        border = BorderStroke(2.dp, Color.White ),
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = s,
                placeholder = painterResource(R.drawable.fake_avtar),  // Placeholder when loading
                error = painterResource(R.drawable.fake_avtar)  // Error image in case of failure
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

