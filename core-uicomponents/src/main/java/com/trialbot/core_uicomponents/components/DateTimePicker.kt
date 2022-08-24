package com.trialbot.core_uicomponents.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trialbot.core_designsystem.ui.TaskOasisIcon
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme
import com.trialbot.core_uicomponents.R
import com.trialbot.core_utils.toLocalDateTimeCurrentZone
import com.trialbot.core_utils.toStringFormatted
import com.trialbot.core_utils.toStringFullFormatted
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun AppDatePicker(
    modifier: Modifier = Modifier,
    startedDate: LocalDateTime,
    isSetWhenStarted: Boolean = false,
    onDateChanged: (LocalDateTime?) -> Unit
) {
    val pickedDate = remember {
        mutableStateOf(
            if (isSetWhenStarted) startedDate else null
        )
    }

    val onDatePicked = { _: DatePicker, year: Int, month: Int, day: Int ->
        if (pickedDate.value?.year != year || pickedDate.value?.monthValue != (month + 1) || pickedDate.value?.dayOfMonth != day) {
            pickedDate.value = LocalDateTime.of(
                year, month + 1, day, pickedDate.value?.hour ?: 0, pickedDate.value?.minute ?: 0
            )
            onDateChanged(pickedDate.value)
        }
    }

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        onDatePicked,
        (pickedDate.value ?: startedDate).year,
        (pickedDate.value ?: startedDate).month.ordinal,
        (pickedDate.value ?: startedDate).dayOfMonth,
    )

    PickerForm(
        modifier = modifier,
        currentDateTime = pickedDate.value,
        icon = TaskOasisIcon.DrawableResourceIcon(TaskOasisIcons.calendar),
        pickerType = PickerType.DATE,
        onClick = {
            datePickerDialog.show()
        },
        onCancel = {
            if (pickedDate.value != null) {
                pickedDate.value = null
                onDateChanged(null)
            }
        }
    )
}

@Composable
fun AppTimePicker(
    modifier: Modifier = Modifier,
    startedTime: LocalTime,
    isSetWhenStarted: Boolean = false,
    onTimeChanged: (LocalTime?) -> Unit
) {
    val pickedTime = remember {
        mutableStateOf(
            if (isSetWhenStarted) startedTime else null
        )
    }

    val onTimePicked = { _: TimePicker, hour: Int, minute: Int ->
        if (pickedTime.value?.hour != hour || pickedTime.value?.minute != minute) {
            pickedTime.value = LocalTime.of(hour, minute)
            onTimeChanged(pickedTime.value)
        }
    }

    val timePickerDialog = TimePickerDialog(
        LocalContext.current,
        onTimePicked,
        (pickedTime.value ?: startedTime).hour,
        (pickedTime.value ?: startedTime).minute,
        DateFormat.is24HourFormat(LocalContext.current)
    )

    PickerForm(
        modifier = modifier,
        currentDateTime = if (pickedTime.value != null) LocalDateTime.of(LocalDate.now(), pickedTime.value) else null,
        icon = TaskOasisIcon.DrawableResourceIcon(TaskOasisIcons.timeClock),
        pickerType = PickerType.TIME,
        onClick = {
            timePickerDialog.show()
        },
        onCancel = {
            if (pickedTime.value != null) {
                pickedTime.value = null
                onTimeChanged(null)
            }
        }
    )
}

private enum class PickerType {
    DATE,
    TIME
}

@Composable
private fun PickerForm(
    modifier: Modifier = Modifier,
    currentDateTime: LocalDateTime?,
    icon: TaskOasisIcon,
    pickerType: PickerType,
    onClick: () -> Unit,
    onCancel: () -> Unit
) {
    val text = when (pickerType) {
        PickerType.DATE -> {
            currentDateTime?.toStringFullFormatted() ?: stringResource(R.string.not_set)
        }
        PickerType.TIME -> {
            currentDateTime?.toLocalTime()?.toStringFormatted() ?: stringResource(R.string.not_set)

        }
    }

    Row {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .clickable { onClick() }
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                when (icon) {
                    is TaskOasisIcon.DrawableResourceIcon -> {
                        Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = "picker icon",
                            tint = MaterialTheme.colors.onSurface,
                            modifier = Modifier.size(30.dp, 30.dp)
                        )
                    }
                    is TaskOasisIcon.ImageVectorIcon -> {
                        Icon(
                            imageVector = icon.imageVector,
                            contentDescription = "picker icon",
                            tint = MaterialTheme.colors.onSurface,
                            modifier = Modifier.size(30.dp, 30.dp)
                        )
                    }
                }
            }
        }
        IconButton(
            onClick = { onCancel() },
            modifier = Modifier
                .offset(x = (-25).dp, y = (8).dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
                .size(19.dp, 19.dp)
        ) {
            Icon(
                imageVector = TaskOasisIcons.close,
                contentDescription = "unselect",
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.size(11.dp, 11.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AppDatePickerFormPreview() {
    TaskOasisTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            AppDatePicker(
                modifier = Modifier
                    .padding(15.dp)
                    .size(178.dp, 50.dp),
                startedDate = Instant.now().toLocalDateTimeCurrentZone(),
                isSetWhenStarted = true,
                onDateChanged = {

                }
            )
            AppDatePicker(
                modifier = Modifier
                    .padding(15.dp)
                    .size(178.dp, 50.dp),
                startedDate = Instant.now().toLocalDateTimeCurrentZone(),
                isSetWhenStarted = false,
                onDateChanged = {

                }
            )
            AppTimePicker(
                modifier = Modifier
                    .padding(15.dp)
                    .size(178.dp, 50.dp),
                startedTime = Instant.now().toLocalDateTimeCurrentZone().toLocalTime(),
                isSetWhenStarted = true,
                onTimeChanged = {

                }
            )
        }
    }
}