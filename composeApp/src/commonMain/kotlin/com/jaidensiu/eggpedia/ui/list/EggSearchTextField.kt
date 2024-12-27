@file:OptIn(ExperimentalMaterialApi::class)

package com.jaidensiu.eggpedia.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.jaidensiu.eggpedia.app.Route
import com.jaidensiu.eggpedia.ui.shared.isAndroid
import com.jaidensiu.eggpedia.ui.theme.TextFieldPlaceHolder

@Composable
fun EggSearchTextField(
    modifier: Modifier = Modifier,
    searchQuery: MutableState<String>,
    onSearchQueryChange: (String) -> Unit,
    focusRequester: FocusRequester,
    interactionSource: MutableInteractionSource,
    route: Route
) {
    val isFocused = interactionSource.collectIsFocusedAsState().value

    BasicTextField(
        value = searchQuery.value,
        onValueChange = { onSearchQueryChange(it) },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .focusable()
            .clickable { focusRequester.requestFocus() },
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
        singleLine = true,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(value = MaterialTheme.colors.onSurface)
    ) { innerTextField ->
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = searchQuery.value,
            innerTextField = innerTextField,
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            placeholder = {
                if (route == Route.EggsList) {
                    Text(
                        text = "Search for a recipe",
                        color = if (isFocused) Color.Transparent else TextFieldPlaceHolder(),
                        maxLines = 1
                    )
                } else if (route == Route.SavedEggsList) {
                    Text(
                        text = "Search for a saved recipe",
                        color = if (isFocused) Color.Transparent else TextFieldPlaceHolder(),
                        maxLines = 1
                    )
                }
            },
            contentPadding = if (searchQuery.value.isBlank()) {
                outlinedTextFieldPadding(
                    top = if (isAndroid) 6.dp else 5.dp,
                    bottom = if (isAndroid) 6.dp else 5.dp,
                )
            } else {
                outlinedTextFieldPadding(
                    top = if (isAndroid) 10.dp else 9.dp,
                    bottom = if (isAndroid) 10.dp else 9.dp,
                )
            }
        )
    }
}
