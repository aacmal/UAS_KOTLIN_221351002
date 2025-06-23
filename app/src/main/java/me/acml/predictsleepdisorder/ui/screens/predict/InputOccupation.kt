package me.acml.predictsleepdisorder.ui.screens.predict

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.acml.predictsleepdisorder.R
import me.acml.predictsleepdisorder.ui.ModelConstants
import me.acml.predictsleepdisorder.ui.components.SelectAnimated
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme

@Composable
fun InputOccupation(
    occupation: String?,
    onOccupationChange: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            stringResource(R.string.inptut_occupation),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            style = PredictSleepDisorderTheme.typography.titleLarge.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
            )
        )
        SelectAnimated(
            items = ModelConstants.OCCUPATION_MAPPING.keys.toList(),
            selectedItem = occupation,
            onSelectionChange = { selected ->
                onOccupationChange(selected)
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}