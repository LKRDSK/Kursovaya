package com.hotels.dto;

import com.hotels.entity.ClientEntity;
import com.hotels.entity.HotelEntity;
import com.hotels.entity.RoomEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ClientDTO {

    private Long id;
    private String fio;
    private String passport;
    private Long roomId;
    private Long hotelId;

    public ClientDTO(Long id, String fio, String passport, Long roomId, Long hotelId) {
        this.id = id;
        this.fio = fio;
        this.passport = passport;
        this.roomId = roomId;
        this.hotelId = hotelId;
    }

    public static ClientDTO toClientDto(ClientEntity client) {
        return new ClientDTO(client.getId(), client.getFio(), client.getPassport(), client.getRoom() == null ? 0L : client.getRoom().getId(), client.getHotel().getId());
    }

}
