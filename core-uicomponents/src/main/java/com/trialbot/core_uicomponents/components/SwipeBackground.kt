package com.trialbot.core_uicomponents.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.trialbot.core_designsystem.ui.TaskOasisIcons

/**
 * Background of the swiping element with an animated icon.
 *
 *
 * To animate the icon you need to make [degrees] animateFloatAsState.
 *
 * @param degrees icon rotating angle, that animating when element is swiped enough
 */
@Composable
fun SwipeBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.error)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = TaskOasisIcons.deleteIcon,
            contentDescription = "Delete element",
            tint = MaterialTheme.colors.onError,
            modifier = Modifier.rotate(degrees)
        )
    }
}