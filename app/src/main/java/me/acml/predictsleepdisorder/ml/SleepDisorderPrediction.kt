package me.acml.predictsleepdisorder.ml

data class SleepDisorderPrediction(
    val label: String,
    val confidence: Float
)
