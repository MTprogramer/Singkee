package com.Singlee.forex.screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.Singlee.forex.DataModels.SignalData
import com.Singlee.forex.R
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.duble_extra_light
import com.Singlee.forex.ui.theme.hintColor
import com.Singlee.forex.ui.theme.light_red
import com.Singlee.forex.ui.theme.signalCradencils
import com.Singlee.forex.ui.theme.signalType
import com.Singlee.forex.ui.theme.signalValues
import com.Singlee.forex.ui.theme.titleColor
import com.Singlee.forex.ui.theme.upgradeGradient

// Define a provider class for SignalData
class SignalDataProvider : PreviewParameterProvider<SignalData> {
    override val values = sequenceOf(
        SignalData(false, false,2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(true, true,2406.0, 2906.0, 2006.0, "AXUSD")
    )
}


@Composable
fun AllSignalScreen(navController: NavHostController)
{
    val list = listOf(
        SignalData(false, false, 2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(false, false, 2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(false, false, 2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(false, false, 2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(false, false, 2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD"),
        SignalData(false, true, 2406.0, 2906.0, 2006.0, "AXUSD")
    )

    Column(Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = "All Signals",
            style =MaterialTheme.typography.titleLarge,
            modifier =  Modifier.padding(top = 25.dp , bottom = 20.dp)
        )
        LazyColumn {
           items(list.size){2
               signalDesign(data = list[it])
           }
        }
    }
}

@Preview(showBackground = true , backgroundColor = 0xFF15191F )
@Composable
fun signalDesign(@PreviewParameter(SignalDataProvider::class) data : SignalData)
{

    Spacer(modifier = Modifier.height(15.dp))
    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(duble_extra_light, shape = RoundedCornerShape(10.dp))
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
                       if (data.buy)
                           R.drawable.buydot
                       else
                           R.drawable.selldot
                   ),
                   modifier = Modifier.size(7.dp),
                   contentDescription = "",
                   contentScale = ContentScale.Crop
               )
               Text(
                   text = if (data.buy) "BUY" else "SELL",
                   color = if (data.buy) blue else light_red,
                   style = signalType,
                   modifier = Modifier.padding(start = 10.dp)
               )
               Text(
                   text = data.pairName,
                   color = Color.White,
                   style = signalType,
                   modifier = Modifier.padding(start = 10.dp)
               )
               if (data.isPremium)
                   upgradeBtn(upgradeGradient)

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
fun upgradeBtn(gradientColors: List<Color>)
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
                .clickable { },
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
    fun signalValueDesign(tyoe: String, value: Double, modifier: Modifier, premium: Boolean)
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