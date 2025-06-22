package me.acml.predictsleepdisorder.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.acml.predictsleepdisorder.R
import me.acml.predictsleepdisorder.ui.libs.spatialExpressiveSpring
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme
import me.acml.predictsleepdisorder.ui.theme.textField

val gender = listOf("Male", "Female")

@Composable
fun GenderPicker(
    selectedGender: String? = "Male",
    onSelectionChange: (String) -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(PredictSleepDisorderTheme.shapes.textField)
    ) {
        val maxWidth = this.maxWidth
        val tabWidth = maxWidth / 2

        val indicatorShape = 12.dp
        val indicatorColor = Color.White.copy(alpha = 0.1f)

        val indicatorOffset by animateDpAsState(
            targetValue = tabWidth * (gender.indexOf(selectedGender)).toInt(),
            animationSpec = spatialExpressiveSpring(),
            label = "indicator offset"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    drawWithLayer {
                        // Border
                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                            size = Size(size.width / 2, size.height),
                            color = Color.White,
                            cornerRadius = CornerRadius(
                                x = indicatorShape.toPx(),
                                y = indicatorShape.toPx()
                            ),
                            style = Stroke(width = 1.5.dp.toPx())
                        )
                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                            size = Size(size.width / 2 - 4, size.height - 4),
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
            gender.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .width(tabWidth)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null,
                            onClick = {
                                onSelectionChange(
                                    gender[index]
                                )
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = if (index == 1) R.drawable.rounded_female_24 else R.drawable.rounded_male_24
                            ),
                            tint = Color.White,
                            contentDescription = null,
                        )
                        Text(
                            text = when (index) {
                                0 -> stringResource(R.string.male)
                                1 -> stringResource(R.string.female)
                                else -> ""
                            },
                            color = Color.White,
                            style = PredictSleepDisorderTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Black)
                        )
                    }
                }
            }
        }
    }
}

fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}
