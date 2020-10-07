package com.company;

public class SeatManagementService {

    private final int capacity;
    private int seatNum;
    private Aircraft aircraft;
    private final FlightsManagementService flightMgt;

    public SeatManagementService(FlightsManagementService flightMgt) {
        this.flightMgt = flightMgt;
        capacity = aircraft.getCapacity();
    }




}
