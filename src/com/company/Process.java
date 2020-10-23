package com.company;

import java.sql.Connection;
import java.util.Scanner;

public class Process {
    private final Connection connection;

    public Process(Connection connection) {
        this.connection = connection;
    }

    public void performProcess()

    {
        Scanner input = new Scanner(System.in);
        System.out.println("Reselect operations to perform");
        System.out.println("0. Exit");
        System.out.println("1. Manage Aircraft");
        System.out.println("2. Manage Flight");
        System.out.println("3. Manage Booking");
        System.out.println("4. Manage Passenger");

        int operation = input.nextInt();

            switch (operation) {

                case 1:
                    AircraftMenu aircraftMenu = new AircraftMenu(connection);
                    aircraftMenu.process();
                    break;

                case 2:
                    FlightMenu flightMenu = new FlightMenu(connection);
                    flightMenu.process();
                    break;

                case 3:
                    BookingMenu bookingMenu = new BookingMenu(connection);
                    bookingMenu.process();
                    break;

                case 4:
                    PassengerMenu passengerMenu = new PassengerMenu(connection);
                    passengerMenu.process();
                    break;

                case 0:
                    System.exit(0);
                    break;

                default:
                    System.out.println("invalid operation");
                    System.out.println("input option 1-4 to perform an operation; 0 to quit");
            }

    }
}
