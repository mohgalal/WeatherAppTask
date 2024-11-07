package com.galal.weazerapp.ui

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.galal.domain.models.City
import com.galal.domain.models.DailyForecastElement
import com.galal.weazerapp.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WeatherDailyListWithFAB(
    dailyWeatherList: List<DailyForecastElement>,
    cityName:City
   // navController: NavController
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = colorResource(id = R.color.purple_200)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "FAB",
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            WeatherDailyList(paddingValues, dailyWeatherList, cityName)
        }
    )
}

@Composable
fun WeatherDailyList(
    paddingValues: PaddingValues = PaddingValues(),
    dailyWeatherList: List<DailyForecastElement>,
    cityName:City
) {
    val weatherList = processForecastData(dailyWeatherList)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            WeatherTodayHeader(todayWeather = weatherList.first(), countryName = "${cityName.name}, ${cityName.country}")
            Spacer(modifier = Modifier.height(16.dp))
        }

        itemsIndexed(weatherList) { _, day ->
            WeatherItem(day)
            Spacer(modifier = Modifier.padding(2.dp))
        }
    }
}

@Composable
fun WeatherTodayHeader(todayWeather: DailyForecastElement, countryName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = countryName,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        val todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMMM yyyy"))
        Text(
            text = todayDate,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )

        val weatherIcon = getCustomIconForWeather(todayWeather.weather[0].icon)
        Image(
            painter = painterResource(id = weatherIcon),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(top = 8.dp)
        )

        Text(
            text = "${todayWeather.main.temp_max.toInt()}K",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = todayWeather.weather[0].description.capitalize(),
            fontSize = 18.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun WeatherItem(weather: DailyForecastElement) {

    Surface(
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            val weatherIcon = getCustomIconForWeather(weather.weather[0].icon)
            Text(
                text = processDayOfWeek(dt = weather.dt),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Image(
                painter = painterResource(id = weatherIcon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = weather.weather[0].description,
                modifier = Modifier
                    .weight(1f),
                fontSize = 18.sp
            )

            Text(
                text = "${weather.main.temp_max.toInt()}K",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp),
                textAlign = TextAlign.End,
                fontSize = 20.sp
            )

        }
    }
}


@Composable
fun processDayOfWeek(dt: Long): String {
    val context = LocalContext.current

    val date = Instant.ofEpochSecond(dt)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    // Check if the date is today
    val today = LocalDate.now()
    return when (date) {
        today -> context.getString(R.string.today)
        today.plusDays(1) -> context.getString(R.string.tomorrow)
        else -> date.format(
            DateTimeFormatter.ofPattern(
                "EEEE",
                Locale.getDefault()
            )
        )
    }
}

private fun getCustomIconForWeather(iconCode: String): Int {
    return when (iconCode) {
        "01d", "01n" -> R.drawable.ic_clear_sky
        "02d", "02n" -> R.drawable.ic_few_cloud
        "03d", "03n" -> R.drawable.ic_scattered_clouds
        "04d", "04n" -> R.drawable.ic_broken_clouds
        "09d", "09n" -> R.drawable.ic_shower_rain
        "10d", "10n" -> R.drawable.ic_rain
        "11d", "11n" -> R.drawable.ic_thunderstorm
        "13d", "13n" -> R.drawable.ic_snow
        "50d", "50n" -> R.drawable.ic_mist
        else -> R.drawable.ic_clear_sky
    }
}


private fun processForecastData(forecastList: List<DailyForecastElement>): List<DailyForecastElement> {
    return forecastList
        .groupBy { element ->
            Instant.ofEpochSecond(element.dt)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }
        .map { (date, dailyElements) ->
            val maxTemp = dailyElements.maxOf { it.main.temp_max }
            val minTemp = dailyElements.minOf { it.main.temp_min }

            dailyElements.first().copy(
                main = dailyElements.first().main.copy(temp_max = maxTemp, temp_min = minTemp)
            )
        }
}

@Preview
@Composable
fun PreviewRow() {

    Surface(
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Text(
                text = "Today",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
            )
            Image(
                painter = painterResource(id = R.drawable.ic_clear_sky),
                contentDescription = null
            )

            Text(
                text = " Sunset weather",
                modifier = Modifier
                    .weight(1f),
                fontSize = 16.sp
            )

            Text(
                text = "32",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp),
                textAlign = TextAlign.End,
                fontSize = 16.sp
            )

        }
    }
}
