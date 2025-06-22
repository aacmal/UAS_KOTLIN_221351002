package me.acml.predictsleepdisorder.ui.libs

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun readCsvFromAssets(
    context: Context,
    fileName: String,
): List<List<String>> = withContext(Dispatchers.IO) {
    val data = mutableListOf<List<String>>()
    context.assets.open(fileName).bufferedReader().useLines { lines ->
        lines.forEachIndexed { index, line ->
            val values = line.split(",").map { it.trim() }
            data.add(values)
        }
    }
    data
}
