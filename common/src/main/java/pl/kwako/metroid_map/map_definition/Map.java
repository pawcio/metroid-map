package pl.kwako.metroid_map.map_definition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Map {
    private Integer mapWidth;
    private Integer mapHeight;
    private Integer roomWidth;
    private Integer roomHeight;
    private List<Room> rooms;

    private Map() {
        // default constructor used in deserialization
    }

    public Map(Integer mapWidth, Integer mapHeight, Integer roomWidth, Integer roomHeight, Collection<Room> rooms) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
        this.rooms = new ArrayList<>(rooms);
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
        return Collections.unmodifiableCollection(rooms);
    }
}
