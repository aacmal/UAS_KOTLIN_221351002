package me.acml.predictsleepdisorder.ui.screens.predict

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import me.acml.predictsleepdisorder.R
import me.acml.predictsleepdisorder.ui.AppViewModel
import me.acml.predictsleepdisorder.ui.LocalNavAnimatedVisibilityScope
import me.acml.predictsleepdisorder.ui.LocalSharedTransitionScope
import me.acml.predictsleepdisorder.ui.libs.PredictBoundsKey
import me.acml.predictsleepdisorder.ui.libs.PredictResultBoundsKey
import me.acml.predictsleepdisorder.ui.libs.boundsTransform
import me.acml.predictsleepdisorder.ui.libs.nonSpatialExpressiveSpring
import me.acml.predictsleepdisorder.ui.libs.spatialExpressiveSpring
import me.acml.predictsleepdisorder.ui.theme.backgroundPrimary
import me.acml.predictsleepdisorder.ui.theme.foregroundPrimary

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PredictScreen(viewModel: AppViewModel, back: () -> Unit, toResult: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val features = uiState.features
    val step = uiState.step

    val context = LocalContext.current

    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedTransitionScope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No SharedElementScope found")

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

    fun onBack() {
        if (step > 1) {
            viewModel.previousStep()
        } else {
            back()
            viewModel.reset()
        }
    }

    fun onProceed() {
        try {
            features.validate()
            toResult()
        } catch (e: IllegalArgumentException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            return
        }
    }

    BackHandler {
        onBack()
    }


    with(sharedTransitionScope) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors().copy(
                        containerColor = Color.Transparent, titleContentColor = Color.White
                    ),
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { onBack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = foregroundPrimary
                            )
                        }
                    },
                    actions = {
                        AnimatedContent(
                            targetState = step,
                            label = "PredictStepAction",
                            transitionSpec = {
                                scaleIn(
                                    animationSpec = spatialExpressiveSpring(),
                                    initialScale = 0.8f
                                ) + fadeIn(nonSpatialExpressiveSpring()) togetherWith
                                        scaleOut(
                                            animationSpec = spatialExpressiveSpring(),
                                            targetScale = 0.8f
                                        ) + fadeOut(nonSpatialExpressiveSpring())
                            }
                        ) { target ->
                            if (target == 8) {
                                Button(
                                    onClick = {
                                        onProceed()
                                    },
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .sharedBounds(
                                            sharedContentState = rememberSharedContentState(
                                                key = PredictResultBoundsKey
                                            ),
                                            boundsTransform = boundsTransform,
                                            animatedVisibilityScope = animatedVisibilityScope,
                                            exit = fadeOut(nonSpatialExpressiveSpring()),
                                            enter = fadeIn(nonSpatialExpressiveSpring()),
                                            clipInOverlayDuringTransition = OverlayClip(
                                                RoundedCornerShape(
                                                    roundedCornerAnim
                                                )
                                            ),
                                        ),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = foregroundPrimary,
                                        contentColor = backgroundPrimary
                                    ),
                                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                                ) {
                                    Text(
                                        stringResource(R.string.proceed),
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.rounded_sleep_score_24),
                                        modifier = Modifier.size(ButtonDefaults.IconSize),
                                        contentDescription = stringResource(R.string.proceed)
                                    )
                                }
                            } else {
                                TextButton(
                                    onClick = {
                                        viewModel.reset()
                                        back()
                                    },
                                ) {
                                    Text(
                                        stringResource(R.string.cancel_action),
                                        color = foregroundPrimary,
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.rounded_cancel_24),
                                        modifier = Modifier.size(ButtonDefaults.IconSize),
                                        tint = foregroundPrimary,
                                        contentDescription = stringResource(R.string.cancel_action)
                                    )
                                }

                            }
                        }
                    }
                )
            },
            containerColor = backgroundPrimary,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        roundedCornerAnim
                    )
                )
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = PredictBoundsKey
                    ),
                    boundsTransform = boundsTransform,
                    animatedVisibilityScope = animatedVisibilityScope,
                    exit = fadeOut(nonSpatialExpressiveSpring()),
                    enter = fadeIn(nonSpatialExpressiveSpring()),
                    clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(roundedCornerAnim))
                )
        ) { innerPadding ->
            CompositionLocalProvider(
                LocalContentColor provides foregroundPrimary
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    AnimatedContent(
                        targetState = step,
                        label = "PredictStepContent",
                        transitionSpec = {
                            if (targetState > initialState) {
                                slideInHorizontally(
                                    initialOffsetX = { fullWidth -> fullWidth },
                                    animationSpec = spatialExpressiveSpring()
                                ) togetherWith slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> -fullWidth },
                                    animationSpec = spatialExpressiveSpring()
                                )
                            } else {
                                // Previous step: slide dari kiri ke kanan
                                slideInHorizontally(
                                    initialOffsetX = { fullWidth -> -fullWidth },
                                    animationSpec = spatialExpressiveSpring()
                                ) togetherWith slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> fullWidth },
                                    animationSpec = spatialExpressiveSpring()
                                )
                            }
                        }
                    ) { targetState ->
                        when (targetState) {
                            1 -> InputAgeAndGender(
                                age = features.age.toInt(), onAgeChange = {
                                    viewModel.updateAge(it.toFloat())
                                },
                                gender = features.gender.toString(),
                                onGenderChange = {
                                    viewModel.updateGender(it)
                                },
                                onNext = {
                                    viewModel.nextStep()
                                }
                            )

                            2 -> InputSleepDuration(
                                sleepDuration = features.sleepDuration,
                                onSleepDuration = {
                                    viewModel.updateSleepDuration(it.toFloat())
                                },
                                onNext = {
                                    viewModel.nextStep()
                                }
                            )

                            3 -> InputActivityLevel(
                                activityLevel = features.physicalActivityLevel,
                                onActivityLevelChange = {
                                    viewModel.updatePhysicalActivityLevel(it.toFloat())
                                },
                                dailySteps = features.dailySteps,
                                onDailyStepsChange = {
                                    viewModel.updateDailySteps(it.toFloat())
                                },
                                onNext = {
                                    viewModel.nextStep()
                                }
                            )

                            4 -> InputHeartAndBloodPressureProperties(
                                heartRate = features.heartRate,
                                onHeartRateChange = {
                                    viewModel.updateHeartRate(it.toFloat())
                                },
                                systolic = features.systolicBP,
                                onSystolicChange = {
                                    viewModel.updateSystolicBP(it.toFloat())
                                },
                                diastolic = features.diastolicBP,
                                onDiastolicChange = {
                                    viewModel.updateDiastolicBP(it.toFloat())
                                },
                                onNext = {
                                    viewModel.nextStep()
                                }
                            )

                            5 -> InputSleepQuality(
                                sleepQuality = features.qualityOfSleep,
                                onSleepQualityChange = {
                                    viewModel.updateQualityOfSleep(it.toFloat())
                                },
                                onNext = {
                                    viewModel.nextStep()
                                }
                            )

                            6 -> InputStressLevel(
                                stressLevel = features.stressLevel,
                                onStressLevelChange = {
                                    viewModel.updateStressLevel(it.toFloat())
                                },
                                onNext = {
                                    viewModel.nextStep()
                                }
                            )

                            7 -> InputBMI(
                                bmi = features.bmi,
                                onBmiChange = {
                                    viewModel.updateBMI(it)
                                },
                                onNext = {
                                    viewModel.nextStep()
                                }
                            )

                            8 -> InputOccupation(
                                occupation = features.occupation,
                                onOccupationChange = {
                                    viewModel.updateOccupation(it)
                                }
                            )
                        }
                    }
                }
            }

        }

    }
}

@Composable
fun rememberFieldValidator(
    warningMessageResId: Int = R.string.check_form
): (Any?, () -> Unit) -> Unit {
    val context = LocalContext.current
    val warningMessage = stringResource(warningMessageResId)

    return remember {
        { fieldValue: Any?, onSuccess: () -> Unit ->
            val isValid = when (fieldValue) {
                null -> false
                is String -> fieldValue != "null" && fieldValue.isNotEmpty()
                is Float -> fieldValue >= 0f
                else -> true
            }

            if (!isValid) {
                Toast.makeText(context, warningMessage, Toast.LENGTH_SHORT).show()
            } else {
                onSuccess()
            }
        }
    }
}