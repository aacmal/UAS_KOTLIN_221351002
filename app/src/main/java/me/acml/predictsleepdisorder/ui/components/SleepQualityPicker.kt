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
fun SleepQualityPicker(
    selectedIndex: Int = -1, // Index dari 0-5 (untuk nilai 4-9)
    onSelectionChange: (Int) -> Unit,
) {
    val sleepQualities = listOf(
        "My sleep is poor",             // Index 0 = Value 4
        "My sleep is not very restful", // Index 1 = Value 5
        "My sleep is fairly good",      // Index 2 = Value 6
        "My sleep is quite restful",    // Index 3 = Value 7
        "My sleep is very restful",     // Index 4 = Value 8
        "My sleep is exceptionally restful" // Index 5 = Value 9
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
                    // Border
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
        sleepQualities.forEachIndexed { index, quality ->
            val isSelected = selectedIndex == index
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
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null,
                        onClick = {
                            onSelectionChange(index)
                        }
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = quality,
                    color = Color.White,
                    style = PredictSleepDisorderTheme.typography.bodyLarge.copy(
                        fontWeight = if (selectedIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                        fontSize = animatedFontSize.value.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Fungsi helper untuk convert index ke nilai (4-9)
fun indexToSleepQualityValue(index: Int): Int = index + 4

// Fungsi helper untuk convert nilai (4-9) ke index
fun sleepValueToIndex(value: Int): Int = (value - 4).coerceIn(0, 5)