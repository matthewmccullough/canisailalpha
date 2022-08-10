package com.matthewjmccullough.canisailalpha

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

const val NOAA_WEB_SERVICE_URL = "https://api.tidesandcurrents.noaa.gov/api/prod/datagetter?date=latest&station=9446484&product=water_level&datum=STND&time_zone=gmt&units=english&format=json"

@Composable
fun StationComposable(noaaStation: NOAAStation) {
    Column(Modifier.fillMaxWidth().fillMaxHeight().padding(10.dp), Arrangement.Top) {
        Text("Location: " + noaaStation.metadata.name)
        Text("Water Level: " + noaaStation.data[0].v.toString())
        Text("Can Sail: " + SailingHelpers.canSail(noaaStation))
    }
}

@Preview
@Composable
fun PreviewStations() {
    val noaaStation = SailingHelpers.getPreviewNOAAStation()
    StationComposable(noaaStation)
}

@Composable
fun LiveStations(client: HttpClient) {
    val noaaStation = NOAAStation()
    val mutableStation: MutableState<NOAAStation> = remember { mutableStateOf(noaaStation) }

    LaunchedEffect(mutableStation) {
        mutableStation.value = client.get(NOAA_WEB_SERVICE_URL).body()
    }

    StationComposable(mutableStation.value)
}
