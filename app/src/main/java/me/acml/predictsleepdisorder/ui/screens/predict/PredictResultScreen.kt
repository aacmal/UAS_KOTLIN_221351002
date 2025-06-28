package me.acml.predictsleepdisorder.ui.screens.predict

import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.acml.predictsleepdisorder.R
import me.acml.predictsleepdisorder.ui.AppViewModel
import me.acml.predictsleepdisorder.ui.LocalNavAnimatedVisibilityScope
import me.acml.predictsleepdisorder.ui.LocalSharedTransitionScope
import me.acml.predictsleepdisorder.ui.libs.PredictResultBoundsKey
import me.acml.predictsleepdisorder.ui.libs.boundsTransform
import me.acml.predictsleepdisorder.ui.libs.nonSpatialExpressiveSpring
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme
import me.acml.predictsleepdisorder.ui.theme.textField

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PredictResultScreen(
    toHome: () -> Unit = {},
    toPredict: () -> Unit = {},
    viewModel: AppViewModel,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedTransitionScope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No SharedElementScope found")

    val uiState = viewModel.uiState.collectAsState()
    val predictionResult = uiState.value.predictionResult

    LaunchedEffect(Unit) {
        viewModel.makePrediction()
    }

    if (predictionResult == null) {
        return
    }

    val roundedCornerAnim by animatedVisibilityScope.transition.animateDp(
        label = "rounded corner",
        transitionSpec = {
            tween(
                durationMillis = 800
            )
        }) { enterExit ->
        when (enterExit) {
            EnterExitState.PreEnter -> 50.dp
            EnterExitState.Visible -> 0.dp
            EnterExitState.PostExit -> 50.dp
        }
    }

    with(sharedTransitionScope) {
        Surface(
            modifier = Modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = PredictResultBoundsKey
                    ),
                    boundsTransform = boundsTransform,
                    animatedVisibilityScope = animatedVisibilityScope,
                    exit = fadeOut(nonSpatialExpressiveSpring()),
                    enter = fadeIn(nonSpatialExpressiveSpring()),
                    clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(roundedCornerAnim))
                )
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = when (predictionResult.predictedClass) {
                        "Sleep Apnea" -> "Based on the analysis, you may be experiencing Sleep Apnea. We recommend consulting a healthcare professional for further evaluation."
                        "Insomnia" -> "Based on the analysis, you may be experiencing Insomnia. Consider improving your bedtime routine and seeking professional advice if needed."
                        "Normal" -> "Based on the analysis, no sleep disorder was detected."
                        else -> "No prediction available, please try again."
                    },
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .fillMaxWidth(),
                    style = PredictSleepDisorderTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        lineHeight = 40.sp,
                    )
                )
                Button(
                    onClick = {
                        toHome()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = PredictSleepDisorderTheme.colors.primary
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = PredictSleepDisorderTheme.colors.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .height(50.dp),
                    shape = PredictSleepDisorderTheme.shapes.textField
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.round_home_24),
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        contentDescription = stringResource(R.string.cancel_action)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.back_to_home),
                        style = PredictSleepDisorderTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = {
                        toPredict()
                    },
                    border = BorderStroke(
                        width = 1.dp,
                        color = PredictSleepDisorderTheme.colors.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = PredictSleepDisorderTheme.shapes.textField
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.rounded_sleep_score_24),
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        contentDescription = stringResource(R.string.cancel_action)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.predict_again),
                        style = PredictSleepDisorderTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

//@Composable
//@Preview
//private fun PredictResultScreenPreview() {
//    PredictSleepDisorderTheme {
//        PredictResultScreen()
//    }
//}