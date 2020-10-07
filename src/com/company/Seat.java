package com.company;

import java.util.Scanner;

public class Seat {
    final int FIRST = 1;
    final int BUSINESS = 2;
    final int ECONOMY = 3;
    private int seatClass;
    private int capacity = 100;
    boolean[] arrSeats = new boolean[capacity];

    public Seat(int seatClass, String flightcode) {
        this.seatClass = seatClass;
        this.capacity = capacity;
    }

    public int getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(int seatClass) {
        this.seatClass = seatClass;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public int assignSeat(int section){

        switch (section){
            case FIRST:
                if(getFreeSeats(section)> 0){
                    for (int i = 0; i < 10 ; i++)
                        if (arrSeats[i] == false){
                            arrSeats[i] = true;
                            return i;
                        }
                }
                break;
            case ECONOMY:
                if(getFreeSeats(section)> 0){
                    for (int i = 10; i < 60 ; i++)
                        if (arrSeats[i] == false){
                            arrSeats[i] = true;
                            return i;
                        }
                }
                break;

            case BUSINESS:
                if(getFreeSeats(section)> 0){
                    for (int i = 60; i < arrSeats.length ; i++)
                        if (arrSeats[i] == false){
                            arrSeats[i] = true;
                            return i;
                        }
                }
                break;
            default:
                System.out.printf("All seats in section \"%s\" are booked.\n", section);
                System.out.printf("Would you like to be moved to another section"+ "Y or N");
                Scanner sc = new Scanner(System.in);
                if(sc.next().charAt(0) == 'y'){
                    System.out.println("1. Firstclass");
                    System.out.println("2. Business");
                    System.out.println("3. Economy");
                    int i = sc.nextInt();
                    assignSeat(i);
                }
                else{
                    System.out.println("\nNext flight leaves in 3 hours.\n");}

        }
        return Integer.parseInt(null);
    }

    private int getFreeSeats(int section){
        int total = 0;
        switch (section){
            case FIRST:
                for(int i=0; i<10; i++){
                    if(arrSeats[i] == false)
                        total += 1;
                }
            case BUSINESS:
                for(int i=10; i<60; i++){
                    if(arrSeats[i] == false)
                    total += 1;
            }
            case ECONOMY:
                for(int i=60; i<arrSeats.length; i++){
                    if(arrSeats[i] == false)
                        total += 1;
                }
        }
        return total;
    }
    public boolean seatsAvailable(){
        for(boolean seat : arrSeats)
            if(seat == false)
                return true;

        return false;
    }
}



