package com.altankoc.bankapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.altankoc.bankapp.ui.component.NoInternetDialog
import com.altankoc.bankapp.ui.navigation.AppNavigation
import com.altankoc.bankapp.ui.theme.BankAppTheme
import com.altankoc.bankapp.ui.theme.DarkBlue
import com.altankoc.bankapp.ui.theme.MediumBlue
import com.altankoc.bankapp.ui.theme.OffWhite
import com.altankoc.bankapp.util.network.NetworkStatusChecker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val context = LocalContext.current
            val internetFlow = NetworkStatusChecker.networkChecker(context)
            val view = LocalView.current

            SideEffect {
                val activity = context as Activity
                val window = activity.window
                val insetsController = WindowCompat.getInsetsController(window, view)

                window.statusBarColor = DarkBlue.toArgb()
                insetsController.isAppearanceLightStatusBars = false

                window.navigationBarColor = OffWhite.toArgb()
                insetsController.isAppearanceLightNavigationBars = true
            }

            NoInternetDialog(internetFlow)

            BankAppTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    DarkBlue,
                                    MediumBlue,
                                    OffWhite
                                ),
                                startY = 0f,
                                endY = 800f
                            )
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.Transparent
                        ) {
                            val navController = rememberNavController()
                            AppNavigation(navController)
                        }
                    }
                }
            }
        }
    }
}