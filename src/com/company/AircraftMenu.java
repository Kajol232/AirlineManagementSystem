package com.company;

import javax.xml.bind.SchemaOutputResolver;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class AircraftMenu {
    private final AircraftManagementService aircraftManagementService;
    private final Connection con;

    public AircraftMenu(Connection con) {
        this.con = con;
        aircraftManagementService = new AircraftManagementService(con);
    }

    Scanner input = new Scanner(System.in);

    public void process() {
        System.out.println("7. Quit");
        System.out.println("0. Return");
        System.out.println("1. Create aircraft");
        System.out.println("2. Update aircraft");
        System.out.println("3. Delete aircraft");
        System.out.println("4. Search aircraft");
        System.out.println("5. Print AircraftList");
        System.out.println("6. Return to Main Menu");

        AtomicBoolean continueInput = new AtomicBoolean(true);
        while (continueInput.get()) {
            int operation = input.nextInt();

            if (operation == 1) {
                createAircraft();
            } else if (operation == 2) {
                updateAircraft();
            } else if (operation == 3) {
                deleteAircraft();
            } else if (operation == 4) {
                searchAircraft();
            } else if (operation == 5) {
               aircraftManagementService.printAircraftList();
                System.out.println("Select 0 to return to perform another operation; 6 to return to main menu; " +
                        "7 to quit");
            }else if (operation == 6){
                Process process = new Process(con);
                process.performProcess();
            }else if(operation == 0){
                process();
            }else if(operation == 7){
                continueInput.set(false);
                System.exit(0);
            }else{
                System.out.println("Invalid selection; Kindly select 0 to return to menu ; 7 to quit");
            }
        }
    }
    private void createAircraft(){
        input.useDelimiter("\n");
        System.out.println("Input name of aircraft");
        String name = input.next();

        System.out.println("Input model of aircraft");
        String model = input.next();

        System.out.println("Input capacity of aircraft");
        int capacity = input.nextInt();

        System.out.println("Processing.........");

        aircraftManagementService.createAircraft(name, model, capacity);

        System.out.println("Select 0 to return to perform another operation; 6 to return to main menu; 7 to quit");
    }

    private void updateAircraft(){
        input.useDelimiter("\n");
        System.out.println("Input model of aircraft");
        String model = input.next();

        System.out.println("Processing.........");
        Aircraft aircraft = aircraftManagementService.getAircraftByModel(model);

        if (aircraft == null) {
            System.out.println("Aircraft does not exist");
        }else{

            System.out.println("Aircraft Model: " + aircraft.getModel());
            System.out.println("Aircraft Name: " + aircraft.getName());
            System.out.println("Aircraft Capacity: " + aircraft.getCapacity());

            System.out.println("Do you want to proceed");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int choice = input.nextInt();

            if (choice == 1) {

                System.out.println("Input name of aircraft");
                String name = input.next();
                aircraft.setName(name);

                System.out.println("Input capacity of aircraft");
                int capacity = input.nextInt();
                aircraft.setCapacity(capacity);

                System.out.println("Updating............");

                aircraftManagementService.updateAircraft(aircraft);
        }
        }
        System.out.println("Select 0 to return to perform another operation; 6 to return to main menu; 7 to quit");

    }

    private void deleteAircraft(){
        System.out.println("Input model of aircraft");
        String model = input.next();

        Aircraft aircraft = aircraftManagementService.getAircraftByModel(model);

        if (aircraft == null){
            System.out.println("Aircraft does not exist");
        }else {

            System.out.println("Deleting...........");

            aircraftManagementService.deleteAircraft(model);
        }
        System.out.println("Select 0 to return to perform another operation; 6 to return to main menu; 7 to quit");
    }
    private void searchAircraft(){
        System.out.println("Input query; \n Note: query can either be name or model of aircraft");
        String query = input.next();

        System.out.println("Searching.........");

        aircraftManagementService.searchAircraft(query);
        System.out.println("Select 0 to return to perform another operation; 6 to return to main menu; 7 to quit");
    }

}
