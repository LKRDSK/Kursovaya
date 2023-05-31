package com.hotels.service;

import com.hotels.dto.HotelDTO;
import com.hotels.dto.RoomDTO;
import com.hotels.entity.HotelEntity;
import com.hotels.entity.RoomEntity;
import com.hotels.repository.HotelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public ResponseEntity<List<HotelDTO>> hotels() {
        return ResponseEntity.ok().body(hotelRepository.findAll().stream().map(HotelDTO::toHotelDto).toList());
    }

    public ResponseEntity<HotelDTO> hotel(Long hotelId) {
        return ResponseEntity.ok().body(HotelDTO.toHotelDto(hotelRepository.findById(hotelId).orElseThrow()));
    }

    public ResponseEntity<String> getPriceHotelRooms(Long hotelId) {
        try {
            HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow();
            return ResponseEntity.ok().body("Сумма всех аппартаментов отеля: " + hotel.getRooms().stream().map(RoomEntity::getRoomRate).reduce(Long::sum).orElseThrow());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<List<RoomDTO>> getEmptyRooms(Long hotelId) {
        try {
            HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow();
            List<RoomDTO> emptyRooms = new ArrayList<>();
            hotel.getRooms().forEach(room -> {
                if(room.getClient() == null) {
                    emptyRooms.add(RoomDTO.toRoomDto(room));
                }
            });
            return ResponseEntity.ok().body(emptyRooms);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<HotelDTO> createHotel(HotelEntity hotel) {
        return ResponseEntity.ok().body(HotelDTO.toHotelDto(hotelRepository.save(hotel)));
    }

    public ResponseEntity<HotelDTO> updateHotel(HotelEntity chHotel, Long hotelId) {
        try {
            HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow();
            if(chHotel.getName() != null) {
                hotel.setName(chHotel.getName());
            }
            if(chHotel.getCntEmpl() != null) {
                hotel.setCntEmpl(chHotel.getCntEmpl());
            }
            return ResponseEntity.ok().body(HotelDTO.toHotelDto(hotelRepository.save(hotel)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<HotelDTO> removeHotel(Long hotelId) {
        try {
            HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow();
            hotelRepository.delete(hotel);
            return ResponseEntity.ok().body(HotelDTO.toHotelDto(hotel));
        } catch(NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
