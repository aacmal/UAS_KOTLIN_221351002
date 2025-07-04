package me.acml.predictsleepdisorder.ui.libs

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.spring

fun <T> spatialExpressiveSpring() = spring<T>(
    dampingRatio = 0.8f,
    stiffness = 380f
)
fun <T> nonSpatialExpressiveSpring() = spring<T>(
    dampingRatio = 1f,
    stiffness = 1600f
)

@OptIn(ExperimentalSharedTransitionApi::class)
val boundsTransform = BoundsTransform { _, _ ->
    spatialExpressiveSpring()
}

data object PredictBoundsKey
data object PredictResultBoundsKey