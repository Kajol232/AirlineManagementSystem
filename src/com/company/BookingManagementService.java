package com.company;


import java.sql.*;
import java.util.Formatter;
import java.util.Locale;

public class BookingManagementService {
    private final Connection con;
    private PreparedStatement preparedStatement = null;
    private  ResultSet rs = null;
    StringBuilder sb = new StringBuilder();
    Formatter formatter = new Formatter(sb, Locale.US);


    public BookingManagementService(FlightsManagementService flightMgt, Connection con){
        this.con = con;
    }


    public void createNewBooking(String ticketId, String nameOfPassenger, String typeOfFlight, String classOfFlight,
                                 String dateOfFlight, String passengerId, String destination, String departure, Time time){

            try {
               preparedStatement = con.prepareStatement("insert into booking (ticketId, passengerId, name," +
                       " destination, departure, date,time,flight_type, flight_class) values (?,?,?,?,?,?,?,?,?)");

               preparedStatement.setString(1,ticketId);
               preparedStatement.setString(2, passengerId);
               preparedStatement.setString(3,nameOfPassenger);
               preparedStatement.setString(4,destination);
               preparedStatement.setString(5, departure);
               preparedStatement.setString(6, dateOfFlight);
               preparedStatement.setString(7, String.valueOf(time));
               preparedStatement.setString(8, typeOfFlight);
               preparedStatement.setString(9, classOfFlight);

               preparedStatement.executeUpdate();

            }
            catch (Exception e){
                System.out.println("Unable to save aircraft.");
                e.printStackTrace();
            }
        }


    public void updateBooking(Booking booking, String passid){
        try {
            preparedStatement = con.prepareStatement("update booking set ticketId = ?, " +
                            "flight_type =?,flight_class =? where  passengerId =?");
            preparedStatement.setString(1, booking.getTicketId());
            preparedStatement.setString(2, booking.getTypeOfFlight());
            preparedStatement.setString(2, booking.getClassOfFlight());
            preparedStatement.setString(4, passid);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Unable to update record");
            e.printStackTrace();
        }

    }

    public void deleteBooking(String bookingId){
        try {
            preparedStatement = con.prepareStatement("delete from booking where ticketId =?");
            preparedStatement.setString(1,bookingId);
            preparedStatement.executeUpdate();

            System.out.println("Booking deleted successfully");
        } catch (SQLException e) {
            System.out.println("Unable to delete record");
            e.printStackTrace();
        }
    }

    public Booking searchBookingById(String bookingId){
        try {
            preparedStatement = con.prepareStatement("select  * from booking where " +
                    "ticketId = ?");
            preparedStatement.setString(1, bookingId);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                String passId = rs.getString(2);
                String passName = rs.getString(3);
                String dest = rs.getString(4);
                String dep = rs.getString(5);
                String date = rs.getString(6);
                Time time = Time.valueOf(rs.getString(7));
                String type = rs.getString(8);
                String classes = rs.getString(9);

                return new Booking(bookingId,passId,passName,dest,dep,date,time,type,classes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    public void searchBooking(String query) {
        formatter.format("**********Search Result*************\n");
        formatter.format("%1$-25s %2$-10s %3$-15s %4$-10s %5$-10s %6$-10s %7$-15s %8$-20s %9$-10s\n ",
                "TicketId", "PassengerId", "Name", "Departure", "Destination", "Time", "Date", "Type", "Class");

        try {
            preparedStatement = con.prepareStatement("select * from booking where? " +
                    "in(ticketId, passengerId, name, destination, departure, date, time)");
            preparedStatement.setString(1, query);
            rs = preparedStatement.executeQuery();

            int count = 0;
            while (rs.next()) {
                String ticketId = rs.getString(1);
                String passId = rs.getString(2);
                String name = rs.getString(3);
                String dest = rs.getString(4);
                String dept = rs.getString(5);
                String date = rs.getString(6);
                String time = rs.getString(7);
                String type = rs.getString(8);
                String classes = rs.getString(9);
                count++;

                formatter.format("%1$-25s %2$-10s %3$-15s %4$-10s %5$-10s %6$-10s %7$-15s %8$-20s %9$-10s\n ",
                        ticketId, passId, name, dept, dest, time, date, type, classes);
            }
            if (count > 0){
                System.out.println(sb.toString());
            }else{
                System.out.println("No available booking");
            }
        } catch (SQLException e) {
            System.out.println("invalid query");
            e.printStackTrace();
        }
    }




}
