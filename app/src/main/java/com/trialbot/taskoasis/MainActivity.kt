package com.trialbot.taskoasis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme
import com.trialbot.feature_auth.presentation.screens.FeatureauthNavGraph
import com.trialbot.taskoasis.navigation.RootNavGraph
import com.trialbot.taskoasis.navigation.RootNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskOasisTheme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colors.isLight
                val backgroundColor = MaterialTheme.colors.background

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = backgroundColor,
                        darkIcons = useDarkIcons
                    )
                }

                val navController = rememberNavController()
                DestinationsNavHost(
                    navController = navController,
                    navGraph = RootNavGraph,
                    startRoute = FeatureauthNavGraph,
                    dependenciesContainerBuilder = {
                        dependency(RootNavigator(destination, navController))
                    }
                )
            }
        }
    }
}