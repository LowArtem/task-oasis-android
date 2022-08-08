package com.trialbot.core_designsystem.ui

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.trialbot.core_designsystem.R

/**
 * App icons access object
 */
object TaskOasisIcons {
    val visibilityOn = R.drawable.ic_visible
    val visibilityOff = R.drawable.ic_invisible
    val plus = Icons.Default.Add
    val burgerMenu = R.drawable.ic_burger_menu
    val moreDots = Icons.Default.MoreVert
    val home = Icons.Default.Home
    val bulletedList = R.drawable.ic_bulleted_list
    val habit = R.drawable.ic_calendar_repeat
    val settings = Icons.Default.Settings
    val circleFilled = R.drawable.ic_circle_filled
    val cancelCircle = R.drawable.ic_circle_cancel
    val checkCircle = Icons.Default.CheckCircle
    val repeatCircle = R.drawable.ic_circle_replay
    val circleTarget = R.drawable.ic_circle_dot
    val moreArrowDownExpanded = Icons.Default.ArrowDropDown
    val moreArrowRightCollapsed = R.drawable.ic_arrow_right
    val checkBoxUnchecked = R.drawable.ic_checkbox
    val checkBoxChecked = R.drawable.ic_checkbox_checked
    val alarm = R.drawable.ic_alarm
    val plusCircle = R.drawable.ic_circle_plus
    val minusCircle = R.drawable.ic_circle_minus
    val backArrowLeft = Icons.Default.ArrowBack
    val timeClock = R.drawable.ic_schedule_clock
    val calendar = R.drawable.ic_calendar
    val repeat = R.drawable.ic_repeat
    val priorityExclamation = R.drawable.ic_exclamation
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class TaskOasisIcon {
    data class ImageVectorIcon(val imageVector: ImageVector) : TaskOasisIcon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : TaskOasisIcon()
}