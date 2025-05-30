package PharmacyApp;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.time.LocalDate;

class UserOperations {
    //establishing connection
    private final Connection connection;
    UserOperations(Connection connection){
        this.connection = connection;
    }
    void printMedicines(ResultSet rs){
        try {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME_OF_MEDICINE");
                int quantity = rs.getInt("QUANTITY");
                double price = rs.getDouble("PRICE");
                java.sql.Date expireDate = rs.getDate("Expire_Date");
                String category = rs.getString("Category");
                System.out.println("Id : " + id + " name : " + name + " quantity : " + quantity + " price : " + price + " expireDate : " + expireDate + " Category : " + category);
            }
            rs.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    //display all medicines
     void displayMedicines(){
        try{
            String query = "SELECT * FROM MEDICINES";
            Statement statement = connection.createStatement();
            printMedicines(statement.executeQuery(query));
            statement.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

     void insertMedicine(String name, int quantity, double price, LocalDate expireDate, String category) {
        try {
            String query = "INSERT INTO MEDICINES (NAME_OF_MEDICINE, QUANTITY, PRICE, EXPIRE_DATE, CATEGORY) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,quantity);
            preparedStatement.setDouble(3,price);
            preparedStatement.setDate(4,java.sql.Date.valueOf(expireDate) );
            preparedStatement.setString(5,category);
            int rowAffected = preparedStatement.executeUpdate();
            System.out.println(rowAffected + "row(s) affected.");
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

     void updateQuantityIncrease(String name, int quantity, LocalDate expireDate) {

        try {
            String query = "UPDATE MEDICINES SET QUANTITY = QUANTITY + ? , EXPIRE_DATE = ? WHERE NAME_OF_MEDICINE = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,quantity);
            preparedStatement.setDate(2,java.sql.Date.valueOf(expireDate));
            preparedStatement.setString(3,name);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + "row(s) affected");
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    void updateQuantityDecrease(String name, int quantityRequired){
        try{
            String query = "SELECT QUANTITY FROM MEDICINES WHERE NAME_OF_MEDICINE = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                int quantityAvailable = rs.getInt("Quantity");
                if(quantityAvailable == 0){
                    System.out.println(name + " is out of stock.");
                }else if(quantityAvailable < quantityRequired){
                    System.out.println("Sorry we only have "+ quantityAvailable + " available.");
                }else {
                    query = "UPDATE MEDICINES SET QUANTITY = QUANTITY - ? WHERE NAME_OF_MEDICINE = ?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query);
                    preparedStatement2.setInt(1,quantityRequired);
                    preparedStatement2.setString(2,name);
                    int rowsAffected = preparedStatement2.executeUpdate();
                    System.out.println(rowsAffected + " row(s) affected.");
                }
            }else{
                System.out.println(name + " not found.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    void removeMedicine(String name) {
        try {
            String query = "DELETE FROM MEDICINES WHERE NAME_OF_MEDICINE = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(name + " is removed from Medicines " + rowsAffected + " row(s) affected.");
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    void displayMedicinesViaCategory(String category) {
       try{
           String query = "SELECT * FROM MEDICINES WHERE CATEGORY = ?";
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setString(1,category);
           printMedicines(preparedStatement.executeQuery());
           preparedStatement.close();
       }catch (SQLException e){
           System.out.println(e.getMessage());
       }
    }

    void expiringMedicines() {
        try{
            String query = "SELECT * FROM MEDICINES WHERE EXPIRE_DATE < CURDATE() + INTERVAL 30 DAY";
            Statement statement = connection.createStatement();
            System.out.println("Medicines expiring within 30 days : ");
            printMedicines(statement.executeQuery(query));
            statement.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
