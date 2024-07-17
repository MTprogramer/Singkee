package com.Singlee.forex.screens.Auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.Singlee.forex.Utils.OTP
import com.Singlee.forex.graph.AuthRouts
import com.Singlee.forex.ui.theme.editextbg
import com.Singlee.forex.ui.theme.errorStyle
import com.Singlee.forex.ui.theme.light_blue
import com.Singlee.forex.ui.theme.mediumHint
import com.github.ehsannarmani.otp.ui.Otp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun otpScreem(navController: NavHostController, email: String?, password: String?)
{
    val otp = remember{ mutableStateOf("") }
    val otpError = remember{ mutableStateOf("Otp is not valid") }
    val countDownStateString = remember { mutableStateOf("") }
    val resamdState = remember { mutableStateOf(false) }
    val verifyState = remember { mutableStateOf(false) }
    val isCountLunch = remember { mutableStateOf(true) }


    val otpValidation = remember { mutableStateOf(false) }
    val otpExpiration = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }

    if (isCountLunch.value)
        startCount(countDownStateString , resamdState , isCountLunch , otpExpiration)


    Box(modifier = Modifier.padding(start = 20.dp , end = 20.dp))
    {
        Column(
            Modifier.verticalScroll(rememberScrollState()))
        {
            header("Verify your Phone Number","A 6 digit OTP code has been sent to " , false)

            HighlightedText(
                text = "$email \nenter the code to continue.",
                selectedTexts = listOf(
                    "$email"  to light_blue,
                ),
                style = MaterialTheme.typography.titleMedium
            )
            otpBox(
                otpValue = otp ,
                modifier = Modifier
                    .size(55.dp, 90.dp)
                    .background(editextbg),
                type = "Enter OTP",
                verifyState = verifyState,
                onError = otpValidation.value,
                info = otpError.value
            )
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, bottom = 20.dp), horizontalAlignment = Alignment.CenterHorizontally )
            {
                Spacer(modifier = Modifier.height(40.dp))
                clickAbleText(
                    space =0,
                    nonClickable ="Resend code in ",
                    clickable = "${countDownStateString.value}  ",
                    alignment = Arrangement.Center ,
                    enabled = resamdState.value,
                    nonClickableStyle = MaterialTheme.typography.titleMedium) {
                    isCountLunch.value = true
                    OTP.otpEmail(email!!)
                }


                Spacer(modifier = Modifier.height(40.dp))
                button(title = "Verify" , enabled = verifyState.value , isLoading = isLoading.value)
                {
                    if (otpExpiration.value)
                    {
                        otpError.value = "Your Otp Expired"
                        otpValidation.value = true
                    }
                    else {
                        if (otp.value == OTP.currentOtp)
                            navController.navigate("${ AuthRouts.NewPassRoute.route }/$email/$password")
                        else {
                            otpError.value = "Otp is not valid"
                            otpValidation.value = true
                        }
                    }

                }

                Spacer(modifier = Modifier.height(40.dp))
                clickAbleText(
                    space = 0,
                    nonClickable ="Back to " ,
                    clickable = "Login",
                    alignment = Arrangement.Center ,
                    enabled = true,
                    nonClickableStyle = mediumHint) {
                    navController.popBackStack(AuthRouts.LoginRoute.route, inclusive = false) }
                }
            }
        }
}



@Composable
private fun otpBox(
    otpValue: MutableState<String>,
    modifier: Modifier,
    type: String,
    verifyState: MutableState<Boolean>,
    onError: Boolean , info:String
) {
    textView(
        text = type,
        fontStyle = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(0.dp, 25.dp, 0.dp, 8.dp)
    )

    Otp(
        count = 6,
        error = false,
        success = true,
        successColor = editextbg,
        focusedColor = Color.Gray,
        unFocusedColor = Color.Gray,
        onOtpChange = {value, finished ->  if(!finished) verifyState.value = false},
        onFinish = { otp->
            Log.d("otp",otp)
            otpValue.value = otp
            verifyState.value = true

        },
        modifier= modifier
    )
    if (onError)
    {
        Text(
            text = info,
            modifier = Modifier.padding(top = 5.dp),
            style = errorStyle
        )
    }
}

@Composable
fun startCount(
    countDownStateString: MutableState<String>,
    buttonState: MutableState<Boolean>,
    isCountLunch: MutableState<Boolean>,
    otpExpiration: MutableState<Boolean>
)
{
    val countDownState = remember { mutableStateOf(60) } // 1 minute = 60 seconds
    LaunchedEffect(Unit) {
        launch {
            buttonState.value = false
            otpExpiration.value = false
            while (countDownState.value > 0) {
                countDownStateString.value = countDownState.value.toString() + " sec"
                delay(1000) // wait for 1 second
                countDownState.value -= 1
            }
            otpExpiration.value = true
            buttonState.value = true
            countDownStateString.value = "Resend"
            countDownState.value = 60
            isCountLunch.value = false

        }
    }
}







