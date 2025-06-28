package me.acml.predictsleepdisorder.ui.screens.home

import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.acml.predictsleepdisorder.R
import me.acml.predictsleepdisorder.ui.LocalNavAnimatedVisibilityScope
import me.acml.predictsleepdisorder.ui.LocalSharedTransitionScope
import me.acml.predictsleepdisorder.ui.libs.Destination
import me.acml.predictsleepdisorder.ui.libs.PredictBoundsKey
import me.acml.predictsleepdisorder.ui.libs.boundsTransform
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme
import java.time.LocalTime

data class Menu(
    val name: String,
    val destination: String,
    val icon: Int,
    val description: String = "",
)

val menus = arrayOf(
    Menu(
        "Datasets",
        Destination.DATASETS,
        R.drawable.rounded_database_24,
        "Lihat dataset yang digunakan untuk melatih model"
    ),
    Menu(
        "Features", Destination.FEATURES, R.drawable.rounded_featured_play_list_24,
        "Lihat fitur yang digunakan untuk melatih model"
    ),
    Menu(
        "Modeling", Destination.MODELING, R.drawable.rounded_model_training_24,
        "Lihat proses membangun model dan arsitektur model yang digunakan"
    )
)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    navigateTo: (String) -> Unit = {},
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedTransitionScope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No SharedElementScope found")

    val roundedCornerAnimation by animatedVisibilityScope.transition
        .animateDp(
            label = "rounded corner",
            transitionSpec = { tween(durationMillis = 500) }) { enterExit: EnterExitState ->
            when (enterExit) {
                EnterExitState.PreEnter -> 0.dp
                EnterExitState.Visible -> 16.dp
                EnterExitState.PostExit -> 16.dp
            }
        }

    with(sharedTransitionScope) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    containerColor = PredictSleepDisorderTheme.colors.primary,
                    contentColor = PredictSleepDisorderTheme.colors.onPrimary,
                    onClick = {
                        navigateTo(Destination.PREDICT)
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.predict),
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.rounded_sleep_score_24),
                            contentDescription = "Predict"
                        )
                    },
                    modifier = Modifier.sharedBounds(
                        sharedContentState = rememberSharedContentState(
                            key = PredictBoundsKey
                        ),
                        boundsTransform = boundsTransform,
                        animatedVisibilityScope = animatedVisibilityScope,
                        clipInOverlayDuringTransition = OverlayClip(
                            RoundedCornerShape(
                                roundedCornerAnimation
                            )
                        ),
                        enter = fadeIn(),
                        exit = fadeOut()
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .then(
                        Modifier.padding(horizontal = 16.dp)
                    )
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Greetings(
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = {
                        navigateTo(Destination.ABOUT)
                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = "Info"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                stringResource(R.string.about),
                                style = PredictSleepDisorderTheme.typography.bodyMedium
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(32.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    menus.map { menu ->
                        Card(
                            onClick = {
                                navigateTo(menu.destination)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                        ) {
                            Row {
                                Icon(
                                    imageVector = ImageVector.vectorResource(menu.icon),
                                    contentDescription = null,
                                    modifier = Modifier.padding(16.dp)
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp)
                                        .weight(1f),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        menu.name,
                                        style = PredictSleepDisorderTheme.typography.bodyLarge,
                                        modifier = Modifier
                                    )
                                    if (menu.description.isNotEmpty()) {
                                        Text(
                                            menu.description,
                                            style = PredictSleepDisorderTheme.typography.bodySmall.copy(
                                                color = PredictSleepDisorderTheme.colors.onSurfaceVariant,
                                            ),
                                            modifier = Modifier.padding(top = 6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class DayTime {
    MORNING,
    AFTERNOON,
    EVENING,
    NIGHT
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier
) {
    val localTimeHour = LocalTime.now().hour
    val dayTime = when (localTimeHour) {
        in 0..11 -> DayTime.MORNING
        in 12..15 -> DayTime.AFTERNOON
        in 16..20 -> DayTime.EVENING
        in 21..23 -> DayTime.NIGHT
        else -> DayTime.MORNING
    }

    Row(
        modifier = modifier.padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                id = when (dayTime) {
                    DayTime.MORNING -> R.drawable.sun
                    DayTime.AFTERNOON -> R.drawable.sun
                    DayTime.EVENING -> R.drawable.sun_fog
                    DayTime.NIGHT -> R.drawable.moon
                }
            ),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = stringResource(
                when (dayTime) {
                    DayTime.MORNING -> R.string.good_morning
                    DayTime.AFTERNOON -> R.string.good_afternoon
                    DayTime.EVENING -> R.string.good_evening
                    DayTime.NIGHT -> R.string.good_night
                }
            ),
            style = PredictSleepDisorderTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}


@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreen()
}