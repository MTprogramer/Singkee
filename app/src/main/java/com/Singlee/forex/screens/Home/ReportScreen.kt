package com.Singlee.forex.screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.Singlee.forex.R
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.extra_light
import com.Singlee.forex.ui.theme.hintColor
import com.Singlee.forex.ui.theme.red

@Composable
fun ReportScreen(navController: NavHostController)
{
    Column (
        Modifier
            .padding(horizontal = 20.dp, vertical = 25.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())){
        TodayprofiteLayut()

        Spacer(modifier = Modifier.height(20.dp))


        Row {
            tradesBox(modifier = Modifier.weight(1f), name = "Win" , R.drawable.win_icon)
            Spacer(modifier = Modifier. width(20.dp))
            tradesBox(modifier = Modifier.weight(1f), name = "Lose" , R.drawable.lose_icon)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            tradesBox(modifier = Modifier.weight(1f), name = "Win rate" , R.drawable.win_rate)
            Spacer(modifier = Modifier. width(20.dp))
            tradesBox(modifier = Modifier.weight(1f), name = "Total Trades" , R.drawable.all_trades_icon)
        }


        Column{
            tradesDetail(true , false)
            tradesDetail(false , false)
            tradesDetail(false , true)
            tradesDetail(false , false)
            tradesDetail(false , true)
            tradesDetail(false , false)
            tradesDetail(true , true)
            tradesDetail(false , false)
            tradesDetail(false , true)
            tradesDetail(true , true)
            tradesDetail(false , false)
            tradesDetail(false , false)
        }


    }
}

@Composable
fun tradesDetail(b: Boolean, is_buy: Boolean)
{
    if (b)
    {
        Text(
            text = "Today",
            style = MaterialTheme.typography.displayMedium,
            color = hintColor,
            modifier = Modifier.padding(vertical = 20.dp)
        )
    }

    Column {
        Row (verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = painterResource(R.drawable.buydot),
                modifier = Modifier.size(7.dp),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Text(
                text = "ETHUSDm,",
                style = MaterialTheme.typography.displayMedium,
                color = hintColor,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 5.dp)
            )
            Text(
                text = " buy 20.01",
                style = MaterialTheme.typography.displayMedium,
                color = if (is_buy) blue else red,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 5.dp)
            )

            Box(Modifier.fillMaxWidth() , contentAlignment = Alignment.TopEnd) {
                Text(
                    text = "2019.11.22, 12:25 ",
                    style = MaterialTheme.typography.displayMedium,
                    color = hintColor,
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "235.57",
                style = MaterialTheme.typography.displayMedium,
                color = hintColor,
                modifier = Modifier.padding(end = 5.dp)
            )
            Image(
                painter = painterResource(R.drawable.back_icon),
                modifier = Modifier
                    .size(20.dp)
                    .rotate(180f),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
            Text(
                text = " 235.57",
                style = MaterialTheme.typography.displayMedium,
                color = hintColor,
                modifier = Modifier.padding(start = 5.dp)
            )

            Box(Modifier.fillMaxWidth() , contentAlignment = Alignment.TopEnd) {
                Text(
                    text = "-1634.22",
                    style = MaterialTheme.typography.displayMedium,
                    color =  if (is_buy) blue else red,
                )
            }
        }

        Box(
            Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(extra_light))


    }


}

@Composable
fun TodayprofiteLayut() {

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .background(
                blue,
                RoundedCornerShape(20.dp)
            )
            .padding(10.dp)
            .height(63.dp)
    )
    {
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Today Profit",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
            )
        }
        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "10,000.00",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                )
                Text(
                    text = "PIPS",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

    @Composable
    fun tradesBox(modifier: Modifier , name : String , icon : Int)
    {
        Box(modifier = modifier
            .background(extra_light, RoundedCornerShape(20.dp))
            .padding(15.dp)
            .height(79.dp))
        {
            Column(modifier.fillMaxSize()) {
                Row (Modifier.fillMaxWidth())
                {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        Modifier.size(31.dp)
                    )
                    Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "10",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 28.sp,
                            color = Color.White,
                        )
                    }
                }
            }

                Box (contentAlignment = Alignment.BottomStart , modifier = Modifier.fillMaxHeight()){
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                    )
                }

            }
        }



