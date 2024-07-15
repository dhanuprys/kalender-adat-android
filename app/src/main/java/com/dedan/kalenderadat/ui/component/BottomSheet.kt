package com.dedan.kalenderadat.ui.component

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    collapsable: Boolean = true,
    onCollapseRequest: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    var offset by remember { mutableStateOf(0f) }

    LaunchedEffect(collapsable) {
        offset = 0f
    }

    Card(
        modifier = modifier.then(
            if (collapsable)
                Modifier.offset { IntOffset(0, offset.toInt()) }
            else
                Modifier
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
        shape = RoundedCornerShape(
            topStart = 40.dp,
            topEnd = 40.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            if (offset > 300f)
                                onCollapseRequest()
                            else
                                offset = 0f
                        }
                    ) { change, dragAmount ->
                        change.consume()
                        offset = (offset + dragAmount.y).coerceIn(0f, 700f)
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InlineBox(modifier = Modifier
                .width(100.dp)
                .height(5.dp)
            )
        }
        content()
    }
}

@Composable
fun InlineBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(20.dp))
    )
}