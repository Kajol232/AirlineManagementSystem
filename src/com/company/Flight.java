package com.company;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.Random;

public class Flight {
    private String departure, destination;
    private Aircraft aircraft;
    private Time departureTime;
    private  String code;
    public Flight(String departure, String destination, Time departureTime, Aircraft aircraft, String code) {

        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.aircraft=aircraft;
        this.code = code;
    }

    public Flight(String departure, String destination) {
        this.departure = departure;
        this.destination = destination;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public String getCode() {
        return code;
    }

    public String getDeparture() {
        return departure;
    }

    public String setCode(String destination){
        String code = generateFlightCode(destination);
        return code;
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

    public Time generateDepartureTime() {
        final Random random = new Random();
        final int millisInDay = 24*60*60*1000;
        departureTime = new Time(random.nextInt(millisInDay));

        return departureTime;
    }

    public Time getDepartureTime() {
        return departureTime;
    }


    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public String generateFlightCode(String destination){
        String code;
        CodeGenerator codeGen = new CodeGenerator();
        code = codeGen.genFlightCode(destination);
        return code;
    }
    public boolean hasSeatAvailable(String flightCode){


        return true;
    }
}
