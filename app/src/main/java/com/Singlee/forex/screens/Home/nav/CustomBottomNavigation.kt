package com.Singlee.forex.screens.Home.nav

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.Singlee.forex.R
import com.Singlee.forex.graph.HomeRoutes
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.mediumTitle



@Preview(showBackground = true)
@Composable
fun CustomBottomNavigationPreview() {
        CustomBottomNavigation(
            selectedRoute = HomeRoutes.HomeRoute.route,
            onItemSelected = {}
        )

}

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

    // Get the index of the selected route
    val selectedIndex = items.indexOfFirst { it.route == selectedRoute }

    val navShape= RoundedCornerShape(topStart = 24.dp,topEnd = 24.dp)

    BoxWithConstraints {
        val maxWidth = maxWidth
        val itemWidth = maxWidth / items.size // Calculate item width

        // Animate the offset of the indicator based on the selected index
        val indicatorOffset by animateDpAsState(
            targetValue = (selectedIndex * itemWidth.value + (itemWidth.value / 2) - 25).dp
        )
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

                    // Indicator at the top

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
        //ths boc is indicator
        Box(
            modifier = Modifier
                .size(50.dp, 3.dp)
                .offset(
                    x =indicatorOffset.coerceIn(0.dp, maxWidth - 50.dp), // Center the indicator and ensure it stays within bounds
                    y = 10.dp
                )
                .clip(RoundedCornerShape(10))
                .background(blue)
        )
    }
}
