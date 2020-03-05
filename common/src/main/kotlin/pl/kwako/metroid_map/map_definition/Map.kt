package pl.kwako.metroid_map.map_definition

import java.util.*

data class Map(
        val mapWidth: Int,
        val mapHeight: Int,
        val roomWidth: Int,
        val roomHeight: Int,
        val rooms: List<Room> = Collections.emptyList()
)
