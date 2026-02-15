package com.fredev.Hotel.eXXelent;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/rooms")
@CrossOrigin(origins = "http://localhost:5173",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT},
        allowedHeaders = "*"
)
public class RoomController
{
    private final RoomHandler roomHandler;
    private final RoomPresenter roomPresenter;

    public RoomController(RoomHandler roomHandler, RoomPresenter roomPresenter)
    {
        this.roomHandler = roomHandler;
        this.roomPresenter = roomPresenter;
    }

    @GetMapping
    public List<HotelRoom> getAllRooms()
    {
        System.out.println("Anfrage erhalten!");
        return roomPresenter.showAllRooms();
    }

    @GetMapping("/byID")
    public HotelRoom getRoomByID(@RequestParam int id)
    {
        return roomPresenter.showHotelRoomByID(id);
    }

    @DeleteMapping("/delete")
    public void deleteRoomByID(@RequestParam int id)
    {
        roomHandler.deleteHotelRoom(id);
    }

    @PostMapping("/add")
    public void addNewRoom(@RequestBody HotelRoom hotelRoom)
    {
        roomHandler.safeNewRoom(hotelRoom);
    }

    @PostMapping("/filter")
    public List<HotelRoom> getFilteredHotelRooms(@RequestBody RoomFilterDTO filterData)
    {
        return roomPresenter.filterHotelRooms(filterData);
    }

    @PostMapping("/edit")
    public void editRoom(@RequestBody RoomFilterDTO testDTO)
    {
        roomHandler.editExistingRoom(testDTO);
    }

}
