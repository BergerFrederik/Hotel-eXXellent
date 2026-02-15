package com.fredev.Hotel.eXXelent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomHandler
{
    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    //create new room
    //edit room via roomnumber
    //edit or delete room
    public void deleteHotelRoom(int id)
    {
        hotelRoomRepository.deleteById(id);
    }

    public void safeNewRoom(HotelRoom hotelRoom)
    {
        hotelRoomRepository.save(hotelRoom);
    }

    public void editExistingRoom(RoomFilterDTO testDTO)
    {
        HotelRoom roomToEdit = hotelRoomRepository.findById(testDTO.getRoomNumber())
                .orElseThrow(() -> new RuntimeException("Zimmer nicht gefunden"));

        if (testDTO.getRoomSize() != null)
        {
            roomToEdit.setRoomSize(testDTO.getRoomSize());
        }

        if (testDTO.hasMinibar() != null)
        {
            roomToEdit.setHasMinibar(testDTO.hasMinibar());
        }

        if (testDTO.isOccupied() != null)
        {
            roomToEdit.setOccupied(testDTO.isOccupied());
        }

        hotelRoomRepository.save(roomToEdit);
    }
}
