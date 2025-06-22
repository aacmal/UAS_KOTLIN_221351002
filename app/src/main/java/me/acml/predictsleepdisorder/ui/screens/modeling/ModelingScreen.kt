package me.acml.predictsleepdisorder.ui.screens.modeling

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.acml.predictsleepdisorder.R
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme

data class ModelingContent(
    val title: String,
    val description: List<String>,
    val image: Int
)

val contents = arrayOf(
    ModelingContent(
        title = "Pre-processing",
        description = listOf(
            "Data preprocessing pada kode ini meliputi transformasi Blood Pressure menjadi dua kolom terpisah (Systolic dan Diastolic)",
            "Encoding variabel kategorikal menggunakan LabelEncoder untuk Gender, Occupation, dan BMI Category",
            "Penting: Preprocessing normalisasi akan dilakukan di dalam model menggunakan layer tf.keras.layers.Normalization, sehingga tidak perlu mentransform ulang nilai input saat inference"
        ),
        image = R.drawable.preprocessing
    ),
    ModelingContent(
        title = "Labeling",
        description = listOf(
            "Fitur (X): 12 fitur yang terdiri dari 9 fitur numerik dan 3 fitur hasil encoding kategorikal",
            "Label (y): Sleep Disorder yang di-encode menjadi 3 kelas (biasanya: None, Insomnia, Sleep Apnea)",
            "Semua fitur dikonversi ke tipe float32 untuk kompatibilitas dengan TensorFlow"
        ),
        image = R.drawable.labeling
    ),
    ModelingContent(
        title = "Data Splitting",
        description = listOf(),
        image = R.drawable.data_splitting
    ),
    ModelingContent(
        title = "Arsitektur Model",
        description = listOf(
            "Input Layer: Menerima 12 fitur",
            "Normalization Layer: Melakukan standardisasi data secara otomatis (preprocessing built-in)",
            "Hidden Layer 1: Dense layer dengan 64 neuron, aktivasi ReLU",
            "Dropout 1: Dropout rate 30% untuk mencegah overfitting",
            "Hidden Layer 2: Dense layer dengan 32 neuron, aktivasi ReLU",
            "Dropout 2: Dropout rate 20%",
            "Hidden Layer 3: Dense layer dengan 16 neuron, aktivasi ReLU",
            "Output Layer: 3 neuron dengan aktivasi softmax untuk klasifikasi 3 kelas",
        ),
        image = R.drawable.model_architecture
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelingScreen(
    back: () -> Unit = {}
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { back() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(stringResource(R.string.modeling))
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
                stringResource(R.string.overview),
                style = PredictSleepDisorderTheme.typography.bodyLarge.copy(
                    color = PredictSleepDisorderTheme.colors.primary,
                    fontWeight = FontWeight.SemiBold
                ),
            )
            Spacer(Modifier.height(8.dp))
            Text(
                stringResource(R.string.model_architecture_description),
                textAlign = TextAlign.Justify,
                style = PredictSleepDisorderTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(16.dp))
            contents.forEach { content ->
                ModelingContentItem(
                    title = content.title,
                    description = content.description,
                    image = content.image
                )
            }
        }
    }
}

@Composable
private fun ModelingContentItem(
    title: String,
    description: List<String>,
    image: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            title,
            style = PredictSleepDisorderTheme.typography.bodyLarge.copy(
                color = PredictSleepDisorderTheme.colors.primary,
                fontWeight = FontWeight.SemiBold
            ),
        )
        Spacer(Modifier.height(8.dp))
        Image(
            painter = painterResource(id = image),
            contentDescription = title,
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(8.dp))
        description.forEach { desc ->
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Spacer(
                    Modifier
                        .padding(top = 6.dp)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(PredictSleepDisorderTheme.colors.primary)
                )
                Text(
                    desc,
                    style = PredictSleepDisorderTheme.typography.bodyMedium
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp,
            color = PredictSleepDisorderTheme.colors.outlineVariant
        )
    }
}

@Composable
@Preview
private fun ModelingScreenPreview() {
    ModelingScreen()
}