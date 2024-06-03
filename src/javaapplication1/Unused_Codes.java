package javaapplication1;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Unused_Codes {
    //unused code!!!
//unused code!!!
//unused code!!!
//unused code!!!
//unused code!!!
//private void scaleImage(String location, JLabel label) {
//        ImageIcon icon = new ImageIcon(location);
//        Image img = icon.getImage();
//        Image imgScale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
//        ImageIcon scaledIcon = new ImageIcon(imgScale);
//        label.setIcon(scaledIcon);
//
//    }
////    first phase
//    //    first phase
////    first phase
////    first phase
////    first phase
////    first phase
//
//    private void floorselection() {
//        try {
//            Floors.removeAllItems();
//            Rooms.removeAllItems();
//            Rooms.addItem("select");
//            Rooms.setSelectedItem(1);
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
//            String sql = "select * from floors";
//            PreparedStatement pst = con.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                Floors.addItem(rs.getString(1));
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
//
//    private void roomselection() {
//        try {
//            Rooms.removeAllItems();
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
//            if (Floors.getSelectedItem().equals("First Floor")) {
//                val = "floor1";
//            } else if (Floors.getSelectedItem().equals("Second Floor")) {
//                val = "floor2";
//            } else if (Floors.getSelectedItem().equals("Third Floor")) {
//                val = "floor3";
//            } else {
//                JOptionPane.showMessageDialog(this, "Please select a floor first!");
//            }
//            String sql = "select room_no from " + val;
//            PreparedStatement pst = con.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                Rooms.addItem(rs.getString(1));
//            }
//        } catch (HeadlessException | SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
//
//    private void pricing() {
//        try {
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
//            val1 = Rooms.getSelectedItem().toString();
//            String sql = "select * from (select * from floor1 union select * from floor2 union select * from floor3) as u where u.room_no = '" + val1 + "'";
//            PreparedStatement pst = con.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                Pricing.setText(String.valueOf(rs.getString("price")));
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
    //unused code!!
    //unused code!!
    //unused code!!
    //unused code!!
    //unused code!!
    //unused code!!
    //unused code!!
    //unused code!!
//    private void deveselector() {
//        try {
//            devselect.removeAllItems();
//            devselect.addItem("Select");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
//            String sql = "select dev_Name from developers";
//            PreparedStatement pst = con.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                devselect.addItem(rs.getString(1));
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
//    
//    private void agent_selector() {
//        try {
//            agent_selector.removeAllItems();
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
//            String sql = "select Agent_Name from agents";
//            PreparedStatement pst = con.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                agent_selector.addItem(rs.getString(1));
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
//    
//    private void pricing_thirdPhase(String loc) {
//        try {
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
//            String sql = "select * from projects where Proj_Name = " + loc;
//            PreparedStatement pst = con.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                JName.setText(String.valueOf(rs.getString("Proj_Name")));
//                JPricing.setText(String.valueOf(rs.getString("Proj_Price")));
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
//    private void actionPerformed(ActionEvent e) {
//        if (e.getSource() == jRadioButton1) {
//            pricing_thirdPhase(jRadioButton1.toString());
//        } else if (e.getSource() == jRadioButton2) {
//            pricing_thirdPhase(jRadioButton2.toString());
//        } else if (e.getSource() == jRadioButton3) {
//            pricing_thirdPhase(jRadioButton3.toString());
//        }
//    }
    
    
    
    
//            if (devselect.getSelectedItem().equals("John Cyril Russo")) {
//            display_image("1001", house_1, jRadioButton1, Price_1, Developer_1);
//            display_image("1002", house_2, jRadioButton2, Price_2, Developer_2);
//            display_image("1003", house_3, jRadioButton3, Price_3, Developer_3);
//            house_1.setVisible(true);
//            house_2.setVisible(true);
//            house_3.setVisible(true);
//        } else if (devselect.getSelectedItem().equals("Kenneth Jose Aparece")) {
//            display_image("1004", house_1, jRadioButton1, Price_1, Developer_1);
//            display_image("1005", house_2, jRadioButton2, Price_2, Developer_2);
//            display_image("1006", house_3, jRadioButton3, Price_3, Developer_3);
//            house_1.setVisible(true);
//            house_2.setVisible(true);
//            house_3.setVisible(true);
//        } else if (devselect.getSelectedItem().equals("Magno Canama")) {
//            display_image("1007", house_1, jRadioButton1, Price_1, Developer_1);
//            display_image("1008", house_2, jRadioButton2, Price_2, Developer_2);
//            display_image("1009", house_3, jRadioButton3, Price_3, Developer_3);
//            house_1.setVisible(true);
//            house_2.setVisible(true);
//            house_3.setVisible(true);
//        } else if (devselect.getSelectedItem().equals("Select")) {
//            house_1.setVisible(false);
//            house_2.setVisible(false);
//            house_3.setVisible(false);
//            jRadioButton1.setText(null);
//            jRadioButton2.setText(null);
//            jRadioButton3.setText(null);
//        }
}
