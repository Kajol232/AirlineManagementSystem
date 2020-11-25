package com.company;

import java.sql.Time;

public class Flight {
    private String departure, destination;
    private final String aircraftModel;
    private Time departureTime;
    private String date;
    private int capacity;

    public Flight(String departure, String destination, Time departureTime,String date,
                  String aircraftModel) {
        //flight should contain aircraft not aircraft model.

        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.aircraftModel = aircraftModel;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Time getDepartureTime() {
        return departureTime;
    }


    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public String toString(){
        return "[" + this.aircraftModel + ", " + this.departure + this.destination + this.departureTime + "]";
    }

}
