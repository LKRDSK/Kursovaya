package com.hotels.controller;

import com.hotels.dto.ClientDTO;
import com.hotels.entity.ClientEntity;
import com.hotels.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ClientDTO>> clients() {
        return clientService.clients();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDTO> client(@PathVariable Long clientId) {
        return clientService.client(clientId);
    }

    @PostMapping("/add/{hotelId}/{roomId}")
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientEntity client, @PathVariable Long hotelId, @PathVariable Long roomId) {
        return clientService.addClient(client, hotelId, roomId);
    }

    @PostMapping("/{clientId}/{roomId}")
    public ResponseEntity<ClientDTO> addRoomToClient(@PathVariable Long clientId, @PathVariable Long roomId) {
        return clientService.addRoomToClient(clientId, roomId);
    }

    @PatchMapping("/{clientId}")
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientEntity client, @PathVariable Long clientId) {
        return clientService.updateClient(client, clientId);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<ClientDTO> removeClient(@PathVariable Long clientId) {
        return clientService.removeClient(clientId);
    }

}
