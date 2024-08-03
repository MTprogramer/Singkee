package com.Singlee.forex.screens.Home.nav

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.Singlee.forex.R
import com.Singlee.forex.graph.HomeRoutes
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.mediumTitle

@Composable
fun CustomBottomNavigation(
    selectedRoute:String,
    onItemSelected:(String)->Unit
) {



    val items= listOf(
        navItem(HomeRoutes.HomeRoute.route),
        navItem(HomeRoutes.StartChatRoute.route),
        navItem(HomeRoutes.ReportScreen.route),
        navItem(HomeRoutes.ProfileScreen.route),
    )


    val navShape= RoundedCornerShape(topStart = 24.dp,topEnd = 24.dp)

    Column {


        Row(
            modifier = Modifier
                .offset(y = 10.dp)
                .paint(
                    painter = painterResource(id = R.drawable.navbg),
                    contentScale = ContentScale.FillBounds
                )
                .fillMaxWidth()
                .height(80.dp)
                .clip(navShape),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {


            items.forEach {

                val isSelected = it.route == selectedRoute

                val color = if (!isSelected)
                    mediumTitle
                else
                    blue

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (!isSelected)
                                onItemSelected(it.route)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = it.image),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = it.lable,
                        style = MaterialTheme.typography.titleSmall,
                        color = if (!isSelected) mediumTitle else blue,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }

            }
        }
    }
}