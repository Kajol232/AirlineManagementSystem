package com.company;


import java.sql.Connection;
import java.sql.Time;
import java.util.Formatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookingMenu {
    Connection con;
    AircraftManagementService aircraftManagementService;

    FlightsManagementService flightsManagementService;

    BookingManagementService bookingManagementService;

    StringBuilder sb = new StringBuilder();
    Formatter formatter = new Formatter(sb, Locale.US);



    Generator gen = new Generator();



    public BookingMenu(Connection con){
        this.con = con;
        aircraftManagementService =new AircraftManagementService(con);
        flightsManagementService = new FlightsManagementService(aircraftManagementService, con);
        bookingManagementService = new BookingManagementService(flightsManagementService, con);

    }

    Scanner input = new Scanner(System.in);


    public void process() {

        System.out.println("Welcome, Kindly Book Your Flight");
        System.out.println("Kindly select any of the option listed below");
        System.out.println("6. Quit");
        System.out.println("0. Return");
        System.out.println("1. Create Booking");
        System.out.println("2. Update Booking");
        System.out.println("3. Delete Booking");
        System.out.println("4. Retrieve Booking");
        System.out.println("5. Return to Main Menu");

        AtomicBoolean continueInput = new AtomicBoolean(true);

        while (continueInput.get()) {

            int operation = input.nextInt();
            if (operation == 1){
                createBooking();
            } else if (operation == 2) {
                updateBooking();
            } else if (operation == 3) {
                deleteBooking();
            } else if (operation == 4) {
                searchBooking();
            }else if (operation == 5) {
                Process process = new Process(con);
                process.performProcess();
            }else if (operation == 6){
                continueInput.set(false);
                System.exit(0);
            }else if (operation == 0){
                process();
            }else {
                System.out.println("Invalid operations, Select 5 to return; 6 to quit");
            }
        }

    }

   private void createBooking(){
       System.out.println("Are you an existing customer?");
       System.out.println("1. Yes");
       System.out.println("2. No");
       int choice = input.nextInt();
       if(choice == 2) {
           System.out.println("Kindly proceed to the registration page to sign up");
           PassengerMenu passengerMenu = new PassengerMenu(con);
           passengerMenu.process();
       }else if(choice == 1){
           PassengerMenu passengerMenu = new PassengerMenu(con);
           boolean result = passengerMenu.getAvailableFlight();
           if(result){
               passengerMenu.selectFlight();
           }else{
               System.out.println("Select 0 to return to perform another operation; 5 to return to main menu;" +
                       " 6 to quit");
           }
       }

    }

    public void createBookingFromSearch(String flightId, String passengerId, String passengerName) {

        Flight flight = flightsManagementService.getFlightByCode(flightId);

        String departure = flight.getDeparture();

        String destination = flight.getDestination();

        String dateOfDeparture = flight.getDate();

        Time time = flight.getDepartureTime();
        double p;

        System.out.println("1. One-way ");
        System.out.println("2. Return ticket?");
        int choice = input.nextInt();
        String typeOfFlight = getTypeOfFlight(choice);

        System.out.println("1. FirstClass");
        System.out.println("2. Business");
        System.out.println("3. Economy");
        int option = input.nextInt();
        String classOfFlight = getClassOfFlight(option);

        p = getPrice(choice, option);

        System.out.println("Dear " + passengerName + ", your flight from " + departure + " to " + destination +
                " is charged at N" + p);
        System.out.println("1. Proceed to payment page");
        System.out.println("0. Exit");
        int select = input.nextInt();



        if (select == 1) {
            System.out.println("Processing...........");
            String seat = flightsManagementService.assignSeat(flightId, classOfFlight);
            String ticketId = gen.genTicketId(seat, flightId, passengerId);
            bookingManagementService.createNewBooking(ticketId, passengerName, typeOfFlight, classOfFlight,
                    dateOfDeparture,
                    passengerId, destination, departure, time);

            printTicket(passengerName,ticketId,time,dateOfDeparture,destination,departure,classOfFlight, typeOfFlight);

            System.out.println("Select 0 to return to perform another operation; 5 to return to main menu; 6 to quit");
        }
    }


    //check payment ans seat assignation and also what happens to the previous seatnum.
        private void updateBooking(){
            System.out.println("Note: You can only update the ticket type and ticket class");
            System.out.println("Input TicketId");
            String ticketId =input.next();
            System.out.println("Processing*******************");
            Booking booking = bookingManagementService.searchBookingById(ticketId);

            if (booking == null) {
                System.out.println("Booking does not exist");
            }else{

                formatter.format("%1$-25s %2$-10s %3$-15s %4$-10s %5$-10s %6$-10s %7$-15s %8$-20s %9$-10s\n ",
                        "TicketId", "PassengerId", "Name", "Departure", "Destination", "Time", "Date", "Type", "Class");

                formatter.format("%1$-25s %2$-10s %3$-15s %4$-10s %5$-10s %6$-10s %7$-15s %8$-20s %9$-10s\n ",
                        ticketId, booking.getPassengerId(), booking.getNameOfPassenger(), booking.getDeparture(),
                        booking.getDestination(),booking.getTime(), booking.getDateOfFlight(),
                        booking.getTypeOfFlight(), booking.getClassOfFlight());
                System.out.println(sb.toString());

                System.out.println("Do you want to proceed");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int select = input.nextInt();

                if (select == 1){
                    System.out.println("Kindly select option to update");
                    System.out.println("0. Both");
                    System.out.println("1. Ticket Type");
                    System.out.println("2. Ticket Class");
                    int c = input.nextInt();

                    String typeOfFlight = null;
                    String classOfFlight = null;
                    int choice = 0, option = 0;

                    if (c == 0){
                        System.out.println("1. One-way ");
                        System.out.println("2. Return ticket?");
                        choice = input.nextInt();
                        typeOfFlight = getTypeOfFlight(choice);
                        booking.setTypeOfFlight(typeOfFlight);

                        System.out.println("1. FirstClass");
                        System.out.println("2. Business");
                        System.out.println("3. Economy");
                        option = input.nextInt();
                        classOfFlight = getClassOfFlight(option);
                        booking.setClassOfFlight(classOfFlight);

                    }else if(c == 1){
                        System.out.println("1. One-way ");
                        System.out.println("2. Return ticket?");
                        choice = input.nextInt();
                        typeOfFlight = getTypeOfFlight(choice);
                        booking.setTypeOfFlight(typeOfFlight);

                    }else if (c == 2){
                        System.out.println("1. FirstClass");
                        System.out.println("2. Business");
                        System.out.println("3. Economy");
                        option = input.nextInt();
                        classOfFlight = getClassOfFlight(option);
                        booking.setClassOfFlight(classOfFlight);

                    } else{
                        System.out.println("invalid option selected");
                    }
                    double p = getPrice(choice, option);
                    System.out.println("New charges is " + p + "Kindly proceed to payment section to regularize " +
                            "your payment");

                    System.out.println("Updating...............");

                    String flightId = ticketId.substring(0, 7);
                    String seatNum = flightsManagementService.assignSeat(flightId, classOfFlight);
                    String newTicketId = gen.genTicketId(seatNum, flightId, booking.getPassengerId());
                    booking.setTicketId(newTicketId);

                    String passId = booking.getPassengerId();

                    bookingManagementService.updateBooking(booking, passId);

                    String name = booking.getNameOfPassenger();
                    String dest = booking.getDestination();
                    String dep = booking.getDeparture();
                    String date = booking.getDateOfFlight();
                    Time time = booking.getTime();

                    printTicket(name, newTicketId, time, date, dest, dep, classOfFlight, typeOfFlight);


                }
                System.out.println("Select 0 to return to perform another operation; 5 to return to main menu; " +
                        "6 to quit");

            }
    }

    private void deleteBooking(){
        System.out.println("input bookingId");
        String code = input.next();

        System.out.println("Deleting...................");
        bookingManagementService.deleteBooking(code);

        System.out.println("Select 0 to return to perform another operation; 5 to return to main menu; 6 to quit");
    }

    private void searchBooking(){
        System.out.println("input query; \n Note:query can either be TicketId, PassengerId, Destination, Departure," +
                "Date or Time of flight");
        String query = input.next();

        System.out.println("Searching...................");

        bookingManagementService.searchBooking(query);
        System.out.println("Select 0 to return to perform another operation; 5 to return to main menu; 6 to quit");
    }

    private String getTypeOfFlight(int choice){
        if (choice == 1){
            return "One-way";
        }else if(choice == 2){
            return "Return ticket";
        }
        return "Invalid selection";
    }

    private String getClassOfFlight(int choice){
        if (choice == 1){
            return "FirstClass";
        }else if(choice == 2){
            return "Business";
        }else if(choice == 3){
            return "Economy";
        }
        return "invalid Selection";
    }


    private double getPrice (int type, int grade){
        double p = 20000;
        if(grade == 3){
            if(type == 1){
                return p;
            }else{
                return p * 2;
            }
        }else if (grade == 2){
            if(type == 1){
                return p + 5000;
            }else{
                return 2 * (p + 5000);
            }
        }else{
            if(type == 1){
                return p + 10000;
            }else{
                return 2 * (p + 10000);
            }
        }
    }

    private void printTicket(String name, String ticketId, Time timeOfFlight, String date, String destination,
                             String departure, String classes, String ticketType){
        System.out.println("***********Booking Successful************");
        System.out.println("Below are your flight details");
        System.out.println("PASSENGER NAME: " + name);
        System.out.println("TICKET ID: "+ ticketId);
        System.out.println("DEPARTURE AIRPORT: " + departure);
        System.out.println("DESTINATION AIRPORT: " + destination);
        System.out.println("DATE OF DEPARTURE: " + date);
        System.out.println("TIME OF DEPARTURE: " + timeOfFlight);
        System.out.println("TICKET TYPE: " + ticketType);
        System.out.println("TICKET SECTION: " + classes);
        System.out.println("Bon Voyage!!!!!!!!!!");
    }





}



