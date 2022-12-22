package SQL;

import Objects.*;

import java.sql.*;
import java.util.ArrayList;

public class SQLDatabaseConnection {

    // Connect to your database.
    // Replace server name, username, and password with your credentials

    public static ArrayList<User> usersList = new ArrayList<User>();
    public static ArrayList<Car> carsList = new ArrayList<Car>();                       //2
    public static  ArrayList<Repair> repairsList = new ArrayList<Repair>();              //5
    public static ArrayList<Ride> ridesList = new ArrayList<Ride>();               //3
    public static ArrayList<Service> servicesList = new ArrayList<Service>(); //4
    public static ArrayList<Trip> tripsList= new ArrayList<Trip>();   //1
    private static ResultSet rs = null;

    private static String connectionUrl =
            "jdbc:sqlserver://localhost:1433;"
                    + "database=CarBase2;"
                    + "user=sa;"
                    + "password=reallyStrongPwd123;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=30;";
    public static void usersRefresh()
    {
        usersList.clear();
        get_users();
    }
    public static ArrayList<User> get_users() {


        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String query = "SELECT*from users";
            rs = statement.executeQuery(query);

            int i =0;
            while (rs.next()) {
                String usn = rs.getString(1);
                String pwd = rs.getString(2);
                boolean isAdmin;
                if (i==1)
                    isAdmin = true;
                else
                    isAdmin = false;

                User user = new User(usn,pwd,isAdmin);

                usersList.add(user);
                i=i+1;
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }
    public static ArrayList<Service> get_services()
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String query = "SELECT*from Service";
            rs = statement.executeQuery(query);


            while (rs.next()) {
                int id = rs.getInt(1);
                int car_id = rs.getInt(2);
                int repair_id = rs.getInt(3);
                Date date = rs.getDate(4);

                Service service = new Service(id,car_id,repair_id,date);

                servicesList.add(service);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return servicesList;
    }

    public static ArrayList<Repair> get_repair()
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String query = "SELECT*from Repairs";
            rs = statement.executeQuery(query);


            while (rs.next()) {
                int id = rs.getInt(1);
                String type = rs.getString(2);
                short time = rs.getShort(3);

                Repair repair = new Repair(id, type, time);

                repairsList.add(repair);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return repairsList;
    }

    public static ArrayList<Ride> get_rides()
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String query = "SELECT*from Ride";
            rs = statement.executeQuery(query);


            while (rs.next()) {
                int id = rs.getInt(1);
                int trip_id = rs.getInt(2);
                String car_num = rs.getString(3);
                Date dateBegin = rs.getDate(4);
                Date dateEnd = rs.getDate(5);
                float cargoWeight = rs.getFloat(6);

                Ride ride = new Ride(id, trip_id, car_num, dateBegin, dateEnd, cargoWeight);

                ridesList.add(ride);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return ridesList;
    }
    public static ArrayList<Trip> get_trip()
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String query = "SELECT*from Trip";
            rs = statement.executeQuery(query);


            while (rs.next()) {
                int id = rs.getInt(1);
                String start = rs.getString(2);
                float distance = rs.getFloat(3);
                String end = rs.getString(4);

                Trip trip = new Trip(id, start, distance, end);

                tripsList.add(trip);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return tripsList;
    }

    public static ArrayList<Car> get_cars()
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String query = "SELECT*from Car";
            rs = statement.executeQuery(query);


            while (rs.next()) {
                int id = rs.getInt(1);
                String number = rs.getString(2);
                String brand = rs.getString(3);
                String type = rs.getString(4);
                String color = rs.getString(5);

                Car car = new Car(id, number, brand, type, color);

                carsList.add(car);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return carsList;
    }


    public static void remove_from_list(int id,int list)
    {
        if(list==1)
        {
            for(Trip trip : tripsList)
            {
                if(trip.getId()==id)
                    ridesList.remove(trip);
            }
        }
        if(list==3)
        {
            for(Ride ride : ridesList)
            {
                if(ride.getId()==id)
                    ridesList.remove(ride);
            }
        }
        if(list==4)
        {
            for(Service service : servicesList)
            {
                if(service.getId()==id)
                    servicesList.remove(service);
            }
        }
        if(list==5)
        {
            for(Repair repair : repairsList)
            {
                if(repair.getId()==id)
                    repairsList.remove(repair);
            }
        }
        if(list==2)
        {
            for(Car car : carsList)
            {
                if(car.getId()==id)
                    repairsList.remove(car);
            }
        }
    }

    public static void update_table(String query)
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            System.out.println(query);
            statement.executeUpdate(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int max_id(String column_name,String table_name)
    {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String query = "SELECT MAX("+column_name+") FROM "+table_name;
            rs = statement.executeQuery(query);
            rs.next();
            result = rs.getInt(1);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void insert_row(String query)
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            System.out.println(query);
            statement.executeUpdate(query);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void drop_row(int id, String name, String idx_name)
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String temp_index = Integer.toString(id);

            String query = "DELETE "+name+" WHERE "+idx_name+" = "+temp_index;
            System.out.println(query);
            statement.executeUpdate(query);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}