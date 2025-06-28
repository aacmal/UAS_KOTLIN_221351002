package me.acml.predictsleepdisorder.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.acml.predictsleepdisorder.ui.libs.Destination
import me.acml.predictsleepdisorder.ui.libs.nonSpatialExpressiveSpring
import me.acml.predictsleepdisorder.ui.libs.rememberPredictSleepDisorderController
import me.acml.predictsleepdisorder.ui.libs.spatialExpressiveSpring
import me.acml.predictsleepdisorder.ui.screens.about.AboutScreen
import me.acml.predictsleepdisorder.ui.screens.datasets.DatasetsScreen
import me.acml.predictsleepdisorder.ui.screens.features.FeaturesScreen
import me.acml.predictsleepdisorder.ui.screens.home.HomeScreen
import me.acml.predictsleepdisorder.ui.screens.modeling.ModelingScreen
import me.acml.predictsleepdisorder.ui.screens.predict.PredictResultScreen
import me.acml.predictsleepdisorder.ui.screens.predict.PredictScreen
import me.acml.predictsleepdisorder.ui.screens.welcome.WelcomeScreen


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PredictSleepDisorderApp(viewModel: AppViewModel) {
    val navController = rememberPredictSleepDisorderController()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadCsvData(context)
    }

    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this@SharedTransitionLayout,
        ) {
            NavHost(
                navController = navController.navController,
                startDestination = Destination.WELCOME,
            ) {
                composableWithCompositionLocal(
                    route = Destination.WELCOME,
                    enterTransition = {
                        slideIn(
                            initialOffset = { fullSize ->
                                IntOffset(
                                    x = fullSize.width,
                                    y = 0
                                )
                            },
                            animationSpec = spatialExpressiveSpring()
                        )
                    },
                    exitTransition = {
                        slideOut(
                            targetOffset = { fullSize ->
                                IntOffset(
                                    x = fullSize.width,
                                    y = 0
                                )
                            },
                            animationSpec = spatialExpressiveSpring()
                        )
                    }
                ){
                    WelcomeScreen(
                        toHome = {
                            navController.navigateToHome()
                        }
                    )
                }
                composableWithCompositionLocal(
                    route = Destination.HOME,
                    enterTransition = null, exitTransition = null
                ) { backStackEntry ->
                    HomeScreen(
                        navigateTo = { navController.navigateTo(backStackEntry, it) })
                }
                composableWithCompositionLocal(
                    route = Destination.PREDICT
                ) { backStackEntry ->
                    PredictScreen(
                        viewModel = viewModel,
                        back = {
                            navController.upPress()
                        },
                        toResult = {
                            navController.navigateToPredictionResult()
                        }
                    )
                }
                composableWithCompositionLocal(
                    route = Destination.ABOUT,
                    enterTransition = {
                        slideIn(
                            initialOffset = { fullSize ->
                                IntOffset(
                                    x = fullSize.width,
                                    y = 0
                                )
                            },
                            animationSpec = spatialExpressiveSpring()
                        )
                    },
                    exitTransition = {
                        slideOut(
                            targetOffset = { fullSize ->
                                IntOffset(
                                    x = fullSize.width,
                                    y = 0
                                )
                            },
                            animationSpec = spatialExpressiveSpring()
                        )
                    }
                ) { backStackEntry ->
                    AboutScreen(
                        back = {
                            navController.upPress()
                        }
                    )
                }
                composableWithCompositionLocal(
                    route = Destination.DATASETS,
                    enterTransition = {
                        slideIn(
                            initialOffset = { fullSize ->
                                IntOffset(
                                    x = 0,
                                    y = fullSize.height
                                )
                            },
                            animationSpec = spatialExpressiveSpring()
                        )
                    },
                    exitTransition = {
                        slideOut(
                            targetOffset = { fullSize ->
                                IntOffset(
                                    x = 0,
                                    y = fullSize.height
                                )
                            },
                            animationSpec = spatialExpressiveSpring()
                        )
                    }
                ) { backStackEntry ->
                    DatasetsScreen(
                        datasets = viewModel.datasets.collectAsState().value,
                        back = {
                            navController.upPress()
                        }
                    )
                }
                composableWithCompositionLocal(
                    route = Destination.FEATURES,
                    enterTransition = {
                        slideIn(
                            initialOffset = { fullSize ->
                                IntOffset(
                                    x = 0,
                                    y = fullSize.height
                                )
                            },
                            animationSpec = spatialExpressiveSpring()
                        )
                    },
                    exitTransition = {
                        slideOut(
                            targetOffset = { fullSize ->
                                IntOffset(
                                    x = 0,
                                    y = fullSize.height
                                )
                            },
                            animationSpec = spatialExpressiveSpring()
                        )
                    }
                ) { backStackEntry ->
                    FeaturesScreen(
                        back = {
                            navController.upPress()
                        }
                    )
                }
                composableWithCompositionLocal(
                    route = Destination.MODELING,
                    enterTransition = {
                        slideIn(
                            initialOffset = { fullSize ->
                                IntOffset(
                                    x = 0,
                                    y = fullSize.height
                                )
                            },
                            animationSpec = spatialExpressiveSpring()
                        )
                    },
                    exitTransition = {
                        slideOut(
                            targetOffset = { fullSize ->
                                IntOffset(
                                    x = 0,
                                    y = fullSize.height
                                )
                            },
                            animationSpec = spatialExpressiveSpring()
                        )
                    }
                ) { backStackEntry ->
                    ModelingScreen(
                        back = {
                            navController.upPress()
                        }
                    )
                }
                composableWithCompositionLocal(
                    route = Destination.PREDICT_RESULT,
                    exitTransition = {
                        slideOut(
                            targetOffset = { fullSize ->
                                IntOffset(
                                    x = 0,
                                    y = fullSize.height
                                )
                            },
                            animationSpec = spatialExpressiveSpring()
                        )
                    }
                ) {
                    PredictResultScreen(
                        toHome = {
                            navController.navigateToHome()
                        },
                        toPredict = {
                            navController.navigateToPredict()
                        },
                        viewModel = viewModel,
                    )
                }
            }
        }

    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PredictSleepDisorderApp(viewModel: AppViewModel) {
//    val uiState by viewModel.uiState.collectAsState()
//    val features = uiState.features
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "Predict Sleep Disorder",
//                        style = PredictSleepDisorderTheme.typography.titleLarge
//                    )
//                })
//        }) { innerPadding ->
//        Column(
//            verticalArrangement = Arrangement.spacedBy(12.dp),
//            modifier = Modifier
//                .padding(innerPadding)
//                .then(Modifier.padding(horizontal = 16.dp))
//                .fillMaxWidth()
//        ) {
//            OutlinedTextField(
//                modifier = Modifier.fillMaxWidth(),
//                value = if (features.age.toInt() > 0) features.age.toInt().toString() else "",
//                onValueChange = {
//                    Log.d("PredictSleepDisorderApp", "Age input: $it")
//                    val value = it.filter { c -> c.isDigit() }
//                    viewModel.updateAge(value.toFloatOrNull() ?: 0f)
//                },
//                label = { Text(stringResource(R.string.age)) },
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Number
//                ),
//                shape = PredictSleepDisorderTheme.shapes.textField
//            )
//            OutlinedTextField(
//                modifier = Modifier.fillMaxWidth(),
//                value = if (features.sleepDuration.toInt() > 0) features.sleepDuration.toInt()
//                    .toString() else "",
//                onValueChange = {
//                    val value = it.filter { c -> c.isDigit() }
//                    viewModel.updateSleepDuration(value.toFloatOrNull() ?: 0f)
//                },
//                label = { Text(stringResource(R.string.sleep_duration)) },
//                shape = PredictSleepDisorderTheme.shapes.textField
//            )
//            OutlinedTextField(
//                modifier = Modifier.fillMaxWidth(),
//                value = if (features.qualityOfSleep.toInt() > 0) features.qualityOfSleep.toInt()
//                    .toString() else "",
//                onValueChange = {
//                    val value = it.filter { c -> c.isDigit() }
//                    viewModel.updateQualityOfSleep(value.toFloatOrNull() ?: 0f)
//                },
//                label = { Text(stringResource(R.string.quality_of_sleep)) },
//                shape = PredictSleepDisorderTheme.shapes.textField
//            )
//            OutlinedTextField(
//                modifier = Modifier.fillMaxWidth(),
//                value = if (features.physicalActivityLevel.toInt() > 0) features.physicalActivityLevel.toInt()
//                    .toString() else "",
//                onValueChange = {
//                    val value = it.filter { c -> c.isDigit() }
//                    viewModel.updatePhysicalActivityLevel(value.toFloatOrNull() ?: 0f)
//                },
//                label = { Text(stringResource(R.string.physical_activity)) },
//                shape = PredictSleepDisorderTheme.shapes.textField
//            )
//            OutlinedTextField(
//                modifier = Modifier.fillMaxWidth(),
//                value = if (features.stressLevel.toInt() > 0) features.stressLevel.toInt()
//                    .toString() else "",
//                onValueChange = {
//                    val value = it.filter { c -> c.isDigit() }
//                    viewModel.updateStressLevel(value.toFloatOrNull() ?: 0f)
//                },
//                label = { Text(stringResource(R.string.stress_level)) },
//                shape = PredictSleepDisorderTheme.shapes.textField
//            )
//            OutlinedTextField(
//                modifier = Modifier.fillMaxWidth(),
//                value = if (features.heartRate.toInt() > 0) features.heartRate.toInt()
//                    .toString() else "",
//                onValueChange = {
//                    val value = it.filter { c -> c.isDigit() }
//                    viewModel.updateHeartRate(value.toFloatOrNull() ?: 0f)
//                },
//                label = { Text(stringResource(R.string.heart_rate)) },
//                shape = PredictSleepDisorderTheme.shapes.textField
//            )
//            OutlinedTextField(
//                modifier = Modifier.fillMaxWidth(),
//                value = if (features.dailySteps.toInt() > 0) features.dailySteps.toInt()
//                    .toString() else "",
//                onValueChange = {
//                    val value = it.filter { c -> c.isDigit() }
//                    viewModel.updateDailySteps(value.toFloatOrNull() ?: 0f)
//                },
//                label = { Text(stringResource(R.string.daily_steps)) },
//                shape = PredictSleepDisorderTheme.shapes.textField
//            )
//            Row(
//               horizontalArrangement = Arrangement.spacedBy(8.dp),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                OutlinedTextField(
//                    modifier = Modifier.weight(1f),
//                    value = if (features.systolicBP.toInt() > 0) features.systolicBP.toInt()
//                        .toString() else "",
//                    onValueChange = {
//                        val value = it.filter { c -> c.isDigit() }
//                        viewModel.updateSystolicBP(value.toFloatOrNull() ?: 0f)
//                    },
//                    label = { Text(stringResource(R.string.systolic)) },
//                    shape = PredictSleepDisorderTheme.shapes.textField
//                )
//                OutlinedTextField(
//                    modifier = Modifier.weight(1f),
//                    value = if (features.diastolicBP.toInt() > 0) features.diastolicBP.toInt()
//                        .toString() else "",
//                    onValueChange = {
//                        val value = it.filter { c -> c.isDigit() }
//                        viewModel.updateDiastolicBP(value.toFloatOrNull() ?: 0f)
//                    },
//                    label = { Text(stringResource(R.string.diastolic)) },
//                    shape = PredictSleepDisorderTheme.shapes.textField
//                )
//            }
//            Button(onClick = {
//                Log.d("PredictSleepDisorderApp", "Predict button clicked")
//                viewModel.makePrediction()
//            }, modifier = Modifier.fillMaxWidth(), shape = PredictSleepDisorderTheme.shapes.textField) {
//                Text(text = stringResource(R.string.predict), modifier = Modifier.padding(8.dp))
//            }
//            if (uiState.predictionResult != null) {
//                Text(
//                    text = stringResource(
//                        R.string.prediction_result,
//                    ),
//                    style = PredictSleepDisorderTheme.typography.bodyLarge
//                )
//                Text(
//                    uiState.predictionResult!!.predictedClass,
//                    style = PredictSleepDisorderTheme.typography.bodyMedium
//                )
//            }
//        }
//    }
//}

//@Preview
//@Composable
//private fun PredictSleepDisorderAppPreview() {
//    PredictSleepDisorderApp(viewModel = AppViewModel())
//}

fun NavGraphBuilder.composableWithCompositionLocal(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        fadeIn(nonSpatialExpressiveSpring())
    },
    exitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        fadeOut(nonSpatialExpressiveSpring())
    },
    popEnterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    popExitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route,
        arguments,
        deepLinks,
        enterTransition,
        exitTransition,
        popEnterTransition,
        popExitTransition
    ) {
        CompositionLocalProvider(
            LocalNavAnimatedVisibilityScope provides this@composable
        ) {
            content(it)
        }
    }
}

val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }
