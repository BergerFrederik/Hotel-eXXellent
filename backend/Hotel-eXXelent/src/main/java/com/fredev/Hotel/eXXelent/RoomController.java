package com.fredev.Hotel.eXXelent;

import org.springframework.web.bind.annotation.*;
import java.util.List;

// This class acts as bridge between the frontend and the services. It delegates incoming requests to the respective services and maps the results back to DTOs for the frontend

@RestController
@RequestMapping("/rest/rooms")

// to ensure Vue connection is allowed
@CrossOrigin(origins = "http://localhost:5173",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT},
        allowedHeaders = "*"
)
public class RoomController
{
    private final RoomHandlerService roomHandler;

    private final RoomPresenterService roomPresenter;

    // constructor to be able to use finals
    public RoomController(RoomHandlerService roomHandler, RoomPresenterService roomPresenter)
    {
        this.roomHandler = roomHandler;
        this.roomPresenter = roomPresenter;
    }

    @GetMapping("/list")
    public List<HotelRoom> getAllRooms()
    {
        return roomPresenter.showAllRooms();
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
    public List<HotelRoom> getFilteredHotelRooms(@RequestBody RoomInformationDTO filterData)
    {
        return roomPresenter.filterHotelRooms(filterData);
    }

    @PostMapping("/edit")
    public void editRoom(@RequestBody RoomInformationDTO testDTO)
    {
        roomHandler.editExistingRoom(testDTO);
    }

    @GetMapping("/home")
    public String numFreeRooms()
    {
        return roomPresenter.getNumFreeRooms();
    }
}
