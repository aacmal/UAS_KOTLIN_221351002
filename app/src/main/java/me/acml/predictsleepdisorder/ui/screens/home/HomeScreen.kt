package me.acml.predictsleepdisorder.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.acml.predictsleepdisorder.R
import me.acml.predictsleepdisorder.ui.libs.Destination
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme

data class Menu(
    val name: String,
    val destination: String,
    val icon: Int,
)

val menus = arrayOf(
    Menu("Predict", Destination.PREDICT, R.drawable.rounded_sleep_score_24),
    Menu("Datasets", Destination.DATASETS, R.drawable.rounded_database_24),
)

@Composable
fun HomeScreen(
    navigateTo: (String) -> Unit = {},
) {
    Scaffold { innerPadding ->
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
                Text(
                    "Greetings", style = PredictSleepDisorderTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Button(onClick = {
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(menu.icon),
                                contentDescription = null,
                                modifier = Modifier.padding(16.dp)
                            )
                            Text(
                                menu.name,
                                style = PredictSleepDisorderTheme.typography.bodyLarge,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreen()
}