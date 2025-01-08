package com.Singlee.forex.screens.Home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import com.Singlee.forex.R
import com.Singlee.forex.ui.theme.extra_light
import com.Singlee.forex.ui.theme.hintColor
import com.Singlee.forex.ui.theme.sans
import com.Singlee.forex.ui.theme.signalType
import com.Singlee.forex.ui.theme.titleColor
import com.Singlee.forex.ui.theme.upgradeGradient
import kotlin.math.absoluteValue


// Define a provider class for SignalData
class point : PreviewParameterProvider<String> {
    override val values = sequenceOf(
        "30x Visibility"
    )
}

val list = listOf(
    "30x Visibility",
    "Weekly Spotlight",
    "Daily 10x Enhancement",
    "Unlock Potential",
    "Expand Reach",
    "Consistent Engagement"
)

@Composable
fun cardCarouselTransition(page: Int, pagerState: PagerState): Modifier {
    val pageOffset =
        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

    // Animate scale from 0.8 (min size) to 1.0 (normal size) when moving between pages
    val scale by animateFloatAsState(
        targetValue = if (pageOffset > 0.5f) 0.8f else 1f, // Shrink when offset > 0.5
        animationSpec = tween(durationMillis = 300), label = "" // Customize the animation duration
    )

    // Adjust transparency based on the scaling factor
    val alpha = if (pageOffset > 0.5f) 0.7f else 1f

    return Modifier.graphicsLayer {
        this.alpha = alpha
        scaleX = scale
        scaleY = scale
    }
}


@OptIn(ExperimentalFoundationApi::class)
fun Modifier.carouselTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
        val transformation =
            lerp(
                start = 0.9f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )
        alpha = transformation
        scaleY = transformation
    }


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun  BuildUserCardSlider(userData: List<PlanInfo>, points: List<List<String>>) {
    val pagerState = rememberPagerState(pageCount = { userData.size })
    Column {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 26.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) { page ->
            val user = userData[page] // Get the user data for the current page
            val points = points[page] // Get the user data for the current page

           Column {
               Card(
                   shape = RoundedCornerShape(20.dp),
                   elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(10.dp)
                       .carouselTransition(page, pagerState) // Apply the carousel transition modifier
//                       .then(cardCarouselTransition(page, pagerState))
               ) {
                   Column(
                       modifier = Modifier
                           .padding(30.dp)
                           .fillMaxWidth(),
                       horizontalAlignment = Alignment.CenterHorizontally,
                   ) {
                       Column {
                           Text(
                               text = "Boost Your Presence",
                               color = Color.White,
                               fontSize = 20.8.sp,
                               fontFamily = sans,
                               fontWeight = FontWeight.SemiBold
                           )
                           Text(
                               text = "Premium Membership Package",
                               color = Color.White,
                               style = MaterialTheme.typography.titleSmall,
                               fontWeight = FontWeight.SemiBold,
                               modifier = Modifier.padding(top = 4.dp)
                           )
                           Spacer(modifier = Modifier.height(8.dp))
                           Row (verticalAlignment = Alignment.Bottom){
                               Text(
                                   text = "$${user.price}",
                                   color = Color.White,
                                   fontSize = 43.8.sp,
                                   fontFamily = sans,
                                   fontWeight = FontWeight.SemiBold,
                               )
                               Text(
                                   text = "/package",
                                   color = Color.White,
                                   fontFamily = sans,
                                   fontSize = 12.sp,
                                   fontWeight = FontWeight.SemiBold,
                                   modifier = Modifier.padding(bottom = 5.dp , start = 5.dp)
                               )
                           }

                           Spacer(modifier = Modifier.height(20.dp))
                           Box(
                               modifier = Modifier
                                   .size(width = 220.dp, height = 1.dp)
                                   .background(titleColor)
                           )
                           Spacer(modifier = Modifier.height(20.dp))

                           repeat(points.size){
                               pointsDesign(value = points[it])
                           }

                           Spacer(modifier = Modifier.height(30.dp))
                            chosePlanBtn()

                       }
                       // Add other user data fields as needed
                   }
               }
           }
        }
        Spacer(modifier = Modifier.height(20.dp))
        // Add the circle indicator below the pager
        CircleIndicator(pagerState = pagerState, pageCount = userData.size)
    }
}

@Composable
fun PremiumPlan(navController: NavHostController) {
    // Sample user data
    val prices = listOf(
        PlanInfo(price = "24.99"),
        PlanInfo(price = "99.99"),
        PlanInfo(price = "199.99")
    )
    val points = listOf(
        list,
        list,
        list,
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier.fillMaxWidth()) {
        Row (horizontalArrangement = Arrangement.End , modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 25.dp)
            .fillMaxWidth()){
            toolbarButton( Modifier.weight(1f), Alignment.CenterStart,image = R.drawable.cross) {navController.popBackStack()}
        }
        InfoLayout()
        BuildUserCardSlider(userData = prices , points)
    }

}

data class PlanInfo(val price: String)



@Composable
fun toolbarButton(modifier: Modifier, alignment: Alignment, image: Int, function: () -> Unit, )
{
    Box(modifier = modifier , contentAlignment = alignment)
    {
        Button(
            onClick = { function.invoke()},
            modifier = Modifier
                .border(1.dp, color = hintColor, shape = CircleShape)
                .size(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(10.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = image),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                contentScale = ContentScale.Fit)

        }
    }
}
@Preview
@Composable
fun InfoLayout()
{
    Column(horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 40.dp)) {

        Row(horizontalArrangement = Arrangement.Start) {
            Image(
                painter = painterResource(id = R.drawable.dimond),
                contentDescription = "",
                Modifier
                    .size(width = 44.dp, height = 42.dp)
                    .padding(top = 5.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Plans",
                color = Color.White,
                fontSize = 28.sp,
                fontFamily = sans,
                fontWeight = FontWeight.SemiBold

            )
        }

        Text(
            text = "Subscribe a plan".uppercase(),
            style = signalType,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 13.dp),
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircleIndicator(pagerState: PagerState, pageCount: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        repeat(pageCount) { page ->
            val color = if (pagerState.currentPage == page) {
                Brush.linearGradient(
                    upgradeGradient,
                    start = Offset(30f, -50f),
                    end = Offset(70.5f, 120f)
                )
            } else
            {
                Brush.linearGradient(
                    listOf(extra_light, extra_light),
                    start = Offset(30f, -50f),
                    end = Offset(70.5f, 120f)
                )
            }

            Box(
                modifier = Modifier
                    .size(14.dp) // Increase the size for better visibility
                    .background(color, shape = CircleShape)
            )
        }
    }
}

@Preview(showBackground = true , backgroundColor = 0xFF15191F)
@Composable
fun pointsDesign(@PreviewParameter(point::class) value : String)
{

    Row (
        Modifier
            .background(Color.Transparent)
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically){
        Box(
            modifier = Modifier
                .background(Color.White, CircleShape)
                .size(6.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(start = 20.dp)
        )
    }

}

@Composable
fun chosePlanBtn()
{
    Box(
        Modifier
            .size(width = 220.dp, height = 47.dp)
            .background(
                brush = Brush.linearGradient(
                    upgradeGradient,
                    start = Offset(80f, -50f),
                    end = Offset(80.5f, 150f)
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { },
        contentAlignment = Alignment.Center
    )
    {
        Text(
            text = "Choose Plan",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )

    }
}