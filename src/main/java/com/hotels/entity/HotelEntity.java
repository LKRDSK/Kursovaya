package com.hotels.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long cntRooms; //количество комнат
    private Long cntEmpl; //количество работников

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ClientEntity> clients = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RoomEntity> rooms = new ArrayList<>();
}
