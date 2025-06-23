package me.acml.predictsleepdisorder.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.acml.predictsleepdisorder.ui.libs.spatialExpressiveSpring
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme
import me.acml.predictsleepdisorder.ui.theme.textField

@Composable
fun StressLevelPicker(
    selectedIndex: Int = -1,
    onSelectionChange: (Int) -> Unit,
) {
    // Nilai dan labelnya sudah diurutkan dari kecil ke besar
    val stressValues = arrayOf(3, 4, 5, 6, 7, 8)
    val stressLabels = listOf(
        "I feel very calm",       // 3
        "I feel quite calm",       // 4
        "I feel neutral",          // 5
        "I feel a bit stressed",   // 6
        "I feel quite stressed",   // 7
        "I feel very stressed"     // 8
    )

    val tabHeight = 56.dp
    val indicatorShape = 12.dp
    val indicatorColor = Color.White.copy(alpha = 0.1f)

    val indicatorOffset by animateDpAsState(
        targetValue = tabHeight * selectedIndex,
        animationSpec = spatialExpressiveSpring(),
        label = "indicator offset"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(PredictSleepDisorderTheme.shapes.textField)
            .drawWithContent {
                drawWithLayer {
                    drawRoundRect(
                        topLeft = Offset(0f, indicatorOffset.toPx()),
                        size = Size(size.width, tabHeight.toPx()),
                        color = Color.White,
                        cornerRadius = CornerRadius(
                            x = indicatorShape.toPx(),
                            y = indicatorShape.toPx()
                        ),
                        style = Stroke(width = 1.5.dp.toPx())
                    )
                    drawRoundRect(
                        topLeft = Offset(2f, indicatorOffset.toPx() + 2f),
                        size = Size(size.width - 4, tabHeight.toPx() - 4),
                        color = indicatorColor,
                        cornerRadius = CornerRadius(
                            x = indicatorShape.toPx(),
                            y = indicatorShape.toPx()
                        ),
                    )
                    drawContent()
                }
            }
    ) {
        stressLabels.forEachIndexed { index, label ->
            val isSelected = index == selectedIndex
            val animatedFontSize by animateDpAsState(
                targetValue = if (isSelected) 18.dp else 16.dp,
                animationSpec = tween(300),
                label = "font size animation"
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(tabHeight)
                    .padding(horizontal = 16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onSelectionChange(index)
                    },
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = label,
                    color = Color.White,
                    style = PredictSleepDisorderTheme.typography.bodyLarge.copy(
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        fontSize = animatedFontSize.value.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Helper untuk convert index ke nilai stress
fun indexToStressLevelValue(index: Int): Int {
    val stressValues = arrayOf(3, 4, 5, 6, 7, 8)
    return stressValues[index.coerceIn(0, stressValues.lastIndex)]
}

// Helper untuk convert nilai stress ke index
fun stressValueToIndex(value: Int): Int {
    val stressValues = arrayOf(3, 4, 5, 6, 7, 8)
    return stressValues.indexOf(value).coerceAtLeast(0)
}
