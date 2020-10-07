package com.company;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class CodeGenerator {
    private String location, code;

    public CodeGenerator() {
    }

    public String getValue() {
        return location;
    }

    public void setValue(String value) {
        this.location = value;
    }
    public String getCode() {
        return code;
    }

    public String genCode(@NotNull String location){
        code = location.substring(0,2);
        return code;
    }

    public String genFlightCode(@NotNull String location){
        code = location.substring(0,2);
        return code;
    }
    public String genBookingCode(@NotNull String passengerName){
        String sub = passengerName.substring(0,3);
        Random rand = new Random();
        int randNum = rand.nextInt(1000);
        code = sub + randNum;
        return code;
    }
    public String genTicketid(int seatNum,String flightCode, String bookingCode){
        code = flightCode + bookingCode + seatNum;
        return code;
    }
}
