package me.acml.predictsleepdisorder.ml

import android.content.Context
import android.util.Log
import me.acml.predictsleepdisorder.ui.ModelConstants
import org.tensorflow.lite.Interpreter
import java.nio.channels.FileChannel

class SleepDisorderModel(context: Context) {
    private var interpreter: Interpreter

    init {
        val modelFile = context.assets.openFd("sleep_disorder_model.tflite").run {
            val inputStream = createInputStream()
            val fileChannel = inputStream.channel
            fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        }
        interpreter = Interpreter(modelFile)
    }

    fun predict(input: FloatArray): SleepDisorderPrediction {
        Log.d("SleepDisorderModel", "Input features: ${input.joinToString(", ")}")
        val output = Array(1) { FloatArray(3)}
        interpreter.run(arrayOf(input), output)
        val labelIndex = output[0].indices.maxByOrNull { output[0][it] } ?: -1
        Log.d("SleepDisorderModel", "Predicted label index: $labelIndex with confidence ${output[0][labelIndex]}")
        return SleepDisorderPrediction(
            ModelConstants.getPredictedClass(labelIndex),
            output[0][labelIndex] // Confidence score for the predicted class
        )

    }
}