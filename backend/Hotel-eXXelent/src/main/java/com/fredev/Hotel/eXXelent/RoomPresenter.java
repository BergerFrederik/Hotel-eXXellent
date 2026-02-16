package com.fredev.Hotel.eXXelent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import java.util.List;


// The RoomPresenter handles every request related to displaying hotel room information

@Service
public class RoomPresenter
{
    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    public List<HotelRoom> showAllRooms()
    {
        return hotelRoomRepository.findAll();
    }

    public List<HotelRoom> filterHotelRooms(RoomInformationDTO filterData)
    {
        // prevention of overriding data with "null"
        HotelRoom precursor = new HotelRoom();
        precursor.setRoomNumber(filterData.getRoomNumber());
        precursor.setRoomSize(filterData.getRoomSize());
        precursor.setHasMinibar(filterData.hasMinibar());
        precursor.setOccupied(filterData.getOccupied());

        return hotelRoomRepository.findAll(Example.of(precursor));
    }

    public String getNumFreeRooms()
    {
        return String.valueOf(hotelRoomRepository.countFreeRooms());
    }
}
