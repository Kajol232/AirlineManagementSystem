package com.company;

import java.sql.*;
import java.util.Formatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;


public class FlightMenu {
   private final FlightsManagementService flightsManagementService;
    private final Connection con;

    StringBuilder sb = new StringBuilder();
    Formatter formatter = new Formatter(sb, Locale.US);

    AircraftManagementService aircraftManagementService;

    public FlightMenu(Connection con) {
        this.con = con;
        aircraftManagementService = new AircraftManagementService(con);
        flightsManagementService = new FlightsManagementService(aircraftManagementService, con);
    }

    Scanner input = new Scanner(System.in);

    public void process() {
        System.out.println("7. Quit");
        System.out.println("0. Return");
        System.out.println("1. Create Flight");
        System.out.println("2. Update Flight");
        System.out.println("3. Delete Flight");
        System.out.println("4. Retrieve Flight");
        System.out.println("5. Print FlightList");
        System.out.println("6. Return to Main Menu");

        AtomicBoolean continueInput = new AtomicBoolean(true);
        while (continueInput.get()) {

            int operation = input.nextInt();

            if (operation == 1) {
                createFlight();
            } else if (operation == 2) {
                updateFlight();
            } else if (operation == 3) {
                deleteFlight();
            } else if (operation == 4) {
                searchFlight();
            }else if (operation == 5){
                printFlightList();
                System.out.println("Select 0 to return to perform another operation; 6 to return to main menu;" +
                        " 7 to quit");
            }else if (operation == 6) {
                Process process = new Process(con);
                process.performProcess();
            }else if (operation == 7){
                continueInput.set(false);
                System.exit(0);
            }else if(operation == 0){
                process();
            }else{
                System.out.println("Invalid operations, Select 6 to return; 7 to quit");
            }
        }
    }
    private void createFlight(){
        System.out.println("Input takeoff point of flight");
        String departure = input.next();

        System.out.println("Input destination of flight");
        String destination = input.next();

        System.out.println("input departure date of flight; Note: input should be in format dd/mm/yy");
        String date = input.next();


        aircraftManagementService.getAvailableAircraft();

        System.out.println("Input model of craft to be assigned");
        String aircraftModel = input.next();

        Aircraft aircraft = aircraftManagementService.getAircraftByModel(aircraftModel);
        aircraft.setStatus("true");
        aircraftManagementService.updateAircraft(aircraft);


        System.out.println("Processing.........");

        flightsManagementService.createNewFlight(departure,destination, aircraftModel, date);

        System.out.println("Select 0 to return to perform another operation; 6 to return to main menu; 7 to quit");
    }

    private void updateFlight(){
        System.out.println("input flightId");
        String flightId = input.next();

        System.out.println("Processing.........");
        Flight flight = flightsManagementService.getFlightByCode(flightId);

        if(flight == null){
            System.out.println("Flight does not exist");
        }else {

            formatter.format("%1$-15s %2$-15s %3$-15s %4$-15s %5$-15s %6$-15s\n ",
                    "FlightId","Departure", "Destination","Time", "Seats","Date");
            formatter.format("%1$-15s %2$-15s %3$-15s %4$-15s %5$-15s %6$-15s\n ",flight.getAircraftModel(),
                    flight.getDeparture(),flight.getDestination(),flight.getDepartureTime(),flight.getCapacity(),
                    flight.getDate());
            System.out.println(sb.toString());

            System.out.println("Do you want to proceed");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int choice = input.nextInt();

            if (choice == 1) {

                System.out.println("Input takeoff point of flight");
                String departure = input.next();
                flight.setDeparture(departure);

                System.out.println("Input destination of flight");
                String destination = input.next();
                flight.setDestination(destination);

                System.out.println("input departure date of flight; Note: input should be in format dd/mm/yy");
                String date = input.next();
                flight.setDate(date);

                System.out.println("Updating............");

                flightsManagementService.updateFlight(flight);
            }
        }

        System.out.println("Select 0 to return to perform another operation; 6 to return to main menu; 7 to quit");
    }

    private void deleteFlight(){
        System.out.println("Input ID of flight");
        String flightId = input.next();

        Flight flight = flightsManagementService.getFlightByCode(flightId);

        if (flight == null){
            System.out.println("Flight does not exist");
        }else {

            System.out.println("Deleting...........");

            flightsManagementService.deleteFlight(flightId);
        }

        System.out.println("Select 0 to return to perform another operation; 6 to return to main menu; 7 to quit");
    }
    private void searchFlight(){
        System.out.println("Input query; \n Note: query can either be flightId, destination or departure of flight");
        String query = input.next();

        System.out.println("Searching.........");

       flightsManagementService.searchFlight(query);

        System.out.println("Select 0 to return to perform another operation; 6 to return to main menu; 7 to quit");
    }
    private void printFlightList(){
        System.out.println("Processing.............");
        formatter.format("********List of Aircraft***********\n");
        formatter.format("%1$-15s %2$-15s %3$-15s %4$-15s %5$-15s %6$-15s\n ",
                "FlightId","Departure", "Destination","Time", "Seats","Date");
        try{
            String query = "select * from flight";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            int count = 0;
            while(rs.next()) {
                String model = rs.getString(1);
                String departure = rs.getString(2);
                String destination = rs.getString(3);
                String t = rs.getString(4);
                Time time = Time.valueOf(t);
                int seats = rs.getInt(5);
                String date = rs.getString(6);
                count++;

                formatter.format("%1$-15s %2$-15s %3$-15s %4$-15s %5$-15s %6$-15s\n ",model,departure, destination,
                        time, seats, date);
            }
            if(count > 0) {
                System.out.println(sb.toString());
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
