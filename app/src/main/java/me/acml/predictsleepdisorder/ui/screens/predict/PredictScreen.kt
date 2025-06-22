package me.acml.predictsleepdisorder.ui.screens.predict

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import me.acml.predictsleepdisorder.ui.AppViewModel
import me.acml.predictsleepdisorder.ui.screens.StepOne

@Composable
fun PredictScreen(viewModel: AppViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val features = uiState.features
    val step = uiState.step

    when(step) {
        1 -> StepOne(age = features.age.toInt(), onAgeChange = {
            Log.d("PredictSleepDisorderApp", "Age changed: $it")
            viewModel.updateAge(it.toFloat())
        })
    }
}