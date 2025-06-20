package me.acml.predictsleepdisorder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import me.acml.predictsleepdisorder.ml.SleepDisorderModel
import me.acml.predictsleepdisorder.ui.AppViewModel
import me.acml.predictsleepdisorder.ui.AppViewModelFactory
import me.acml.predictsleepdisorder.ui.PredictSleepDisorderApp
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val sleepDisorderModel = SleepDisorderModel(this)
            val factory = AppViewModelFactory(sleepDisorderModel)
            val appViewModel: AppViewModel = viewModel(factory = factory)

            PredictSleepDisorderTheme {
                PredictSleepDisorderApp(viewModel = appViewModel)
            }
        }
    }
}