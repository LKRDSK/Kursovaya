package com.hotels.service;

import com.hotels.dto.ClientDTO;
import com.hotels.entity.ClientEntity;
import com.hotels.entity.HotelEntity;
import com.hotels.entity.RoomEntity;
import com.hotels.repository.ClientRepository;
import com.hotels.repository.HotelRepository;
import com.hotels.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public ClientService(ClientRepository clientRepository, HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.clientRepository = clientRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    public ResponseEntity<List<ClientDTO>> clients() {
        return ResponseEntity.ok().body(clientRepository.findAll().stream().map(ClientDTO::toClientDto).toList());
    }

    public ResponseEntity<ClientDTO> client(Long clientId) {
        try {
            return ResponseEntity.ok().body(ClientDTO.toClientDto(clientRepository.findById(clientId).orElseThrow()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<ClientDTO> addClient(ClientEntity client, Long hotelId, Long roomId) {
        try {
            HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow();
            RoomEntity room = roomRepository.findById(roomId).orElseThrow();

            hotel.getClients().add(client);
            room.setClient(client);

            client.setRoom(room);
            client.setHotel(hotel);

            return ResponseEntity.ok().body(ClientDTO.toClientDto(clientRepository.save(client)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<ClientDTO> addRoomToClient(Long clientId, Long roomId) {
        try {
            ClientEntity client = clientRepository.findById(clientId).orElseThrow();
            RoomEntity room = roomRepository.findById(roomId).orElseThrow();
            if (room.getClient() == null) {
                if (client.getRoom() != null) {
                    client.getRoom().setClient(null);
                }
            } else {
               room.getClient().setRoom(null);
            }
            client.setRoom(room);
            room.setClient(client);
            return ResponseEntity.ok().body(ClientDTO.toClientDto(clientRepository.save(client)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<ClientDTO> updateClient(ClientEntity chClient, Long clientId) {
        try {
            ClientEntity client = clientRepository.findById(clientId).orElseThrow();
            if (chClient.getFio() != null) {
                client.setFio(chClient.getFio());
            }
            if (chClient.getPassport() != null) {
                client.setPassport(chClient.getPassport());
            }
            return ResponseEntity.ok().body(ClientDTO.toClientDto(clientRepository.save(client)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<ClientDTO> removeClient(Long clientId) {
        try {
            ClientEntity client = clientRepository.findById(clientId).orElseThrow();
            HotelEntity hotel = client.getHotel();

            hotel.getClients().remove(client);
            client.getRoom().setClient(null);
            clientRepository.delete(client);

            return ResponseEntity.ok().body(ClientDTO.toClientDto(client));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
