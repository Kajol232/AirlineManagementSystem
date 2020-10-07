package com.company;

import org.jetbrains.annotations.NotNull;

public class FlightTicket {
    private int seatNum;
    private Booking booking;
    private Flight flight;

    public FlightTicket(int seatNum, Booking booking, Flight flight) {
        this.seatNum = seatNum;
        this.booking = booking;
        this.flight = flight;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBookingId(Booking booking) {
        this.booking = booking;
    }

    public Flight getFlightId() {
        return flight;
    }

    public void setFlight(String flightId) {
       this.flight = flight;
    }

    public void assignFlightToBooking(@NotNull Booking booking, Flight flight){
        String bookId =booking.getBookingId();


    }
}
