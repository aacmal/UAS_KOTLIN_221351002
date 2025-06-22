package me.acml.predictsleepdisorder.ui.screens.datasets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.acml.predictsleepdisorder.R
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme
import me.acml.predictsleepdisorder.ui.theme.textField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatasetsScreen(
    datasets: List<List<String>> = emptyList(),
    back: () -> Unit = {}
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { back() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                title = {
                    Text(stringResource(R.string.datasets))
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .then(
                    Modifier.padding(horizontal = 16.dp)
                )
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))
            Text(
                stringResource(R.string.datasets_description),
                textAlign = TextAlign.Justify,
                style = PredictSleepDisorderTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(20.dp))
            Text(
                stringResource(R.string.preview),
                style = PredictSleepDisorderTheme.typography.bodyLarge.copy(
                    color = PredictSleepDisorderTheme.colors.primary,
                    fontWeight = FontWeight.SemiBold
                ),
            )
            Spacer(Modifier.height(8.dp))
            CsvTable(
                datasets = datasets.slice(0..5)
            )
            Spacer(Modifier.height(5.dp))
            FullDatasets(
                datasets = datasets
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullDatasets(datasets: List<List<String>>) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Button(
        onClick = {
            showBottomSheet = true
        },
        modifier = Modifier.fillMaxWidth(),
        shape = PredictSleepDisorderTheme.shapes.textField
    ) {
        Text(
            text = stringResource(R.string.view_all),
            style = PredictSleepDisorderTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center
        )
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                CsvTable(
                    datasets = datasets
                )
            }
        }
    }

}

@Composable
fun CsvTable(
    datasets: List<List<String>>,
) {
    val headers = datasets.firstOrNull() ?: emptyList()
    val bodyData = if (datasets.size > 1) datasets.drop(1) else emptyList()

    ResponsiveRoundedTable(
        headers = headers,
        data = bodyData
    )
}

@Composable
fun ResponsiveRoundedTable(
    headers: List<String>,
    data: List<List<String>>
) {
    Column(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()) // scroll horizontal
            .border(
                width = 1.dp,
                color = Color.Gray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp) // border rounded
            )
            .clip(RoundedCornerShape(8.dp)) // clip agar konten mengikuti bentuk rounded
    ) {
        // Header Row
        Row(modifier = Modifier.background(Color.Gray.copy(alpha = 0.2f))) {
            headers.forEach { headerText ->
                TableCell(
                    text = headerText,
                    isHeader = true
                )
            }
        }

        // Data Rows
        data.forEach { rowData ->
            Row {
                rowData.forEach { cellData ->
                    TableCell(
                        text = cellData,
                        isHeader = false
                    )
                }
            }
        }
    }
}

@Composable
fun TableCell(
    text: String,
    isHeader: Boolean = false
) {
    Box(
        modifier = Modifier
            .width(180.dp)
            .border(0.5.dp, Color.Gray.copy(alpha = 0.5f)) // border per cell
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = if (isHeader) {
                MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            } else {
                MaterialTheme.typography.bodyMedium
            }
        )
    }
}

@Composable
@Preview
private fun DatasetsScreenPreview() {
    DatasetsScreen()
}