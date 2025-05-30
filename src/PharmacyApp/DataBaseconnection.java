package PharmacyApp;
//DataBaseConnection class --> as the name says, this class is used to establish the connection between java and mysql.

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;

class DataBaseconnection {
    final private Connection connection;
    final private Properties proprty;

     protected DataBaseconnection(){
        proprty = new Properties();

        //loading the configuration file into properties --> file contains the details required to get connection (url, userName and password).
        try(FileInputStream fip = new FileInputStream("config.properties")) {
            proprty.load(fip);
        }catch (IOException | RuntimeException e) {
            throw new RuntimeException(e);
        }
        String url = proprty.getProperty("url");
        String userName = proprty.getProperty("userName");
        String password = proprty.getProperty("password");

        //loading drivers (optional --> but good practice to add it)
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        //establishing connection.
        try{
            connection = DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //returns connection
    public Connection getConnection(){
        return connection;
    }
    //close connection
    public void closeConnection(){
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
                System.out.println("Connection is now closed.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}