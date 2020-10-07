package com.company;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FlightsManagementService {
    HashMap<String,Flight> _flight;
    private final AircraftManagementService aircraftManagementService;

    public FlightsManagementService(AircraftManagementService aircraftManagementService){
        this.aircraftManagementService = aircraftManagementService;

        _flight = new HashMap<>();
    }

    public void createNewFlight(String code,String departure, String destination,
                                Time departureTime,String aircraftName){

        Aircraft aircraft= aircraftManagementService.getAircraftByName(aircraftName);
        Flight flight = new Flight(departure, destination, departureTime,aircraft,code);



        _flight.put(code, flight);
    }

    public void updateFlight(String code,String departure, String destination, Time departureTime){

        Flight flight = getFlightByCode(code);
        flight.setDeparture(departure);
        flight.setDestination(destination);
        flight.setDepartureTime(departureTime);
    }

    public void deleteFlight(String code){
        _flight.remove(code);
    }

    public Flight getFlightByCode(String code){
        if(_flight.containsValue(code)){

            throw new UnsupportedOperationException("Flight exists");
        }
        return _flight.get(code);
    }

    public ArrayList<Flight> searchFlight(String query){
        ArrayList<Flight> result = new ArrayList<>();
        for (Flight a:_flight.values()) {
            if(a.getCode().contains(query)|| a.getDeparture().contains(query) || a.getDestination().contains(query)){
                result.add(a);
            }
        }
        return result;
    }







}
