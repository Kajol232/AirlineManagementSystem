package com.company;

import java.sql.Time;

public class Booking {
    private String nameOfPassenger;
    private String typeOfFlight;
    private String classOfFlight;
    private String destination;
    private String departure;
    private String dateOfFlight;
    private final Time time;
   private final String passengerId;
   private  String ticketId;


    public Booking(String ticketId,String passengerId, String nameOfPassenger, String destination, String departure, String dateOfFlight, Time time, String typeOfFlight, String classOfFlight  ) {
        this.ticketId = ticketId;
        this.passengerId = passengerId;
        this.nameOfPassenger = nameOfPassenger;
        this.time = time;
        this.typeOfFlight = typeOfFlight;
        this.classOfFlight = classOfFlight;
        this.dateOfFlight = dateOfFlight;
        this.destination = destination;
        this.departure = departure;



    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTypeOfFlight() {
        return typeOfFlight;
    }

    public void setTypeOfFlight(String typeOfFlight) {
        this.typeOfFlight = typeOfFlight;
    }

    public String getClassOfFlight() {
        return classOfFlight;
    }

    public void setClassOfFlight(String classOfFlight) {
        this.classOfFlight = classOfFlight;
    }

    public String getDateOfFlight() {
        return dateOfFlight;
    }

    public void setDateOfFlight(String dateOfFlight) {
        this.dateOfFlight = dateOfFlight;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getNameOfPassenger() {
        return nameOfPassenger;
    }

    public void setNameOfPassenger(String nameOfPassenger) {
        this.nameOfPassenger = nameOfPassenger;
    }

    public Time getTime() {
        return time;
    }

    public String getPassengerId() {
        return passengerId;
    }
}
