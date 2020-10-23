package com.company;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Seat {
    final String FIRST_CLASS = "FirstClass";
    final String ECONOMY = "Economy";
    final String BUSINESS = "Business";
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    private final String flightId;
    Connection con;

    Scanner input = new Scanner(System.in);



    public Seat(String flightId, Connection con) {

        this.flightId = flightId;
        this.con = con;


    }



    public int assignSeat(String section){
        switch (section){
            case FIRST_CLASS:
                try {
                    preparedStatement = con.prepareStatement("select * from seats where flightid =?");
                    preparedStatement.setString(1,flightId);
                    rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        int seatNum = rs.getInt(2);
                        if(seatNum > 0){
                        updateSeat(flightId, seatNum, section);
                        return seatNum;
                        }else{
                            selectOtherSection(section);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case ECONOMY:
                try {
                    preparedStatement = con.prepareStatement("select * from seats where flightid =?");
                    preparedStatement.setString(1,flightId);
                    rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        int seatNum = rs.getInt(4);
                        if(seatNum > 0) {
                            updateSeat(flightId, seatNum, section);
                            return seatNum;
                        }else{
                            selectOtherSection(section);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case BUSINESS:
                try {
                    preparedStatement = con.prepareStatement("select * from seats where flightid =?");
                    preparedStatement.setString(1,flightId);
                    rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        int seatNum = rs.getInt(3);
                        if (seatNum > 0) {
                            updateSeat(flightId, seatNum, section);
                            return seatNum;
                        }else{
                            selectOtherSection(section);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            default:
                System.out.println("Invalid section selected");
        }

        return 0;
    }

    //Add code for checking payment for section change.
    private void selectOtherSection(String section){
        String selection;
        System.out.println("All seats in section " + section + " are booked; Kindly select another section");
        if(section.equals(FIRST_CLASS)){
            System.out.println("1. Economy");
            System.out.println("2. Business");
            int choice = input.nextInt();
           selection = getSection(choice,"Economy", "Business");
           assignSeat(selection);
        }else if(section. equals(BUSINESS)){
            System.out.println("1. FirstClass");
            System.out.println("2. Economy");
            int choice = input.nextInt();
            selection = getSection(choice,"FirsClass", "Economy");
            assignSeat(selection);
        }else {
            System.out.println("1. Economy");
            System.out.println("2. FirstClass");
            int choice = input.nextInt();
            selection = getSection(choice,"Economy", "FirsClass");
            assignSeat(selection);
        }
    }

    private  void updateSeat(String id, int seat, String section){
        seat -= 1;
        switch (section) {
            case FIRST_CLASS:
                try {
                    preparedStatement = con.prepareStatement("update seats set firstclass = ? where flightid = ? ");
                    preparedStatement.setInt(1, seat);
                    preparedStatement.setString(2, id);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case BUSINESS:
                try {
                    preparedStatement = con.prepareStatement("update seats set business = ? where flightid = ? ");
                    preparedStatement.setInt(1, seat);
                    preparedStatement.setString(2, id);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case ECONOMY:
                try {
                    preparedStatement = con.prepareStatement("update seats set economy = ? where flightid = ? ");
                    preparedStatement.setInt(1, seat);
                    preparedStatement.setString(2, id);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:

        }
    }
    private String getSection(int choice, String section1, String section2){
        if (choice == 1){
            return section1;
        }else if(choice == 2){
            return section2;
        }else
        return "invalid Selection";
    }


}



