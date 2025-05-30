package PharmacyApp;
//PharmacyApp class is like the user interface --> displays list of action that can be performed and reads the prompt from user to do that action/task.

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class PharmacyApp{
    static LocalDate readExpireDate(Scanner scanner){
        LocalDate expireDate = null;
        try{
            while(expireDate == null){
                String input = scanner.nextLine();
                expireDate = LocalDate.parse(input);
            }
        }catch (DateTimeParseException e){
            e.printStackTrace();
        }
        return expireDate;
    }
    public static void main(String[] args) {
        DataBaseconnection dataBaseconnection = null;
        Scanner scanner = new Scanner(System.in);
        try{
            dataBaseconnection = new DataBaseconnection();
            UserOperations userOperations = new UserOperations(dataBaseconnection.getConnection());
            while(true){
                System.out.println("1.Display Medicines.");
                System.out.println("2.Insert Medicine.");
                System.out.println("3.Update Add Medicine quantity.");
                System.out.println("4.Update Decrease Medicine quantity.");
                System.out.println("5.Remove Medicine.");
                System.out.println("6.show Medicine by Category.");
                System.out.println("7.show expiring medicines.");
                System.out.println("8.Exit.");
                System.out.println("Select the option to be performed: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                if(choice == 8) break;

                switch (choice){
                    case 1:
                        userOperations.displayMedicines();
                        break;
                    case 2:
                        System.out.println("Medicine name: ");
                        String name = scanner.nextLine();
                        System.out.println("Quantity: ");
                        int quantity = scanner.nextInt();
                        System.out.println("Price: ");
                        double price = scanner.nextDouble();
                        System.out.println("Expire Date in the format 'YYYY-MM-DD' : ");
                        scanner.nextLine();
                        LocalDate expireDate = readExpireDate(scanner);
                        System.out.println("Category of Medicine (ex: fever/painkiller/cold): ");
                        String category = scanner.nextLine();
                        userOperations.insertMedicine(name, quantity,price, expireDate,category);
                        break;
                    case 3:
                        System.out.println("Enter the name of the medicine : ");
                        name = scanner.nextLine();
                        System.out.println("Quantity added : ");
                        quantity = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Expire Date : ");
                        expireDate = readExpireDate(scanner);
                        userOperations.updateQuantityIncrease(name, quantity, expireDate);
                        break;
                    case 4:
                        System.out.println("Enter the name of the medicine : ");
                        name = scanner.nextLine();
                        System.out.println("Quantity required : ");
                        quantity = scanner.nextInt();
                        userOperations.updateQuantityDecrease(name, quantity);
                        break;
                    case 5:
                        System.out.println("Enter the name of medicine to be removed: ");
                        name = scanner.nextLine();
                        userOperations.removeMedicine(name);
                        break;
                    case 6:
                        System.out.println("Enter category of Medicine: ");
                        category = scanner.nextLine();
                        userOperations.displayMedicinesViaCategory(category);
                        break;
                    case 7:
                        userOperations.expiringMedicines();
                        break;
                    default:
                        System.out.println("Enter a valid option  (number in range 1 - 7).");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(dataBaseconnection != null){
                dataBaseconnection.closeConnection();
            }
            scanner.close();
        }
    }
}