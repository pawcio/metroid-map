package pl.kwako.metroid_map.view

interface MouseState {
//    val x: Int
//    val y: Int
//    val wheelRotation: Int

    fun getX(): Int
    fun getY(): Int
    fun popWheelRotation(): Int
}
