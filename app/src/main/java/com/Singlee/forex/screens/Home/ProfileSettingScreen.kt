package com.Singlee.forex.screens.Home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.Singlee.forex.DataModels.UserData
import com.Singlee.forex.R
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.screens.Auth.button
import com.Singlee.forex.screens.Home.ViewModels.UserViewModel
import com.Singlee.forex.ui.theme.duble_extra_light
import com.Singlee.forex.ui.theme.errorStyle
import com.Singlee.forex.ui.theme.titleColor

@Composable
fun ProfileSettingScreen(userViewModel: UserViewModel)
{

    val data by userViewModel.userDataresult.collectAsState(initial = Response.Empty)
    val update by userViewModel.userDataUpdate.collectAsState(initial = Response.Empty)
    val btnState = remember { mutableStateOf(false)}
    val isLoading = remember { mutableStateOf(false)}

    val emailValidation = remember { mutableStateOf(false)}
    val passwordValidation = remember { mutableStateOf(false)}
    val isThirdyParty = remember { mutableStateOf(false)}

    val name = remember {mutableStateOf("")}
    val email = remember {mutableStateOf("")}
    val password = remember {mutableStateOf("")}

    var user : UserData ? = null

    if (isThirdyParty.value)
        btnState.value = name.value.isNotEmpty() && user != null
    else
        btnState.value = name.value.isNotEmpty() && password.value.isNotEmpty() && user != null


    userViewModel.getUserData()


    LaunchedEffect(data) {
        when (data) {
            is Response.Loading -> { Log.d("status", "Loading") }
            is Response.Success -> {
                user = (data as Response.Success<UserData>).data
                user?.let {
                    name.value = it.name
                    email.value = it.email
                    password.value = it.password
                    Log.d("status", "Success: ${it.email}")
                }

            }
            is Response.Error -> { Log.d("error", (data as Response.Error).message) }
            Response.Empty -> { Log.d("status", "Empty response") }
        }
    }


    LaunchedEffect(update) {
        when (data) {
            is Response.Loading -> {
                isLoading.value = true
                Log.d("status", "Loading")
            }
            is Response.Success -> {
                isLoading.value = false
                Log.d("status", "Success: ${update}")
            }
            is Response.Error -> {
                isLoading.value = false
                Log.d("error", (data as Response.Error).message)
            }
            Response.Empty -> { Log.d("status", "Empty response") }
        }
    }


    Column(Modifier.padding(horizontal = 20.dp , vertical = 25.dp)) {
        ProfileToolbar("Profile Edit"){}
        Spacer(modifier = Modifier.height(20.dp))
        CircularImageView()

        Spacer(modifier = Modifier.height(30.dp))

        textField(false , "Name" , name , false ,"" , true)
        textField(false , "Email" , email , emailValidation.value,"Email not valid" , false)
        if (!isThirdyParty.value)
            textField(true , "Password" , password , passwordValidation.value , "Password" , true)

        Spacer(modifier = Modifier.height(30.dp))
        Box(modifier = Modifier.fillMaxWidth() , contentAlignment = Alignment.Center)
        {
            button(
                title = "Update" ,
                btnState.value ,
                button = {userViewModel.updateUserData(userData = user!!)}  ,
                isLoading = isLoading.value)
        }
    }
}


@Composable
fun textField(isPassword: Boolean, title: String, value: MutableState<String> , onError: Boolean , info : String , enabled : Boolean )
{

    val isShow = remember {
        mutableStateOf(true)
    }


    TextField(
        value = value.value,
        onValueChange = { newValue -> value.value = newValue },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(title) },
        colors = TextFieldDefaults.colors(unfocusedContainerColor = duble_extra_light , unfocusedLabelColor = titleColor),
        trailingIcon = {
            if (isPassword)
            {
                TextButton(onClick = { isShow.value = !isShow.value}) {
                    Text(
                        text = if (!isShow.value) "Hide" else "Show",
                        color = titleColor,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        enabled = enabled,
        visualTransformation = if (!isShow.value) VisualTransformation.None else PasswordVisualTransformation()
    )
    if (onError)
    {
        Text(
            text = info,
            modifier = Modifier.padding(start = 15.dp),
            style = errorStyle
        )
    }
}

@Composable
fun CircularImageView() {
    Box(contentAlignment = Alignment.Center , modifier = Modifier.fillMaxWidth())
    {
        Box(contentAlignment = Alignment.CenterEnd) {
            Image(
                painter = painterResource(id = R.drawable.fake_avtar),
                contentDescription = "Circular Image",
                modifier = Modifier
                    .size(100.dp)// Size of the image
                    .clip(CircleShape), // Clip the image to a circular shape
                contentScale = ContentScale.Crop // Crop the image if it's larger than the view
            )
            Image(
                painter = painterResource(id = R.drawable.edit_image_icon),
                contentDescription = "Circular Image",
                modifier = Modifier
                    .offset(x = (-10).dp, y = 35.dp)
                    .border(1.dp, titleColor, CircleShape),
                contentScale = ContentScale.Crop // Crop the image if it's larger than the view
            )
        }
    }
}

@Composable
fun ProfileToolbar(title: String, onBack: () -> Unit)
{
    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp) , contentAlignment = Alignment.Center)
    {
        toolbarButton(
            Modifier.align(Alignment.CenterStart),
            Alignment.CenterStart,
            image = R.drawable.back_icon,
            function = {onBack})
        Text(
            text = title,
            style =MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            textAlign = TextAlign.Center,
            color = titleColor
        )
    }
}