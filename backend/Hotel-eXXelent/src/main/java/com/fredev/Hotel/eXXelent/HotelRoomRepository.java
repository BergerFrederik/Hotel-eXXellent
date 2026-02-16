package com.fredev.Hotel.eXXelent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// This JPA Repository is used to communicate with the H2 database
@org.springframework.stereotype.Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Integer>
{
    @Query("SELECT count(room) FROM HotelRoom room Where room.occupied = false")
    int countFreeRooms();
}
