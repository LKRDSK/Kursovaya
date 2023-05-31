package com.hotels.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private Long roomRate; //стоимость за 1 день

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private HotelEntity hotel;
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private ClientEntity client;
}
