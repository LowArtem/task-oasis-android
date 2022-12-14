package com.trialbot.taskoasis.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme
import com.trialbot.feature_auth.presentation.ui.screens.AuthNavGraph
import com.trialbot.taskoasis.navigation.RootNavGraph
import com.trialbot.taskoasis.navigation.RootNavigator
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        setContent {
            TaskOasisTheme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colors.isLight
                val backgroundColor = MaterialTheme.colors.background
                val navController = rememberNavController()

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = backgroundColor,
                        darkIcons = useDarkIcons
                    )
                }

                DestinationsNavHost(
                    navController = navController,
                    navGraph = RootNavGraph,
                    startRoute = if (viewModel.isUserLoggedIn) MainNavGraph else AuthNavGraph,
                    dependenciesContainerBuilder = {
                        dependency(RootNavigator(destination, navController))
                    }
                )
            }
        }
    }
}