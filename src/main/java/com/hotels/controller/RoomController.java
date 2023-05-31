package com.hotels.controller;

import com.hotels.dto.RoomDTO;
import com.hotels.entity.RoomEntity;
import com.hotels.service.RoomService;
import lombok.experimental.PackagePrivate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/")
    public ResponseEntity<List<RoomDTO>> rooms() {
        return roomService.rooms();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDTO> room(@PathVariable Long roomId) {
        return roomService.room(roomId);
    }

    @PostMapping("/{hotelId}")
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomEntity room, @PathVariable Long hotelId) {
        return roomService.addRoom(room, hotelId);
    }

    @PatchMapping("/{roomId}")
    public ResponseEntity<RoomDTO> updateRoom(@RequestBody RoomEntity room, @PathVariable Long roomId) {
        return roomService.updateRoom(room, roomId);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<RoomDTO> removeRoom(@PathVariable Long roomId) {
        return roomService.removeRoom(roomId);
    }
}
