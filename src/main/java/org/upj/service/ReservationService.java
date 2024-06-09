package org.upj.service;

import org.upj.models.Customer;
import org.upj.models.IRoom;
import org.upj.models.Reservation;
import org.upj.util.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class ReservationService {

    public static ReservationService reservationService;

    static {
        ReservationService.reservationService = new ReservationService();
    }

    private Collection<IRoom> roomList;
    private Collection<Reservation> reservationList;

    public ReservationService() {
        this.roomList = new ArrayList<>();
        this.reservationList = new ArrayList<>();
    }

    Collection<Reservation> reservations;

    public void addRoom(IRoom room){
        roomList.add(room);
    }

    public IRoom getARoom(String roomId) {
        return roomList.stream().filter(room -> room.getRoomNumber().equals(roomId)).findFirst().orElse(null);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) throws Exception {
        Collection<String> reservedRoom = reservationList.stream().filter(reservation -> checkInDate.compareTo(reservation.getCheckOutDate()) < 0
                        &&  checkOutDate.compareTo(reservation.getCheckOutDate()) >= 0)
                .map(reservation -> reservation.getRoom().getRoomNumber()).collect(Collectors.toList());

        if(reservedRoom.size() > 0) {
            throw new Exception("This room has been booked for this date range");
        }

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationList.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Collection<String> reservedRoom = reservationList.stream().filter(reservation -> checkInDate.compareTo(reservation.getCheckOutDate()) < 0
                                                        &&  checkOutDate.compareTo(reservation.getCheckOutDate()) >= 0)
                .map(reservation -> reservation.getRoom().getRoomNumber()).collect(Collectors.toList());
        if(reservedRoom.size() == roomList.size()) {
            reservedRoom = reservationList.stream()
                    .filter(reservation -> Utils.addDate(checkInDate,7).compareTo(reservation.getCheckOutDate()) < 0
                            &&  Utils.addDate(checkOutDate,7).compareTo(reservation.getCheckOutDate()) >= 0)
                    .map(reservation -> reservation.getRoom().getRoomNumber()).collect(Collectors.toList());
        }

        if(reservedRoom.size() < roomList.size()) {
            Collection<String> finalReservedRoom = reservedRoom;
            return roomList.stream().filter(room -> !finalReservedRoom.contains(room.getRoomNumber())).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){
        return reservationList.stream()
                .filter(reservation -> reservation.getCustomer().getEmail().equals(customer.getEmail()))
                .collect(Collectors.toList());
    }

    public void printAllReservation(){
        if (reservationList.isEmpty())
            System.out.println("No reservation found");
        else reservationList.forEach(System.out::println);
    }

    public Collection<IRoom> getRoomList() {
        return roomList;
    }

    public void setRoomList(Collection<IRoom> roomList) {
        this.roomList = roomList;
    }

    public Collection<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(Collection<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
