    package com.Singlee.forex.screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.Singlee.forex.DataModels.Message
import com.Singlee.forex.R
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.duble_extra_light
import com.Singlee.forex.ui.theme.editextbg
import com.Singlee.forex.ui.theme.extra_light
import com.Singlee.forex.ui.theme.focusEditextbg
import com.Singlee.forex.ui.theme.sans
import com.Singlee.forex.ui.theme.statusBar
import com.Singlee.forex.ui.theme.titleColor
import com.Singlee.forex.ui.theme.white
    
    

    val messages = mutableListOf(
        Message("1","hello " , "k",""),
        Message("1","kia hall chal ha " , "k",""),
        Message("1","ty ki kari ja ry o " , "k",""),
        Message("2","mai theek tu suna " , "k",""),
        Message("2","ki kar ria aj kal " , "k",""),
        Message("2","sab wadia " , "k","")
    )

    @Preview
    @Composable
fun ChatScreen()
{

  Box(Modifier.padding(start = 20.dp , end = 20.dp , top = 20.dp , bottom = 15.dp)){
      Column(Modifier.verticalScroll(rememberScrollState())) {
          ChatToolbar()

          Spacer(modifier = Modifier.height(20.dp))

          messages.forEachIndexed{index, message ->
              if (message.id != "2")
              {
                  if (index != 0)
                  {
                      if ( messages[index - 1].id != message.id)
                          oppositeReply(true , message)
                      else
                          oppositeReply(isShowProfile = false, message = message )
                  }
                  else
                      oppositeReply(isShowProfile = true, message = message )
              }
              else
              {
                  if (index < messages.size-1)
                  {
                      if (messages[index + 1].id != message.id)
                          ReplyMessage(false , message)
                      else
                          ReplyMessage( true, message )
                  }
                  else
                      ReplyMessage( false, message )
              }
          }
      }
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
               .align(Alignment.BottomCenter)
               .background(statusBar)
               .padding(vertical = 5.dp),
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
                       .clickable {
                           if (value.value.isNotEmpty()) messages.add(
                               Message(
                                   "1",
                                   value.value
                               )
                           )
                       }
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
fun ChatToolbar()
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

@Composable
fun oppositeReply(isShowProfile : Boolean, message: Message)
{
    val radius = if (isShowProfile) 0f else 50f
    Spacer(modifier = Modifier.height(5.dp))

    Row {

        if (isShowProfile)
        {
            Image(
                painter = painterResource(id = R.drawable.fake_avtar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(33.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
        else
            Spacer(modifier = Modifier.width(43.dp))


        Box(modifier = Modifier
            .wrapContentWidth()
            .background(
                duble_extra_light,
                RoundedCornerShape(
                    topStart = radius,
                    topEnd = 50f,
                    bottomStart = 50f,
                    bottomEnd = 50f,
                )
            )
        )
        {
            Text(
                text = message.content,
                fontFamily = sans,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = white,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(horizontal = 10.dp , vertical = 15.dp)
            )
        }
    }
}

@Composable
fun ReplyMessage(isLast: Boolean, message: Message)
{
    Spacer(modifier = Modifier.height(5.dp))
    val radius = if(!isLast) 0f else 50f

     Box(contentAlignment = Alignment.CenterEnd , modifier = Modifier
         .fillMaxWidth()
         .padding(start = 20.dp))
    {
        Box(modifier = Modifier
            .background(
                blue,
                RoundedCornerShape(
                    topStart = 50f ,
                    topEnd = 50f,
                    bottomStart = 50f,
                    bottomEnd = radius,
                )
            )
        )
        {
            Text(
                text = message.content,
                fontFamily = sans,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = white,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(horizontal = 10.dp , vertical = 15.dp)
            )
        }
    }
}