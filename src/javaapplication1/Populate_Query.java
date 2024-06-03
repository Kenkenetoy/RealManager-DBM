package javaapplication1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class Populate_Query {

    public HashMap<String, Integer> populateCombo(String ID, String Name, String Table, String current_user_id, String database) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        Statement st;
        ResultSet rs;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            st = con.createStatement();
            rs = st.executeQuery("select " + ID + ", " + Name + " from " + Table + " where Realty_ID = '" + current_user_id + "'");
            Populate_Combo cmi;

            while (rs.next()) {
                cmi = new Populate_Combo(rs.getInt(1), rs.getString(2));
                map.put(cmi.getComName(), cmi.getComID());
            }

        } catch (SQLException ex) {
            Logger.getLogger(Populate_Query.class.getName()).log(Level.SEVERE, null, ex);
        }

        return map;
    }
}
