package org.upj.api;

import org.upj.models.Customer;
import org.upj.models.IRoom;
import org.upj.service.CustomerService;
import org.upj.service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    public static AdminResource adminResource;

    static {
        AdminResource.adminResource = new AdminResource();
    }

    public Customer getCustomer(String email){
        Customer customer = CustomerService.customerService.getCustomer(email);
        if (customer == null) {
            System.out.println("Customer not found");
            return null;
        }
        return customer;
    }

    public void addRoom(List<IRoom> rooms){
        rooms.forEach(room -> ReservationService.reservationService.addRoom(room));
        System.out.println("Rooms were added successfully");
    }

    public Collection<IRoom> getAllRooms(){
        return ReservationService.reservationService.getRoomList();
    }

    public Collection<Customer> getAllCustomers(){
        return CustomerService.customerService.getAllCustomers();
    }

    public void displayAllReservations(){
        ReservationService.reservationService.printAllReservation();
    }
}
