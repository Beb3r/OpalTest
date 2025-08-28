package com.gb.opaltest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gb.opaltest.core.navigation.api.NavControllerAccessor
import com.gb.opaltest.presentation.MainApp
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navControllerAccessor = get<NavControllerAccessor>()
            MainApp(navControllerAccessor = navControllerAccessor)
        }
    }
}
