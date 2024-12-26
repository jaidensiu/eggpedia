@file:OptIn(ExperimentalMaterialApi::class)

package com.jaidensiu.eggpedia.ui.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaidensiu.eggpedia.data.Egg

@Composable
fun EggListItem(
    egg: Egg,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = { onClick() },
        modifier = modifier.padding(6.dp)
    ) {
        Row(modifier = Modifier.padding(6.dp)) {
            Text(text = egg.name)
        }
    }
}
