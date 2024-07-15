package com.foof.signalprovider.screens

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.foof.signalprovider.R
import com.foof.signalprovider.graph.Nav
import com.foof.signalprovider.screens.Auth.signUpScreen
import com.foof.signalprovider.ui.theme.SignalProviderTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                        Nav()
                    }
                }
            }
        }
    }
}




@Composable fun app()
{
    
}


