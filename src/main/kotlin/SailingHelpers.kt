package com.matthewjmccullough.canisailalpha

class SailingHelpers {
    companion object LogicAndHelpers {
        private const val TIDE_LEVEL_REQUIRED_TO_SAIL = 3

        fun canSail(noaaStation: NOAAStation): Boolean {
            val waterLevel: Double = noaaStation.data[0].v
            return waterLevel > TIDE_LEVEL_REQUIRED_TO_SAIL
        }

        fun getPreviewNOAAStation(): NOAAStation {
            return NOAAStation(Metadata("9999999", "Preview-ville", 47.9999, -122.9999), mutableListOf(Datum("2029-09-09 03:54", 9.999, "0.099","0,0,0,0", "p")))
        }
    }
}