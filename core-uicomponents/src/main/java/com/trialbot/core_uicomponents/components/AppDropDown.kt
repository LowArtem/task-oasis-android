package com.trialbot.core_uicomponents.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trialbot.core_designsystem.ui.TaskOasisIcon
import com.trialbot.core_designsystem.ui.TaskOasisIcons
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme

data class AppDropDownItem(
    val text: String,
    val icon: TaskOasisIcon?,
    val iconTint: @Composable () -> Color = { MaterialTheme.colors.onSurface }
)

@Composable
fun AppDropDown(
    modifier: Modifier = Modifier,
    items: List<AppDropDownItem>,
    icon: TaskOasisIcon?,
    iconTint: @Composable () -> Color = { MaterialTheme.colors.onSurface },
    defaultSelectedItem: Int = 0,
    onItemChanged: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(defaultSelectedItem) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
            .clickable {
                expanded = true
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppDropDownItemForm(item = AppDropDownItem(
                text = items[selectedItem].text,
                icon = if (items[selectedItem].icon == null) icon else items[selectedItem].icon,
                iconTint = iconTint
            ))
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    modifier = Modifier.align(Alignment.Start),
                    contentPadding = PaddingValues(horizontal = 15.dp, vertical = 10.dp),
                    onClick = {
                        if (selectedItem != index) {
                            selectedItem = index
                            onItemChanged(index)
                        }
                        expanded = false
                    }
                ) {
                    AppDropDownItemForm(item = item)
                }
            }
        }
    }
}

@Composable
private fun AppDropDownItemForm(
    item: AppDropDownItem
) {
    Text(
        text = item.text,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onSurface
    )
    when (item.icon) {
        is TaskOasisIcon.DrawableResourceIcon -> {
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(id = item.icon.id),
                contentDescription = item.text,
                tint = item.iconTint()
            )
        }
        is TaskOasisIcon.ImageVectorIcon -> {
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = item.icon.imageVector,
                contentDescription = item.text,
                tint = item.iconTint()
            )
        }
        null -> Unit
    }
}


@Preview(showBackground = true)
@Composable
private fun AppDropDownPreview() {
    TaskOasisTheme {
        val items = listOf(
            AppDropDownItem(
                text = "None",
                icon = null
            ),
            AppDropDownItem(
                text = "Two days before",
                icon = null
            ),
            AppDropDownItem(
                text = "Two weeks before",
                icon = TaskOasisIcon.of(TaskOasisIcons.circleFilled)
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            AppDropDown(
                modifier = Modifier
                    .padding(15.dp)
                    .size(241.dp, 50.dp),
                items = items,
                icon = TaskOasisIcon.of(TaskOasisIcons.repeat),
                onItemChanged = {}
            )
        }
    }
}