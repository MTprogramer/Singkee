package com.Singlee.forex.screens.Home.nav

import androidx.collection.emptyLongSet
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.button_blue
import com.Singlee.forex.ui.theme.duble_extra_light
import com.Singlee.forex.ui.theme.extra_light
import com.Singlee.forex.ui.theme.light_blue
import com.Singlee.forex.ui.theme.red
import com.Singlee.forex.ui.theme.titleColor


@Preview
@Composable
fun Prefrences()
{
    Column {
        CustomSwitch(enable = true) {}
        Spacer(modifier = Modifier.height(10.dp))
        CustomSwitch(enable = false) {}
    }
}

@Composable
fun CustomSwitch(
    enable : Boolean,
    onCheckedChanged: (checked: Boolean) -> Unit
) {

    var isEnable by remember {mutableStateOf(enable)}

    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(30.dp) ,
        modifier = Modifier
            .size(width = 50.dp ,
                height = 20.dp)
            .clickable {
                isEnable = !isEnable
                onCheckedChanged(isEnable)
            })
    {
        Box(modifier = Modifier.background(if (!isEnable) duble_extra_light else button_blue) , contentAlignment = Alignment.Center)
        {
            if (isEnable)
            {
                Row (
                    Modifier
                        .padding(3.dp)
                        .fillMaxSize() , horizontalArrangement = Arrangement.End){
                    Box(modifier = Modifier
                        .size(15.dp)
                        .background(if (!isEnable) extra_light else titleColor, shape = CircleShape))
                }
            }
            else
            {
                Row (
                    Modifier
                        .padding(3.dp)
                        .fillMaxSize() , horizontalArrangement = Arrangement.Start){
                    Box(modifier = Modifier
                        .size(15.dp)
                        .background(if (!isEnable) extra_light else blue, shape = CircleShape))
                }
            }

        }
    }


}