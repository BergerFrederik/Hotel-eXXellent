package com.fredev.Hotel.eXXelent;

import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Integer>
{

}
