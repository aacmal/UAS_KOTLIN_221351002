package me.acml.predictsleepdisorder.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import me.acml.predictsleepdisorder.R
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme
import me.acml.predictsleepdisorder.ui.theme.textField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictSleepDisorderApp(viewModel: AppViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val features = uiState.features

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Predict Sleep Disorder",
                        style = PredictSleepDisorderTheme.typography.titleLarge
                    )
                })
        }) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(innerPadding)
                .then(Modifier.padding(horizontal = 16.dp))
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = if (features.age.toInt() > 0) features.age.toInt().toString() else "",
                onValueChange = {
                    Log.d("PredictSleepDisorderApp", "Age input: $it")
                    val value = it.filter { c -> c.isDigit() }
                    viewModel.updateAge(value.toFloatOrNull() ?: 0f)
                },
                label = { Text(stringResource(R.string.age)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                shape = PredictSleepDisorderTheme.shapes.textField
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = if (features.sleepDuration.toInt() > 0) features.sleepDuration.toInt()
                    .toString() else "",
                onValueChange = {
                    val value = it.filter { c -> c.isDigit() }
                    viewModel.updateSleepDuration(value.toFloatOrNull() ?: 0f)
                },
                label = { Text(stringResource(R.string.sleep_duration)) },
                shape = PredictSleepDisorderTheme.shapes.textField
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = if (features.qualityOfSleep.toInt() > 0) features.qualityOfSleep.toInt()
                    .toString() else "",
                onValueChange = {
                    val value = it.filter { c -> c.isDigit() }
                    viewModel.updateQualityOfSleep(value.toFloatOrNull() ?: 0f)
                },
                label = { Text(stringResource(R.string.quality_of_sleep)) },
                shape = PredictSleepDisorderTheme.shapes.textField
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = if (features.physicalActivityLevel.toInt() > 0) features.physicalActivityLevel.toInt()
                    .toString() else "",
                onValueChange = {
                    val value = it.filter { c -> c.isDigit() }
                    viewModel.updatePhysicalActivityLevel(value.toFloatOrNull() ?: 0f)
                },
                label = { Text(stringResource(R.string.physical_activity)) },
                shape = PredictSleepDisorderTheme.shapes.textField
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = if (features.stressLevel.toInt() > 0) features.stressLevel.toInt()
                    .toString() else "",
                onValueChange = {
                    val value = it.filter { c -> c.isDigit() }
                    viewModel.updateStressLevel(value.toFloatOrNull() ?: 0f)
                },
                label = { Text(stringResource(R.string.stress_level)) },
                shape = PredictSleepDisorderTheme.shapes.textField
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = if (features.heartRate.toInt() > 0) features.heartRate.toInt()
                    .toString() else "",
                onValueChange = {
                    val value = it.filter { c -> c.isDigit() }
                    viewModel.updateHeartRate(value.toFloatOrNull() ?: 0f)
                },
                label = { Text(stringResource(R.string.heart_rate)) },
                shape = PredictSleepDisorderTheme.shapes.textField
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = if (features.dailySteps.toInt() > 0) features.dailySteps.toInt()
                    .toString() else "",
                onValueChange = {
                    val value = it.filter { c -> c.isDigit() }
                    viewModel.updateDailySteps(value.toFloatOrNull() ?: 0f)
                },
                label = { Text(stringResource(R.string.daily_steps)) },
                shape = PredictSleepDisorderTheme.shapes.textField
            )
            Row(
               horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = if (features.systolicBP.toInt() > 0) features.systolicBP.toInt()
                        .toString() else "",
                    onValueChange = {
                        val value = it.filter { c -> c.isDigit() }
                        viewModel.updateSystolicBP(value.toFloatOrNull() ?: 0f)
                    },
                    label = { Text(stringResource(R.string.systolic)) },
                    shape = PredictSleepDisorderTheme.shapes.textField
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = if (features.diastolicBP.toInt() > 0) features.diastolicBP.toInt()
                        .toString() else "",
                    onValueChange = {
                        val value = it.filter { c -> c.isDigit() }
                        viewModel.updateDiastolicBP(value.toFloatOrNull() ?: 0f)
                    },
                    label = { Text(stringResource(R.string.diastolic)) },
                    shape = PredictSleepDisorderTheme.shapes.textField
                )
            }
            Button(onClick = {
                Log.d("PredictSleepDisorderApp", "Predict button clicked")
                viewModel.makePrediction()
            }, modifier = Modifier.fillMaxWidth(), shape = PredictSleepDisorderTheme.shapes.textField) {
                Text(text = stringResource(R.string.predict), modifier = Modifier.padding(8.dp))
            }
            if (uiState.predictionResult != null) {
                Text(
                    text = stringResource(
                        R.string.prediction_result,
                    ),
                    style = PredictSleepDisorderTheme.typography.bodyLarge
                )
                Text(
                    uiState.predictionResult!!.predictedClass,
                    style = PredictSleepDisorderTheme.typography.bodyMedium
                )
            }
        }
    }
}

//@Preview
//@Composable
//private fun PredictSleepDisorderAppPreview() {
//    PredictSleepDisorderApp(viewModel = AppViewModel())
//}