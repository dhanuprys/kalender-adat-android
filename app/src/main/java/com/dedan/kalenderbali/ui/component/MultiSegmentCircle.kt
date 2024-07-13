package com.dedan.kalenderbali.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MultiSegmentCircle(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(Color.Transparent, CircleShape) // Inner background
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val segmentAngle = 360f / colors.size
            var startAngle = 0f

            for (color in colors) {
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = segmentAngle,
                    useCenter = true
                )
                startAngle += segmentAngle
            }
        }

        // Inner content box
        Row(
            modifier = Modifier
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.surface, shape = CircleShape)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun PreviewMultiSegmentCircle() {
    MultiSegmentCircle(
        modifier = Modifier.size(200.dp),
        colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta)
    )
}