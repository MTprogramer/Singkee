package com.Singlee.forex.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.Singlee.forex.R
import com.Singlee.forex.Utils.SharedPrefs
import com.Singlee.forex.graph.Nav
import com.Singlee.forex.screens.Auth.AuthViewModel
import com.Singlee.forex.ui.theme.SignalProviderTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    val viewmodel : AuthViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        window.statusBarColor = resources.getColor(R.color.status)

        setContent {
            SignalProviderTheme {
                Scaffold (  modifier = Modifier.background(color = Color.Transparent)
                ){ innerPadding ->
                    Box (
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize(1f)
                            .paint(
                                painter = painterResource(id = R.drawable.bg),
                                contentScale = ContentScale.FillBounds
                            )){
                        Nav(sharedPrefs.getBoolean("LOGGED_IN"))
                    }
                }
            }
        }
    }
    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewmodel.callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}




