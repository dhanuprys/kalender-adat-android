package com.dedan.kalenderbali.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextWithBullet(
    text: String,
    modifier: Modifier = Modifier
) {
    Row {
        Text(text = "• ", modifier = modifier)
        Text(text = text, modifier = modifier)
    }
}