package me.acml.predictsleepdisorder.ui.screens.predict

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.acml.predictsleepdisorder.ui.AppViewModel
import me.acml.predictsleepdisorder.ui.LocalNavAnimatedVisibilityScope
import me.acml.predictsleepdisorder.ui.LocalSharedTransitionScope
import me.acml.predictsleepdisorder.ui.libs.PredictBoundsKey
import me.acml.predictsleepdisorder.ui.libs.boundsTransform
import me.acml.predictsleepdisorder.ui.libs.nonSpatialExpressiveSpring
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PredictScreen(viewModel: AppViewModel, back: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val features = uiState.features
    val step = uiState.step

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

    BackHandler {
        if (step > 1) {
            viewModel.previousStep()
        } else {
            back()
        }
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
                        IconButton(onClick = { back() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Localized description",
                                tint = PredictSleepDisorderTheme.colors.onPrimary
                            )
                        }
                    },
                )
            },
            containerColor = PredictSleepDisorderTheme.colors.primary,
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (step) {
                    1 -> StepOne(
                        age = features.age.toInt(), onAgeChange = {
                            viewModel.updateAge(it.toFloat())
                        },
                        gender = features.gender.toString(),
                        onGenderChange = {
                            viewModel.updateGender(it)
                        }
                    )
                }
            }

        }

    }
}