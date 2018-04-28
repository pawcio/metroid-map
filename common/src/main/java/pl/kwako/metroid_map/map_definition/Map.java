package pl.kwako.metroid_map.map_definition;

import java.util.Collection;

public class Map {
    private Integer mapWidth;
    private Integer mapHeight;
    private Integer roomWidth;
    private Integer roomHeight;
    private Collection<Room> rooms;

    private Map() {
        // default constructor used in deserialization
    }

    public Map(Integer mapWidth, Integer mapHeight, Integer roomWidth, Integer roomHeight, Collection<Room> rooms) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
        this.rooms = rooms;
    }

    public Integer getMapWidth() {
        return mapWidth;
    }

    public Integer getMapHeight() {
        return mapHeight;
    }

    public Integer getRoomWidth() {
        return roomWidth;
    }

    public Integer getRoomHeight() {
        return roomHeight;
    }

    public Collection<Room> getRooms() {
        return rooms;
    }
}
