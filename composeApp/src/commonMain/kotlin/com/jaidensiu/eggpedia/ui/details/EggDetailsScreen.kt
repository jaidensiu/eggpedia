package com.jaidensiu.eggpedia.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.jaidensiu.eggpedia.data.Egg
import com.jaidensiu.eggpedia.ui.shared.isAndroid
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EggDetailsScreen(
    viewModel: ViewModel = koinViewModel(),
    egg: Egg,
    onClickBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = if (isAndroid) 32.dp else 16.dp)
    ) {
        Button(onClick = { onClickBack() }) {
            Text(text = "back")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = egg.name)
                egg.cookingSteps.forEach {
                    Text(text = it)
                }
            }
        }
    }
}
