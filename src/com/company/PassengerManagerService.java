package com.company;

import java.sql.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;

public class PassengerManagerService {
    private final Connection con;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    private final HashMap<String, Passenger> _passenger;
    Generator gen = new Generator();
    private String code;
    StringBuilder sb = new StringBuilder();
    Formatter formatter = new Formatter(sb, Locale.US);
    private final BookingManagementService bookingManagementService;

    public PassengerManagerService(BookingManagementService bookingManagementService, Connection con) {
        _passenger = new HashMap<>();
        this.con = con;
        this.bookingManagementService = bookingManagementService;
    }

    public String createPassenger(String name, String mobile, String email, String address) {
        code = gen.genPassengerId(name);
        try {
            preparedStatement = con.prepareStatement("insert into passenger (id, name, email, address," +
                    " phone_number) values (?,?,?,?,?)");

            preparedStatement.setString(1, code);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, mobile);

            preparedStatement.executeUpdate();
            System.out.println("Passenger record saved successfully");
            return code;

        } catch (Exception e) {
            System.out.println("Unable to save passenger");
            e.printStackTrace();
        }
        try {
            if (preparedStatement != null) preparedStatement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  null;
    }


    public void updatePassenger(String id, Passenger passenger) {
        try {
            preparedStatement = con.prepareStatement("update passenger set " +
                    " phone_number =?, email =?, address =? where id =?");

            preparedStatement.setString(1, passenger.getMobile());
            preparedStatement.setString(2, passenger.getEmail());
            preparedStatement.setString(3, passenger.getAddress());
            preparedStatement.setString(4, id);
            preparedStatement.executeUpdate();

            System.out.println("Passenger records updated successfully");
        } catch (Exception e) {
            System.out.println("Unable to update");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deletePassenger(String id) {
        try {
            preparedStatement = con.prepareStatement("delete from passenger " +
                    "where id =?");

            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Passenger record deleted successfully");
        } catch (SQLException e) {
            System.out.println("unable to delete");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Passenger getPassengerById(String id) {
        try {
            preparedStatement = con.prepareStatement("select * from " +
                    "passenger where id =?");
            preparedStatement.setString(1, id);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString(2);
                String email = rs.getString(3);
                String address = rs.getString(4);
                String mobile = rs.getString(5);

                return new Passenger(name, mobile, email, address);

            }
        } catch (SQLException e) {
            System.out.println("Passenger does not exits");
            e.printStackTrace();
        }
        return null;
    }


    public void searchPassenger(String query) {
        formatter.format("**********Search Result*************\n");
        formatter.format("%1$-25s %2$-10s %3$-15s %4$-10s %5$-10s\n ",
                "PassengerId", "Name", "Email", "Mobile", "Address");

        try {
            preparedStatement = con.prepareStatement("select * from passenger " +
                    "where ? in (id, name, email, phone_number, address)");
            preparedStatement.setString(1, query);
            rs = preparedStatement.executeQuery();

            int count = 0;
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                String address = rs.getString(4);
                String mobile = rs.getString(5);
                count++;

                formatter.format("%1$-25s %2$-10s %3$-15s %4$-10s %5$-10s\n ",
                        id, name, email, mobile, address);
            }
            if(count > 0){
                System.out.println(sb.toString());
            }

        } catch (SQLException e) {
            System.out.println("Invalid query");
            e.printStackTrace();
        }
    }

}


