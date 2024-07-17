package com.Singlee.forex.screens.Auth



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Utils.isValidEmail
import com.Singlee.forex.graph.AuthRouts
import com.Singlee.forex.ui.theme.mediumHint

@Composable
fun forgetPasswordScreen(navController: NavHostController, authViewModel: AuthViewModel)
{
    val email = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("Email not valid") }

    val emailValidation = remember { mutableStateOf(false)}
    val btnState = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }


    btnState.value = email.value.isNotEmpty()


    val result by authViewModel.userExist.collectAsState(initial = Response.Empty)

    LaunchedEffect(result) {// Handling registration result
        when (result) {
            is Response.Loading -> {
                isLoading.value = true
            }

            is Response.Success -> {
                isLoading.value = false
            }
            is Response.Error -> {
                isLoading.value = false
                errorMessage.value = (result as Response.Error).message
                emailValidation.value = true
            }
            Response.Empty -> {}
        }
    }

    Box(modifier = Modifier.padding(start = 20.dp , end = 20.dp))
    {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())) {
            header("Forgot Password","Enter your email to continue" , false)
            editextBox(email, false , Modifier.padding(0.dp, 25.dp, 0.dp, 8.dp) , "Email Address" , emailValidation.value , errorMessage.value)
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, bottom = 20.dp), horizontalAlignment = Alignment.CenterHorizontally ) {
                button(title = "Get Code" , btnState.value , isLoading = isLoading.value)
                {
                    emailValidation.value = !email.value.isValidEmail()
                    errorMessage.value = "Email not valid"

                    if (!emailValidation.value)
                        authViewModel.userExist(email.value , navController)
                }
                Spacer(modifier = Modifier.height(40.dp))
                clickAbleText(
                    space =0, nonClickable ="Back to " ,
                    clickable = "Login", alignment = Arrangement.Center,
                    enabled = true, nonClickableStyle = mediumHint) {navController.popBackStack(AuthRouts.LoginRoute.route,false)}
            }
        }
    }
}




