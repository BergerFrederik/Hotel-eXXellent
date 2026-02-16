package com.fredev.Hotel.eXXelent;

import jakarta.persistence.*;

// HotelRoom is used to define a HotelRoom entity and use it for the database

@Entity
@Table(name = "hotel_rooms")
public class HotelRoom
{

    @Id
    private Integer roomNumber;

    // to force either one of the 3 room sizes
    @Enumerated(EnumType.STRING)
    private RoomSize roomSize;
    private Boolean hasMinibar;
    private Boolean occupied;

    // For Hibernate to instantiate a Java-Object via Java Reflection
    public HotelRoom()
    {

    }
    public HotelRoom(Integer roomNumber, RoomSize roomSize, Boolean hasMinibar, Boolean occupied)
    {
        this.roomNumber = roomNumber;
        this.roomSize = roomSize;
        this.hasMinibar = hasMinibar;
        this.occupied = occupied;
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

    public Boolean getHasMinibar() {
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
}
