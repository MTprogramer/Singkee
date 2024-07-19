package com.Singlee.forex.screens.Auth

import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Singlee.forex.R
import com.Singlee.forex.ui.theme.authLogo
import com.Singlee.forex.ui.theme.button_blue
import com.Singlee.forex.ui.theme.duble_extra_light
import com.Singlee.forex.ui.theme.editextbg
import com.Singlee.forex.ui.theme.errorStyle
import com.Singlee.forex.ui.theme.extra_light
import com.Singlee.forex.ui.theme.focusEditextbg
import com.Singlee.forex.ui.theme.hintColor
import com.Singlee.forex.ui.theme.interFontFamily
import com.Singlee.forex.ui.theme.smalhint
import com.Singlee.forex.ui.theme.titleColor
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

@Composable
fun Toolbar(sreen_name_visibility : Boolean) {
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        Modifier
            .padding(0.dp, 20.dp, 0.dp, 40.dp)
            .fillMaxWidth()
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Singlee",
            style = authLogo
        )
       if (sreen_name_visibility)
       {
           Spacer(modifier = Modifier.weight(1f)) // Spacer to push the text to the right
           Text(text = "Sign in" ,
               fontSize = 16.sp ,
               fontFamily = interFontFamily ,
               fontWeight = FontWeight.SemiBold ,
               color = hintColor,
           )
       }
    }
}

@Composable
fun textView(text :String , fontStyle: TextStyle , modifier: Modifier)
{
    Text(text = text ,
        style = fontStyle,
        modifier = modifier
    )
}

@Composable
fun header(title : String , dis : String , screenVis : Boolean)
{
    Toolbar(screenVis)
    textView(text = title,
        fontStyle = MaterialTheme.typography.titleLarge ,
        modifier = Modifier.padding(0.dp) )
    textView(
        text = dis,
        fontStyle = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 12.dp)
    )
}


@Composable
fun editext(hint: String, value: MutableState<String> , is_password:Boolean)
{
    var is_show = remember {
        mutableStateOf(false)
    }

    TextField(
        value = value.value,
        onValueChange = { value.value = it },
        placeholder = { Text(text = hint , style = MaterialTheme.typography.labelSmall) },
        textStyle = MaterialTheme.typography.bodySmall,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email , imeAction = ImeAction.Done),
        colors = TextFieldDefaults.colors(
            cursorColor = titleColor,
            focusedContainerColor = focusEditextbg ,
            unfocusedContainerColor = editextbg,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        trailingIcon = {
            if (is_password)
            {
                TextButton(onClick = { is_show.value = !is_show.value}) {
                    Text(
                        text = if (is_show.value) "Hide" else "Show",
                        color = titleColor,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            else
                null
        },
        visualTransformation = if (!is_show.value) VisualTransformation.None else PasswordVisualTransformation()
        )

}

@Composable
fun clickAbleText(
    space: Int,
    nonClickable: String,
    clickable: String,
    alignment: Arrangement.Horizontal,
    nonClickableStyle : TextStyle,
    enabled : Boolean,
    onClick: () -> Unit
)
{
    Column()
    {
        Spacer(modifier = Modifier.height(space.dp))
        Row (horizontalArrangement = alignment){
            textView(text = nonClickable, fontStyle = nonClickableStyle , Modifier.padding(0.dp))
            textView(text = " $clickable", fontStyle = MaterialTheme.typography.displayLarge ,
                Modifier
                    .padding(0.dp)
                    .clickable(onClick = onClick, enabled = enabled))
        }
    }
}

@Composable
fun otherSignMethod(btnState:Boolean , isLoading: Boolean  , buttonTitle : String , facebook : ()-> Unit , google : () -> Unit , button :()-> Unit ){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
       Column (horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier.fillMaxSize()) {
           Spacer(modifier = Modifier.height(30.dp))
           Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier.fillMaxWidth()
           ) {
               HorizontalDivider(
                   modifier = Modifier
                       .weight(1f)
                       .padding(end = 10.dp),
                   thickness = 1.dp,
                   color = extra_light
               )
               Text(
                   text = "Or Register with",
                   modifier = Modifier.padding(horizontal = 10.dp),
                   style = smalhint
               )
               HorizontalDivider(
                   modifier = Modifier
                       .weight(1f)
                       .padding(start = 10.dp),
                   thickness = 1.dp,
                   color = extra_light
               )
           }

           Spacer(modifier = Modifier.height(30.dp))
           
           Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
              otherOtherButton(facebook , R.drawable.facebook_icon)
              otherOtherButton(google, R.drawable.google_icon)
           }
           
           Spacer(modifier = Modifier.height(30.dp))

           button(title = "Sign in" , btnState , button = button  , isLoading = isLoading)
       }
    }
}

@Composable
fun otherOtherButton(button: () -> Unit, image: Int)
{
    Box(
        modifier = Modifier
            .size(48.dp)
            .border(width = 1.dp, color = extra_light, shape = RoundedCornerShape(10.dp))
            .background(duble_extra_light, shape = RoundedCornerShape(10.dp))
            .clickable(onClick = button),
        contentAlignment = Alignment.Center

    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier = Modifier
                .size(20.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun button(title : String , enabled: Boolean,isLoading : Boolean ,button: () -> Unit)
{
    Button(onClick =  button ,
        colors = ButtonDefaults.buttonColors(
            containerColor = button_blue  ,
            contentColor = titleColor,
            disabledContainerColor = extra_light,
            disabledContentColor = titleColor
        ),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .height(58.dp)
            .width(226.dp)
        ) {

       when(isLoading)
       {
           false -> Text(text = title )
           true -> CircularProgressIndicator(color = Color.White , modifier = Modifier.size(30.dp))
       }
    }
}

@Composable
fun HighlightedText(text: String, selectedTexts: List<Pair<String, Color>> , style: TextStyle) {
    val annotatedString = buildAnnotatedString {
        append(text)
        selectedTexts.forEach { (selectedText, color) ->
            val startIndex = text.indexOf(selectedText)
            if (startIndex != -1) {
                addStyle(
                    style = SpanStyle(color = color),
                    start = startIndex,
                    end = startIndex + selectedText.length
                )
            }
        }
    }

    Text(
        text = annotatedString,
        modifier = Modifier,
        style = style
    )
}

@Composable
fun editextBox(
    state: MutableState<String>, is_password:Boolean , modifier: Modifier , type:String , onError: Boolean , info:String
) {
    textView(
        text = type,
        fontStyle = MaterialTheme.typography.titleSmall,
        modifier = modifier
    )
    editext(
        hint = "Enter your ${type.lowercase()}",
        state, is_password
    )
    if (onError)
    {
        Text(
            text = info,
            modifier = Modifier.padding(5.dp),
            style = errorStyle
        )
    }
}


@Composable
fun FullScreenProgressIndicator(show: Boolean) {
    if (show) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0x00000000)), // Semi-transparent background
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}


@Composable
fun FacebookButton(
    onAuthComplete: () -> Unit,
    onAuthError: (Exception) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val loginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val launcher = rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(callbackManager, null)) {
        // nothing to do. handled in FacebookCallback
    }

    DisposableEffect(Unit) {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                // do nothing
            }

            override fun onError(error: FacebookException) {
                onAuthError(error)
            }

            override fun onSuccess(result: LoginResult) {
                // user signed in successfully
                // TODO Forward to Firebase Auth
                // check next step in composables.com/blog/firebase-auth-facebook
            }
        })

        onDispose {
            loginManager.unregisterCallback(callbackManager)
        }
    }
    Button(
        modifier = modifier,
        onClick = {
            // start the sign-in flow
            launcher.launch(listOf("email", "public_profile"))
        }) {
        Text("Continue with Facebook")
    }
}



