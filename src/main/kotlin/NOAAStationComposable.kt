package com.matthewjmccullough.canisailalpha

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

const val NOAA_WEB_SERVICE_URL = "https://api.tidesandcurrents.noaa.gov/api/prod/datagetter?date=latest&station=9446484&product=water_level&datum=STND&time_zone=gmt&units=english&format=json"
var theMutableStation = mutableStateOf(NOAAStation())
var theHttpClient: HttpClient = HttpClient()

@Composable
private fun StationComposable(noaaStation: NOAAStation) {
    Column(Modifier.fillMaxWidth().fillMaxHeight().padding(50.dp), Arrangement.Top) {
        Text("Location: " + noaaStation.metadata.name)
        Text("Last updated: " + noaaStation.data[0].t)
        Text("Water Level: " + noaaStation.data[0].v.toString())
        Text("Can Sail: " + SailingHelpers.canSail(noaaStation))
        Button(onClick = {
            //updateStationData(theHttpClient, theMutableStation)
        }) {
            Text(text = "Refresh Data")
        }
    }
}

@Preview
@Composable
fun PreviewStations() {
    val noaaStation = SailingHelpers.getPreviewNOAAStation()
    StationComposable(noaaStation)
}

@Composable
fun Stations(httpClient: HttpClient) {
    theHttpClient = httpClient
    remember { theMutableStation }

    LaunchedEffect(theMutableStation) {
        updateStationData(httpClient, theMutableStation)
    }

    StationComposable(theMutableStation.value)
}

suspend fun updateStationData(httpClient: HttpClient, stationToUpdate: MutableState<NOAAStation>) {
    Log.d("StationData","About to update station data")
    stationToUpdate.value = httpClient.get(NOAA_WEB_SERVICE_URL).body()
}
