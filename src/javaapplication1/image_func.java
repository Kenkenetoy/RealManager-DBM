package javaapplication1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.table.TableModel;

public class image_func {

    public image_func() {

    }

    public void insert_values_for_projects(String database, int proj_id, String proj_name, int proj_price, String dir, int dev_ID, String proj_status) {
        try {

            String date = new SimpleDateFormat("MMMM-dd-YYYY HH:mm:ss").format(Calendar.getInstance().getTime()).toUpperCase();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, "root", "");
            String sql = "insert ignore into projects(Proj_ID, Proj_Name, Proj_Price, Proj_Status, Proj_Image, Dev_Branch_ID, Is_Archived, Date_Created, Date_Modified) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            FileInputStream img = new FileInputStream(dir);
            pst.setInt(1, proj_id);
            pst.setString(2, proj_name);
            pst.setInt(3, proj_price);
            pst.setString(4, proj_status);
            pst.setBinaryStream(5, img, img.available());
            pst.setInt(6, dev_ID);
            pst.setString(7, "No");
            pst.setString(8, date);
            pst.setString(9, date);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void update_values_for_projects(String database, String proj_name, int proj_price, String dir, String ID) {
        try {
            String date = new SimpleDateFormat("MMMM-dd-YYYY HH:mm:ss").format(Calendar.getInstance().getTime()).toUpperCase();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, "root", "");
            String sql = "update projects set Proj_Name = ?, Proj_Price = ?, Proj_Image = ?, Date_Modified = ? where Proj_ID = " + ID;
            PreparedStatement pst = conn.prepareStatement(sql);
            FileInputStream img = new FileInputStream(dir);
            pst.setString(1, proj_name);
            pst.setInt(2, proj_price);
            pst.setBinaryStream(3, img, img.available());
            pst.setString(4, date);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void status_projects(String database, String status, String ID) {
        try {

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, "root", "");
            String sql = "update projects set Proj_Status=? where Proj_ID = " + ID;
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, status);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);

        }
    }
}
