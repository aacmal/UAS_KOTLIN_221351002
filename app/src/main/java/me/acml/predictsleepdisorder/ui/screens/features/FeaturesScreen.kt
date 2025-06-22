package me.acml.predictsleepdisorder.ui.screens.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.acml.predictsleepdisorder.R
import me.acml.predictsleepdisorder.ui.theme.PredictSleepDisorderTheme

data class Feature(
    val featureName: String,
    val dataType: String,
    val valueType: String,
    val description: String
)

val features = arrayOf(
    Feature(
        featureName = "Person ID",
        dataType = "Int64",
        valueType = "Numerik",
        description = "Identitas unik untuk setiap individu dalam dataset. Fitur ini berfungsi sebagai kunci pembeda agar data masing-masing partisipan tetap terstruktur dan mudah diakses secara spesifik."
    ),
    Feature(
        featureName = "Gender",
        dataType = "Object",
        valueType = "Kategorikal",
        description = "Jenis kelamin individu, biasanya diisi sebagai 'Male' atau 'Female'. Informasi ini berguna untuk mempelajari perbedaan kebutuhan dan kualitas tidur berdasarkan gender."
    ),
    Feature(
        featureName = "Age",
        dataType = "Int64",
        valueType = "Numerik",
        description = "Usia individu dalam satuan tahun. Faktor usia mempengaruhi kebutuhan durasi tidur dan pola tidur sehari-hari, sehingga fitur ini penting untuk analisis kesehatan."
    ),
    Feature(
        featureName = "Occupation",
        dataType = "Object",
        valueType = "Kategorikal",
        description = "Jenis pekerjaan individu, misalnya Software Engineer, Teacher, atau lainnya. Informasi ini membantu menganalisis hubungan stres pekerjaan dan aktivitas harian terhadap kualitas tidur."
    ),
    Feature(
        featureName = "Sleep Duration",
        dataType = "Float64",
        valueType = "Numerik",
        description = "Durasi tidur individu per hari dalam satuan jam. Fitur ini merupakan indikator utama kualitas istirahat dan kesehatan, serta dapat digunakan untuk melihat kecukupan tidur seseorang."
    ),
    Feature(
        featureName = "Quality of Sleep",
        dataType = "Int64",
        valueType = "Numerik",
        description = "Skor kualitas tidur individu, biasanya dalam skala 1 hingga 10. Skor ini mencerminkan seberapa nyenyak dan nyaman tidur seseorang dalam semalam."
    ),
    Feature(
        featureName = "Physical Activity Level",
        dataType = "Int64",
        valueType = "Numerik",
        description = "Tingkat aktivitas fisik individu per hari, misalnya jumlah menit berolahraga. Aktivitas fisik sangat berpengaruh terhadap kualitas tidur dan tingkat stres seseorang."
    ),
    Feature(
        featureName = "Stress Level",
        dataType = "Int64",
        valueType = "Numerik",
        description = "Tingkat stres individu dalam skala numerik, umumnya 1 hingga 10. Semakin tinggi nilainya, semakin besar stres yang dialami dan berpengaruh negatif terhadap kualitas tidur dan kesehatan."
    ),
    Feature(
        featureName = "BMI Category",
        dataType = "Object",
        valueType = "Kategorikal",
        description = "Kategori Indeks Massa Tubuh seperti 'Normal', 'Overweight', atau 'Obese'. Informasi ini berguna untuk melihat hubungan berat badan dan risiko gangguan tidur serta kesehatan secara keseluruhan."
    ),
    Feature(
        featureName = "Blood Pressure",
        dataType = "Object",
        valueType = "Kategorikal",
        description = "Tekanan darah dalam format Sistolik/Diastolik, contoh '120/80'. Fitur ini mencerminkan kondisi kardiovaskular individu dan dampaknya terhadap kesehatan dan kualitas tidur."
    ),
    Feature(
        featureName = "Heart Rate",
        dataType = "Int64",
        valueType = "Numerik",
        description = "Detak jantung per menit (bpm) individu. Heart rate berguna untuk mengetahui kesehatan jantung dan tingkat kebugaran tubuh yang mempengaruhi tidur dan stres."
    ),
    Feature(
        featureName = "Daily Steps",
        dataType = "Int64",
        valueType = "Numerik",
        description = "Jumlah langkah harian individu. Fitur ini memberi gambaran tingkat aktivitas dan gaya hidup seseorang, sehingga bisa dikaitkan dengan kualitas tidur dan kesehatan tubuh."
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturesScreen(
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
                    Text(stringResource(R.string.features))
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .then(
                    Modifier.padding(horizontal = 16.dp)
                )
        ) {
            items(features){ feature ->
                FeatureCard(
                    number = features.indexOf(feature) + 1,
                    featureName = feature.featureName,
                    dataType = feature.dataType,
                    valueType = feature.valueType,
                    description = feature.description
                )
            }
        }
    }
}

@Composable
private fun FeatureCard(
    number: Int,
    featureName: String,
    dataType: String,
    valueType: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(30.dp)
                .clip(
                    CircleShape
                )
                .background(PredictSleepDisorderTheme.colors.primaryContainer)
        ) {
            Text(
                text = number.toString(),
                style = PredictSleepDisorderTheme.typography.bodyLarge.copy(
                    color = PredictSleepDisorderTheme.colors.primary,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(30.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(PredictSleepDisorderTheme.colors.primaryContainer)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = featureName,
                    style = PredictSleepDisorderTheme.typography.bodyLarge.copy(
                        color = PredictSleepDisorderTheme.colors.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = dataType,
                        style = PredictSleepDisorderTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = valueType,
                        style = PredictSleepDisorderTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = PredictSleepDisorderTheme.colors.surfaceContainerLow,
            ) {
                Text(
                    text = description,
                    textAlign = TextAlign.Justify,
                    style = PredictSleepDisorderTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
            HorizontalDivider(
                color = PredictSleepDisorderTheme.colors.outlineVariant,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
    }
}

@Composable
@Preview
private fun FeatureCardPreview() {
    FeatureCard(
        number = 1,
        featureName = "Heart Rate",
        dataType = "Continuous",
        valueType = "BPM",
        description = "The heart rate is the number of times the heart beats per minute."
    )
}

@Composable
@Preview
private fun FeaturesScreenPreview() {
    FeaturesScreen()
}