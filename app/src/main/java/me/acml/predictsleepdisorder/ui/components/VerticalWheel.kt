package me.acml.predictsleepdisorder.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.acml.predictsleepdisorder.ui.theme.backgroundPrimary
import kotlin.math.abs

@Composable
fun VerticalWheel(
    value: Float,
    onValueChange: (Float) -> Unit,
    ranges: List<Float>,
) {
    val itemHeightDp = 60.dp
    val padding = (132.dp - itemHeightDp) / 2

    var savedScrollIndex by rememberSaveable {
        mutableIntStateOf(ranges.indexOf(value).takeIf { it != -1 } ?: 0)
    }

    // LazyListState dengan initial position
    val state = rememberLazyListState(
        initialFirstVisibleItemIndex = savedScrollIndex
    )

    var isInternalUpdate by remember { mutableStateOf(false) }
    var isInitialized by remember { mutableStateOf(false) }

    var centerIndex by remember { mutableIntStateOf(savedScrollIndex) }

    val currentCenterIndex by remember {
        derivedStateOf {
            val layoutInfo = state.layoutInfo
            if (layoutInfo.visibleItemsInfo.isEmpty()) return@derivedStateOf centerIndex

            val center = layoutInfo.viewportStartOffset + (layoutInfo.viewportSize.height / 2)

            layoutInfo.visibleItemsInfo.minByOrNull { item ->
                val itemCenter = item.offset + (item.size / 2)
                abs(itemCenter - center)
            }?.index ?: centerIndex
        }
    }

    LaunchedEffect(currentCenterIndex) {
        centerIndex = currentCenterIndex
        // Simpan posisi scroll terbaru
        savedScrollIndex = currentCenterIndex
    }

    LaunchedEffect(state.isScrollInProgress) {
        if (!state.isScrollInProgress && !isInternalUpdate && isInitialized) {
            val centerAge = ranges.getOrNull(centerIndex)
            if (centerAge != null && centerAge != value) {
                isInternalUpdate = true
                onValueChange(centerAge)
                isInternalUpdate = false
            }
        }
    }

    LaunchedEffect(state.layoutInfo.totalItemsCount) {
        if (state.layoutInfo.totalItemsCount > 0 && !isInitialized) {
            delay(100) // Tunggu sebentar agar layout stabil
            state.scrollToItem(savedScrollIndex, scrollOffset = 45)
            isInitialized = true
        }
    }

    // Handle perubahan value dari external
    LaunchedEffect(value) {
        if (!isInternalUpdate && isInitialized) {
            val targetIndex = ranges.indexOf(value)
            if (targetIndex != -1 && targetIndex != centerIndex) {
                centerIndex = targetIndex
                savedScrollIndex = targetIndex
                state.animateScrollToItem(targetIndex, scrollOffset = 45)
            }
        }
    }

    Box(
        modifier = Modifier.wrapContentWidth()
    ) {
        LazyColumn(
            modifier = Modifier.height(100.dp).align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = state,
            flingBehavior = rememberSnapFlingBehavior(
                lazyListState = state, snapPosition = SnapPosition.Center
            ),
            contentPadding = PaddingValues(vertical = padding)
        ) {
            items(ranges.size) { index ->
                val age = ranges[index]
                val isCentered = index == centerIndex
                val text = if (age % 1.0f == 0f) {
                    age.toInt().toString()
                } else {
                    age.toString()
                }

                val scale by animateFloatAsState(
                    targetValue = if (isCentered) if (text.contains(".")) 1.2f else 1.5f else 0.8f,
                    animationSpec = tween(300, easing = FastOutSlowInEasing),
                    label = "scale_animation"
                )

                Box(
                    modifier = Modifier
                        .height(itemHeightDp)
                        .scale(scale),
                ) {
                    Text(
                        text = text,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(16.dp),
                        softWrap = false,
                        style = MaterialTheme.typography.titleLarge,
                        color = if (isCentered) Color.White
                        else Color.White.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Center indicator box
        Box(
            modifier = Modifier
                .height(itemHeightDp)
                .width(100.dp)
                .align(Alignment.Center)
                .border(
                    width = 1.5.dp, color = Color.White, shape = RoundedCornerShape(8.dp)
                )
                .background(
                    color = Color.White.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)
                )
        )

        // Top gradient overlay
        Box(
            modifier = Modifier
                .height(15.dp)
                .width(60.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            backgroundPrimary, Color.Transparent
                        )
                    )
                )
                .align(Alignment.TopCenter)
        )

        // Bottom gradient overlay
        Box(
            modifier = Modifier
                .height(15.dp)
                .width(60.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, backgroundPrimary
                        )
                    )
                )
                .align(Alignment.BottomCenter)
        )
    }
}