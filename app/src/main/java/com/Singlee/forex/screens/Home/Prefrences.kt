package com.Singlee.forex.screens.Home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.Singlee.forex.DataModels.SettingData
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.screens.Home.ViewModels.UserViewModel
import com.Singlee.forex.ui.theme.button_blue
import com.Singlee.forex.ui.theme.duble_extra_light
import com.Singlee.forex.ui.theme.extra_light
import com.Singlee.forex.ui.theme.titleColor


@Composable
fun Prefrences(userViewModel: UserViewModel, navController: NavHostController)
{
    val settingUpdate by userViewModel.userDataUpdate.collectAsState(initial = Response.Empty)
    var remderUi by remember {
        mutableStateOf(false)
    }
    userViewModel.getSettingData()

    val data = remember {mutableStateOf<SettingData>(SettingData())}

    LaunchedEffect(Unit)
    {
        data.value = userViewModel.userSetting()
        remderUi = true
        Log.d("get",data.value.toString())
    }

    LaunchedEffect(settingUpdate) {
        when(settingUpdate){
            Response.Empty -> {
            }
            is Response.Error ->{
                Log.d("updateSetting", (settingUpdate as Response.Error).message)
            }
            Response.Loading -> {
            }
            is Response.Success -> {
                Log.d("updateSetting","success")
            }
        }
    }



    // Reusable function to handle switch changes
    val onSwitchChange: (SettingData, (SettingData) -> SettingData) -> Unit = { setting, update ->
        val updatedSetting = update(setting)
        data.value = updatedSetting
        userViewModel.updateSettingData(updatedSetting)
    }

    Column(Modifier.padding(horizontal = 20.dp , vertical = 25.dp)) {

        ProfileToolbar("Preferences") {navController.popBackStack()}

        Text(
            text = "Notifications",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 20.dp),
            color = titleColor
        )
        if (remderUi) {
            itemBox(
                "New Signal",
                "Enable to see new signal notifications",
                data.value.signal
            ) { res ->
                onSwitchChange(data.value) { it.copy(signal = res) }
            }
            itemBox(
                "Chat Notification",
                "Enable to see chat messages notifications",
                data.value.chat_Notifications
            ) { res ->
                onSwitchChange(data.value) { it.copy(chat_Notifications = res) }
            }
            itemBox(
                "Support Notification",
                "Enable to see support notifications",
                data.value.support_Notifications
            ) { res ->
                onSwitchChange(data.value) { it.copy(support_Notifications = res) }
            }
            itemBox(
                "Progress Notification",
                "Enable to see progress notifications",
                data.value.progress_Notifications
            ) { res ->
                onSwitchChange(data.value) { it.copy(progress_Notifications = res) }
            }
            itemBox(
                "Offers Notification",
                "Enable to see offers notifications",
                data.value.offer_Notifications
            ) { res ->
                onSwitchChange(data.value) { it.copy(offer_Notifications = res) }
            }
            itemBox(
                "Team Messages",
                "Enable to see team messages notifications",
                data.value.team_Notifications
            ) { res ->
                onSwitchChange(data.value) { it.copy(team_Notifications = res) }
            }
        }
    }
}


@Composable
fun itemBox(title:String , des : String , isEnabled : Boolean , onActive :(Boolean)-> Unit)
{
    Spacer(modifier = Modifier.height(10.dp))
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(height = 70.dp)
        .background(
            duble_extra_light,
            RoundedCornerShape(15.dp)
        ),
        contentAlignment = Alignment.CenterStart
    )
    {
        Row(verticalAlignment = Alignment.CenterVertically  , modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                )
                Text(
                    text = des,
                    style = MaterialTheme.typography.displayMedium,
                    color = titleColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
            Box(modifier = Modifier.fillMaxHeight() , contentAlignment = Alignment.Center)
            {
                CustomSwitch(enable = isEnabled) {onActive(it)}
            }
        }
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
            .size(
                width = 50.dp,
                height = 20.dp
            )
            .clickable {
                isEnable = !isEnable
                onCheckedChanged(isEnable)
            })
    {
        Box(modifier = Modifier.background(if (!isEnable) extra_light else button_blue) , contentAlignment = Alignment.Center)
        {
            if (isEnable)
            {
                Row (
                    Modifier
                        .padding(3.dp)
                        .fillMaxSize() , horizontalArrangement = Arrangement.End){
                    Box(modifier = Modifier
                        .size(15.dp)
                        .background(titleColor, shape = CircleShape))
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
                        .background(titleColor, shape = CircleShape))
                }
            }
        }
    }
}