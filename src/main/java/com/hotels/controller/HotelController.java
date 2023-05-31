package com.hotels.controller;

import com.hotels.dto.HotelDTO;
import com.hotels.dto.RoomDTO;
import com.hotels.entity.HotelEntity;
import com.hotels.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/")
    public ResponseEntity<List<HotelDTO>> hotels() {
        return hotelService.hotels();
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDTO> hotel(@PathVariable Long hotelId) {
        return hotelService.hotel(hotelId);
    }

    //вычисляемое поле
    @GetMapping("/priceRooms/{hotelId}")
    public ResponseEntity<String> getPriceHotelRooms(@PathVariable Long hotelId) {
        return hotelService.getPriceHotelRooms(hotelId);
    }

    //сложный метод использующий данные из нескольких таблиц
    @GetMapping("/emptyRooms/{hotelId}")
    public ResponseEntity<List<RoomDTO>> getEmptyRooms(@PathVariable Long hotelId) {
        return hotelService.getEmptyRooms(hotelId);
    }

    @PostMapping("/")
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelEntity hotel) {
        return hotelService.createHotel(hotel);
    }

    @PatchMapping("/{hotelId}")
    public ResponseEntity<HotelDTO> updateHotel(@RequestBody HotelEntity hotel, @PathVariable Long hotelId) {
        return hotelService.updateHotel(hotel, hotelId);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<HotelDTO> removeHotel(@PathVariable Long hotelId) {
        return hotelService.removeHotel(hotelId);
    }
}
