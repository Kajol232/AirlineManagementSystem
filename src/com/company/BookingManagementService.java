package com.company;

import sun.security.krb5.internal.Ticket;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BookingManagementService {
    HashMap<String,Booking> _booking;
    private final FlightsManagementService flightMgt;


    public BookingManagementService(FlightsManagementService flightMgt){
        this.flightMgt = flightMgt;
        _booking = new HashMap<>();
    }


    public void createNewBooking(String nameOfPassenger, String typeOfFlight, int classOfFlight,
                                 String dateOfFlight, String bookingId, String destination, String departure){

         Booking booking = new Booking(nameOfPassenger, typeOfFlight, classOfFlight,dateOfFlight, destination, departure);

        if(_booking.containsValue(bookingId)){
            throw new UnsupportedOperationException("Flight already Booked");
        }
        _booking.put(bookingId,booking);
    }

    public void updateBooking(String nameOfPassenger, String typeOfFlight, int classOfFlight,
                             String dateOfFlight, String bookingId, String destination, String departure){

        Booking booking = searchBookingById(bookingId);
        booking.setClassOfFlight(classOfFlight);
        booking.setDateOfFlight(dateOfFlight);
        booking.setNameOfPassenger(nameOfPassenger);
        booking.setTypeOfFlight(typeOfFlight);
        booking.setDestination(destination);
        booking.setDeparture(departure);
    }

    public void deleteBooking(String bookingId){
        _booking.remove(bookingId);
    }

    public Booking searchBookingById(String bookingId){

        if(!_booking.containsValue(bookingId)){
            throw new UnsupportedOperationException("Flight not booked");
        }
        return _booking.get(bookingId);
    }

    public ArrayList<Booking> searchBooking(String query){
        ArrayList<Booking> result = new ArrayList<>();
        for (Booking a:_booking.values()) {
            if(a.getBookingId().contains(query) || a.getNameOfPassenger().contains(query) || a.getDeparture().contains(query)
            || a.getDestination().contains(query)){
                result.add(a);
            }
        }
        return result;
    }

}
