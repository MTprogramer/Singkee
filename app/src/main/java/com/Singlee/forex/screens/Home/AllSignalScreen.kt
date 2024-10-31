package com.Singlee.forex.screens.Home

import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.Singlee.forex.DataModels.SignalData
import com.Singlee.forex.R
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.graph.HomeRoutes
import com.Singlee.forex.screens.Home.ViewModels.SignalVideoModel
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.duble_extra_light
import com.Singlee.forex.ui.theme.hintColor
import com.Singlee.forex.ui.theme.light_red
import com.Singlee.forex.ui.theme.signalCradencils
import com.Singlee.forex.ui.theme.signalType
import com.Singlee.forex.ui.theme.signalValues
import com.Singlee.forex.ui.theme.titleColor
import com.Singlee.forex.ui.theme.upgradeGradient
import com.google.gson.Gson


@Composable
fun AllSignalScreen(navController: NavHostController, signalViewModel: SignalVideoModel)
{
    LaunchedEffect(Unit) {
        signalViewModel.getActiveSignals(true)
    }

    var loading by remember { mutableStateOf(false) }
    val signalState by signalViewModel.signals.collectAsState(Response.Empty)
    var signals by remember { mutableStateOf(emptyList<SignalData>()) }
    var randerui by remember { mutableStateOf(false) }

    LaunchedEffect(signalState) {
        when (signalState) {
            is Response.Loading -> {
                loading = true
            }

            is Response.Error -> {
                loading = false
                Log.d ("error" , (signalState as Response.Error).message)
            }

            is Response.Success -> {
                loading = false
                signals = (signalState as Response.Success<List<SignalData>>).data
                randerui = true
            }

            Response.Empty -> {}
        }
    }


//    val list = listOf(
//        SignalData(false, false, 2406.0, 2906.0, 2006.0, "AXUSD"),
//        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
//        SignalData(false, false, 2406.0, 2906.0, 2006.0, "AXUSD"),
//        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
//        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
//        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
//        SignalData(false, false, 2406.0, 2906.0, 2006.0, "AXUSD"),
//        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
//        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
//        SignalData(false, false, 2406.0, 2906.0, 2006.0, "AXUSD"),
//        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
//        SignalData(false, false, 2406.0, 2906.0, 2006.0, "AXUSD"),
//        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
//        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD")
//    )

        Box(Modifier.fillMaxSize()) {

            if (randerui) {
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "All Signals",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 25.dp, bottom = 10.dp)
                    )
                    LazyColumn {
                        items(signals.size) {
                            signalDesign(data = signals[it], it ,
                                { navController.navigate(HomeRoutes.PremiumRoute.route) })
                            {
                                position ->
                                val signalDataJson = Uri.encode(Gson().toJson(signals[position]))
                                navController.navigate("${HomeRoutes.SignalDetail.route}/$signalDataJson")

                            }
                        }
                    }
                }
            }
            if (loading) {
                CustomLoadingAnimation()
            }
        }
}

@Composable
private fun signalDesign(data: SignalData, i: Int, function: () -> Unit, itemClick: (Int) -> Unit)
{

    Spacer(modifier = Modifier.height(15.dp))
    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(duble_extra_light, shape = RoundedCornerShape(10.dp))
            .clickable { if (data.isPremium) function.invoke() else itemClick.invoke(i) }
    )
    {
       Column {
           Row(
               verticalAlignment = Alignment.CenterVertically ,
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(
                       start = 15.dp,
                       top = 15.dp
                   ))
           {
               Image(
                   painter = painterResource(
                       if (data.type == "Buy")
                           R.drawable.buydot
                       else
                           R.drawable.selldot
                   ),
                   modifier = Modifier.size(7.dp),
                   contentDescription = "",
                   contentScale = ContentScale.Crop
               )
               Text(
                   text = if (data.type =="Buy") "BUY" else "SELL",
                   color = if (data.type =="Buy") blue else light_red,
                   style = signalType,
                   modifier = Modifier.padding(start = 10.dp)
               )
               Text(
                   text = data.cate,
                   color = Color.White,
                   style = signalType,
                   modifier = Modifier.padding(start = 10.dp)
               )
               if (data.isPremium)
                   upgradeBtn(upgradeGradient , function)

           }
           Row(Modifier.padding( top = 12.dp , bottom = 15.dp) , horizontalArrangement = Arrangement.SpaceBetween)
           {
               signalValueDesign("sl",3604.0,Modifier.weight(1f) , data.isPremium)
               signalValueDesign("", 3604.0, Modifier.weight(1f), data.isPremium)
               signalValueDesign("tp", 3604.0, Modifier.weight(1f), data.isPremium)
           }
       }
    }

}

@Composable
private fun upgradeBtn(gradientColors: List<Color>, function: () -> Unit)
{
    Row (
        Modifier
            .fillMaxWidth()
            .padding(end = 15.dp) , horizontalArrangement = Arrangement.End){
        Box(
            Modifier
                .wrapContentSize()
                .background(
                    brush = Brush.linearGradient(
                        gradientColors,
                        start = Offset(30f, -50f),
                        end = Offset(70.5f, 120f)
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 5.dp, vertical = 4.dp)
                .clickable(onClick = function),
            contentAlignment = Alignment.Center
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically ,
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(horizontal = 5.dp)

            ) {
                Text(
                    text = "Upgrade",
                    color = Color.White,
                    style = signalCradencils,
                    modifier = Modifier
                        .padding( end = 5.dp)
                )
                Image(
                    painter = painterResource(R.drawable.lockicon),
                    modifier = Modifier.size(15 .dp),
                    contentDescription = "",
                )
            }
        }
    }
}


    @Composable
  private  fun signalValueDesign(tyoe: String, value: Double, modifier: Modifier, premium: Boolean)
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally , modifier = modifier ) {
            Text(
                text = when (tyoe) {
                    "tp" -> "Take Profit"
                    "sl" -> "Stop Loss"
                    else -> "Entry"
                },
                color = titleColor,
                style = signalCradencils,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box (
                Modifier
                    .wrapContentSize()
                    .background(
                        color = when (tyoe) {
                            "tp" -> blue
                            "sl" -> light_red
                            else -> hintColor
                        },
                        shape = RoundedCornerShape(30.dp)
                    )
                    .padding(
                        horizontal = if (premium) 20.dp else 10.dp,
                        vertical = if (premium) 6.dp else 4.dp
                    )
            )
            {
                if (!premium) {
                    Text(
                        text = value.toString(),
                        color = Color.White,
                        style = signalValues
                    )
                }
                else
                {
                    Image(
                        painter = painterResource(id = R.drawable.hide_icon),
                        contentDescription = "",
                        Modifier
                            .height(11.dp)
                            .width(14.dp)
                    )
                }
            }
        }
    }

@Preview
@Composable
fun LoadingDialog() {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(text = "Loading", color = Color.Blue)
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(
                        color = Color.Blue,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Please wait...", color = Color.Blue)
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
}

@Preview
@Composable
fun CustomLoadingAnimation() {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(true) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing)
            )
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            progress = { progress.value },
            modifier = Modifier.size(40.dp),
            color = Color.Blue,
        )
    }
}

