package com.hotels.service;

import com.hotels.dto.RoomDTO;
import com.hotels.entity.ClientEntity;
import com.hotels.entity.HotelEntity;
import com.hotels.entity.RoomEntity;
import com.hotels.repository.HotelRepository;
import com.hotels.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public ResponseEntity<List<RoomDTO>> rooms() {
        return ResponseEntity.ok().body(roomRepository.findAll().stream().map(RoomDTO::toRoomDto).toList());
    }

    public ResponseEntity<RoomDTO> room(Long roomId) {
        try {
            return ResponseEntity.ok().body(RoomDTO.toRoomDto(roomRepository.findById(roomId).orElseThrow()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<RoomDTO> addRoom(RoomEntity room, Long hotelId) {
        try {
            HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow();
            hotel.getRooms().add(room);
            hotel.setCntRooms(hotel.getCntRooms() + 1);
            room.setHotel(hotel);
            room.setClient(null);
            return ResponseEntity.ok().body(RoomDTO.toRoomDto(roomRepository.save(room)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<RoomDTO> updateRoom(RoomEntity chRoom, Long roomId) {
        try {
            RoomEntity room = roomRepository.findById(roomId).orElseThrow();
            if (chRoom.getNumber() != null) {
                room.setNumber(chRoom.getNumber());
            }
            if (chRoom.getRoomRate() != null) {
                room.setRoomRate(chRoom.getRoomRate());
            }
            return ResponseEntity.ok().body(RoomDTO.toRoomDto(roomRepository.save(room)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<RoomDTO> removeRoom(Long roomId) {
        try {
            RoomEntity room = roomRepository.findById(roomId).orElseThrow();
            HotelEntity hotel = room.getHotel();
            ClientEntity client = room.getClient();

            client.setRoom(null);
            hotel.getRooms().remove(room);
            hotel.setCntRooms(hotel.getCntRooms() - 1);

            roomRepository.delete(room);

            return ResponseEntity.ok().body(RoomDTO.toRoomDto(room));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}