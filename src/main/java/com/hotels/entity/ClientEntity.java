package com.hotels.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fio;
    private String passport;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private RoomEntity room;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private HotelEntity hotel;
}
