package com.trialbot.feature_tasks.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme
import com.trialbot.core_designsystem.ui.theme.disabledColor
import com.trialbot.core_model.enum.Difficulty
import com.trialbot.core_model.enum.Priority
import com.trialbot.core_utils.toLocalDateTimeCurrentZone
import com.trialbot.core_utils.toStringFormatted
import com.trialbot.feature_tasks.data.model.TaskShortDto
import java.time.Instant

const val EXPANDING_TRANSITION_DURATION = 300

@Composable
fun TaskGroup(
    title: String,
    onExpandButtonClick: () -> Unit,
    itemsCount: Int = 0,
    isExpanded: Boolean = false,
    modifier: Modifier = Modifier,
    tasks: List<TaskShortDto> = emptyList()
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANDING_TRANSITION_DURATION)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(EXPANDING_TRANSITION_DURATION)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            // Expand from the top.
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANDING_TRANSITION_DURATION)
        ) + fadeOut(
            // Fade in with the initial alpha of 0.3f.
            animationSpec = tween(EXPANDING_TRANSITION_DURATION)
        )
    }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = itemsCount.toString(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.disabledColor,
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
            IconButton(
                onClick = onExpandButtonClick,
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isExpanded) TaskOasisIcons.moreArrowDownExpanded else TaskOasisIcons.moreArrowRightCollapsed
                    ),
                    contentDescription = "Task group expand button",
                    modifier = Modifier.size(30.dp, 30.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = tasks.isNotEmpty() && isExpanded,
            enter = enterTransition,
            exit = exitTransition
        ) {
            Surface(color = MaterialTheme.colors.surface) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    tasks.forEach { task ->
                        TaskItem(
                            text = task.name,
                            onClick = { /*TODO*/ },
                            onCheckedChanged = { /*TODO*/ },
                            isChecked = task.status,
                            priority = task.priority,
                            deadline = task.deadline.toLocalDateTimeCurrentZone().toStringFormatted(),
                            hasNotification = task.hasNotification,
                            hasRepeat = task.hasRepeat
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false, device = "id:pixel_3_xl")
@Composable
fun TaskGroupPreview() {

    val tasks = listOf(
        TaskShortDto(
            name = "Make habit screen",
            deadline = Instant.now(),
            status = false,
            difficulty = Difficulty.NORMAL,
            priority = Priority.IMPORTANT,
            hasNotification = true,
            hasRepeat = false,
            id = 1
        ),
        TaskShortDto(
            name = "Start to code this android app",
            deadline = Instant.now(),
            status = false,
            difficulty = Difficulty.NORMAL,
            priority = Priority.NORMAL,
            hasNotification = false,
            hasRepeat = true,
            id = 2
        ),
        TaskShortDto(
            name = "Create a new habit",
            deadline = Instant.now(),
            status = false,
            difficulty = Difficulty.NORMAL,
            priority = Priority.LOW,
            hasNotification = false,
            hasRepeat = false,
            id = 3
        ),
        TaskShortDto(
            name = "Go to swim",
            deadline = Instant.now(),
            status = false,
            difficulty = Difficulty.NORMAL,
            priority = Priority.LOW,
            hasNotification = true,
            hasRepeat = true,
            id = 4
        )
    )

    val isExpanded = remember {
        mutableStateOf(false)
    }


    TaskOasisTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            TaskGroup(
                title = "This week",
                itemsCount = 4,
                isExpanded = isExpanded.value,
                onExpandButtonClick = { isExpanded.value = !isExpanded.value },
                modifier = Modifier.padding(15.dp),
                tasks = tasks
            )
        }
    }
}