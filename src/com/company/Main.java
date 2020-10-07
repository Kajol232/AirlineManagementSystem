package com.company;
import javax.swing.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

public class Main {

    public static void main(String[] args) throws ParseException {
        final int SEARCH_FLIGHT = 1;
        final int BOOK_FLIGHT = 2;

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome, Kindly Book Your Flight");
        System.out.println("Kindly select any of the option listed below");
        System.out.println("0. Exit");
        System.out.println("1. Search for a Flight");
        System.out.println("2. Book a Flight");

        int operation = input.nextInt();

        switch (operation){

            case SEARCH_FLIGHT:
                System.out.println("Kindly input flight name");
                String query = input.nextLine();

            break;

            case BOOK_FLIGHT:

        System.out.println("What's your name?");
        String passengerName =input.next();
        System.out.println("What's your takeoff point?");
        String departure = input.next();
        System.out.println("Where are you going?");
        String destination = input.next();
       // SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");
        System.out.println("When are you going?(dd/mm/yy)");
        String dateOfDeparture = input.next();
        //Date date = formatter.parse(dateOfDeparture);
        System.out.println("One-way or Return ticket?");
        String typeOfFlight = input.next();
        System.out.println("1.FirstClass" +
                            "2. Business " +
                            "3. Economy?");
      int classOfFlight = input.nextInt();
        Booking book = new Booking(passengerName,typeOfFlight,classOfFlight,dateOfDeparture,departure,destination);
       book.setBookingCode(passengerName);
                String code = book.getBookingId();
        String flightId = book.assignFlighttoBook();
        Seat seat = new Seat(classOfFlight,flightId);
        int seatNum = seat.assignSeat(classOfFlight);
                System.out.println("Dear " + passengerName + ", your booking id is " + code + " and you are on Flight "
                        + flightId + " with seat no " + seatNum);
        }

    }
}
