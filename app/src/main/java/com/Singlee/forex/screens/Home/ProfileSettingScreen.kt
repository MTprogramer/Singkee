package com.Singlee.forex.screens.Home

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
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.Singlee.forex.R
import com.Singlee.forex.screens.Auth.button
import com.Singlee.forex.ui.theme.duble_extra_light
import com.Singlee.forex.ui.theme.errorStyle
import com.Singlee.forex.ui.theme.titleColor

@Preview
@Composable
fun ProfileSettingScreen()
{

    val btnState = remember { mutableStateOf(false)}
    val isLoading = remember { mutableStateOf(false)}

    val emailValidation = remember { mutableStateOf(false)}
    val passwordValidation = remember { mutableStateOf(false)}

    val name = remember {mutableStateOf("")}
    val email = remember {mutableStateOf("")}
    val password = remember {mutableStateOf("")}

    Column(Modifier.padding(horizontal = 20.dp , vertical = 25.dp)) {
        ProfileToolbar("Profile Edit"){}
        Spacer(modifier = Modifier.height(20.dp))
        CircularImageView()

        Spacer(modifier = Modifier.height(30.dp))

        textField(false , "Name" , name , false ,"")
        textField(false , "Email" , email , emailValidation.value,"Email not valid")
        textField(true , "Password" , password , passwordValidation.value , "Password")

        Spacer(modifier = Modifier.height(30.dp))
        Box(modifier = Modifier.fillMaxWidth() , contentAlignment = Alignment.Center)
        {

            button(
                title = "Sign in" ,
                btnState.value ,
                button = {}  ,
                isLoading = isLoading.value)
        }


    }
}


@Composable
fun textField(isPassword: Boolean, title: String, value: MutableState<String> , onError: Boolean , info : String)
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