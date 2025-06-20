package me.acml.predictsleepdisorder.ml

data class SleepDisorderFeatures(
    val age: Float = 0f,
    val sleepDuration: Float = 0f,
    val qualityOfSleep: Float = 0f,
    val physicalActivityLevel: Float = 0f,
    val stressLevel: Float = 0f,
    val heartRate: Float = 0f,
    val dailySteps: Float = 0f,
    val systolicBP: Float = 0f,
    val diastolicBP: Float = 0f,
    val gender: String? = null,
    val occupation: String? = null,
    val bmi: String? = null
)
