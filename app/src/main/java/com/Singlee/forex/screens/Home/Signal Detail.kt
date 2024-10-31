package com.Singlee.forex.screens.Home

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.Singlee.forex.DataModels.SignalData
import com.Singlee.forex.R
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.duble_extra_light
import com.Singlee.forex.ui.theme.sans
import com.Singlee.forex.ui.theme.titleColor

@Composable
fun SignalDetail(signalData: SignalData, navController: NavHostController) {
    Column(
        Modifier
            .padding(horizontal = 20.dp, vertical = 25.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ProfileToolbar(title = "Signal Detail") { navController.popBackStack() }
        Spacer(modifier = Modifier.height(30.dp))

        // Image loading with animation
        val painter = rememberAsyncImagePainter(
            model = signalData.imageURL,  // Use the URL directly
            placeholder = painterResource(R.drawable.fake_avtar),  // Placeholder when loading
            error = painterResource(R.drawable.fake_avtar)  // Error image in case of failure
        )

        val painterState = painter.state  // Track painter's state

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = painter,
                contentDescription = "Signal Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            // Show a loading animation when the image is still loading
            if (painterState is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Render signal details
        signalDetail(signalData)

        Spacer(modifier = Modifier.height(50.dp))

        note()
    }
}


@Composable
fun signalDetail(signalData: SignalData)
{
    Column{
        SignalTitle()
        signalData.tp_list.forEachIndexed() { index , tp ->
            if (index == 0)
            {
                SignalDesign(signalData.sl , signalData.entry , signalData.tp_list[index])
            }
            else
            {
                SignalDesign("//" ,"//",signalData.tp_list[index])
            }
        }
    }
}
@Composable
fun SignalTitle()
{
    Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
        Text(
            text = "Stop Loss",
            style = MaterialTheme.typography.displaySmall,
            fontSize = 17.sp
        )
        Text(
            text = "Entry Point",
            style = MaterialTheme.typography.displaySmall,
            fontSize = 17.sp
        )
        Text(
            text = "Take Profit",
            style = MaterialTheme.typography.displaySmall,
            fontSize = 17.sp
        )
    }
}
@Composable
fun SignalDesign( sl : String , entry : String , tp : String )
{
    Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .padding(top = 5.dp)){
        Text(
            text = sl,
            fontFamily = sans,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = titleColor,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
        Text(
            text = entry,
            fontFamily = sans,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = titleColor,
            letterSpacing = 1.sp
        )
        Text(
            text = tp,
            fontFamily = sans,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = titleColor,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(end = 10.dp)
        )
    }
}

@Preview
@Composable
fun note()
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(duble_extra_light, RoundedCornerShape(20f)))
        {
            Column(Modifier.padding(20.dp)) {
                Text(
                    text = "NOTE",
                    style = MaterialTheme.typography.displaySmall,
                    fontSize = 15.sp,
                    color = blue
                )

                Text(
                    text = "Trading signals are expert predictions based on current market analysis. Please be aware that markets can move unpredictably at any time. After achieving your expected profit, it is recommended to exit the trade promptly. Always trade responsibly and follow risk management tolerance.",
                    fontFamily = sans,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = titleColor,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        }
}


