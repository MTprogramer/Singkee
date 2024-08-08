package com.Singlee.forex.screens.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Singlee.forex.R
import com.Singlee.forex.ui.theme.mediumTitle
import com.Singlee.forex.ui.theme.titleColor


val infoList = listOf("Name" , "Email address" , "Phone number" , "Payment information")
val list_use_info = listOf(
    "To provide, maintain, and improve our app and services",
    "To personalize your experience and deliver content tailored to your interests" ,
    "To process payments and transactions " ,
    "To communicate with you, including sending notifications, updates, and promotional offers",
    "To monitor and analyze usage patterns and trends to improve our app",
    "To comply with legal obligations and protect our rights and the rights of others"
)
val shareInfoList = listOf(
    R.string.share1,
    R.string.share2,
    R.string.share3,
)
val choiceList = listOf(
    R.string.your1,
    R.string.your2,
    R.string.your3,
)

@Composable
fun PrivacyPolicy()
{
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 25.dp))
    {
        Spacer(modifier = Modifier.height(20.dp))
        ProfileToolbar(title = "Privacy Policy") {}

        //into
        Text(
            text = "Introduction",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 20.sp
        )
        text(id = R.string.intodiction)


        //Personal info
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Personal Information",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 20.sp
        )
        text(id = R.string.persoalInfo)
        infoList.forEach { pointText(it) }

        //Use Personal info
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Use Your Informatoin",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 20.sp
        )
        text(id = R.string.useInfo)
        list_use_info.forEach { pointText(it) }

        //share Personal info
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Share Information",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 20.sp
        )
        text(id = R.string.shareInfo)
        shareInfoList.forEach { text(it) }


        //share Personal info
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Security",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 20.sp
        )
        text(id = R.string.sicurity)




        //your choice
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Your choices",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 20.sp
        )
        text(id = R.string.yourChoie)
        choiceList.forEach { text(it) }


        //your choice
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Children Privacy",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 20.sp
        )
        text(id = R.string.childPrivacy)


        //your choice
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Contact Us",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 20.sp
        )
        text(id = R.string.contextUs)





    }
}

@Composable
fun pointText(value: String)
{
    Row (verticalAlignment = Alignment.CenterVertically)
    {
        Box (
            Modifier
                .padding(horizontal = 10.dp)
                .size(7.dp)
                .background(titleColor, CircleShape))
        Text(
            text = value,
            style = MaterialTheme.typography.displayMedium,
            color = mediumTitle
        )
    }
}