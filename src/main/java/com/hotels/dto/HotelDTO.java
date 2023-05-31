package com.hotels.dto;

import com.hotels.entity.ClientEntity;
import com.hotels.entity.HotelEntity;
import com.hotels.entity.RoomEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HotelDTO {

    private Long id;
    private String name;
    private Long cntRooms;
    private Long cntEmpl;
    private List<Long> clientsId;
    private List<Long> roomsId;

    public HotelDTO(Long id, String name, Long cntRooms, Long cntEmpl, List<Long> clientsId, List<Long> roomsId) {
        this.id = id;
        this.name = name;
        this.cntRooms = cntRooms;
        this.cntEmpl = cntEmpl;
        this.clientsId = clientsId;
        this.roomsId = roomsId;
    }

    public static HotelDTO toHotelDto(HotelEntity hotel) {
        return new HotelDTO(hotel.getId(), hotel.getName(), hotel.getCntRooms(), hotel.getCntEmpl(),
                hotel.getClients().stream().map(ClientEntity::getId).toList(),
                hotel.getRooms().stream().map(RoomEntity::getId).toList());
    }

}
