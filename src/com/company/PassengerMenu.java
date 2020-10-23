package com.company;

import java.sql.Connection;


import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class PassengerMenu {
    private final Connection con;

    private final FlightsManagementService flightsManagementService;

    private final PassengerManagerService passengerManagerService;

    private final BookingMenu book;

    public PassengerMenu(Connection con) {
        this.con = con;
        AircraftManagementService aircraftManagementService = new AircraftManagementService(con);

        flightsManagementService = new FlightsManagementService(aircraftManagementService, con);

        book = new BookingMenu(con);
        BookingManagementService bookingManagementService = new BookingManagementService(flightsManagementService, con);

        passengerManagerService = new PassengerManagerService(bookingManagementService, con);

    }

    Scanner input = new Scanner(System.in);




    public void process() {

        System.out.println("Welcome");
        System.out.println("Kindly select any of the option listed below");
        System.out.println("*. Quit");
        System.out.println("0. Return");
        System.out.println("1. Register");
        System.out.println("2. Update record");
        System.out.println("3. Delete record");
        System.out.println("4. Retrieve record");
        System.out.println("5. Search Flight Available");
        System.out.println("6. Return to Main Menu");

        AtomicBoolean continueInput = new AtomicBoolean(true);
        while (continueInput.get()) {


            int operation = input.nextInt();

            if (operation == 1) {
                createRecord();
            } else if (operation == 2) {
                updateRecord();
            } else if (operation == 3) {
                deleteRecord();
            } else if (operation == 4) {
                searchRecord();
            } else if (operation == 5){
                boolean result = getAvailableFlight();
                if(result){
                    selectFlight();
                }else{
                    System.out.println("Select 0 to return to perform another operation;5 to book a flight; 6 to return to main menu; * to quit");
                }

            }else if (operation == 6) {
                Process process = new Process(con);
                process.performProcess();
            }else if (operation == 0){
                process();
            }else {
                continueInput.set(false);
                System.exit(0);
            }
        }
    }

    private void createRecord() {
        input.useDelimiter("\n");
        System.out.println("Input Name");
        String passengerName = input.next();

        System.out.println("input mobile number");
        String mobile = input.next();

        System.out.println("input email");
        String email = input.next();

        System.out.println("input address");
        String address = input.next();

        System.out.println("Processing....................");

        String id = passengerManagerService.createPassenger(passengerName, mobile, email, address);

        printPassengerRecord(id, passengerName, email, mobile, address);

        System.out.println("Select 0 to return to perform another operation;5 to book a flight; 6 to return to main menu; * to quit");
    }

    private void updateRecord() {
        input.useDelimiter("\n");

        System.out.println("Input passengerId");
        String id =input.next();

        Passenger passenger = passengerManagerService.getPassengerById(id);

        if (passenger == null){
            System.out.println("Passenger not found");
        }else{
            System.out.println("Name: " + passenger.getName());
            System.out.println("Email: " + passenger.getEmail());
            System.out.println("Mobile: " + passenger.getMobile());
            System.out.println("Address: " + passenger.getAddress());

            System.out.println("Do you want to proceed");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int choice = input.nextInt();

            if (choice == 1){
                System.out.println("input mobile number");
                String mobile = input.next();
                passenger.setMobile(mobile);

                System.out.println("input email");
                String email = input.next();
                passenger.setEmail(email);

                System.out.println("input address");
                String address = input.next();
                passenger.setAddress(address);

                System.out.println("Processing......................");
                passengerManagerService.updatePassenger(id,passenger);
                System.out.println("Select 0 to return to perform another operation; 6 to return to main menu; " +
                        "* to quit");

            }
        }

        System.out.println("Select 0 to return to perform another operation; 6 to return to main menu; * to quit");
    }

    private void deleteRecord() {
        System.out.println("Input passengerId");
        String id =input.next();

        System.out.println("Deleting............");

        passengerManagerService.deletePassenger(id);
        System.out.println("Select 0 to return to perform another operation; 6 to return to main menu; * to quit");
    }

    private void searchRecord() {
        input.useDelimiter("\n");
        System.out.println("Input query; \n Note: query can either be name, email address or mobile number of passenger");
        String query = input.next();

        System.out.println("Searching...................");
        passengerManagerService.searchPassenger(query);

        System.out.println("Select 0 to return to perform another operation; 6 to return to main menu; * to quit");

    }

    public boolean getAvailableFlight(){

        System.out.println("input query; \n Note: query can either be takeOff location, destination or date");
        String query = input.next();

        System.out.println("Processing.................");

        return flightsManagementService.searchFlight(query);
    }
    public void selectFlight(){
        System.out.println("Do you want to proceed to Book a flight");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int choice = input.nextInt();
        if(choice == 1){
            System.out.println("Kindly input your Passenger id");
            String passengerId = input.next();
            Passenger pass = passengerManagerService.getPassengerById(passengerId);
            String name = pass.getName();
                System.out.println("Kindly input the FlightId of your choice from the list given below");
                String FlightId = input.next();
                book.createBookingFromSearch(FlightId, passengerId, name);

            System.out.println("Processing......................");

        }else {
            System.out.println("Select 0 to return to perform another operation; 7 to return to main menu; * to quit");
        }
    }

    private void printPassengerRecord(String id, String name, String email, String mobile, String address){

        System.out.println("***********Record saved successfully************");
        System.out.println("Below are your details");
        System.out.println("PASSENGER ID: " + id);
        System.out.println("PASSENGER NAME: " + name);
        System.out.println("EMAIL: " + email);
        System.out.println("MOBILE: " + mobile);
        System.out.println("Address: " + address);
        System.out.println("Kindly note down your passenger id");

    }

}
