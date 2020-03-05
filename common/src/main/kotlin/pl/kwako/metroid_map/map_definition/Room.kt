package pl.kwako.metroid_map.map_definition

data class Room(
        val x: Int,
        val y: Int,
        val hash: String,
        val hasLeftDoor: Boolean,
        val hasRightDoor: Boolean
)
