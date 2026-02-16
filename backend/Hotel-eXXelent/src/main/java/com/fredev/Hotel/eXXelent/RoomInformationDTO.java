package com.fredev.Hotel.eXXelent;

// used to transfer basic room information between front- and backend
public class RoomInformationDTO
{
    private Integer roomNumber;
    private RoomSize roomSize;
    private Boolean hasMinibar;
    private Boolean occupied;

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

    public Boolean getOccupied() {
        return occupied;
    }
    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }


    // Jackson creates an empty instance of RoomInformationDTO first and fills it with data afterward
    public RoomInformationDTO()
    {

    }
}
