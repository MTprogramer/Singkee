package com.Singlee.forex.screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Singlee.forex.R
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.duble_extra_light
import com.Singlee.forex.ui.theme.sans
import com.Singlee.forex.ui.theme.titleColor

@Preview
@Composable
fun SignalDetail()
{
    Column(
        Modifier
            .padding(horizontal = 20.dp, vertical = 25.dp)
            .verticalScroll(
                rememberScrollState()
            )) {
        ProfileToolbar(title = "Signal Detail") {}
        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.fake_avtar),
            contentDescription = "",
            Modifier
                .fillMaxWidth()
                .height(height = 200.dp)
                .clip(RoundedCornerShape(10.dp)),
            colorFilter = ColorFilter.tint(Color.Red),
            contentScale = ContentScale.FillBounds
        )


        Spacer(modifier = Modifier.height(20.dp))
        signalDetail()

        Spacer(modifier = Modifier.height(50.dp))

        note()
    }

}

@Preview
@Composable
fun signalDetail()
{
    Column{
        SignalTitle()
        SignalDesign()
        SignalDesign()
        SignalDesign()



    }
}
@Preview
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
@Preview
@Composable
fun SignalDesign()
{
    Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .padding(top = 5.dp)){
        Text(
            text = "1889.32",
            fontFamily = sans,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = titleColor,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
        Text(
            text = "1889-90",
            fontFamily = sans,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = titleColor,
            letterSpacing = 1.sp
        )
        Text(
            text = "1889.32",
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


