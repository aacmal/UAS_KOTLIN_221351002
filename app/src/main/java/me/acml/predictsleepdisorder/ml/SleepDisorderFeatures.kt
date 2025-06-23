package me.acml.predictsleepdisorder.ml

data class SleepDisorderFeatures(
    val age: Float = 29f,
    val sleepDuration: Float = 7f,
    val qualityOfSleep: Float = 0f,
    val physicalActivityLevel: Float = 50f,
    val stressLevel: Float = 0f,
    val heartRate: Float = 75f,
    val dailySteps: Float = 3000f,
    val systolicBP: Float = 115f,
    val diastolicBP: Float = 75f,
    val gender: String? = null,
    val occupation: String? = null,
    val bmi: String? = null
) {
    // Ensure all values are non-negative
    fun validate(){
        require(age >= 0) { "Age must be non-negative" }
        require(sleepDuration >= 0) { "Sleep Duration must be non-negative" }
        require(qualityOfSleep >= 0) { "Quality of Sleep must be non-negative" }
        require(physicalActivityLevel >= 0) { "Physical Activity Level must be non-negative" }
        require(stressLevel >= 0) { "Stress Level must be non-negative" }
        require(heartRate >= 0) { "Heart Rate must be non-negative" }
        require(dailySteps >= 0) { "Daily Steps must be non-negative" }
        require(systolicBP >= 0) { "Systolic BP must be non-negative" }
        require(diastolicBP >= 0) { "Diastolic BP must be non-negative" }
        require(gender != null) { "Gender cannot be null" }
        require(occupation != null) { "Occupation cannot be null" }
        require(bmi != null) { "BMI cannot be null" }
    }
}