package com.company;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.Random;

public class Generator {
    private String code;

    public Generator() {
    }


    public String genPassengerId(@NotNull String passengerName){
        String sub = passengerName.substring(0,3);
        Random rand = new Random();
        int randNum = rand.nextInt(1000);
        code = sub + randNum;
        return code;
    }
    public String genTicketId(String seatNum,String flightCode, String passengerId){
        code = flightCode + "/" + passengerId + "/" + seatNum;
        return code;
    }
    public Time generateDepartureTime() {
        final Random random = new Random();
        final int millisInDay = 24*60*60*1000;

        return new Time(random.nextInt(millisInDay));
    }
}
