package com.foof.signalprovider.screens.Auth


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foof.signalprovider.Utils.hasCapitalLetter
import com.foof.signalprovider.Utils.hasNumber
import com.foof.signalprovider.Utils.hasSpecialCharacter
import com.foof.signalprovider.ui.theme.light_blue
import com.foof.signalprovider.ui.theme.smalhint
import com.foof.signalprovider.ui.theme.titleColor

@Composable
fun newPasScreen(navController: NavHostController)
{
    val newPassword = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val char8 = remember { mutableStateOf(false) }
    val oneCap = remember { mutableStateOf(false) }
    val oneNumber = remember { mutableStateOf(false) }
    val oneSpecial = remember { mutableStateOf(false) }

    val comfirmPassError = remember { mutableStateOf(false) }
    val verifyState = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }


    char8.value = newPassword.value.length >= 8
    oneCap.value = hasCapitalLetter(newPassword.value)
    oneNumber.value = hasNumber(newPassword.value)
    oneSpecial.value = hasSpecialCharacter(newPassword.value)

    verifyState.value = (char8.value && oneCap.value)&&(oneNumber.value && oneSpecial.value) && password.value.isNotEmpty()





    Box(modifier = Modifier.padding(horizontal = 20.dp))
    {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())) {
            header("Set a new Password","Set a new password for your account" , false)
            editextBox(newPassword, true , Modifier.padding(0.dp, 25.dp, 0.dp, 8.dp) , "New Password", false,"")

            HighlightedText(
                text = "Must be more than 8 characters and contain at least one capital letter, one number and one special character",
                selectedTexts = listOf(
                    "8 characters" to if (char8.value) light_blue else titleColor,
                    "one number" to if (oneNumber.value) light_blue else titleColor,
                    "one special character" to if (oneSpecial.value) light_blue else titleColor,
                    "one capital letter" to if (oneCap.value) light_blue else titleColor
                            ),
                style = smalhint
            )

            Spacer(modifier = Modifier.height(10.dp))

            editextBox(password, true , Modifier.padding(0.dp, 16.dp, 0.dp, 8.dp) , "Confirm Password" , comfirmPassError.value , "Password not match")

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, bottom = 20.dp), horizontalAlignment = Alignment.CenterHorizontally )
            {
                Spacer(modifier = Modifier.height(40.dp))
                button(title = "Save" , enabled = verifyState.value , isLoading = isLoading.value) {
                    comfirmPassError.value = newPassword.value != password.value
                    Log.d("vaaa",comfirmPassError.value.toString())

                    navController.navigate("login"){popUpTo("login"){inclusive = true} }

                }

            }
        }
    }
}


