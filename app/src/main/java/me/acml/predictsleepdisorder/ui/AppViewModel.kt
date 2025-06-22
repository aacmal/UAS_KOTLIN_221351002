package me.acml.predictsleepdisorder.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.acml.predictsleepdisorder.ml.SleepDisorderFeatures
import me.acml.predictsleepdisorder.ml.SleepDisorderModel
import me.acml.predictsleepdisorder.ui.libs.readCsvFromAssets

class AppViewModelFactory(
    private val sleepDisorderModel: SleepDisorderModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(sleepDisorderModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class AppViewModel(private val sleepDisorderModel: SleepDisorderModel): ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState(SleepDisorderFeatures()))
    val uiState = _uiState.asStateFlow()

    private val _datasets = MutableStateFlow<List<List<String>>>(emptyList())
    val datasets = _datasets.asStateFlow()

    fun makePrediction() {
        val prediction = sleepDisorderModel.predict(
            floatArrayOf(
                _uiState.value.features.age,
                _uiState.value.features.sleepDuration,
                _uiState.value.features.qualityOfSleep,
                _uiState.value.features.physicalActivityLevel,
                _uiState.value.features.stressLevel,
                _uiState.value.features.heartRate,
                _uiState.value.features.dailySteps,
                _uiState.value.features.systolicBP,
                _uiState.value.features.diastolicBP,
                ModelConstants.getGenderCode("Female"),
                ModelConstants.getOccupationCode("Sales Representative"),
                ModelConstants.getBMICode("Obese")
            )
//            floatArrayOf(
//                28f, // Example age
//                8f, // Example sleep duration
//                5f, // Example quality of sleep
//                40f, // Example physical activity level
//                8f, // Example stress level
//                85f, // Example heart rate
//                5000f, // Example daily steps
//                140f, // Example systolic BP
//                90f, // Example diastolic BP
//
//
//            )
        )
        _uiState.value = _uiState.value.copy(
            predictionResult = PredictionResult(
                predictedClass = prediction.label,
                confidence = prediction.confidence
            )
        )
    }

    fun updateAge(age: Float) {
        _uiState.value = _uiState.value.copy(
            features = _uiState.value.features.copy(age = age)
        )
    }

    fun updateSleepDuration(sleepDuration: Float) {
        _uiState.value = _uiState.value.copy(
            features = _uiState.value.features.copy(sleepDuration = sleepDuration)
        )
    }

    fun updateQualityOfSleep(qualityOfSleep: Float) {
        _uiState.value = _uiState.value.copy(
            features = _uiState.value.features.copy(qualityOfSleep = qualityOfSleep)
        )
    }

    fun updatePhysicalActivityLevel(physicalActivityLevel: Float) {
        _uiState.value = _uiState.value.copy(
            features = _uiState.value.features.copy(physicalActivityLevel = physicalActivityLevel)
        )
    }

    fun updateStressLevel(stressLevel: Float) {
        _uiState.value = _uiState.value.copy(
            features = _uiState.value.features.copy(stressLevel = stressLevel)
        )
    }

    fun updateHeartRate(heartRate: Float) {
        _uiState.value = _uiState.value.copy(
            features = _uiState.value.features.copy(heartRate = heartRate)
        )
    }

    fun updateDailySteps(dailySteps: Float) {
        _uiState.value = _uiState.value.copy(
            features = _uiState.value.features.copy(dailySteps = dailySteps)
        )
    }

    fun updateSystolicBP(systolicBP: Float) {
        _uiState.value = _uiState.value.copy(
            features = _uiState.value.features.copy(systolicBP = systolicBP)
        )
    }

    fun updateDiastolicBP(diastolicBP: Float) {
        _uiState.value = _uiState.value.copy(
            features = _uiState.value.features.copy(diastolicBP = diastolicBP)
        )
    }

    fun updateGender(gender: String) {
        _uiState.value = _uiState.value.copy(
            features = _uiState.value.features.copy(
                gender = gender
            )
        )
    }

    fun updateOccupation(occupation: String) {
        _uiState.value = _uiState.value.copy(
            features = _uiState.value.features.copy(
                occupation = occupation
            )
        )
    }

    fun updateBMI(bmi: String) {
        _uiState.value = _uiState.value.copy(
            features = _uiState.value.features.copy(bmi = bmi)
        )
    }

    fun nextStep() {
        _uiState.value = _uiState.value.copy(
            step = _uiState.value.step + 1
        )
    }

    fun previousStep() {
        if (_uiState.value.step > 0) {
            _uiState.value = _uiState.value.copy(
                step = _uiState.value.step - 1
            )
        }
    }

    fun loadCsvData(context: Context) {
        viewModelScope.launch {
            val data = readCsvFromAssets(context, "datasets.csv")
            _datasets.value = data
        }
    }
}

data class PredictionResult(
    val predictedClass: String, val confidence: Float
)

data class AppUiState(
    val features: SleepDisorderFeatures,
    val predictionResult: PredictionResult? = null,
    val step: Int = 1
)