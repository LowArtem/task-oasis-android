package com.trialbot.feature_auth.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.trialbot.core_designsystem.ui.theme.poppins

@Composable
fun TextWithLink(
    modifier: Modifier = Modifier,
    mainText: String,
    linkText: String,
    onLinkClicked: () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = mainText,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onPrimary
        )

        Text(
            modifier = Modifier.clickable {
                onLinkClicked()
            },
            text = AnnotatedString(text = linkText),
            style = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = MaterialTheme.colors.primary,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}