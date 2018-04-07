package pl.kwako.metroid_map.map_definition;

public class Room {
    private Integer x;
    private Integer y;
    private String hash;
    private boolean hasLeftDoor;
    private boolean hasRightDoor;

    public Room(Integer x, Integer y, String hash, boolean hasLeftDoor, boolean hasRightDoor) {
        this.x = x;
        this.y = y;
        this.hash = hash;
        this.hasLeftDoor = hasLeftDoor;
        this.hasRightDoor = hasRightDoor;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public String getHash() {
        return hash;
    }

    public boolean isHasLeftDoor() {
        return hasLeftDoor;
    }

    public boolean isHasRightDoor() {
        return hasRightDoor;
    }
}
