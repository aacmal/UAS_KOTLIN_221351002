package me.acml.predictsleepdisorder.ui.screens.predict

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.acml.predictsleepdisorder.R
import me.acml.predictsleepdisorder.ui.components.HorizontalWheel
import me.acml.predictsleepdisorder.ui.components.VerticalWheel
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme

@Composable
fun InputHeartAndBloodPressureProperties(
    heartRate: Float,
    onHeartRateChange: (Float) -> Unit = {},
    systolic: Float,
    onSystolicChange: (Float) -> Unit = {},
    diastolic: Float,
    onDiastolicChange: (Float) -> Unit = {},
    onNext: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .then(
                Modifier.padding(horizontal = 16.dp)
            )
    ) {
        Text(
            stringResource(R.string.enter_heart_rate),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            style = PredictSleepDisorderTheme.typography.titleLarge.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
            )
        )
        HorizontalWheel(
            value = heartRate,
            onValueChange = {
                onHeartRateChange(it)
            },
            ranges = (65..90 step 5).map { it.toFloat() }.toList()
        )
        Text(
            stringResource(R.string.bpm),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            color = Color.White.copy(alpha = 0.7f),
            style = PredictSleepDisorderTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            stringResource(R.string.blood_preasure_question),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(),
            style = PredictSleepDisorderTheme.typography.titleLarge.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
            )
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            VerticalWheel(
                value = systolic,
                onValueChange = onSystolicChange,
                ranges = (115..140 step 2).map { it.toFloat() }.toList(),
            )
            Text(
                text = "/",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically),
                style = PredictSleepDisorderTheme.typography.titleLarge
            )
            VerticalWheel(
                value = diastolic,
                onValueChange = onDiastolicChange,
                ranges = (75..95 step 2).map { it.toFloat() }.toList(),
            )
        }
        Text(
            stringResource(R.string.mmhg),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(),
            color = Color.White.copy(alpha = 0.7f),
            style = PredictSleepDisorderTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                onNext()
            },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = PredictSleepDisorderTheme.colors.primary
            ),
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
        ) {
            Text(
                stringResource(R.string.next_step),
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                modifier = Modifier.size(ButtonDefaults.IconSize),
                contentDescription = stringResource(R.string.next_step)
            )
        }

    }
}