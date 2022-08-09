package com.trialbot.feature_auth.presentation.ui.screens.destinations

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.*

/**
 * Handy typealias of [AuthTypedDestination] when you don't
 * care about the generic type (probably most cases for app's use)
 */
typealias AuthDestination = AuthTypedDestination<*>

/**
 * AuthTypedDestination is a sealed version of [DestinationSpec]
 */
sealed interface AuthTypedDestination<T>: DestinationSpec<T>

/**
 * AuthDirectionDestination is a sealed version of [DirectionDestinationSpec]
 */
sealed interface AuthDirectionDestination: AuthTypedDestination<Unit>, DirectionDestinationSpec
