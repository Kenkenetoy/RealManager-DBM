package javaapplication1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author user
 */
public class start {

    static image_func insert_image;

    public static void main(String[] args) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
        Statement stmtt = con.createStatement();
        String database = "Finalsdicks1";

        try {
            stmtt.executeUpdate("create database if not exists " + database);
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, "root", "");
            Statement stmt = conn.createStatement();
            System.out.println("Database created successfully...");

            stmt.executeUpdate("create table if not exists realty"
                    + "(Realty_ID int(25) primary key auto_increment, "
                    + "Realty_Name varchar(25), "
                    + "Realty_Location varchar(50), "
                    + "Realty_Password varchar(50),"
                    + "Is_Archived varchar(25), "
                    + "Date_Created varchar(25), "
                    + "Date_Modified varchar(25)) "
                    + "auto_increment = 1000");
            System.out.println("realty table created successfully...");

            stmt.executeUpdate("create table if not exists developers"
                    + "(Dev_Branch_ID int(25) primary key, "
                    + "Dev_Name varchar(25), "
                    + "Dev_Location varchar(50), "
                    + "Realty_ID int(25), "
                    + "Is_Archived varchar(25), "
                    + "Date_Created varchar(25), "
                    + "Date_Modified varchar(25), "
                    + "foreign key (Realty_ID) references realty(Realty_ID))");
            System.out.println("developers table created successfully...");

            stmt.executeUpdate("create table if not exists agents"
                    + "(Agent_ID int(25) primary key auto_increment, "
                    + "Agent_Name varchar(50) unique, "
                    + "Agent_Rank varchar(25), "
                    + "Agent_Address varchar(50), "
                    + "Agent_Age int(25), "
                    + "Agent_Location varchar(50), "
                    + "Agent_Gender varchar(25), "
                    + "Agent_Email varchar(50), "
                    + "Agent_Contact varchar(25),"
                    + "Realty_ID int(25),"
                    + "Is_Archived varchar(25), "
                    + "Date_Created varchar(25), "
                    + "Date_Modified varchar(25), "
                    + "foreign key (Realty_ID) references realty(Realty_ID)) "
                    + "auto_increment = 1000");
            System.out.println("agents table created successfully...");

            stmt.executeUpdate("create table if not exists client"
                    + "(Client_ID int(25) primary key auto_increment, "
                    + "Client_Name varchar(25), "
                    + "Client_Address varchar(50), "
                    + "Client_Age int(25), "
                    + "Client_Gender varchar(25), "
                    + "Client_EmailAddress varchar(50), "
                    + "Client_ContactNo varchar(25), "
                    + "Agent_ID int(25) , "
                    + "Is_Archived varchar(25), "
                    + "Date_Created varchar(25), "
                    + "Date_Modified varchar(25), "
                    + "foreign key (Agent_ID) references agents(Agent_ID), unique (Client_Name)) "
                    + "auto_increment = 1000");
            System.out.println("client table created successfully...");

            stmt.executeUpdate("create table if not exists projects"
                    + "(Proj_ID int(25) primary key auto_increment, "
                    + "Proj_Name varchar(25), "
                    + "Proj_Price int(25), "
                    + "Proj_Status varchar(25), "
                    + "Proj_Image mediumblob, "
                    + "Dev_Branch_ID int(25), "
                    + "Client_ID int(25),"
                    + "Is_Archived varchar(25), "
                    + "Date_Created varchar(25), "
                    + "Date_Modified varchar(25), "
                    + "foreign key (Dev_Branch_ID) references developers(Dev_Branch_ID), "
                    + "foreign key (Client_ID) references client(Client_ID)) "
                    + "auto_increment = 1000");
            System.out.println("developers table created successfully...");

            stmt.executeUpdate("create table if not exists sales_log"
                    + "(Sales_ID int(25) primary key auto_increment, "
                    + "Realty_ID int(25), "
                    + "Agent_ID int(25), "
                    + "Client_ID int(25), "
                    + "Dev_Branch_ID int(25), "
                    + "Proj_ID int(25), "
                    + "Pay_Method varchar(25), "
                    + "Incentives varchar(25), "
                    + "Total_Sales_Price double(50, 2), "
                    + "Realty_commission double(25, 2), "
                    + "Agent_commission double(25, 2), "
                    + "Equity double(25, 2), "
                    + "Is_Archived varchar(25), "
                    + "Date_Created varchar(25), "
                    + "foreign key (Realty_ID) references realty(Realty_ID), "
                    + "foreign key (Agent_ID) references agents(Agent_ID), "
                    + "foreign key (Client_ID) references client(Client_ID), "
                    + "foreign key (Dev_Branch_ID) references developers(Dev_Branch_ID), "
                    + "foreign key (Proj_ID) references projects(Proj_ID)) auto_increment = 1000");
            System.out.println("sales log table created successfully...");

//            stmt.executeUpdate("insert ignore into agents(Agent_ID, Agent_Name, Agent_Rank, Agent_Address, Agent_Age, Agent_Location, Agent_Gender, Agent_Email, Agent_Contact) values "
//                    + "(1001, 'placeholder1', 'placeholder', 'placeholder', 20, 'placeholder', 'placeholder', 'placeholder', 'placeholder'), "
//                    + "(1002, 'placeholder2', 'placeholder', 'placeholder', 25, 'placeholder', 'placeholder', 'placeholder', 'placeholder'), "
//                    + "(1003, 'placeholder3', 'placeholder', 'placeholder', 30, 'placeholder', 'placeholder', 'placeholder', 'placeholder')");
//            System.out.println("data inserted into agents successfully...");
//
//            stmt.executeUpdate("insert ignore into realty(Realty_ID, Realty_Name, Realty_Location, Realty_Password) values "
//                    + "(1001, 'Realty_Owner_1', 'Location_Unkown', 'start12')");
//            System.out.println("data inserted into realty successfully...");
//            
//            stmt.executeUpdate("insert ignore into developers(Dev_Branch_ID, Dev_name, Dev_Location, Realty_ID) values "
//                    + "(1001, 'John Cyril Russo', 'Dao, Tagbilaran', 1001), "
//                    + "(1002, 'Kenneth Jose Aparece', 'Cabilao, Loon', 1001), "
//                    + "(1003, 'Magno Canama', 'Panglao', 1001)");
//            System.out.println("data inserted into developers successfully...");
//            insert_image = new image_func();
//            insert_image.insert_values_for_projects(database, "dev_1.1", 1000, "C:\\Users\\Acer\\Documents\\NetBeansProjects\\advanceddatabase\\dev_1.1.jpg", 1001);
//            insert_image.insert_values_for_projects(database, "dev_1.2", 2000, "C:\\Users\\Acer\\Documents\\NetBeansProjects\\advanceddatabase\\src\\assets\\dev_1.2.jpg", 1001);
//            insert_image.insert_values_for_projects(database, "dev_1.3", 3000, "C:\\Users\\Acer\\Documents\\NetBeansProjects\\advanceddatabase\\src\\assets\\dev_1.3.jpg", 1001);
//            insert_image.insert_values_for_projects(database, "dev_2.1", 4000, "C:\\Users\\Acer\\Documents\\NetBeansProjects\\advanceddatabase\\src\\assets\\dev_2.1.jpg", 1002);
//            insert_image.insert_values_for_projects(database, "dev_2.2", 5000, "C:\\Users\\Acer\\Documents\\NetBeansProjects\\advanceddatabase\\src\\assets\\dev_2.2.jpg", 1002);
//            insert_image.insert_values_for_projects(database, "dev_2.3", 6000, "C:\\Users\\Acer\\Documents\\NetBeansProjects\\advanceddatabase\\src\\assets\\dev_2.3.jpg", 1002);
//            insert_image.insert_values_for_projects(database, "dev_3.1", 7000, "C:\\Users\\Acer\\Documents\\NetBeansProjects\\advanceddatabase\\src\\assets\\dev_3.1.jpg", 1003);
//            insert_image.insert_values_for_projects(database, "dev_3.2", 8000, "C:\\Users\\Acer\\Documents\\NetBeansProjects\\advanceddatabase\\src\\assets\\dev_3.2.jpg", 1003);
//            insert_image.insert_values_for_projects(database, "dev_3.3", 9000, "C:\\Users\\Acer\\Documents\\NetBeansProjects\\advanceddatabase\\src\\assets\\dev_3.3.jpg", 1003);
//            System.out.println("data inserted into projects successfully...");
            Log_In log = new Log_In(database);
            log.show();
            log.setVisible(true);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        } finally {
            con.close();
        }
    }

}
