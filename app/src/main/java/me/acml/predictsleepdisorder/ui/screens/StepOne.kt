package me.acml.predictsleepdisorder.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.acml.predictsleepdisorder.R
import me.acml.predictsleepdisorder.ui.components.AgePicker
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme

@Composable
fun StepOne(
    age: Int,
    onAgeChange: (Int) -> Unit,
) {
    Scaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .then(
                    Modifier.padding(horizontal = 16.dp)
                )
        ) {
            Text(
                stringResource(R.string.enter_your_age),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                style = PredictSleepDisorderTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 45.sp,
                    lineHeight = 50.sp,
                )
            )
            AgePicker(
                value = age,
                onValueChange = onAgeChange,
            )
                Spacer(modifier = Modifier.height(10.dp))
            IconButton(onClick = {}, modifier = Modifier.align(Alignment.End)) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = stringResource(R.string.next_step))
            }

        }
    }
}

@Composable
@Preview
private fun StepOnePreview() {
    StepOne(
        age = 30,
        onAgeChange = {}
    )
}