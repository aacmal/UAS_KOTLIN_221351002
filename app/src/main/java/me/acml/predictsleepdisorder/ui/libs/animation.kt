package me.acml.predictsleepdisorder.ui.libs

import androidx.compose.animation.core.spring

fun <T> spatialExpressiveSpring() = spring<T>(
    dampingRatio = 0.8f,
    stiffness = 380f
)