package com.Singlee.forex.screens.Auth


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.navigation.NavController
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Utils.Constant
import com.Singlee.forex.Utils.hasCapitalLetter
import com.Singlee.forex.Utils.hasNumber
import com.Singlee.forex.Utils.hasSpecialCharacter
import com.Singlee.forex.Utils.isValidEmail
import com.Singlee.forex.graph.AuthRouts
import com.Singlee.forex.screens.MainActivity
import com.Singlee.forex.ui.theme.mediumHint
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import kotlinx.coroutines.launch

@Composable
fun signUpScreen(navController: NavController, authViewModel: AuthViewModel)
{

    val email = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPass = remember { mutableStateOf("") }
    val error = remember { mutableStateOf("Password dose not match") }


    val emailValidation = remember { mutableStateOf(false)}
    val passValidation = remember { mutableStateOf(false)}
    val passMatch = remember { mutableStateOf(false)}
    val btnState = remember { mutableStateOf(false)}
    val isLoading = remember { mutableStateOf(false)}


    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)
    val scope = rememberCoroutineScope()

    val loginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }


    DisposableEffect(Unit) {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                // do nothing
                Log.d("error","cencel")
            }

            override fun onError(error: FacebookException) {
                Log.d("error",error.message.toString())
            }

            override fun onSuccess(result: LoginResult) {

                scope.launch {
                    val token = result.accessToken.token
                    val credential = FacebookAuthProvider.getCredential(token)
                    authViewModel.googleSignIn(credential)
                }

            }
        })

        onDispose {
            loginManager.unregisterCallback(callbackManager)
        }
    }



    val result by authViewModel.registrationStatus.collectAsState(initial = Response.Empty)


    LaunchedEffect(result) {// Handling registration result
        when (result) {
            is Response.Loading -> {
                isLoading.value = true
                Log.d("status", "Losding")
            }
            is Response.Success -> {
                isLoading.value = false
                Log.d("status", "suceess + ${(result as Response.Success<AuthResult>).data.user?.email}")
            }
            is Response.Error -> {
                isLoading.value = false
                passMatch.value = true
                error.value = (result as Response.Error).message
                Log.d("status", (result as Response.Error).message)
            }
           is Response.Empty -> {}
        }
    }


    btnState.value = ((email.value.isNotEmpty() && name.value.isNotEmpty()) && (password.value.isNotEmpty() && confirmPass.value.isNotEmpty()))

    Box(modifier = Modifier.padding(start = 20.dp , end = 20.dp))
    {

        Column(
            Modifier
                .verticalScroll(rememberScrollState())) {
            header("Create your free account", "To get started with Treyd, create your account." , true)
            editextBox(name, false , Modifier.padding(0.dp, 25.dp, 0.dp, 8.dp) , "Name",false , "")
            editextBox(email, false , Modifier.padding(0.dp, 16.dp, 0.dp, 8.dp) , "Email" , emailValidation.value , "Email not valid")
            editextBox(password, true , Modifier.padding(0.dp, 16.dp, 0.dp, 8.dp) , "Password" , passValidation.value , "Password must contain one digit one number one capital letter and one spacial char")
            editextBox(confirmPass, true , Modifier.padding(0.dp, 16.dp, 0.dp, 8.dp) , "Confirm Password", passMatch.value,error.value)

            otherSignMethod(btnState.value , isLoading.value ,"Sign up",{


                authViewModel.signInWithFacebook(context as MainActivity)

            },{
                authViewModel.googleSignIn(credentialManager,context, Constant.clint_ID)
            },{
                emailValidation.value = !email.value.isValidEmail()
                passMatch.value = password.value != confirmPass.value
                passValidation.value = ((password.value.length >= 8 && hasNumber(password.value)) && (hasCapitalLetter(password.value) && hasSpecialCharacter(password.value))).not()

                if (!emailValidation.value && !passMatch.value && !passValidation.value){
                    authViewModel.signUp(email.value,password.value,name.value)
                }
            })

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, bottom = 20.dp), contentAlignment = Alignment.BottomCenter ) {
                clickAbleText(
                    space =0,
                    nonClickable ="Already have an account? " ,
                    clickable = "Login",
                    alignment = Arrangement.Center ,
                    enabled = true, nonClickableStyle = mediumHint) {navController.popBackStack(
                    AuthRouts.LoginRoute.route, inclusive = false) } }
            }
        }
    }




