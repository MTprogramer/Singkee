package com.Singlee.forex.screens.Auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.credentials.CredentialManager
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.Singlee.forex.DataModels.UserData
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Utils.Constant
import com.Singlee.forex.Utils.isValidEmail
import com.Singlee.forex.graph.AuthRouts
import com.Singlee.forex.ui.theme.mediumHint

@Composable
fun loginScreen(
    navController: NavHostController,
    authViewModel : AuthViewModel = hiltViewModel()
)
{
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val error = remember { mutableStateOf("") }

    val emailValidation = remember { mutableStateOf(false)}
    val userValidation = remember { mutableStateOf(false)}
    val btnState = remember { mutableStateOf(false)}
    val isLoading = remember { mutableStateOf(false)}

    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)

    btnState.value = email.value.isNotEmpty() && password.value.isNotEmpty()

    val result by authViewModel.signInStatus.collectAsState(initial = Response.Empty)



    // Handling login result
    // Handling registration result
    LaunchedEffect(result) {
        when (result) {
            is Response.Loading -> {
                isLoading.value = true
                Log.d("status", "Loading")
            }
            is Response.Success -> {
                isLoading.value = false
                val user = (result as Response.Success<UserData>).data
                Log.d("status", "Success: ${user.email}")
            }
            is Response.Error -> {
                isLoading.value = false
                error.value = (result as Response.Error).message
                userValidation.value = true
                Log.d("error", (result as Response.Error).message)
            }
            Response.Empty -> {
                Log.d("status", "Empty response")
            }
        }
    }


    Box(modifier = Modifier.padding(horizontal = 20.dp).background(Color.Transparent))
    {

        Column(
            Modifier
                .background(Color.Transparent)
                .verticalScroll(rememberScrollState())) {
            header("Welcome Back \uD83E\uDD17  ","Login to your account." , true)
            editextBox(email, false , Modifier.padding(0.dp, 25.dp, 0.dp, 8.dp) , "Email" , emailValidation.value , "Email not valid" )
            editextBox(password, true , Modifier.padding(0.dp, 16.dp, 0.dp, 8.dp) , "Password", userValidation.value , error.value)
            clickAbleText(
                space = 16,
                nonClickable = "Forget Password" ,
                clickable = "Recover" , Arrangement.Absolute.Left ,
                enabled = true,
                nonClickableStyle = MaterialTheme.typography.displaySmall) {navController.navigate(AuthRouts.ForgetPassRoute.route)}

            otherSignMethod(btnState.value, isLoading.value,"Sign in",
                {

            },
                {
                    authViewModel.googleSignIn(credentialManager,context,Constant.clint_ID)
             },
                {
                emailValidation.value = !email.value.isValidEmail()
                if (!emailValidation.value){
                    authViewModel.signIn(email.value , password.value)
                }

            })

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp, top = 40.dp), contentAlignment = Alignment.BottomCenter ) {
                    clickAbleText(
                        space =0,
                        nonClickable ="Dont have an account? " ,
                        clickable = "Sign Up",
                        alignment = Arrangement.Center ,
                        enabled = true,
                        nonClickableStyle = mediumHint) {navController.navigate(AuthRouts.RegisterRoute.route)}
            }
        }
    }
}



