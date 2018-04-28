package pl.kwako.metroid_map.analyzer.region;

import javax.json.bind.annotation.JsonbProperty;

// TODO: add x and y offset here when it will be needed
public class RegionSettings {

    @JsonbProperty("imageFileName")
    private String imageFileName;
    @JsonbProperty("roomCountX")
    private int roomCountX;
    @JsonbProperty("roomCountY")
    private int roomCountY;

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public void setRoomCountX(int roomCountX) {
        this.roomCountX = roomCountX;
    }

    public void setRoomCountY(int roomCountY) {
        this.roomCountY = roomCountY;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public int getRoomCountX() {
        return roomCountX;
    }

    public int getRoomCountY() {
        return roomCountY;
    }
}
