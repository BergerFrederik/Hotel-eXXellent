package com.fredev.Hotel.eXXelent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomPresenter
{
    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    //Hotelzimmer mittels Zimmernummer abfragen
    public HotelRoom showHotelRoomByID(int id)
    {
        return hotelRoomRepository.findById(id).orElse(null);
    }

    //Hotelzimmer in einer Liste anzeigen
    public List<HotelRoom> showAllRooms()
    {
        return hotelRoomRepository.findAll();
    }

    //Hotelzimmer filtern

    public List<HotelRoom> filterHotelRooms(RoomFilterDTO filterData)
    {
        HotelRoom precursor = new HotelRoom();
        precursor.setRoomNumber(filterData.getRoomNumber());
        precursor.setRoomSize(filterData.getRoomSize());
        precursor.setHasMinibar(filterData.hasMinibar());
        precursor.setOccupied(filterData.isOccupied());

        return hotelRoomRepository.findAll(Example.of(precursor));
    }
}
