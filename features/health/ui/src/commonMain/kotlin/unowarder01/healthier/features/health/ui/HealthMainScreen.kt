package unowarder01.healthier.features.health.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import org.jetbrains.compose.resources.DrawableResource
import unowarder01.healthier.core.designsystem.components.image.AppImage
import unowarder01.healthier.core.designsystem.components.text_field.AppTextField
import unowarder01.healthier.core.designsystem.extensions.getScreenWidth
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.clinic_american_hospital
import unowarder01.healthier.designsystem.generated.resources.clinic_aversi
import unowarder01.healthier.designsystem.generated.resources.clinic_new_hospital
import unowarder01.healthier.designsystem.generated.resources.doctor_1
import unowarder01.healthier.designsystem.generated.resources.doctor_2
import unowarder01.healthier.designsystem.generated.resources.doctor_3
import unowarder01.healthier.designsystem.generated.resources.ic_analysis
import unowarder01.healthier.designsystem.generated.resources.ic_doctor
import unowarder01.healthier.designsystem.generated.resources.ic_dropdown
import unowarder01.healthier.designsystem.generated.resources.ic_more
import unowarder01.healthier.designsystem.generated.resources.ic_procedure
import unowarder01.healthier.designsystem.generated.resources.ic_search

@Composable
fun HealthMainScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .systemBarsPadding()
    ) {
        item { Toolbar() }
        item { Search() }
        item { PopularSection() }
        item { ClinicsSection() }
        item { DoctorsSection() }
    }
}

/**
 * TOOLBAR
 */
@Composable
private fun Toolbar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Text(
            text = "Доброе утро!",
            color = colorScheme.onBackground,
            style = typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(end = 16.dp)
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(end = 16.dp)
                .clip(shapes.large)
                .background(
                    color = colorScheme.surfaceContainerHigh,
                    shape = shapes.large
                )
                .border(
                    width = 1.dp,
                    color = colorScheme.outlineVariant,
                    shape = shapes.large
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "Тбилиси",
                color = colorScheme.onSurfaceVariant,
                style = typography.labelSmall
            )
            AppImage(
                image = Res.drawable.ic_dropdown,
                color = colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .size(8.dp)
            )
        }
    }
}

/**
 * SEARCH
 */
@Composable
private fun Search() {
    AppTextField(
        value = "",
        onValueChange = {},
        placeholder = "Клиника / доктор / процедура",
        leadingIcon = Res.drawable.ic_search,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
    )
}

/**
 * POPULAR SECTION
 */
@Composable
private fun PopularSection() {
    RowSection("Популярное") {
        PopularItem("Врачи", Res.drawable.ic_doctor)
        PopularItem("Анализы", Res.drawable.ic_analysis)
        PopularItem("Процедуры", Res.drawable.ic_procedure)
        PopularItem("Ещё", Res.drawable.ic_more)
    }
}

@Composable
private fun RowScope.PopularItem(
    text: String,
    icon: DrawableResource
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .weight(1f)
            .height(72.dp)
            .clip(shapes.medium)
            .background(
                color = colorScheme.surfaceContainerHigh,
                shape = shapes.medium
            )
            .border(
                width = 1.dp,
                color = colorScheme.outlineVariant,
                shape = shapes.medium
            )
    ) {
        AppImage(
            image = icon,
            color = colorScheme.onBackground,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = text,
            color = colorScheme.onBackground,
            style = typography.labelSmall,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

/**
 * CLINICS SECTION
 */
@Composable
private fun ClinicsSection() {
    val clinics = listOf(
        Triple("American Hospital", "Chughureti", Res.drawable.clinic_american_hospital),
        Triple("New Hospital", "Ortachala", Res.drawable.clinic_new_hospital),
        Triple("Aversi", "Saburtalo", Res.drawable.clinic_aversi),
    )
    val itemWidth = 0.45 * (getScreenWidth() - 32.dp)
    val horizontalPadding = 12.dp
    val titleLines = clinics
        .map { it.first }
        .calculateTitleLines(
            itemWidth = itemWidth,
            horizontalPadding = horizontalPadding
        )

    LazyRowSection("Клиники") {
        clinics.forEach { clinic ->
            item {
                ClinicDoctorView(
                    itemWidth = itemWidth,
                    aspectRatio = 1f,
                    title = clinic.first,
                    subtitle = clinic.second,
                    icon = clinic.third,
                    titleLines = titleLines,
                    horizontalPadding = horizontalPadding
                )
            }
        }
    }
}

/**
 * DOCTORS SECTION
 */
@Composable
private fun DoctorsSection() {
    val doctors = listOf(
        Triple("American Hospital", "Chughureti", Res.drawable.doctor_1),
        Triple("New Hospital", "Ortachala", Res.drawable.doctor_2),
        Triple("Aversi", "Saburtalo", Res.drawable.doctor_3),
        Triple("American Hospital", "Chughureti", Res.drawable.doctor_1),
        Triple("New Hospital", "Ortachala", Res.drawable.doctor_2),
        Triple("Aversi", "Saburtalo", Res.drawable.doctor_3)
    )
    val itemWidth = 0.37 * (getScreenWidth() - 32.dp)
    val horizontalPadding = 12.dp
    val titleLines = doctors
        .map { it.first }
        .calculateTitleLines(
            itemWidth = itemWidth,
            horizontalPadding = horizontalPadding
        )

    LazyRowSection("Доктора") {
        doctors.forEach { clinic ->
            item {
                ClinicDoctorView(
                    itemWidth = itemWidth,
                    aspectRatio = 0.75f,
                    title = clinic.first,
                    subtitle = clinic.second,
                    icon = clinic.third,
                    titleLines = titleLines,
                    horizontalPadding = horizontalPadding
                )
            }
        }
    }
}

/**
 * BASE
 */
@Composable
private fun Section(
    header: String,
    content: @Composable ColumnScope.(Modifier) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = header,
            color = colorScheme.onBackground,
            style = typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        content(
            Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun RowSection(
    header: String,
    content: @Composable RowScope.() -> Unit
) {
    Section(header) { modifier ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.horizontalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            content()
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
private fun LazyRowSection(
    header: String,
    content: LazyListScope.() -> Unit
) {
    Section(header) { modifier ->
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
        ) {
            item { Spacer(modifier = Modifier.width(8.dp)) }
            content()
            item { Spacer(modifier = Modifier.width(8.dp)) }
        }
    }
}

@Composable
private fun ClinicDoctorView(
    itemWidth: Dp,
    aspectRatio: Float,
    title: String,
    subtitle: String,
    icon: DrawableResource,
    titleLines: Int,
    horizontalPadding: Dp
) {
    Column(
        modifier = Modifier
            .width(itemWidth)
            .clip(shapes.small)
            .background(
                color = colorScheme.surface,
                shape = shapes.small
            )
            .border(
                width = 1.dp,
                color = colorScheme.outlineVariant,
                shape = shapes.small
            )
    ) {
        val imageShape = shapes.medium.copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        )
        AppImage(
            image = icon,
            scale = ContentScale.Crop,
            modifier = Modifier
                .width(itemWidth)
                .aspectRatio(aspectRatio)
                .clip(imageShape)
        )
        Text(
            text = title,
            color = colorScheme.onSurface,
            style = typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            minLines = titleLines,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp, start = horizontalPadding, end = horizontalPadding)
        )
        Text(
            text = subtitle,
            color = colorScheme.onSurfaceVariant,
            style = typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(
                top = 4.dp,
                bottom = 9.dp,
                start = horizontalPadding,
                end = horizontalPadding
            )
        )
    }
}

@Composable
private fun List<String>.calculateTitleLines(
    itemWidth: Dp,
    horizontalPadding: Dp,
    maxLines: Int = 2
): Int {
    val style = typography.labelMedium.copy(fontWeight = FontWeight.Bold)
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val availableWidth = with(density) {
        (itemWidth - horizontalPadding * 2).roundToPx()
    }

    return maxOfOrNull { title ->
        textMeasurer.measure(
            text = AnnotatedString(title),
            style = style,
            maxLines = maxLines,
            constraints = Constraints(maxWidth = availableWidth)
        ).lineCount
    } ?: 1
}
