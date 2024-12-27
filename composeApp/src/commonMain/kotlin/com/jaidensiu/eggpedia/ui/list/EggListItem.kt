@file:OptIn(ExperimentalMaterialApi::class)

package com.jaidensiu.eggpedia.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.jaidensiu.eggpedia.data.Egg

@Composable
fun EggListItem(
    egg: Egg,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val painter = rememberAsyncImagePainter(model = egg.imageUrl)

    Card(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = egg.name)
            Spacer(modifier = Modifier.height(12.dp))
            Image(
                painter = painter,
                contentDescription = ""
            )
        }
    }
}
