package com.hotels.dto;

import com.hotels.entity.RoomEntity;
import lombok.Data;

@Data
public class RoomDTO {

    private Long id;
    private String number;
    private Long roomRate;
    private Long hotelId;
    private Long clientId;

    public RoomDTO(Long id, String number, Long roomRate, Long hotelId, Long clientId) {
        this.id = id;
        this.number = number;
        this.roomRate = roomRate;
        this.hotelId = hotelId;
        this.clientId = clientId;
    }

    public static RoomDTO toRoomDto(RoomEntity room) {
        return new RoomDTO(room.getId(), room.getNumber(), room.getRoomRate(), room.getHotel().getId(), room.getClient() == null ? 0L : room.getClient().getId());
    }
}
