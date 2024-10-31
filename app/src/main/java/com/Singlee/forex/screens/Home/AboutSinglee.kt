package com.Singlee.forex.screens.Home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.Singlee.forex.R
import com.Singlee.forex.ui.theme.mediumTitle


@Composable
fun AboutSinglee(navController: NavHostController)
{
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 25.dp))
    {
        Spacer(modifier = Modifier.height(20.dp))
        ProfileToolbar(title = "About Singlee") {navController.popBackStack()}
        text(id = R.string.aboutSinglee)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Our Mission",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 20.sp
        )
        text(id = R.string.ourMission)

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Why Singlee?",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 20.sp
        )
        text(id = R.string.point_1)
        text(id = R.string.point_2)
        text(id = R.string.point_3)
        text(id = R.string.point_4)

        Spacer(modifier = Modifier.height(10.dp))
        text(id = R.string.last_text)

    }
}

@Composable
fun text(id : Int)
{
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = stringResource(id = id),
        style = MaterialTheme.typography.displayMedium,
        color = mediumTitle

    )
}
