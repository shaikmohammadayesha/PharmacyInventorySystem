Pharmacy Inventory System

A Java-based console application for managing a pharmacy's medicine inventory.

## Features
- Display all medicines
- Insert new medicines
- Update medicine quantity
- Delete medicines
- Filter medicines by category
- Show medicines expiring within 30 days

## Setup
1. **Prerequisites**:
    - Java 8 or higher
    - MySQL Server
    - MySQL Connector/J (included in `lib/mysql-connector-j-8.0.33.jar`)

2. **Database Setup**:
    - Create a MySQL database named `pharmacy_db`:
      ```sql
      CREATE DATABASE pharmacy_db;
      USE pharmacy_db;
      CREATE TABLE Medicines (
          ID INT AUTO_INCREMENT PRIMARY KEY,
          NAME_OF_MEDICINE VARCHAR(100) UNIQUE,
          QUANTITY INT,
          PRICE DECIMAL(10, 2),
          EXPIRE_DATE DATE,
          CATEGORY VARCHAR(50)
      );
      ALTER TABLE Medicines AUTO_INCREMENT = 1000;
      ```
3. **Configuration**:
   - Copy config.properties.template to config.properties.
   - Edit config.properties with your MySQL credentials:
    ```text
    url=jdbc:mysql://localhost:3306/pharmacy_db?useSSL=false&serverTimezone=Asia/Kolkata
    user=your_mysql_username
    password=your_mysql_password
   ```
