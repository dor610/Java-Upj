package org.upj.models;

public class FreeRoom extends Room{

    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0d, roomType);
    }

    @Override
    public boolean isFree() {
        return true;
    }

    @Override
    public String toString() {
        return "Free Room: " + this.roomNumber + " - " + this.roomType + ", Price: " + this.price;
    }
}
