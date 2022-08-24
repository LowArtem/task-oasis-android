package com.trialbot.core_uicomponents.components.task

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.trialbot.core_uicomponents.R
import com.trialbot.core_uicomponents.components.ExpandableText
import com.trialbot.core_uicomponents.components.InputLineField
import com.trialbot.core_uicomponents.components.SwipeBackground

/**
 * Task item
 *
 * @param isExpandable if true replaces the action of [onClick] function and changes
 * item's expanding state when item clicked
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskItem(
    text: String,
    onClick: () -> Unit,
    onCheckedChanged: (Boolean) -> Unit,
    onSwipeToDelete: () -> Unit,
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    priority: Priority = Priority.LOW,
    deadline: String? = null,
    hasNotification: Boolean = false,
    hasRepeat: Boolean = false,
    isAddingItem: Boolean = false,
    defaultAddingItemState: Boolean = false,
    onItemAdded: ((text: String) -> Unit)? = null,
    onInputStartedCallback: (@Composable () -> Unit)? = null,
    isExpandable: Boolean = false,
) {
    if (isAddingItem)
        require(onItemAdded != null) {
            "If you want to use this item as adding item, you need to specify the action of adding item"
        }


    val swipeState = rememberDismissState()
    val swipeDirection = swipeState.dismissDirection
    val isSwiped = swipeState.isDismissed(DismissDirection.EndToStart)

    if (isSwiped && swipeDirection == DismissDirection.EndToStart) {
        onSwipeToDelete()
    }

    val degrees by animateFloatAsState(
        if (swipeState.targetValue == DismissValue.Default)
            0f
        else
            -45f
    )

    val localText = remember { mutableStateOf(text) }

    if (!isAddingItem) {
        SwipeToDismiss(
            state = swipeState,
            directions = setOf(DismissDirection.EndToStart),
            dismissThresholds = { FractionalThreshold(fraction = 0.3f) },
            background = { SwipeBackground(degrees = degrees) }
        ) {
            TaskItemBody(
                text = localText.value,
                onTextChanged = { localText.value = it },
                onClick = onClick,
                onCheckedChanged = onCheckedChanged,
                modifier = modifier,
                isChecked = isChecked,
                priority = priority,
                deadline = deadline,
                hasNotification = hasNotification,
                hasRepeat = hasRepeat,
                isAddingItem = isAddingItem,
                defaultAddingItemState = defaultAddingItemState,
                isExpandable = isExpandable,
                onItemAdded = onItemAdded,
                onInputStartedCallback = null
            )
        }
    }
    else {
        TaskItemBody(
            text = localText.value,
            onTextChanged = { localText.value = it },
            onClick = onClick,
            onCheckedChanged = onCheckedChanged,
            modifier = modifier,
            isChecked = isChecked,
            priority = priority,
            deadline = deadline,
            hasNotification = hasNotification,
            hasRepeat = hasRepeat,
            isAddingItem = isAddingItem,
            defaultAddingItemState = defaultAddingItemState,
            isExpandable = isExpandable,
            onItemAdded = onItemAdded,
            onInputStartedCallback = onInputStartedCallback
        )
    }
}

@Composable
private fun TaskItemBody(
    text: String,
    onTextChanged: (String) -> Unit,
    onClick: () -> Unit,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    priority: Priority,
    deadline: String?,
    hasNotification: Boolean,
    hasRepeat: Boolean,
    isAddingItem: Boolean,
    defaultAddingItemState: Boolean,
    isExpandable: Boolean,
    onItemAdded: ((text: String) -> Unit)?,
    onInputStartedCallback: (@Composable () -> Unit)?,
) {
    val itemAddingState = remember {
        mutableStateOf(defaultAddingItemState)
    }
    val itemExpandedState = remember {
        mutableStateOf(false)
    }
    val itemExpandedStateChanged: () -> Unit = {
        itemExpandedState.value = !itemExpandedState.value
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxWidth()
                .clickable {
                    if (isAddingItem)
                        itemAddingState.value = !itemAddingState.value
                    else
                        onClick()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            TaskMainContent(
                text = text,
                onTextChanged = onTextChanged,
                onCheckedChanged = onCheckedChanged,
                isItemEditing = itemAddingState.value,
                isItemExpanded = itemExpandedState.value,
                isChecked = isChecked,
                isItemAddable = isAddingItem,
                priority = priority,
                onItemAdded = onItemAdded,
                onInputStartedCallback = onInputStartedCallback
            )

            TaskEndingIcons(
                deadline = deadline,
                isChecked = isChecked,
                hasNotification = hasNotification,
                hasRepeat = hasRepeat,
                isAddingItem = isAddingItem,
                isExpandable = isExpandable,
                isItemExpended = itemExpandedState.value,
                itemExpandedStateChanged = itemExpandedStateChanged
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RowScope.TaskMainContent(
    text: String,
    onTextChanged: (String) -> Unit,
    onCheckedChanged: (Boolean) -> Unit,
    isItemEditing: Boolean,
    isItemExpanded: Boolean,
    isChecked: Boolean = false,
    isItemAddable: Boolean = false,
    priority: Priority = Priority.LOW,
    onItemAdded: ((text: String) -> Unit)? = null,
    onInputStartedCallback: (@Composable () -> Unit)? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = Modifier
            .padding(10.dp)
            .weight(4f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isItemAddable) {
            if (!isItemEditing) {
                Icon(
                    imageVector = TaskOasisIcons.plus,
                    contentDescription = "Add",
                    tint = MaterialTheme.colors.disabledColor,
                    modifier = Modifier.size(30.dp, 30.dp)
                )
            } else {
                Icon(
                    painter = painterResource(id = TaskOasisIcons.checkBoxUnchecked),
                    contentDescription = "Checkbox icon",
                    tint = MaterialTheme.colors.disabledColor,
                    modifier = Modifier.size(32.dp, 32.dp)
                )
            }
        } else {
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
        }
        if (isItemAddable) {
            if (!isItemEditing) {
                Text(
                    text = stringResource(R.string.add_subtask_hint),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.disabledColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 10.dp)
                )
            } else {
                InputLineField(
                    value = text,
                    placeholder = stringResource(R.string.enter_description_hint),
                    onValueChanged = { onTextChanged(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                            onItemAdded?.invoke(text)
                        }
                    ),
                    backgroundColor = MaterialTheme.colors.surface,
                    focusRequester = focusRequester,
                    modifier = Modifier.imePadding()
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
                onInputStartedCallback?.invoke()
            }
        } else {
            ExpandableText(
                text = text,
                isExpanded = isItemExpanded,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.body1,
                color = if (!isChecked) MaterialTheme.colors.onSurface else MaterialTheme.colors.disabledColor,
                textDecoration = if (!isChecked) null else TextDecoration.LineThrough,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
private fun RowScope.TaskEndingIcons(
    deadline: String? = null,
    isChecked: Boolean = false,
    hasNotification: Boolean = false,
    hasRepeat: Boolean = false,
    isAddingItem: Boolean = false,
    isExpandable: Boolean = false,
    isItemExpended: Boolean,
    itemExpandedStateChanged: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(end = 7.dp)
            .weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        if (!isAddingItem) {
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
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.subtitle1,
                    color = if (!isChecked) MaterialTheme.colors.infoColor else MaterialTheme.colors.disabledColor,
                    textDecoration = if (!isChecked) null else TextDecoration.LineThrough,
                    modifier = Modifier.padding(end = 2.dp),
                )
            }
        }
        if (isExpandable) {
            IconButton(
                onClick = { itemExpandedStateChanged() },
                modifier = Modifier.padding(end = 2.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isItemExpended) TaskOasisIcons.moreArrowDownExpanded else TaskOasisIcons.moreArrowRightCollapsed
                    ),
                    contentDescription = "Subtask expand button",
                    modifier = Modifier.size(25.dp, 25.dp),
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}


@Preview(device = "id:pixel_3_xl")
@Composable
private fun TaskItemPreview() {
    TaskOasisTheme {

        val isChecked = remember {
            mutableStateOf(false)
        }

        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            TaskItem(
                text = "Start to code this android app app",
                onClick = {  },
                onCheckedChanged = { isChecked.value = it },
                onSwipeToDelete = { },
                isChecked = isChecked.value,
                priority = Priority.NORMAL,
                hasRepeat = true,
                hasNotification = true,
                deadline = "26.05",
            )
            TaskItem(
                text = "",
                onClick = {  },
                onCheckedChanged = { isChecked.value = it },
                onSwipeToDelete = { },
                isChecked = isChecked.value,
                priority = Priority.LOW,
                isAddingItem = true,
                onItemAdded = { }
            )
        }
    }
}