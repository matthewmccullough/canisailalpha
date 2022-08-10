package com.matthewjmccullough.canisailalpha

import kotlinx.serialization.Serializable

@Serializable
data class NOAAStation(
    val metadata: Metadata = Metadata(),
    val data: List<Datum> = mutableListOf(Datum())
)

@Serializable
data class Metadata(
    val id: String = "",
    val name: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0
)

@Serializable
data class Datum(
    val t: String = "",
    val v: Double = 0.0,
    val s: String = "",
    val f: String = "",
    val q: String = ""
)
