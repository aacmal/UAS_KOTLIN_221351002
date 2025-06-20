package me.acml.predictsleepdisorder.ui

object ModelConstants {

    // Target classes (output labels)
    val TARGET_CLASSES = listOf(
        "Insomnia", "Sleep Apnea", "Normal"
    )

    // Gender mapping
    val GENDER_MAPPING = mapOf(
        "Female" to 0f, "Male" to 1f
    )

    // Occupation mapping
    val OCCUPATION_MAPPING = mapOf(
        "Accountant" to 0f,
        "Doctor" to 1f,
        "Engineer" to 2f,
        "Lawyer" to 3f,
        "Manager" to 4f,
        "Nurse" to 5f,
        "Sales Representative" to 6f,
        "Salesperson" to 7f,
        "Scientist" to 8f,
        "Software Engineer" to 9f,
        "Teacher" to 10f
    )

    // BMI Category mapping
    val BMI_MAPPING = mapOf(
        "Normal" to 0f, "Normal Weight" to 1f, "Obese" to 2f, "Overweight" to 3f
    )

    // Feature order (for reference)
    val FEATURE_ORDER = listOf(
        "Age",
        "Sleep Duration",
        "Quality of Sleep",
        "Physical Activity Level",
        "Stress Level",
        "Heart Rate",
        "Daily Steps",
        "Systolic_BP",
        "Diastolic_BP",
        "Gender_Encoded",
        "Occupation_Encoded",
        "BMI_Encoded"
    )

    fun getGenderCode(gender: String): Float = GENDER_MAPPING[gender] ?: 0f

    fun getOccupationCode(occupation: String): Float = OCCUPATION_MAPPING[occupation] ?: 0f

    fun getBMICode(bmiCategory: String): Float = BMI_MAPPING[bmiCategory] ?: 0f

    fun getPredictedClass(classIndex: Int): String {
        return if (classIndex in TARGET_CLASSES.indices) {
            TARGET_CLASSES[classIndex]
        } else {
            "Unknown"
        }
    }
}
