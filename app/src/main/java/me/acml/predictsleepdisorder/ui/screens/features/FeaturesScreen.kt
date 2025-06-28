package me.acml.predictsleepdisorder.ui.screens.features

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
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
        description = "Pengenal unik untuk setiap individu dalam dataset. Fitur ini digunakan untuk membedakan setiap partisipan secara spesifik agar data tetap terstruktur dan tidak terjadi duplikasi identitas."
    ),
    Feature(
        featureName = "Gender",
        dataType = "Object",
        valueType = "Kategorikal",
        description = "Jenis kelamin dari individu, biasanya tercatat sebagai 'Male' (Laki-laki) atau 'Female' (Perempuan). Informasi ini dapat membantu dalam menganalisis perbedaan pola tidur dan kesehatan berdasarkan gender."
    ),
    Feature(
        featureName = "Age",
        dataType = "Int64",
        valueType = "Numerik",
        description = "Usia individu dalam satuan tahun. Usia memiliki pengaruh signifikan terhadap kebutuhan tidur dan kemungkinan munculnya gangguan tidur tertentu."
    ),
    Feature(
        featureName = "Occupation",
        dataType = "Object",
        valueType = "Kategorikal",
        description = "Pekerjaan atau profesi dari individu, seperti Guru, Dokter, atau Software Engineer. Data ini berguna untuk mengkaji hubungan antara tekanan pekerjaan dan kualitas tidur."
    ),
    Feature(
        featureName = "Sleep Duration",
        dataType = "Float64",
        valueType = "Numerik",
        description = "Jumlah jam tidur individu dalam sehari. Durasi tidur merupakan salah satu indikator utama dalam menilai kecukupan istirahat dan kesejahteraan fisik serta mental."
    ),
    Feature(
        featureName = "Quality of Sleep",
        dataType = "Int64",
        valueType = "Numerik",
        description = "Penilaian subjektif terhadap kualitas tidur seseorang dalam skala 1 hingga 10. Nilai yang lebih tinggi menunjukkan tidur yang lebih nyenyak dan memulihkan."
    ),
    Feature(
        featureName = "Physical Activity Level",
        dataType = "Int64",
        valueType = "Numerik",
        description = "Lama waktu aktivitas fisik yang dilakukan individu per hari, diukur dalam menit. Aktivitas fisik berperan penting dalam menjaga kesehatan serta meningkatkan kualitas tidur."
    ),
    Feature(
        featureName = "Stress Level",
        dataType = "Int64",
        valueType = "Numerik",
        description = "Tingkat stres yang dirasakan oleh individu, dinilai dalam skala 1 hingga 10. Skor yang lebih tinggi menunjukkan tingkat stres yang lebih besar dan dapat berdampak negatif pada tidur dan kesehatan."
    ),
    Feature(
        featureName = "BMI Category",
        dataType = "Object",
        valueType = "Kategorikal",
        description = "Kategori Indeks Massa Tubuh individu, seperti 'Underweight', 'Normal', atau 'Overweight'. Kategori ini memberikan indikasi terhadap status berat badan dan potensi risiko kesehatan terkait."
    ),
    Feature(
        featureName = "Blood Pressure",
        dataType = "Object",
        valueType = "Kategorikal",
        description = "Tekanan darah individu yang dituliskan dalam format Sistolik/Diastolik, seperti '120/80'. Informasi ini mencerminkan kesehatan kardiovaskular yang dapat berhubungan dengan gangguan tidur."
    ),
    Feature(
        featureName = "Heart Rate",
        dataType = "Int64",
        valueType = "Numerik",
        description = "Detak jantung saat istirahat, diukur dalam denyut per menit (bpm). Detak jantung memberikan gambaran kondisi jantung dan kebugaran yang memengaruhi kualitas tidur dan tingkat stres."
    ),
    Feature(
        featureName = "Daily Steps",
        dataType = "Int64",
        valueType = "Numerik",
        description = "Jumlah langkah yang diambil individu dalam sehari. Fitur ini merefleksikan tingkat aktivitas fisik harian dan gaya hidup yang berdampak pada kualitas tidur dan kesehatan secara keseluruhan."
    )
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturesScreen(
    back: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
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
                    contentColor = when (dataType) {
                        "Object" -> Color(0xFFF38181)
                        "Int64" -> Color(0xFF3F72AF)
                        "Float64" -> Color(0xFFA4907C)
                        else -> PredictSleepDisorderTheme.colors.onSurface
                    },
                    color = when (dataType) {
                        "Object" -> Color(0xFFF38181).copy(alpha = 0.2f)
                        "Int64" -> Color(0xFF3F72AF).copy(alpha = 0.2f)
                        "Float64" -> Color(0xFFA4907C).copy(alpha = 0.2f)
                        else -> PredictSleepDisorderTheme.colors.surfaceContainerLow
                    },
                    border = BorderStroke(
                        width = 1.dp,
                        color = when (dataType) {
                            "Object" -> Color(0xFFF38181)
                            "Int64" -> Color(0xFF3F72AF)
                            "Float64" -> Color(0xFFA4907C)
                            else -> PredictSleepDisorderTheme.colors.outlineVariant
                        }
                    )
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
                shadowElevation = 1.dp,
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