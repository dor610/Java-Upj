package org.upj.api;

import org.upj.models.Customer;
import org.upj.models.IRoom;
import org.upj.models.Reservation;
import org.upj.service.CustomerService;
import org.upj.service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    public static HotelResource hotelResource;
    static {
        HotelResource.hotelResource = new HotelResource();
    }

    public Customer getCustomer(String email){
        Customer customer = CustomerService.customerService.getCustomer(email);
        if (customer == null) {
            System.out.println("Customer not found");
            return null;
        }
        return customer;
    }

    public void createACustomer(String email,  String firstName, String lastName){
        try {
            CustomerService.customerService.addCustomer(email, firstName, lastName);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Customer was created successfully");
    }

    public IRoom getRoom(String roomNumber){
        return ReservationService.reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date CheckOutDate){
        Customer customer = CustomerService.customerService.getCustomer(customerEmail);
        if (customer == null) {
            System.out.println("Customer not found");
            return null;
        }
        try {
            return ReservationService.reservationService.reserveARoom(customer, room, checkInDate, CheckOutDate);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Collection<Reservation>  getCustomersReservations(String customerEmail){
        Customer customer = CustomerService.customerService.getCustomer(customerEmail);
        if (customer == null) {
            System.out.println("Customer not found");
            return null;
        }
        return ReservationService.reservationService.getCustomersReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return ReservationService.reservationService.findRooms(checkIn, checkOut);
    }
}
