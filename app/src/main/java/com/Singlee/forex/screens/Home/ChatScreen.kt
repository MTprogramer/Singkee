    package com.Singlee.forex.screens.Home

import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Singlee.forex.DataModels.Message
import com.Singlee.forex.R
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Utils.Constant
import com.Singlee.forex.screens.Home.ViewModels.ChatViewModel
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.duble_extra_light
import com.Singlee.forex.ui.theme.editextbg
import com.Singlee.forex.ui.theme.focusEditextbg
import com.Singlee.forex.ui.theme.sans
import com.Singlee.forex.ui.theme.statusBar
import com.Singlee.forex.ui.theme.titleColor
import com.Singlee.forex.ui.theme.white
import kotlinx.coroutines.delay
import java.util.UUID


    @Composable
fun ChatScreen(messageViewModel: ChatViewModel)
{
    val messagesState by messageViewModel.messages.collectAsState(initial = Response.Empty)
    val update by messageViewModel.update.collectAsState(initial = Response.Empty)
    val messages = remember { mutableStateListOf<Message>()}
    val listState = rememberLazyListState()
    LaunchedEffect(Unit)
    {
        messageViewModel.getMessages()
    }

    // Update the scroll when new messages are added
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    LaunchedEffect(messagesState) {
        when (messagesState) {
            Response.Empty -> {
            }
            is Response.Error -> {
                Log.e("ChatScreen", "Error fetching messages: ${(messagesState as Response.Error).message}")
                // Handle error state (optional)
            }
            Response.Loading -> {
                Log.d("ChatScreen", "Loading messages...")
                // Handle loading state (optional)
            }
            is Response.Success -> {
                val newMessages = (messagesState as Response.Success).data

                messages.clear()
                messages.addAll(newMessages)

            }
        }
    }
    LaunchedEffect(update) {
        when (update) {
            Response.Empty -> {
            }
            is Response.Error -> {
                Log.e("ChatScreen", "Error fetching messages: ${(update as Response.Error).message}")
                // Handle error state (optional)
            }
            Response.Loading -> {
                Log.d("ChatScreen", "Loading messages...")
                // Handle loading state (optional)
            }
            is Response.Success -> {
//                messages.clear()
                Log.d("ChatScreen", "Messages upload successfully: $messages")
                // Handle successful state (optional)
            }
        }
    }

  Box(Modifier.padding(start = 20.dp , end = 20.dp , top = 20.dp , bottom = 15.dp)){
      Column() {
          ChatToolbar()


          Spacer(modifier = Modifier.height(20.dp))

          LazyColumn (
              Modifier
                  .padding(bottom = 75.dp)
                  .weight(1f)
                  .imePadding() , state = listState){
              itemsIndexed(messages) { index, message ->
                  if (message.senderId != Constant.userID) {
                      // Handle messages from the opposite sender
                      if (index != 0) {
                          // Check if the previous message is different
                          if (messages[index - 1].senderId != message.senderId) {
                              oppositeReply(isShowProfile = true, message = message)
                          } else {
                              oppositeReply(isShowProfile = false, message = message)
                          }
                      } else {
                          oppositeReply(isShowProfile = true, message = message) // First message
                      }
                  } else {
                      // Handle messages from the current user
                      if (index < messages.size - 1) {
                          // Check if the next message is different
                          if (messages[index + 1].senderId != message.senderId) {
                              ReplyMessage( false, message = message)
                          } else {
                              ReplyMessage(true, message = message)
                          }
                      } else {
                          ReplyMessage(false, message = message) // Last message
                      }
                  }
              }
          }
      }

      replyFeild()
      {
          messages.add(it)
          messageViewModel.sendMessage(it)
      }
  }


}

    @Composable
 fun replyFeild(onReply : (Message)-> Unit)
 {

     val keyboardController = LocalSoftwareKeyboardController.current
     val focusManager = LocalFocusManager.current // Get the focus manager

     var isSending by remember { mutableStateOf(false) }

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
                       .clickable (enabled = !isSending){
                           if (value.value.isNotEmpty()) {
                               isSending = true // Start sending
                               val messageId = UUID
                                   .randomUUID()
                                   .toString()
                               onReply(
                                   Message(
                                       senderId = Constant.userID ?: "",
                                       content = value.value,
                                       messageId = messageId
                                   )
                               )
                               value.value = ""
                               focusManager.clearFocus()
                               keyboardController?.hide()  // Hide the keyboard


                           }

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
     if (isSending) {
         LaunchedEffect(Unit) {
             delay(200) // Adjust the delay as necessary
             isSending = false
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
    val radius = if (isShowProfile) 0f else 30f
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
                    topEnd = 30f,
                    bottomStart = 30f,
                    bottomEnd = 30f,
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
    val radius = if(!isLast) 0f else 30f

     Box(contentAlignment = Alignment.CenterEnd , modifier = Modifier
         .fillMaxWidth()
         .padding(start = 20.dp))
    {
         Row {
             if (!message.isSent){
             CircularProgressIndicator(
                 modifier = Modifier
                     .size(24.dp) // Adjust size as needed
                     .align(Alignment.CenterVertically),
                 color = Color.Gray
             )
             }
             Box(
                 modifier = Modifier
                     .background(
                         blue,
                         RoundedCornerShape(
                             topStart = 30f,
                             topEnd = 30f,
                             bottomStart = 30f,
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
                     modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
                 )
             }
         }
    }
}