package me.acml.predictsleepdisorder.ui.libs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

object Destination {
    const val HOME = "home"
    const val PREDICT = "predict"
    const val DATASETS = "datasets"
    const val ABOUT = "about"
    const val FEATURES = "features"
}

@Composable
fun rememberPredictSleepDisorderController(
    navController: NavHostController = rememberNavController()
): PredictSleepDisorderController = remember(navController) {
    PredictSleepDisorderController(navController)
}

/**
 * Responsible for holding UI Navigation logic.
 */
@Stable
class PredictSleepDisorderController(
    val navController: NavHostController,
) {

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateTo(from: NavBackStackEntry, destination: String) {
        if (from.lifecycleIsResumed()) {
            navController.navigate(destination)
        }
    }

    fun navigateToPredict(from: NavBackStackEntry, addNewCategory: Boolean = true) {
        if (from.lifecycleIsResumed()) {
            navController.navigate(Destination.PREDICT)
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

