package com.company;



import java.sql.*;
import java.util.Formatter;
import java.util.Locale;

public class AircraftManagementService {
    private  final Connection con;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;

    StringBuilder sb = new StringBuilder();
    Formatter formatter = new Formatter(sb, Locale.US);



    public AircraftManagementService(Connection con) {
        this.con = con;
    }

    public void createAircraft(String name, String model, int capacity) {
            try {
                preparedStatement = con.prepareStatement("insert into aircraft (name, model, capacity,status)" +
                        "values (?,?,?,?)");

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, model);
                preparedStatement.setInt(3,capacity);
                preparedStatement.setString(4, "false");

                preparedStatement.executeUpdate();
                System.out.println("Aircraft saved successfully");

            } catch (SQLException e) {
                System.out.println("Aircraft already exists.");
                e.printStackTrace();
            }finally{
                try{
                    if(preparedStatement != null) preparedStatement.close();
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }

    }

    public void updateAircraft(Aircraft aircraft) {
        try {
            preparedStatement = con.prepareStatement("update aircraft set name =?, capacity=?, status=? where model = ?");
            preparedStatement.setString(1,aircraft.getName());
            preparedStatement.setInt(2, aircraft.getCapacity());
            preparedStatement.setString(3, aircraft.getModel());
            preparedStatement.setString(4, aircraft.getStatus());
            preparedStatement.executeUpdate();

            System.out.println("Aircraft records updated successfully");
        } catch (SQLException e) {
            System.out.println("Unable to update");
            e.printStackTrace();
        }
    }

    public void deleteAircraft(String model) {
        try {
            preparedStatement = con.prepareStatement("delete from aircraft where model =?");
            preparedStatement.setString(1, model);
            preparedStatement.executeUpdate();

            System.out.println("Aircraft record deleted successfully");
        } catch (SQLException e) {
            System.out.println("unable to delete");
            e.printStackTrace();
        }
    }

    public Aircraft getAircraftByModel(String model) {
        try {
            preparedStatement = con.prepareStatement("select * from aircraft where model = ?");
            preparedStatement.setString(1, model);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                String name = rs.getString(1);
                int capacity = rs.getInt(3);

                return new Aircraft(name, model, capacity);
            }

        }catch (SQLException e){
            System.out.println("Aircraft does not exits");
            e.printStackTrace();
        }
        return null;
    }

    public void searchAircraft(String query) {
        String name;
        String model;
        int capacity;
        int count = 0;

        formatter.format("********List of Aircraft***********\n");
        formatter.format("%1$-15s %2$-15s %3$-15s\n ","Name"," Model" ,"Capacity");

        try {
            preparedStatement = con.prepareStatement("select * from aircraft where ? in (name,model)");
            preparedStatement.setString(1, query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                name = rs.getString(1);
                model = rs.getString(2);
                capacity = rs.getInt(3);
                count++;

                formatter.format("%1$-15s %2$-15s %3$-15s\n",name, model ,capacity);

            }

            if (count > 0) {
                System.out.println(sb.toString());
            }else{
                System.out.println("Aircraft does not exist.");
            }



        } catch (Exception e) {
            System.out.println("Invalid query");
            e.printStackTrace();
        }
    }

    public void printAircraftList() {
        System.out.println("Processing.............");
        formatter.format("********List of Aircraft***********\n");
        formatter.format("%1$-15s %2$-15s %3$-15s\n ","Name"," Model" ,"Capacity");
        try {
            String query = "select * from aircraft";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            String name;
            String model;
            int capacity;
            int count = 0;
            while (rs.next()) {
                name = rs.getString(1);
                model = rs.getString(2);
                capacity = rs.getInt(3);
                count++;
                formatter.format("%1$-15s %2$-15s %3$-15s\n",name, model ,capacity);

            }
            if(count > 0) {
                System.out.println(sb.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void getAvailableAircraft(){
        System.out.println("Processing.............");
        formatter.format("********List of Aircraft***********\n");
        formatter.format("%1$-15s %2$-15s %3$-15s\n ","Name"," Model" ,"Capacity");
        try {
            String query = "select * from aircraft where status ='false' ";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            String name;
            String model;
            int capacity;
            int count = 0;
            while (rs.next()) {
                name = rs.getString(1);
                model = rs.getString(2);
                capacity = rs.getInt(3);
                count++;
                formatter.format("%1$-15s %2$-15s %3$-15s\n",name, model ,capacity);

            }
            if(count > 0) {
                System.out.println(sb.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}

