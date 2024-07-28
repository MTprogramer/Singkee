package com.Singlee.forex.screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.Singlee.forex.R
import com.Singlee.forex.ui.theme.duble_extra_light
import com.Singlee.forex.ui.theme.extra_light
import com.Singlee.forex.ui.theme.red
import com.Singlee.forex.ui.theme.signalType
import com.Singlee.forex.ui.theme.titleColor

@Composable
fun ProfileScreen(navController: NavHostController)
{
    Column(Modifier.padding(horizontal = 20.dp , vertical = 25.dp))
    {
        logput()
        AvtarDesign()
        Spacer(modifier = Modifier.height(5.dp))
        itemBox(title = "My Profile", dis = "Your profile and personal information", icon =R.drawable.profile_icon) {}
        itemBox(title = "Preferences", dis = "Settings and configurations", icon =R.drawable.prefrences_icon,){}
        itemBox(title = "Privacy policy", dis = "Read Our Terms Privacy Policy", icon =R.drawable.privacy_policy_icon,){}
        itemBox(title = "About", dis = "About “Singlee App”", icon =R.drawable.about_icon,){}
        itemBox(title = "Contact us", dis = "If you have any query contact “Singlee”", icon =R.drawable.contectus_icon,){}

    }
}



@Composable
fun logput()
{
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopEnd)
    {
        Button(onClick = {})
        {
            Text(
                text = "Logout",
                style = MaterialTheme.typography.displayMedium,
                color = red,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}

@Composable
fun AvtarDesign()
{
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.fake_avtar),
                contentDescription = null ,
                Modifier
                    .size(108.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop)

            Spacer(modifier = Modifier.width(15.dp))

            Box(
                modifier = Modifier
                    .clickable { }
                    .size(52.dp)
                    .background(extra_light, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.camera_icon),
                    contentDescription = null ,
                    Modifier
                        .size(26.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }

        }

        Text(
            text = "Hello, Johnson",
            style = signalType,
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 20.dp)
        )

        Text(
            text = "Manage your account & Preferences",
            style = MaterialTheme.typography.displayMedium,
            color = titleColor,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}


@Composable
fun itemBox(title: String, dis: String, icon: Int, function: () -> Unit)
{
    Spacer(modifier = Modifier.height(10.dp))
    Box(modifier = Modifier
        .clickable { function }
        .fillMaxWidth()
        .height(height = 72.dp)
        .background(
            duble_extra_light,
            RoundedCornerShape(15.dp)
        ),
        contentAlignment = Alignment.CenterStart
    )
    {
        Row(verticalAlignment = Alignment.CenterVertically  , modifier = Modifier.padding(horizontal = 10.dp)) {
            Box(modifier = Modifier
                .size(44.dp)
                .background(extra_light, RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            )
            {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null ,
                    Modifier
                        .size(21.dp),
                    contentScale = ContentScale.FillBounds)
            }

            Column(Modifier.padding(start = 10.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                )
                Text(
                    text = dis,
                    style = MaterialTheme.typography.displayMedium,
                    color = titleColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}