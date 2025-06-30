package com.example.aiqraft10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.aiqraft10.navigation.AppNavigation
import com.example.aiqraft10.ui.theme.AIQraft10Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIQraft10Theme {
                AppNavigation()
            }
        }
    }
}
