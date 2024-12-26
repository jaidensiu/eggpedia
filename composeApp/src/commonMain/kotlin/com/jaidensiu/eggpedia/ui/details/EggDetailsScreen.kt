package com.jaidensiu.eggpedia.ui.details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jaidensiu.eggpedia.data.Egg

@Composable
fun EggDetailsScreen(
    egg: Egg
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Text(text = egg.name)
        egg.cookingSteps.forEach {
            Text(text = it)
        }
    }
}
