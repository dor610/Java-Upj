package org.upj;

import org.upj.api.AdminResource;
import org.upj.api.HotelResource;
import org.upj.models.*;
import org.upj.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Scanner sc = new Scanner(System.in);

        main: while (true) {
            System.out.println("----- Menu -----");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.println("Select an option: ");
            String cmd = sc.nextLine();

            switch (cmd) {
                case "1":
                    findAndReserve(sc);
                    break;
                case "2":
                    seeReservation(sc);
                    break;
                case "3":
                    createAccount(sc);
                    break;
                case "4":
                    admin(sc);
                    break;
                case "5":
                    break main;
                default:
                    System.out.println("Invalid Option");
            }
        }
    }

    private static void findAndReserve(Scanner sc){
        String cmd = "";
        System.out.println("----- Find and reserve a room -----");
        Date checkInDate = null;
        Date checkOutDate = null;
        System.out.println("----- Find a room -----");
        while (true) {
            System.out.println("Enter check in date (dd-mm-yyyy): ");
            cmd = sc.nextLine();
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                checkInDate = formatter.parse(cmd);
                break;
            } catch (ParseException e) {
                System.out.println("Invalid check in date");
            }
        }
        while (true) {
            System.out.println("Enter check out date (dd-mm-yyyy): ");
            cmd = sc.nextLine();
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                checkOutDate = formatter.parse(cmd);
                break;
            } catch (ParseException e) {
                System.out.println("Invalid check out date");
            }
        }

        Collection<IRoom> rooms = HotelResource.hotelResource.findARoom(checkInDate, checkOutDate);
        if (rooms.isEmpty())
            System.out.println("No room is available");
        else rooms.forEach(System.out::println);


        System.out.println("----- reserve a room -----");
        while (true) {
            System.out.println("Select room number: ");
            cmd = sc.nextLine();
            String finalCmd = cmd;
            if(rooms.stream().filter(room -> room.getRoomNumber().equals(finalCmd)).count() == 0) {
                System.out.println("Room number is invalid");
            } else {
                break;
            }
        }
        IRoom room = HotelResource.hotelResource.getRoom(cmd);

        while (true) {
            System.out.println("Enter customer email: ");
            cmd = sc.nextLine();
            if(HotelResource.hotelResource.getCustomer(cmd) == null) {
                System.out.println("Customer email is invalid");
            } else {
                break;
            }
        }

        HotelResource.hotelResource.bookARoom(cmd, room, checkInDate, checkOutDate);
        System.out.println("Room was booked successfully");
    }

    private static void seeReservation(Scanner sc){
        String email = "";
        while (true) {
            System.out.println("----- See my reservations -----");
            System.out.println("Enter customer email: ");
            email = sc.nextLine();

            if(HotelResource.hotelResource.getCustomer(email) == null){
                System.out.println("Customer not found");
            } else {
                Collection<Reservation> reservations = HotelResource.hotelResource.getCustomersReservations(email);
                if(reservations.isEmpty()) {
                    System.out.println("No reservation was found");
                }else {
                    reservations.forEach(System.out::println);
                }
                break;
            }
        }
    }

    private static void createAccount(Scanner sc){
        System.out.println("----- Create an account -----");
        String email = "";
        while (true) {
            System.out.println("Enter customer email: ");
            email = sc.nextLine();
            if (!Utils.validateEmail(email)) {
                System.out.println("Email is invalid");
            } else if (HotelResource.hotelResource.getCustomer(email) != null) {
                System.out.println("Email has been registered");
            } else {
                break;
            }
        }
        System.out.println("Enter first name: ");
        String firstName = sc.nextLine();
        System.out.println("Enter last name: ");
        String lastName = sc.nextLine();

        HotelResource.hotelResource.createACustomer(email,firstName,lastName);
    }

    private static void admin(Scanner sc){
        main: while (true) {
            System.out.println("----- Admin -----");
            System.out.println("1. See all customers");
            System.out.println("2. See all rooms");
            System.out.println("3. See all reservations");
            System.out.println("4. Add a room");
            System.out.println("5. Back to main menu");
            System.out.println("Select an option: ");
            String cmd = sc.nextLine();

            switch (cmd) {
                case "1":
                    Collection<Customer> customers = AdminResource.adminResource.getAllCustomers();
                    if(customers.isEmpty())
                        System.out.println("No customer found");
                    else customers.forEach(System.out::println);
                    break;
                case "2":
                    Collection<IRoom> rooms = AdminResource.adminResource.getAllRooms();
                    if(rooms.isEmpty())
                        System.out.println("No room found");
                    else rooms.forEach(System.out::println);
                    break;
                case "3":
                    AdminResource.adminResource.displayAllReservations();
                    break;
                case "4":
                    addARoom(sc);
                    break;
                case "5":
                    break main;
                default:
                    System.out.println("Invalid Option");
            }
        }

    }

    private static void addARoom(Scanner sc) {
        System.out.println("----- Add a room -----");
        String roomNumber = "";
        while (true) {
            System.out.println("Enter room number: ");
            roomNumber = sc.nextLine();
            if (HotelResource.hotelResource.getRoom(roomNumber) != null) {
                System.out.println("This room number has been taken");
            } else {
                break;
            }
        }
        System.out.println("Enter price: ");
        double price = sc.nextDouble();
        sc.nextLine();
        String type = "1";
        while (true) {
            System.out.println("""
                Room type:
                    1. Single
                    2. Double
                """);
            System.out.println("Select room type: ");
            type = sc.nextLine();
            if(!(type.equals("1") || type.equals("2"))) {
                System.out.println("Invalid room type");
            } else {
                break;
            }
        }

        IRoom room = null;
        if (price == 0) {
            room = new FreeRoom(roomNumber, type.equals("1")? RoomType.Single: RoomType.Double);
        } else {
            room = new Room(roomNumber, price, type.equals("1")? RoomType.Single: RoomType.Double);
        }
        List<IRoom> rooms = new ArrayList<>();
        rooms.add(room);
        AdminResource.adminResource.addRoom(rooms);
    }

}