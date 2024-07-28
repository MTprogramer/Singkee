    package com.Singlee.forex.screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.Singlee.forex.R
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.editextbg
import com.Singlee.forex.ui.theme.focusEditextbg
import com.Singlee.forex.ui.theme.sans
import com.Singlee.forex.ui.theme.titleColor

@Composable
fun ChatScreen(navController: NavHostController)
{

    Column(Modifier.padding(horizontal = 20.dp , vertical = 25.dp)) {
        toolbar()

        replyFeild()
    }


}

    @Composable
    fun replyFeild() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            val value = remember { mutableStateOf("") }

           Row(modifier = Modifier
               .align(Alignment.BottomCenter),
               verticalAlignment = Alignment.CenterVertically
           )
           {
               Box(modifier = Modifier
                   .weight(1f)
                   .size(50.dp)
                   .padding(end = 20.dp))
               {
                   TextField(
                       value = value.value,
                       onValueChange = { value.value = it },
                       textStyle = MaterialTheme.typography.bodySmall,
                       placeholder = { Text(text = "Type a message..." , style = MaterialTheme.typography.labelSmall) },
                       shape = CircleShape,
                       modifier = Modifier
                           .fillMaxSize()
                           .border(1.dp, titleColor, CircleShape)
                           .clip(CircleShape),
                       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text , imeAction = ImeAction.Done),
                       colors = TextFieldDefaults.colors(
                           cursorColor = titleColor,
                           focusedContainerColor = focusEditextbg,
                           unfocusedContainerColor = editextbg,
                           focusedIndicatorColor = Color.Transparent,
                           unfocusedIndicatorColor = Color.Transparent,
                       ),
                   )
               }

               Box(
                   modifier = Modifier
                       .size(50.dp)
                       .background(blue, CircleShape),
                   contentAlignment = Alignment.Center
               )
               {
                   Image(
                       painter = painterResource(id = R.drawable.send_icon),
                       contentDescription = null,
                       Modifier.size(19.dp)
                   )
               }

           }
        }
    }

    @Composable
@Preview
fun toolbar()
{
    Row(horizontalArrangement = Arrangement.SpaceBetween)
    {
        toolbarButton(
            Modifier.weight(.5f),
            Alignment.CenterStart,
            image = R.drawable.back_icon,
            function = {})
        title(Modifier.weight(1f))
    }
}

@Composable
@Preview
fun senmessage()
{
    Row(horizontalArrangement = Arrangement.SpaceBetween)
    {
        toolbarButton(
            Modifier.weight(.5f),
            Alignment.CenterStart,
            image = R.drawable.back_icon,
            function = {})
        title(Modifier.weight(1f))
    }
}

@Composable
fun title(weight: Modifier)
{
    Row(
        modifier = weight,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            Modifier
                .background(Color.White, CircleShape)
                .size(45.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.char_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp),
                contentScale = ContentScale.FillWidth
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 10.dp)
        )
        {
            Text(
                text = "Chat Box",
                color = Color.White,
                fontFamily = sans,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(Color.Green, CircleShape)
                        .size(6.dp)
                )
                Text(
                    text = "Always active",
                    color = titleColor,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 5.dp , top = 5.dp)
                )
            }
        }
    }
}