package pl.kwako.metroid_map.view

interface MouseState {
    fun getX(): Int
    fun getY(): Int
    fun popWheelRotation(): Int
}
