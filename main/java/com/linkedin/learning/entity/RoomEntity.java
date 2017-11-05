package com.linkedin.learning.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Integer roomNumber;

    @NotNull
    private String price;

    /*add a list of reservations*/
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST) /*a room can have multiple reservations*/
    private List<ReservationEntity> reservationEntityList;

    /*constructors null, room number & price*/

    public RoomEntity() {
    }

    public RoomEntity(Integer roomNumber, String price) {
        this.roomNumber = roomNumber;
        this.price = price;
    }

    /*added method*/
    public void addReservationEntity(ReservationEntity reservationEntity) {
        if (null == reservationEntity) {
            /*create a new one*/
            reservationEntityList = new ArrayList<>();
            reservationEntityList.add(reservationEntity);
        }
    }

    /*getters and setters*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<ReservationEntity> getReservationEntityList() {
        return reservationEntityList;
    }

    public void setReservationEntityList(List<ReservationEntity> reservationEntityList) {
        this.reservationEntityList = reservationEntityList;
    }
}
