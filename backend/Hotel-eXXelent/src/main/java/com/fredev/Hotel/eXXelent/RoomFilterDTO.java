package com.fredev.Hotel.eXXelent;

public class RoomFilterDTO
{
    private Integer roomNumber;
    private RoomSize roomSize;
    private Boolean hasMinibar;
    private Boolean isOccupied;

    public Integer getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomSize getRoomSize() {
        return roomSize;
    }
    public void setRoomSize(RoomSize roomSize) {
        this.roomSize = roomSize;
    }

    public Boolean hasMinibar() {
        return hasMinibar;
    }
    public void setHasMinibar(Boolean hasMinibar) {
        this.hasMinibar = hasMinibar;
    }

    public Boolean isOccupied() {
        return isOccupied;
    }
    public void setOccupied(Boolean occupied) {
        isOccupied = occupied;
    }
    public RoomFilterDTO()
    {

    }
}
