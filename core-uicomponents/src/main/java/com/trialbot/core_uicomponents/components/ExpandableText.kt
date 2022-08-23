package com.trialbot.core_uicomponents.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trialbot.core_designsystem.ui.theme.TaskOasisTheme

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    isExpanded: Boolean,
    minimizedMaxLines: Int = 1,
    style: TextStyle = MaterialTheme.typography.body1,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colors.onSurface,
    textDecoration: TextDecoration? = null
) {
    Box(modifier = modifier) {
        SelectionContainer {
            Text(
                text = text,
                maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
                style = style,
                color = color,
                textAlign = textAlign,
                textDecoration = textDecoration,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SelfExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    minimizedMaxLines: Int = 1,
    style: TextStyle = MaterialTheme.typography.body1,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colors.onSurface,
    textDecoration: TextDecoration? = null
) {
    val expanded = remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        SelectionContainer {
            Text(
                text = text,
                maxLines = if (expanded.value) Int.MAX_VALUE else minimizedMaxLines,
                style = style,
                color = color,
                textAlign = textAlign,
                textDecoration = textDecoration,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.clickable {
                    expanded.value = !expanded.value
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExpandableTextPreview() {
    TaskOasisTheme {
        SelfExpandableText(
            text = "Следует отметить, что дальнейшее развитие различных форм" +
                    " деятельности напрямую зависит от приоретизации разума над эмоциями. Не следует," +
                    " однако, забывать, что убеждённость некоторых оппонентов говорит о возможностях " +
                    "прогресса профессионального сообщества. С учётом сложившейся международной " +
                    "обстановки, постоянное информационно-пропагандистское обеспечение нашей " +
                    "деятельности способствует подготовке и реализации анализа существующих паттернов" +
                    " поведения. И нет сомнений, что интерактивные прототипы неоднозначны и будут " +
                    "ассоциативно распределены по отраслям. Приятно, граждане, наблюдать, как явные " +
                    "признаки победы институционализации формируют глобальную экономическую сеть и при" +
                    " этом — смешаны с не уникальными данными до степени совершенной неузнаваемости, " +
                    "из-за чего возрастает их статус бесполезности.",
            modifier = Modifier
                .padding(15.dp)
                .padding(top = 20.dp)
        )
    }
}