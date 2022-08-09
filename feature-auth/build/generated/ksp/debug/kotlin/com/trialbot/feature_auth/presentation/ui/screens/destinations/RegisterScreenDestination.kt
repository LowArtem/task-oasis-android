package com.trialbot.feature_auth.presentation.ui.screens.destinations

import androidx.annotation.RestrictTo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import com.ramcosta.composedestinations.navigation.DestinationDependenciesContainer
import com.ramcosta.composedestinations.scope.DestinationScope
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import com.trialbot.feature_auth.presentation.ui.screens.RegisterScreen

object RegisterScreenDestination : AuthDirectionDestination {
         
    operator fun invoke() = this
    
    @get:RestrictTo(RestrictTo.Scope.SUBCLASSES)
    override val baseRoute = "register_screen"

    override val route = baseRoute
    
    @Composable
    override fun DestinationScope<Unit>.Content(
		dependenciesContainerBuilder: @Composable DependenciesContainerBuilder<Unit>.() -> Unit
    ) {
		val dependencyContainer = remember { DestinationDependenciesContainer(this) }
		dependencyContainer.apply { dependenciesContainerBuilder() }

		RegisterScreen(
			navigator = dependencyContainer.require()
		)
    }
    
}