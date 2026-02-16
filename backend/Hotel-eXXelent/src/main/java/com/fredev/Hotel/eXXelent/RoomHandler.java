package com.fredev.Hotel.eXXelent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// The RoomHandler handles every request related to processing and transforming hotel room data
@Service
public class RoomHandler
{
    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    public void deleteHotelRoom(int id)
    {
        hotelRoomRepository.deleteById(id);
    }

    public void safeNewRoom(HotelRoom hotelRoom)
    {
        hotelRoomRepository.save(hotelRoom);
    }

    public void editExistingRoom(RoomInformationDTO roomInformationDTO)
    {
        HotelRoom roomToEdit = hotelRoomRepository.findById(roomInformationDTO.getRoomNumber())
                .orElseThrow(() -> new RuntimeException("Room not found")); // Needed to use the data from the optional

        if (roomInformationDTO.getRoomSize() != null) // preventing to override data with "null" when no change was chosen
        {
            roomToEdit.setRoomSize(roomInformationDTO.getRoomSize());
        }

        if (roomInformationDTO.hasMinibar() != null) // preventing to override data with "null" when no change was chosen
        {
            roomToEdit.setHasMinibar(roomInformationDTO.hasMinibar());
        }

        if (roomInformationDTO.getOccupied() != null) // preventing to override data with "null" when no change was chosen
        {
            roomToEdit.setOccupied(roomInformationDTO.getOccupied());
        }

        hotelRoomRepository.save(roomToEdit);
    }
}
