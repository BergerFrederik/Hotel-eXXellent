package com.fredev.Hotel.eXXelent;

import jakarta.persistence.*;

@Entity
@Table(name = "hotel_rooms")
public class HotelRoom
{

    @Id
    private Integer roomNumber;

    //to force either one of the 3 room sizes
    @Enumerated(EnumType.STRING)
    private RoomSize roomSize;
    private Boolean hasMinibar;
    private Boolean isOccupied;

    public HotelRoom()
    {

    }
    public HotelRoom(Integer roomNumber, RoomSize roomSize, Boolean hasMinibar, Boolean isOccupied)
    {
        this.roomNumber = roomNumber;
        this.roomSize = roomSize;
        this.hasMinibar = hasMinibar;
        this.isOccupied = isOccupied;
    }

    public Integer getRoomNumber()
    {
        return roomNumber;
    }
    public void setRoomNumber(Integer roomNumber)
    {
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
}
