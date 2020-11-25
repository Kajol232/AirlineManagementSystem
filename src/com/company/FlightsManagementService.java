package com.company;


import java.sql.*;
import java.util.Formatter;
import java.util.Locale;

public class FlightsManagementService {
    private final Connection con;
    private PreparedStatement preparedStatement = null;
    private  ResultSet rs =null;
    private final Time departureTime;
    private final AircraftManagementService aircraftManagementService;

    public FlightsManagementService(
            AircraftManagementService aircraftManagementService, Connection con){
        this.con = con;
        this.aircraftManagementService = aircraftManagementService;
        Generator gen = new Generator();
        departureTime = gen.generateDepartureTime();

    }

    public void createNewFlight(String departure, String destination,
                                String model, String date){
        Aircraft aircraft = aircraftManagementService.getAircraftByModel(model);
        int seats = aircraft.getCapacity();

        try{
            preparedStatement = con.prepareStatement("insert into flight (model, departure, destination, time, " +
                    "seats, date) VALUES (?,?,?,?,?,?) ");

            preparedStatement.setString(1, model);
            preparedStatement.setString(2, departure);
            preparedStatement.setString(3, destination);
            preparedStatement.setString(4, String.valueOf(departureTime));
            preparedStatement.setInt(5, seats);
            preparedStatement.setString(6, date);

            preparedStatement.executeUpdate();
            updateSeatSection(seats, model);
            System.out.println("Flight saved successfully");

        }catch (SQLException e){
            System.out.println("Flight already exists");
            e.printStackTrace();
        }finally{
            try{
                if(preparedStatement != null) preparedStatement.close();
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public void updateFlight(Flight flight){
        try {
            preparedStatement = con.prepareStatement("update flight set destination =?," +
                    " departure =?, date =? where model = ?");

            preparedStatement.setString(1, flight.getDestination());
            preparedStatement.setString(2, flight.getDeparture());
            preparedStatement.setString(3, flight.getDate());
            preparedStatement.setString(4, flight.getAircraftModel());
            preparedStatement.executeUpdate();

            System.out.println("Flight records updated successfully");
        }catch (SQLException e){
            System.out.println("Unable to update");
            e.printStackTrace();
        }finally{
            try{
                if(preparedStatement != null) preparedStatement.close();
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public void deleteFlight(String code){
       try{
           preparedStatement = con.prepareStatement("delete from flight " +
                   "where model =?");

           preparedStatement.setString(1, code);
           preparedStatement.executeUpdate();

           System.out.println("Flight record deleted successfully");
       }catch(SQLException e){
           System.out.println("unable to delete");
           e.printStackTrace();
       }
    }

    public Flight getFlightByCode(String code){
        try {
            preparedStatement = con.prepareStatement("select * from " +
                    "flight where model =?");
            preparedStatement.setString(1, code);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                String departure = rs.getString(2);
                String destination = rs.getString(3);
                String t = rs.getString(4);
                Time time = Time.valueOf(t);
                String date = rs.getString(6);

                return new Flight(departure, destination, time,date, code);

            }
        }catch (SQLException e){
            System.out.println("Flight does not exits");
            e.printStackTrace();
        }
        return null;
    }

    public boolean searchFlight(String query){
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);
        formatter.format("**********Search Result*************\n");
        formatter.format("%1$-15s %2$-15s %3$-15s %4$-15s %5$-15s\n ",
                "FlightId","Departure", "Destination","Time", "Date");
        
        try {
            preparedStatement = con.prepareStatement("select * from flight " +
                    "where ? in (model,destination,departure,date)");
            preparedStatement.setString(1, query);
            rs = preparedStatement.executeQuery();
            int count = 0;
            while(rs.next()){
                count = 0;
                String model = rs.getString(1);
                String departure = rs.getString(2);
                String destination= rs.getString(3);
                String t = rs.getString(4);
                Time time = Time.valueOf(t);
                String date = rs.getString(6);
                count++;
                formatter.format("%1$-15s %2$-15s %3$-15s %4$-15s %5$-15s\n ",model,departure, destination,
                        time, date);
            }
               if (count > 0) {
                   System.out.println(sb.toString());
                   return true;
               }else{
                   System.out.println("No available flight for your search");

               }

        }catch (SQLException e){
            System.out.println("invalid query");
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public String assignSeat(String flightId, String seatClass){
        try{
            preparedStatement = con.prepareStatement("select * from flight where model = ?");
            preparedStatement.setString(1, flightId);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
           int cap = rs.getInt(5);

            if (cap > 0) {
                Seat seat = new Seat(flightId , con);
                String seatNum = seat.assignSeat(seatClass) + seatClass.substring(0, 3);

                cap -= 1;
                preparedStatement = con.prepareStatement("update flight set seats =? where model =?");
                preparedStatement.setInt(1, cap);
                preparedStatement.setString(2, flightId);
                preparedStatement.executeUpdate();

                return seatNum;
            }

            }


        }catch (SQLException e){
            System.out.println("unable to assign seat");
            e.printStackTrace();
        }
        return  "Flight not presently available for booking";
    }

    private void updateSeatSection(int capacity, String flightId){
        int firstClass = (int) (capacity * 0.1);
       int business = (int) (capacity *0.3);
        int economy = (int) (capacity*0.6);
        try{
            preparedStatement = con.prepareStatement("insert into seats(flightid, firstclass, business, economy)" +
                    "VALUES (?,?,?,?) ");
            preparedStatement.setString(1, flightId);
            preparedStatement.setInt(2, firstClass);
            preparedStatement.setInt(3, business);
            preparedStatement.setInt(4, economy);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
