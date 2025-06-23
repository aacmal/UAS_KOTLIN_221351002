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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.acml.predictsleepdisorder.ui.libs.spatialExpressiveSpring
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme
import me.acml.predictsleepdisorder.ui.theme.textField

@Composable
fun SelectAnimated(
    items: List<String>,
    selectedItem: String? = null,
    onSelectionChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    tabHeight: Dp = 56.dp,
    indicatorShape: Dp = 12.dp,
    indicatorColor: Color = Color.White.copy(alpha = 0.1f),
    borderColor: Color = Color.White,
    textColor: Color = Color.White,
    selectedFontSize: Dp = 18.dp,
    unselectedFontSize: Dp = 16.dp,
    horizontalPadding: Dp = 16.dp
) {
    // Mencari index dari selected item, default ke 0 jika tidak ditemukan
    val selectedIndex = if (selectedItem != null && selectedItem in items) {
        items.indexOf(selectedItem)
    } else {
        -1
    }

    val indicatorOffset by animateDpAsState(
        targetValue = tabHeight * selectedIndex,
        animationSpec = spatialExpressiveSpring(),
        label = "indicator offset"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(PredictSleepDisorderTheme.shapes.textField)
            .drawWithContent {
                drawWithLayer {
                    // Border
                    drawRoundRect(
                        topLeft = Offset(0f, indicatorOffset.toPx()),
                        size = Size(size.width, tabHeight.toPx()),
                        color = borderColor,
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
        items.forEachIndexed { index, item ->
            val isSelected = selectedIndex == index
            val animatedFontSize by animateDpAsState(
                targetValue = if (isSelected) selectedFontSize else unselectedFontSize,
                animationSpec = tween(300),
                label = "font size animation"
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(tabHeight)
                    .padding(horizontal = horizontalPadding)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null,
                        onClick = {
                            onSelectionChange(item) // Mengirim item string, bukan index
                        }
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = item,
                    color = textColor,
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