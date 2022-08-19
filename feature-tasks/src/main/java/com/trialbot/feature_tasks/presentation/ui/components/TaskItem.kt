package com.trialbot.feature_tasks.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme
import com.trialbot.core_designsystem.ui.theme.attentionColor
import com.trialbot.core_designsystem.ui.theme.disabledColor
import com.trialbot.core_designsystem.ui.theme.infoColor
import com.trialbot.core_model.enum.Priority

@Composable
fun TaskItem(
    text: String,
    onClick: () -> Unit,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    priority: Priority = Priority.LOW,
    deadline: String? = null,
    hasNotification: Boolean = false,
    hasRepeat: Boolean = false
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxWidth()
                .clickable { onClick() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .weight(4f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = onCheckedChanged,
                    modifier = Modifier.size(32.dp, 32.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colors.disabledColor,
                        uncheckedColor = when (priority) {
                            Priority.LOW -> MaterialTheme.colors.onPrimary
                            Priority.NORMAL -> MaterialTheme.colors.attentionColor
                            Priority.IMPORTANT -> MaterialTheme.colors.error
                        },
                        disabledColor = MaterialTheme.colors.disabledColor
                    )
                )
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = if (!isChecked) MaterialTheme.colors.onSurface else MaterialTheme.colors.disabledColor,
                    textDecoration = if (!isChecked) null else TextDecoration.LineThrough,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (hasNotification) {
                    Icon(
                        painter = painterResource(id = TaskOasisIcons.alarm),
                        contentDescription = "has alarm",
                        modifier = Modifier
                            .size(16.dp, 16.dp)
                            .padding(end = 2.dp),
                        tint = if (!isChecked) MaterialTheme.colors.infoColor else MaterialTheme.colors.disabledColor
                    )
                }
                if (hasRepeat) {
                    Icon(
                        painter = painterResource(id = TaskOasisIcons.repeat),
                        contentDescription = "has repeat",
                        modifier = Modifier
                            .size(16.dp, 16.dp)
                            .padding(end = 2.dp),
                        tint = if (!isChecked) MaterialTheme.colors.infoColor else MaterialTheme.colors.disabledColor
                    )
                }
                if (deadline != null) {
                    Text(
                        text = deadline,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle1,
                        color = if (!isChecked) MaterialTheme.colors.infoColor else MaterialTheme.colors.disabledColor,
                        textDecoration = if (!isChecked) null else TextDecoration.LineThrough,
                        modifier = Modifier.padding(end = 2.dp),
                    )
                }
            }
        }
    }
}

@Preview(device = "id:pixel_3_xl")
@Composable
fun TaskItemPreview() {
    TaskOasisTheme {

        val isChecked = remember {
            mutableStateOf(false)
        }

        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            TaskItem(
                text = "Start to code this android app app",
                onClick = { /*TODO*/ },
                onCheckedChanged = { isChecked.value = it },
                isChecked = isChecked.value,
                priority = Priority.NORMAL,
                hasRepeat = true,
                hasNotification = true,
                deadline = "26.05",
            )
        }
    }
}