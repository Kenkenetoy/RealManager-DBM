package javaapplication1;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class Oten extends javax.swing.JFrame {

    String current_id, current_user, database, val, val1, path, current_selected_developer, value_test, current_password;
    String selector_ID_1;
    String selector_ID_2;
    String selector_ID_3;
    String selector_ID_4;
    String proj_status;
    String Client_ID;
    String current_loc, current_date_created;
    static image_func insert_image;

    /**
     * Creates new form Oten
     */
    public Oten() {
        initComponents();
    }

    Oten(String current_user_id, String current_user_name, String database, String current_user_loc, String user_date_created, String current_user_password) {
        initComponents();
//        setIconImage();
        this.current_id = current_user_id;
        this.current_user = current_user_name;
        this.current_loc = current_user_loc;
        this.current_date_created = user_date_created;
        this.database = database;
        this.current_password = current_user_password;
        Set_Visibility(true, false, false, false, false); //home, sales, tools, account, data retrieval
        scaleImage("C:\\Users\\user\\Documents\\NetBeansProjects\\JavaApplication1\\src\\assets\\BG.png", BG);
        scaleImage("C:\\Users\\user\\Documents\\NetBeansProjects\\JavaApplication1\\src\\assets\\soc_logofb.png", FB);
        scaleImage("C:\\Users\\user\\Documents\\NetBeansProjects\\JavaApplication1\\src\\assets\\soc_logotw.png", Twitter);
        scaleImage("C:\\Users\\user\\Documents\\NetBeansProjects\\JavaApplication1\\src\\assets\\soc_logoig.png", Instagram);

        footer_id2.setText("Realty ID: " + current_id);
        Current_User_Date.setText(user_date_created);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        jScrollPane6.getVerticalScrollBar().setUnitIncrement(16);
        jScrollPane14.getVerticalScrollBar().setUnitIncrement(16);
        System.out.println("current password: " + current_password);
        update_combobox("select * from agents where Realty_ID = " + current_id, agent_selector, "Agent_Name");
        update_combobox("select * from developers where Realty_ID = " + current_id, dev_selector, "Dev_Name");
        display_all();
        put_data();
        clock();
    }

    public void Set_Visibility(boolean t_f_1, boolean t_f_2, boolean t_f_3, boolean t_f_4, boolean t_f_5) {
        Home_Panel.setVisible(t_f_1);
        Sales_Panel.setVisible(t_f_2);
        Tools_Panel.setVisible(t_f_3);
        Account_Panel.setVisible(t_f_4);
        Data_Retrieval_Panel.setVisible(t_f_5);
    }

    private void update_combobox(String query, JComboBox box, String column) {
        try {
            box.removeAllItems();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = query;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                box.addItem(rs.getString(column));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        }
    }

    public void submit_agents() {
        String date = new SimpleDateFormat("MMMM-dd-YYYY HH:mm:ss").format(Calendar.getInstance().getTime()).toUpperCase();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "select Agent_ID from agents where Agent_ID = " + Agent_ID.getText();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Error: ID already exists!");
                clear_agents();
            }
        } catch (HeadlessException | SQLException e) {
        }
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "insert ignore into agents("
                    + "Agent_ID, "
                    + "Agent_Name, "
                    + "Agent_Rank, "
                    + "Agent_Address, "
                    + "Agent_Age, "
                    + "Agent_Location, "
                    + "Agent_Gender, "
                    + "Agent_Email, "
                    + "Agent_Contact, "
                    + "Realty_ID, "
                    + "Is_Archived, "
                    + "Date_Created, "
                    + "Date_Modified) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(current_id + Agent_ID.getText()));
            pst.setString(2, Agent_Name.getText());
            pst.setString(3, Agent_Rank.getSelectedItem().toString());
            pst.setString(4, Agent_Address.getText());
            pst.setString(5, Agent_Age.getText());
            pst.setString(6, Agent_Location.getText());
            pst.setString(7, Agent_Gender.getText());
            pst.setString(8, Agent_Email.getText());
            pst.setString(9, Agent_Contact.getText());
            pst.setString(10, (String) current_id);
            pst.setString(11, "No");
            pst.setString(12, date);
            pst.setString(13, date);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "This function is functional!!!!");
        } catch (HeadlessException | SQLException e) {
        }
    }

    public void clear_agents() {
        Agent_ID.setText(null);
        Agent_Name.setText(null);
        Agent_Rank.setSelectedIndex(0);
        Agent_Address.setText(null);
        Agent_Age.setText(null);
        Agent_Location.setText(null);
        Agent_Gender.setText(null);
        Agent_Email.setText(null);
        Agent_Contact.setText(null);
    }

    public void submit_developers() {
        int ID = Integer.parseInt(current_id + Developer_ID.getText());
        String Name = Developer_Name.getText();
        String Location = Developer_Location.getText();
        String date = new SimpleDateFormat("MMMM-dd-YYYY HH:mm:ss").format(Calendar.getInstance().getTime()).toUpperCase();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String check = Developer_ID.getText();
            String sql = "select Dev_Branch_ID from developers where Dev_Branch_ID = " + check;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Error: ID already exists!");
                clear_agents();
            }
        } catch (HeadlessException | SQLException e) {
        }
        try {

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "insert ignore into developers(Dev_Branch_ID, Dev_Name, Dev_Location, Realty_ID, Is_Archived, Date_Created, Date_Modified) values (?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, ID);
            pst.setString(2, Name);
            pst.setString(3, Location);
            pst.setInt(4, Integer.parseInt(current_id));
            pst.setString(5, "No");
            pst.setString(6, date);
            pst.setString(7, date);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "This function is functional!!!!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        }
    }

    public void update_developers() {
        String date = new SimpleDateFormat("MMMM-dd-YYYY HH:mm:ss").format(Calendar.getInstance().getTime()).toUpperCase();
        String Name = Developer_Name.getText();
        String Location = Developer_Location.getText();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "update developers set Dev_Name = ?, Dev_Location = ?, Date_Modified = ? where Dev_Branch_ID = " + Developer_ID.getText();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Name);
            pst.setString(2, Location);
            pst.setString(3, date);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "This function is functional!!!!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        }
    }

    public void clear_developers() {
        Developer_ID.setText(null);
        Developer_Name.setText(null);
        Developer_Location.setText(null);
    }

    public void submit_project() throws SQLException, IOException {
        insert_image = new image_func();
        proj_status = "Available";
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String check = Insert_Project_ID.getText();
            String sql = "select Proj_ID from projects where Proj_ID = " + check;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Error: ID already exists!");
                clear_projects();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        try {
            insert_image.insert_values_for_projects(database, Integer.parseInt(current_id + Insert_Project_ID.getText()), Insert_Project_Name.getText(), Integer.parseInt(Insert_Project_Price.getText()), path, Integer.parseInt(selector_ID_1), proj_status);
            JOptionPane.showMessageDialog(this, "This function is functional!!!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void update_project() throws SQLException, IOException {
        int index = Proj_Dev_Tbl.getSelectedRow();
        TableModel model = Proj_Dev_Tbl.getModel();
        insert_image = new image_func();
        try {
            insert_image.update_values_for_projects(database, Insert_Project_Name.getText(), Integer.parseInt(Insert_Project_Price.getText()), path, Insert_Project_ID.getText());
            display_all();
            JOptionPane.showMessageDialog(this, "This function is functional!!!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void finalize_project(String status) throws SQLException, IOException {
        int index = Proj_Dev_Tbl.getSelectedRow();
        TableModel model = Proj_Dev_Tbl.getModel();
        insert_image = new image_func();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "select Proj_Status from projects where Proj_ID =" + Insert_Project_ID.getText() + " and (Proj_Status = 'Available' or Proj_Status = 'Cancelled' or Proj_Status = 'Finished')";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Error: Project needs to be currently building!");
                clear_agents();
            }
        } catch (HeadlessException | SQLException e) {
        }

        try {
            insert_image.status_projects(database, status, Insert_Project_ID.getText());
            display_all();
            JOptionPane.showMessageDialog(this, "This function is functional!!!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void update_agents() {
        try {
            String date = new SimpleDateFormat("MMMM-dd-YYYY HH:mm:ss").format(Calendar.getInstance().getTime()).toUpperCase();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "update agents set "
                    + "Agent_Name = ?, "
                    + "Agent_Rank = ?, "
                    + "Agent_Address = ?, "
                    + "Agent_Age = ?, "
                    + "Agent_Location = ?, "
                    + "Agent_Gender = ?, "
                    + "Agent_Email = ?, "
                    + "Agent_Contact = ?, "
                    + "Date_Modified = ? "
                    + "where Agent_ID = " + Agent_ID.getText();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Agent_Name.getText());
            pst.setString(2, Agent_Rank.getSelectedItem().toString());
            pst.setString(3, Agent_Address.getText());
            pst.setString(4, Agent_Age.getText());
            pst.setString(5, Agent_Location.getText());
            pst.setString(6, Agent_Gender.getText());
            pst.setString(7, Agent_Email.getText());
            pst.setString(8, Agent_Contact.getText());
            pst.setString(9, date);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "This function is functional!!!!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        }
    }

    public void update_realty() {
        try {
            String date = new SimpleDateFormat("MMMM-dd-YYYY HH:mm:ss").format(Calendar.getInstance().getTime()).toUpperCase();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "update realty set Realty_Name = ?, Realty_Location = ?, Realty_Password = ?, Date_Modified = ? where Realty_ID = " + current_id;
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Update_UserName.getText());
            pst.setString(2, Update_Location.getText());
            pst.setString(3, Update_Password.getText());
            pst.setString(4, date);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "This function is functional!!!!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        }
    }

    public void archive(String table, String column, String yesorno, JTextField field) throws SQLException, IOException {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, "root", "");
            String sql = "update " + table + " set Is_Archived = ? where " + column + " = " + field.getText();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, yesorno);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "This function is functional!!!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void archive_realty() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, "root", "");
            String sql = "update realty set Is_Archived = ? where Realty_ID = " + current_id;
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "Yes");
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "This function is functional!!!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void search_dev() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "select * from developers where Dev_Branch_ID=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Search_Dev.getText());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                Developer_ID.setText(rs.getString("Dev_Branch_ID"));
                Developer_Name.setText(rs.getString("Dev_Name"));
                Developer_Location.setText(rs.getString("Dev_Location"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void search_proj() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "select * from projects join developers on projects.Dev_Branch_ID = developers.Dev_Branch_ID where Proj_ID=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Search_Proj.getText());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                Insert_Project_ID.setText(rs.getString("Proj_ID"));
                Insert_Project_Name.setText(rs.getString("Proj_Name"));
                Insert_Project_Price.setText(rs.getString("Proj_Price"));
                dev_selector.setSelectedItem(rs.getString("developers.Dev_Name"));

            }
            display_image("select * from developers join projects on developers.Dev_Branch_ID = projects.Dev_Branch_ID where Proj_ID = " + Search_Proj.getText(), house_4);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void search_agents() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "select * from agents where Agent_ID=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Search_Dev.getText());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                Agent_ID.setText(rs.getString("Agent_ID"));
                Agent_Name.setText(rs.getString("Agent_Name"));
                Agent_Rank.setSelectedItem(rs.getString("Agent_Rank"));
                Agent_Address.setText(rs.getString("Agent_Address"));
                Agent_Age.setText(rs.getString("Agent_Age"));
                Agent_Location.setText(rs.getString("Agent_Location"));
                Agent_Gender.setText(rs.getString("Agent_Gender"));
                Agent_Email.setText(rs.getString("Agent_Email"));
                Agent_Contact.setText(rs.getString("Agent_Contact"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void clear_projects() {
        Insert_Project_ID.setText(null);
        Insert_Project_Name.setText(null);
        Insert_Project_Price.setText(null);
        path = null;
        house_4.setIcon(null);
    }

    public void submit_client() {
        String date = new SimpleDateFormat("MMMM-dd-YYYY HH:mm:ss").format(Calendar.getInstance().getTime()).toUpperCase();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "select Client_Name from client where Client_Name = '" + Client_Name.getText() + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Error: Client already exists!");
                clear_agents();
            }
        } catch (HeadlessException | SQLException e) {
        }
        try {

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "insert ignore into client(Client_Name, Client_Address, Client_Age, Client_Gender, Client_EmailAddress, Client_ContactNo, Agent_ID, Is_Archived, Date_Created, Date_Modified) values (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Client_Name.getText());
            pst.setString(2, Client_Address.getText());
            pst.setString(3, Client_Age.getText());
            pst.setString(4, Client_Gender.getText());
            pst.setString(5, Client_Email.getText());
            pst.setString(6, Client_Contact.getText());
            pst.setString(7, selector_ID_2);
            pst.setString(8, "No");
            pst.setString(9, date);
            pst.setString(10, date);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "This function is functional!!!!");
        } catch (HeadlessException | SQLException e) {
        }
    }

    public void update_availability() {
        try {
            int index = Table_for_Houses.getSelectedRow();
            TableModel model = Table_for_Houses.getModel();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "update projects set Proj_Status = ?, Client_ID = ? where Proj_ID = " + (model.getValueAt(index, 0).toString());
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "Ongoing");
            pst.setInt(2, Integer.parseInt(selector_ID_3));
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void clear_sales() {
        house.setIcon(null);
        Selected_Name.setText("");
        Selected_Price.setText("");
        Selected_Location.setText("");
        Selected_Developer.setText("");
        agent_selector.setSelectedItem(null);
        JLabel_ID.setText("");
        JLabel_Rank.setText("");
        JLabel_Location.setText("");
        JLabel_Contact.setText("");
        JLabel_Email.setText("");
        Select_incentives.setSelectedItem(null);
        Pay_Method.setSelectedItem(null);
        Client_Name.setText("");
        Client_Address.setText("");
        Client_Age.setText("");
        Client_Gender.setText("");
        Client_Email.setText("");
        Client_Contact.setText("");
    }

    public void submit_Sale() {
        String date = new SimpleDateFormat("MMMM-dd-YYYY HH:mm:ss").format(Calendar.getInstance().getTime()).toUpperCase();
        int index = Table_for_Houses.getSelectedRow();
        TableModel model = Table_for_Houses.getModel();
        String RealtyID = current_id;
        String AgentID = selector_ID_2;
        String ClientID = selector_ID_3;
        String devID = selector_ID_4;
        String ProjID = (String) model.getValueAt(index, 0);
        String pay_method = Pay_Method.getSelectedItem().toString();
        String incentives = Select_incentives.getSelectedItem().toString();
        String total = Selected_Price.getText();
        double calc = Double.parseDouble((String) model.getValueAt(index, 2));
        double realty_com_calc;
        double agent_com_calc;
        String realty_com = null;
        String agent_com = null;
        double equity_calc = calc * 0.3;
        String equity = String.valueOf(equity_calc);
        if (JLabel_Rank.getText().equals("Sales Director")) {
            realty_com_calc = calc * 0.045;
            realty_com = String.valueOf(realty_com_calc);
            agent_com_calc = calc * 0.03;
            agent_com = String.valueOf(agent_com_calc);
        } else if (JLabel_Rank.getText().equals("Sales Manager")) {
            realty_com_calc = calc * 0.04;
            realty_com = String.valueOf(realty_com_calc);
            agent_com_calc = calc * 0.025;
            agent_com = String.valueOf(agent_com_calc);
        } else if (JLabel_Rank.getText().equals("Property Executive")) {
            realty_com_calc = calc * 0.03;
            realty_com = String.valueOf(realty_com_calc);
            agent_com_calc = calc * 0.015;
            agent_com = String.valueOf(agent_com_calc);
        }
//        int incentives = 
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "insert into sales_log(Realty_ID, Agent_ID, Client_ID, Dev_Branch_ID, Proj_ID, Pay_Method, Incentives, Total_Sales_Price, Realty_commission, Agent_commission, Equity, Is_Archived, Date_Created) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, RealtyID);
            pst.setString(2, AgentID);
            pst.setString(3, ClientID);
            pst.setString(4, devID);
            pst.setString(5, ProjID);
            pst.setString(6, pay_method);
            pst.setString(7, incentives);
            pst.setString(8, total);
            pst.setString(9, realty_com);
            pst.setString(10, agent_com);
            pst.setString(11, equity);
            pst.setString(12, "No");
            pst.setString(13, date);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "This function is functional!!!!");
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }

    public void log_out() {
        dispose();
        Log_In signedOut = new Log_In(database);
        signedOut.show();
    }

    //display image from database
    public void display_image(String query, JLabel image_label) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = query;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                byte[] img = rs.getBytes("Proj_Image");
                ImageIcon icon = new ImageIcon(img);
                Image im = icon.getImage();
                Image imgScale = im.getScaledInstance(image_label.getWidth(), image_label.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(imgScale);
                image_label.setIcon(scaledIcon);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        }
    }

    //automate display agent data
    public void display_agent_data() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            val1 = (String) agent_selector.getSelectedItem();
            String sql = "select * from agents where Agent_Name= '" + val1 + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                JLabel_Rank.setText(String.valueOf(rs.getString("Agent_Rank")));
                JLabel_Location.setText(String.valueOf(rs.getString("Agent_Location")));
                JLabel_Email.setText(String.valueOf(rs.getString("Agent_Email")));
                JLabel_Contact.setText(String.valueOf(rs.getString("Agent_Contact")));
                JLabel_ID.setText(String.valueOf(rs.getString("Agent_ID")));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //for uploading image file
    public void upload_image() {
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        //filter the files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
        file.addChoosableFileFilter(filter);
        int result = file.showSaveDialog(null);
        //if the user click on save in Jfilechooser
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = file.getSelectedFile();
            path = selectedFile.getAbsolutePath();
            house_4.setIcon(ResizeImage(path, house_4));
        } //if the user click on save in Jfilechooser
        else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("No File Select");
        }
    }

    public ImageIcon ResizeImage(String ImagePath, JLabel label) {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }

    //wtf is this pls make parameter for second selector
    public void selector_1(String query, String column) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = query;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                selector_ID_1 = String.valueOf(rs.getString(column));
            }
            System.out.println(column + ": " + selector_ID_1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void selector_1_1(String query, String column) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = query;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                selector_ID_4 = String.valueOf(rs.getString(column));
            }
            System.out.println(column + " on table click: " + selector_ID_4);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //wtf is this pls make parameter for second selector
    public void selector_2(String query, String column) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = query;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                selector_ID_2 = String.valueOf(rs.getString(column));
            }
            System.out.println(column + ": " + selector_ID_2);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void selector_client(String query, String column) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = query;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                selector_ID_3 = String.valueOf(rs.getString(column));
            }
            System.out.println(column + ": " + selector_ID_3);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void DisplayAvailable(JTable table) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "select * from developers join projects on developers.Dev_Branch_ID = projects.Dev_Branch_ID where developers.Realty_ID = " + current_id + " and Proj_Status = 'Available' and projects.Is_Archived = 'No' and developers.Is_Archived = 'No'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String value_1 = String.valueOf(rs.getString("Proj_ID"));
                String value_2 = String.valueOf(rs.getString("Proj_Name"));
                String value_3 = String.valueOf(rs.getString("Proj_Price"));
                String value_4 = String.valueOf(rs.getString("Dev_Location"));
                String value_5 = String.valueOf(rs.getString("Dev_Name"));

                String data[] = {value_1, value_2, value_3, value_4, value_5};
                model.addRow(data);

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void DisplayAvailable_Proj_Dev_Tbl(String yesorno, String extra_query, JTable table) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "select * from developers join projects on developers.Dev_Branch_ID = projects.Dev_Branch_ID where developers.Realty_ID = "
                    + current_id + " and projects.Is_Archived='" + yesorno + "'" + extra_query;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String value_1 = String.valueOf(rs.getString("Proj_ID"));
                String value_2 = String.valueOf(rs.getString("Proj_Name"));
                String value_3 = String.valueOf(rs.getString("Proj_Price"));
                String value_4 = String.valueOf(rs.getString("Dev_Location"));
                String value_5 = String.valueOf(rs.getString("Dev_Name"));
//                String value_6 = String.valueOf(rs.getString("developers.Dev_Branch_ID"));
                String value_7 = String.valueOf(rs.getString("Proj_Status"));

                String data[] = {value_1, value_2, value_3, value_4, value_5, value_7};
                model.addRow(data);

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void DisplayAvailable_Agent_Real_Tbl(String yesorno, JTable table) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "select * from agents join realty on agents.Realty_ID = realty.Realty_ID where agents.Realty_ID = " + current_id + " and agents.Is_Archived='" + yesorno + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String value_1 = String.valueOf(rs.getString("Agent_ID"));
                String value_2 = String.valueOf(rs.getString("Agent_Name"));
                String value_3 = String.valueOf(rs.getString("Agent_Rank"));
                String value_4 = String.valueOf(rs.getString("Agent_Address"));
                String value_5 = String.valueOf(rs.getString("Agent_Age"));
                String value_6 = String.valueOf(rs.getString("Agent_Location"));
                String value_7 = String.valueOf(rs.getString("Agent_Gender"));
                String value_8 = String.valueOf(rs.getString("Agent_Email"));
                String value_9 = String.valueOf(rs.getString("Agent_Contact"));
                String value_10 = String.valueOf(rs.getString("Realty_Name"));
                String value_11 = String.valueOf(rs.getString("agents.Realty_ID"));

                String data[] = {value_1, value_2, value_3, value_4, value_5, value_6, value_7, value_8, value_9};
                model.addRow(data);

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void DisplayAvailable_Client_Tbl(String yesorno, JTable table) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "select * from client join agents on client.Agent_ID = agents.Agent_ID where agents.Realty_ID = " + current_id + " and client.Is_Archived='" + yesorno + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while (rs.next()) {

                String value_1 = String.valueOf(rs.getString("Client_ID"));
                String value_2 = String.valueOf(rs.getString("Client_Name"));
                String value_3 = String.valueOf(rs.getString("Client_Address"));
                String value_4 = String.valueOf(rs.getString("Client_Age"));
                String value_5 = String.valueOf(rs.getString("Client_Gender"));
                String value_6 = String.valueOf(rs.getString("Client_EmailAddress"));
                String value_7 = String.valueOf(rs.getString("Client_ContactNo"));
                String value_8 = String.valueOf(rs.getString("Agent_Name"));

                String data[] = {value_1, value_2, value_3, value_4, value_5, value_6, value_7, value_8};
                model.addRow(data);

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void DisplayAvailable_Dev_Real_Tbl(String yesorno, JTable table) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
//            String sql = "select * from developers join realty on developers.Realty_ID = realty.Realty_ID where developers.Realty_ID = " + current_id + " and developers.Is_Archived='" + yesorno + "'";
//            String sql = "select developers.Dev_Branch_ID, Dev_Name, Dev_Location, count(sales_log.Dev_Branch_ID) as count_projects, developers.Realty_ID from developers join sales_log on developers.Dev_Branch_ID = sales_log.Dev_Branch_ID where developers.Realty_ID = " + current_id + " and developers.Is_Archived='" + yesorno + "'";
            String sql = "select developers.Dev_Branch_ID, Dev_Name, Dev_Location, count(sales_log.Dev_Branch_ID) as count_projects, count(projects.Dev_Branch_ID) as available_projects, developers.Realty_ID from developers join sales_log on developers.Dev_Branch_ID = sales_log.Dev_Branch_ID join projects on developers.Dev_Branch_ID = projects.Dev_Branch_ID where developers.Realty_ID = " + current_id + " and developers.Is_Archived = '" + yesorno + "' and projects.Proj_Status = 'Available'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String value_1 = String.valueOf(rs.getString("developers.Dev_Branch_ID"));
                String value_2 = String.valueOf(rs.getString("developers.Dev_Name"));
                String value_3 = String.valueOf(rs.getString("developers.Dev_Location"));
                String value_4 = String.valueOf(rs.getString("count_projects"));
                String value_5 = String.valueOf(rs.getString("available_projects"));

                String data[] = {value_1, value_2, value_3,
                    value_4,
                    value_5
                };
                model.addRow(data);

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void Display_Agent_Earnings() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "select agents.Agent_ID, agents.Agent_Name, cast(sum(Agent_Commission) as decimal (18,2)) as Agent_Earnings, agents.Is_Archived "
                    + "from sales_log join agents on sales_log.Agent_ID = agents.Agent_ID where agents.Is_Archived = 'No' and sales_log.Realty_ID = " + current_id;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) Agent_Earnings.getModel();
            model.setRowCount(0);
            while (rs.next()) {

                String value_1 = String.valueOf(rs.getString("agents.Agent_ID"));
                String value_2 = String.valueOf(rs.getString("agents.Agent_Name"));
                String value_3 = String.valueOf(rs.getString("Agent_Earnings"));

                String data[] = {value_1, value_2, value_3};
                model.addRow(data);

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void Display_Sold_Items() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "select Sales_ID, Agent_Name, Client_Name, Dev_Name, Proj_Name, Pay_Method, Incentives, cast(Total_Sales_Price as decimal (18,2)) as total, Realty_Commission, Agent_Commission, Equity from sales_log "
                    + "join agents on sales_log.Agent_ID = agents.Agent_ID "
                    + "join client on sales_log.Client_ID = client.Client_ID "
                    + "join developers on sales_log.Dev_Branch_ID = developers.Dev_Branch_ID "
                    + "join projects on sales_log.Proj_ID = projects.Proj_ID "
                    + "where sales_log.Realty_ID = " + current_id + " and not sales_log.Is_Archived = 'Yes'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) Show_Sold_Items.getModel();
            model.setRowCount(0);
            while (rs.next()) {

                String value_1 = String.valueOf(rs.getString("Sales_ID"));
                String value_2 = String.valueOf(rs.getString("Agent_Name"));
                String value_3 = String.valueOf(rs.getString("Client_Name"));
                String value_4 = String.valueOf(rs.getString("Dev_Name"));
                String value_5 = String.valueOf(rs.getString("Proj_Name"));
                String value_6 = String.valueOf(rs.getString("Pay_Method"));
                String value_7 = String.valueOf(rs.getString("Incentives"));
                String value_8 = String.valueOf(rs.getString("total"));
                String value_9 = String.valueOf(rs.getString("Realty_Commission"));
                String value_10 = String.valueOf(rs.getString("Agent_Commission"));
                String value_11 = String.valueOf(rs.getString("Equity"));

                String data[] = {value_1, value_2, value_3, value_4, value_5, value_6, value_7, value_8, value_9, value_10, value_11};
                model.addRow(data);

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void display_all() {
        DisplayAvailable(Table_for_Houses);
        DisplayAvailable_Proj_Dev_Tbl("No", "and developers.Is_Archived = 'No'", Proj_Dev_Tbl);
        DisplayAvailable_Proj_Dev_Tbl("Yes", "and developers.Is_Archived = 'No'", Archived_Proj_Dev_Tbl);
        DisplayAvailable_Agent_Real_Tbl("No", Agent_Real_Tbl);
        DisplayAvailable_Agent_Real_Tbl("Yes", Archived_Agent_Real_Tbl);
        DisplayAvailable_Dev_Real_Tbl("No", Dev_Real_Tbl);
        DisplayAvailable_Dev_Real_Tbl("Yes", Archived_Dev_Real_Tbl);
        DisplayAvailable_Client_Tbl("No", Client_Agent_Tbl);
        DisplayAvailable_Client_Tbl("Yes", Archived_Client_Agent_Tbl);
        Display_Sold_Items();
        Display_Agent_Earnings();
    }

    public void scaleImage(String location, JLabel label) {
        ImageIcon icon = new ImageIcon(location);
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        label.setIcon(scaledIcon);
    }

    public void clock() {
        try {
            Timer timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    Calendar cal1 = new GregorianCalendar();
                    Calendar cal2 = cal1.getInstance();

                    String datehome = new SimpleDateFormat("EEEE!").format(Calendar.getInstance().getTime());
                    String date = new SimpleDateFormat("MMMM dd, YYYY - EEEE").format(Calendar.getInstance().getTime()).toUpperCase();
                    String time = new SimpleDateFormat("hh:mm:ss \t").format(Calendar.getInstance().getTime());

                    String am_pm;
                    if (cal1.get(Calendar.AM_PM) == 0) {
                        am_pm = " AM";
                    } else {
                        am_pm = " PM";
                    }

                    Date.setText(date);

                    Time.setText(time
                            + " " + am_pm);
//                    datehome1.setText(datehome);
                    Date dt = new Date();
                    int hours = dt.getHours();
                    int min = dt.getMinutes();

                    if (hours == 1 || hours < 6) {
                        datehome1.setText("Evening");
                        datehome2.setText("Evening");
                    } else if (hours == 6 || hours < 12) {
                        datehome1.setText("Morning");
                        datehome2.setText("Morning");
                    } else if (hours == 12 || hours < 13) {
                        datehome1.setText("Noon");
                        datehome2.setText("Noon");
                    } else if (hours == 13 || hours < 18) {
                        datehome1.setText("Afternoon");
                        datehome2.setText("Afternoon");
                    } else if (hours == 18 || hours < 24) {
                        datehome1.setText("Evening");
                        datehome2.setText("Evening");
                    }
                }
            });
            timer.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void count_data(JTable table, JLabel num_of_rows) {
        DefaultTableModel t = (DefaultTableModel) table.getModel();
        num_of_rows.setText(String.valueOf(t.getRowCount()));
        System.out.println(num_of_rows);
    }

    public void count_data_total(JTable table, JTable table2, JLabel num_of_rows) {
        DefaultTableModel t1 = (DefaultTableModel) table.getModel();
        DefaultTableModel t2 = (DefaultTableModel) table2.getModel();
        String plus = String.valueOf(Integer.valueOf(t1.getRowCount()) + Integer.valueOf(t2.getRowCount()));
        num_of_rows.setText(plus);
        System.out.println(num_of_rows);
    }

    public void count_money(String sum, JLabel num_of_rows) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", "");
            String sql = "select " + sum + " as sum from sales_log where Realty_ID = " + current_id;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                num_of_rows.setText(rs.getString("sum"));
            }
        } catch (Exception e) {

        }

    }

    public void put_data() {
        count_data(Table_for_Houses, count_proj);
        count_data(Agent_Real_Tbl, count_agents);
        count_data(Dev_Real_Tbl, count_devs);
        count_data(Show_Sold_Items, count_sales);
        count_data(Show_Sold_Items, Stats_1);
        count_data(Table_for_Houses, Stats_2);
        count_data(Dev_Real_Tbl, Stats_3);
        count_data(Agent_Real_Tbl, Stats_4);
        count_data_total(Client_Agent_Tbl, Archived_Client_Agent_Tbl, Stats_5);
        count_data_total(Proj_Dev_Tbl, Archived_Proj_Dev_Tbl, Stats_6);
        count_data_total(Dev_Real_Tbl, Archived_Dev_Real_Tbl, Stats_7);
        count_data_total(Agent_Real_Tbl, Archived_Agent_Real_Tbl, Stats_8);
        count_money("cast(sum(Total_Sales_Price) as decimal (18,2))", Stats_9);
        count_money("cast(sum(Realty_Commission)-sum(Agent_Commission) as decimal (18,2))", Stats_10);
        count_money("cast(sum(Realty_Commission)-sum(Agent_Commission) as decimal (18,2))", Stats_17);
        count_money("cast(sum(Agent_Commission) as decimal (18,2))", Stats_11);
        count_money("cast(sum(Total_Sales_Price)-sum(Realty_Commission) as decimal (18,2))", Stats_12);
        System.out.println("Current id: " + current_id);
        System.out.println("Current user: " + current_user);
        System.out.println("Current password: " + current_password);
        jLabelTest.setText("Welcome " + current_user + "!!!");
        jLabelTest1.setText(current_user + " has currently:");
        jLabelTest2.setText(current_user + "'s Account");
        footer_name2.setText("Realty Name: " + current_user);
        Acc_Realty_Name.setText(current_user);
        Acc_Location.setText(current_loc);
        count_comment();
        singular_plural();
    }

    public void singular_plural() {
        if (Integer.parseInt(count_proj.getText()) == 1) {
            Proj_singularplural.setText("Project");
        } else {
            Proj_singularplural.setText("Projects");
        }
        if (Integer.parseInt(count_agents.getText()) == 1) {
            Agents_singularplural.setText("Agent");
        } else {
            Proj_singularplural.setText("Agents");
        }
        if (Integer.parseInt(count_devs.getText()) == 1) {
            Devs_singularplural.setText("Developer");
        } else {
            Proj_singularplural.setText("Developers");
        }
        if (Integer.parseInt(count_sales.getText()) == 1) {
            Sales_singularplural.setText("Sale");
        } else {
            Proj_singularplural.setText("Sales");
        }
    }

    public void count_comment() {
        if (Integer.parseInt(count_proj.getText()) == 0
                && Integer.parseInt(count_agents.getText()) == 0
                && Integer.parseInt(count_devs.getText()) == 0
                && Integer.parseInt(count_sales.getText()) == 0) {
            Count_Comment.setText("Are you new here? Go to Data Tools to start creating!");
        } else if (Integer.parseInt(count_sales.getText()) == 1) {
            Count_Comment.setText("Congratulations " + current_user + ", you sold your first project!!");
        } else if (Integer.parseInt(count_proj.getText()) <= 10
                && Integer.parseInt(count_agents.getText()) <= 10
                && Integer.parseInt(count_devs.getText()) <= 10
                && Integer.parseInt(count_sales.getText()) <= 10) {
            Count_Comment.setText("You are getting started, " + current_user + "!");
        } else if (Integer.parseInt(count_proj.getText()) <= 20
                && Integer.parseInt(count_agents.getText()) <= 20
                && Integer.parseInt(count_devs.getText()) <= 20
                && Integer.parseInt(count_sales.getText()) <= 20) {
            Count_Comment.setText("Keep creating and selling!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel14 = new javax.swing.JPanel();
        jLabel97 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        Client_Name1 = new javax.swing.JTextField();
        Client_Address1 = new javax.swing.JTextField();
        Client_Age1 = new javax.swing.JTextField();
        Client_Gender1 = new javax.swing.JTextField();
        Client_Email1 = new javax.swing.JTextField();
        Client_Contact1 = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        Insert_Project_ID5 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        kGradientPanel6 = new keeptoo.KGradientPanel();
        DevButPar10 = new javax.swing.JPanel();
        Settings4 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        DevButPar12 = new javax.swing.JPanel();
        LogOut4 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        MAIN = new javax.swing.JPanel();
        SidePanel = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        footer_name2 = new javax.swing.JLabel();
        footer_id2 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        Create = new javax.swing.JLabel();
        Tools = new javax.swing.JLabel();
        Account = new javax.swing.JLabel();
        LogOut = new javax.swing.JLabel();
        Home = new javax.swing.JLabel();
        Layers = new javax.swing.JLayeredPane();
        Tools_Panel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Changelogs4 = new javax.swing.JPanel();
        Time = new javax.swing.JLabel();
        ToDate3 = new javax.swing.JLabel();
        Date = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        SidePaneDev = new keeptoo.KGradientPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Dev_Real_Tbl = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        Developer_ID = new javax.swing.JTextField();
        Developer_Name = new javax.swing.JTextField();
        Developer_Location = new javax.swing.JTextField();
        Search_Dev = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        kGradientPanel3 = new keeptoo.KGradientPanel();
        DevButPar2 = new javax.swing.JPanel();
        Settings1 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        DevButPar3 = new javax.swing.JPanel();
        Account1 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        DevButPar1 = new javax.swing.JPanel();
        LogOut1 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        SidePaneDev1 = new keeptoo.KGradientPanel();
        kGradientPanel4 = new keeptoo.KGradientPanel();
        DevButPar4 = new javax.swing.JPanel();
        Settings2 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        DevButPar5 = new javax.swing.JPanel();
        Account2 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        DevButPar6 = new javax.swing.JPanel();
        LogOut2 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        DevButPar11 = new javax.swing.JPanel();
        LogOut5 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        DevButPar15 = new javax.swing.JPanel();
        LogOut6 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        dev_selector = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        Insert_Project_Name = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Insert_Project_Price = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Insert_Project_ID = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        house_4 = new javax.swing.JLabel();
        Upload = new javax.swing.JButton();
        Search_Proj = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Proj_Dev_Tbl = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        SidePaneDev2 = new keeptoo.KGradientPanel();
        kGradientPanel5 = new keeptoo.KGradientPanel();
        DevButPar7 = new javax.swing.JPanel();
        Settings3 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        DevButPar8 = new javax.swing.JPanel();
        Account3 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        DevButPar9 = new javax.swing.JPanel();
        LogOut3 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        Agent_Contact = new javax.swing.JTextField();
        Agent_Rank = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        Agent_ID = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        Agent_Age = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Agent_Name = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Agent_Email = new javax.swing.JTextField();
        Agent_Location = new javax.swing.JTextField();
        Agent_Address = new javax.swing.JTextField();
        Agent_Gender = new javax.swing.JTextField();
        Search_Agents = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        Agent_Real_Tbl = new javax.swing.JTable();
        jScrollPane13 = new javax.swing.JScrollPane();
        Agent_Earnings = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jPanel13 = new javax.swing.JPanel();
        SidePaneDev3 = new keeptoo.KGradientPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        Client_Agent_Tbl = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        jLabel107 = new javax.swing.JLabel();
        SidePaneDev4 = new keeptoo.KGradientPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        Show_Sold_Items = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jLabel101 = new javax.swing.JLabel();
        TakeAdminRights5 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        Changelogs2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        FB = new javax.swing.JLabel();
        Twitter = new javax.swing.JLabel();
        Instagram = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        Account_Panel = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        kGradientPanel7 = new keeptoo.KGradientPanel();
        ToDate5 = new javax.swing.JLabel();
        datehome2 = new javax.swing.JLabel();
        jLabelTest2 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jPanel25 = new javax.swing.JPanel();
        jlabel = new javax.swing.JLabel();
        jlabel4 = new javax.swing.JLabel();
        Stats_4 = new javax.swing.JLabel();
        jlabel10 = new javax.swing.JLabel();
        jlabel12 = new javax.swing.JLabel();
        jlabel8 = new javax.swing.JLabel();
        Stats_7 = new javax.swing.JLabel();
        Acc_Location = new javax.swing.JLabel();
        jlabel11 = new javax.swing.JLabel();
        jlabel7 = new javax.swing.JLabel();
        Acc_Realty_Name = new javax.swing.JLabel();
        Stats_5 = new javax.swing.JLabel();
        Current_User_Date = new javax.swing.JLabel();
        Stats_9 = new javax.swing.JLabel();
        Stats_3 = new javax.swing.JLabel();
        jlabel2 = new javax.swing.JLabel();
        Stats_8 = new javax.swing.JLabel();
        jlabel14 = new javax.swing.JLabel();
        Stats_12 = new javax.swing.JLabel();
        Stats_1 = new javax.swing.JLabel();
        Stats_2 = new javax.swing.JLabel();
        Stats_11 = new javax.swing.JLabel();
        jlabel9 = new javax.swing.JLabel();
        jlabel1 = new javax.swing.JLabel();
        Stats_10 = new javax.swing.JLabel();
        jlabel5 = new javax.swing.JLabel();
        jlabel6 = new javax.swing.JLabel();
        jlabel3 = new javax.swing.JLabel();
        jlabel15 = new javax.swing.JLabel();
        Stats_6 = new javax.swing.JLabel();
        jlabel23 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jlabel13 = new javax.swing.JLabel();
        jlabel16 = new javax.swing.JLabel();
        jlabel17 = new javax.swing.JLabel();
        jlabel18 = new javax.swing.JLabel();
        Update_Location = new javax.swing.JTextField();
        Update_UserName = new javax.swing.JTextField();
        Update_Password = new javax.swing.JPasswordField();
        kGradientPanel9 = new keeptoo.KGradientPanel();
        jlabel19 = new javax.swing.JLabel();
        jlabel20 = new javax.swing.JLabel();
        jlabel21 = new javax.swing.JLabel();
        kGradientPanel10 = new keeptoo.KGradientPanel();
        jlabel22 = new javax.swing.JLabel();
        Update_Password1 = new javax.swing.JPasswordField();
        jlabel24 = new javax.swing.JLabel();
        Update_Password2 = new javax.swing.JPasswordField();
        jlabel25 = new javax.swing.JLabel();
        jlabel30 = new javax.swing.JLabel();
        Home_Panel = new javax.swing.JPanel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        ToDate4 = new javax.swing.JLabel();
        datehome1 = new javax.swing.JLabel();
        jLabelTest = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        count_proj = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        count_agents = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        Devs_singularplural = new javax.swing.JLabel();
        count_devs = new javax.swing.JLabel();
        jLabelTest1 = new javax.swing.JLabel();
        Proj_singularplural = new javax.swing.JLabel();
        count_sales = new javax.swing.JLabel();
        Sales_singularplural = new javax.swing.JLabel();
        Agents_singularplural = new javax.swing.JLabel();
        Count_Comment = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jlabel31 = new javax.swing.JLabel();
        Stats_17 = new javax.swing.JLabel();
        BG = new javax.swing.JLabel();
        Sales_Panel = new javax.swing.JPanel();
        kGradientPanel8 = new keeptoo.KGradientPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        house = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Selected_Name = new javax.swing.JLabel();
        Selected_Price = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        Selected_Location = new javax.swing.JLabel();
        Selected_Developer = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        agent_selector = new javax.swing.JComboBox<>();
        JLabel_Email = new javax.swing.JLabel();
        JLabel_ID = new javax.swing.JLabel();
        JLabel_Location = new javax.swing.JLabel();
        JLabel_Contact = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        Client_Name = new javax.swing.JTextField();
        Client_Address = new javax.swing.JTextField();
        Client_Age = new javax.swing.JTextField();
        Client_Gender = new javax.swing.JTextField();
        Client_Email = new javax.swing.JTextField();
        Client_Contact = new javax.swing.JTextField();
        Pay_Method = new javax.swing.JComboBox<>();
        Select_incentives = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        Sales_Scroll = new javax.swing.JScrollPane();
        Table_for_Houses = new javax.swing.JTable();
        JLabel_Rank = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        sale = new javax.swing.JPanel();
        Data_Retrieval_Panel = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel19 = new javax.swing.JPanel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        SidePaneDev5 = new keeptoo.KGradientPanel();
        kGradientPanel11 = new keeptoo.KGradientPanel();
        DevButPar13 = new javax.swing.JPanel();
        Account4 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        Retrieve_Proj_ID = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        Archived_Proj_Dev_Tbl = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        SidePaneDev9 = new keeptoo.KGradientPanel();
        kGradientPanel15 = new keeptoo.KGradientPanel();
        DevButPar14 = new javax.swing.JPanel();
        Account7 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        Retrieve_Dev_ID = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        Archived_Dev_Real_Tbl = new javax.swing.JTable();
        jPanel29 = new javax.swing.JPanel();
        jLayeredPane6 = new javax.swing.JLayeredPane();
        SidePaneDev10 = new keeptoo.KGradientPanel();
        kGradientPanel16 = new keeptoo.KGradientPanel();
        DevButPar23 = new javax.swing.JPanel();
        Account8 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        Retrieve_Agent_ID = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        Archived_Agent_Real_Tbl = new javax.swing.JTable();
        jPanel31 = new javax.swing.JPanel();
        jLayeredPane7 = new javax.swing.JLayeredPane();
        SidePaneDev11 = new keeptoo.KGradientPanel();
        kGradientPanel17 = new keeptoo.KGradientPanel();
        DevButPar24 = new javax.swing.JPanel();
        Account9 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        Retrieve_Client_ID = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        Archived_Client_Agent_Tbl = new javax.swing.JTable();
        jLabel138 = new javax.swing.JLabel();

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel97.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel97.setText("Update Client");
        jPanel14.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 140, 30));

        jLabel93.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel93.setText("Client Age");
        jLabel93.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel14.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 70, 20));

        jLabel102.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel102.setText("Client Name");
        jLabel102.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel14.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 80, 20));

        Client_Name1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Client_Name1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel14.add(Client_Name1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 270, 20));

        Client_Address1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Client_Address1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel14.add(Client_Address1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 270, 20));

        Client_Age1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Client_Age1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel14.add(Client_Age1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 270, 20));

        Client_Gender1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Client_Gender1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel14.add(Client_Gender1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 270, 20));

        Client_Email1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Client_Email1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel14.add(Client_Email1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 270, 20));

        Client_Contact1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Client_Contact1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel14.add(Client_Contact1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 270, 20));

        jLabel103.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel103.setText("Client Gender");
        jLabel103.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel14.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 80, 20));

        jLabel104.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel104.setText("Client Email");
        jLabel104.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel14.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 80, 20));

        jLabel105.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel105.setText("Client Contact");
        jLabel105.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel14.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 80, 20));

        jLabel106.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel106.setText("Client Address");
        jLabel106.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel14.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 80, 20));

        Insert_Project_ID5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Insert_Project_ID5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel14.add(Insert_Project_ID5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 190, 20));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel28.setText("Search");
        jPanel14.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, 40, 20));

        kGradientPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 0, new java.awt.Color(0, 0, 0)));
        kGradientPanel6.setkEndColor(new java.awt.Color(153, 153, 255));
        kGradientPanel6.setkStartColor(new java.awt.Color(204, 153, 255));
        kGradientPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DevButPar10.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar10.setOpaque(false);
        DevButPar10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Settings4.setBackground(new java.awt.Color(255, 255, 255));
        Settings4.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Settings4.setForeground(new java.awt.Color(255, 255, 255));
        Settings4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Settings4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Settings4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Settings4MouseClicked(evt);
            }
        });
        DevButPar10.add(Settings4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel92.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(255, 255, 255));
        jLabel92.setText("Update");
        DevButPar10.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel6.add(DevButPar10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 190, 30));

        DevButPar12.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar12.setOpaque(false);
        DevButPar12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LogOut4.setBackground(new java.awt.Color(255, 255, 255));
        LogOut4.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        LogOut4.setForeground(new java.awt.Color(255, 255, 255));
        LogOut4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogOut4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogOut4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogOut4MouseClicked(evt);
            }
        });
        DevButPar12.add(LogOut4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel94.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(255, 255, 255));
        jLabel94.setText("Archive");
        DevButPar12.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel6.add(DevButPar12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 190, 30));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RealManager");
        setMaximumSize(new java.awt.Dimension(1035, 640));
        setMinimumSize(new java.awt.Dimension(1035, 640));
        setName("Application"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1035, 640));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MAIN.setBackground(new java.awt.Color(255, 255, 255));
        MAIN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        MAIN.setMaximumSize(new java.awt.Dimension(1030, 600));
        MAIN.setMinimumSize(new java.awt.Dimension(1030, 600));
        MAIN.setPreferredSize(new java.awt.Dimension(1030, 600));
        MAIN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SidePanel.setBackground(new java.awt.Color(51, 26, 127));
        SidePanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SidePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("<html>Real Estate<br>System");
        jLabel21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        SidePanel.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 170, 80));

        kGradientPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(51, 51, 51)));
        kGradientPanel1.setkEndColor(new java.awt.Color(118, 86, 210));
        kGradientPanel1.setkStartColor(new java.awt.Color(155, 86, 210));
        kGradientPanel1.setkTransparentControls(false);
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        footer_name2.setFont(new java.awt.Font("Segoe UI Light", 0, 11)); // NOI18N
        footer_name2.setForeground(new java.awt.Color(255, 255, 255));
        kGradientPanel1.add(footer_name2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 180, 10));

        footer_id2.setFont(new java.awt.Font("Segoe UI Light", 0, 11)); // NOI18N
        footer_id2.setForeground(new java.awt.Color(255, 255, 255));
        kGradientPanel1.add(footer_id2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 560, 180, 10));

        jLabel38.setFont(new java.awt.Font("Segoe UI Light", 0, 11)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("TeamRusso © December 6, 2022");
        kGradientPanel1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, 180, 20));

        Create.setBackground(new java.awt.Color(255, 255, 255));
        Create.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Create.setForeground(new java.awt.Color(255, 255, 255));
        Create.setText("   Create Deal");
        Create.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(255, 255, 255)));
        Create.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Create.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CreateMouseClicked(evt);
            }
        });
        kGradientPanel1.add(Create, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 140, 30));

        Tools.setBackground(new java.awt.Color(255, 255, 255));
        Tools.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Tools.setForeground(new java.awt.Color(255, 255, 255));
        Tools.setText("   Data Tools");
        Tools.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(255, 255, 255)));
        Tools.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tools.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ToolsMouseClicked(evt);
            }
        });
        kGradientPanel1.add(Tools, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 140, 30));

        Account.setBackground(new java.awt.Color(255, 255, 255));
        Account.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Account.setForeground(new java.awt.Color(255, 255, 255));
        Account.setText("   Account");
        Account.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(255, 255, 255)));
        Account.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Account.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AccountMouseClicked(evt);
            }
        });
        kGradientPanel1.add(Account, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 140, 30));

        LogOut.setBackground(new java.awt.Color(255, 255, 255));
        LogOut.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        LogOut.setForeground(new java.awt.Color(255, 255, 255));
        LogOut.setText("   Log Out");
        LogOut.setToolTipText("");
        LogOut.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(255, 255, 255)));
        LogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogOutMouseClicked(evt);
            }
        });
        kGradientPanel1.add(LogOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 140, 30));

        Home.setBackground(new java.awt.Color(255, 255, 255));
        Home.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Home.setForeground(new java.awt.Color(255, 255, 255));
        Home.setText("   Home");
        Home.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(255, 255, 255)));
        Home.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HomeMouseClicked(evt);
            }
        });
        kGradientPanel1.add(Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 140, 30));

        SidePanel.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 600));

        MAIN.add(SidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 600));

        Layers.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Tools_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(245, 245, 245));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(600, 600));

        Changelogs4.setBackground(new java.awt.Color(255, 255, 255));
        Changelogs4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Time.setBackground(new java.awt.Color(255, 246, 242));
        Time.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        Time.setText("(time)");
        Changelogs4.add(Time, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 540, 430, 20));

        ToDate3.setBackground(new java.awt.Color(255, 246, 242));
        ToDate3.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        ToDate3.setText("Today is:");
        Changelogs4.add(ToDate3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 520, 60, 20));

        Date.setBackground(new java.awt.Color(255, 246, 242));
        Date.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        Date.setText("(date)");
        Changelogs4.add(Date, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 520, 360, 20));

        jLabel82.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel82.setText("<html>If data is lost unexpectedly or unintentionally <br>Needs to relog-in.");
        Changelogs4.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 520, 220, 40));

        jTabbedPane3.setBackground(new java.awt.Color(245, 245, 245));
        jTabbedPane3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SidePaneDev.setkEndColor(new java.awt.Color(153, 153, 255));
        SidePaneDev.setkStartColor(new java.awt.Color(204, 153, 255));
        SidePaneDev.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Dev_Real_Tbl.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        Dev_Real_Tbl.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Dev_Real_Tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dev_Branch_ID", "Name", "Location", "Projects Built", "Available Projects"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Dev_Real_Tbl.setSelectionBackground(new java.awt.Color(153, 153, 255));
        Dev_Real_Tbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Dev_Real_Tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Dev_Real_TblMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(Dev_Real_Tbl);

        SidePaneDev.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 770, 270));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 0, new java.awt.Color(102, 102, 102)));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel22.setText(" Developer Location");
        jPanel7.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 110, 20));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel24.setText(" Developer Name");
        jPanel7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 110, 20));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel23.setText(" Developer ID");
        jPanel7.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 110, 20));

        Developer_ID.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Developer_ID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        Developer_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Developer_IDKeyTyped(evt);
            }
        });
        jPanel7.add(Developer_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 270, 20));

        Developer_Name.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Developer_Name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel7.add(Developer_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 270, 20));

        Developer_Location.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Developer_Location.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel7.add(Developer_Location, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 270, 20));

        Search_Dev.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Search_Dev.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        Search_Dev.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Search_DevKeyPressed(evt);
            }
        });
        jPanel7.add(Search_Dev, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 270, 20));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel25.setText("Search");
        jPanel7.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 40, 20));

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
        jPanel7.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 410, 10));

        SidePaneDev.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 430, 160));

        kGradientPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(102, 102, 102)));
        kGradientPanel3.setkEndColor(new java.awt.Color(153, 153, 255));
        kGradientPanel3.setkStartColor(new java.awt.Color(204, 153, 255));
        kGradientPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DevButPar2.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar2.setOpaque(false);
        DevButPar2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Settings1.setBackground(new java.awt.Color(255, 255, 255));
        Settings1.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Settings1.setForeground(new java.awt.Color(255, 255, 255));
        Settings1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Settings1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Settings1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Settings1MouseClicked(evt);
            }
        });
        DevButPar2.add(Settings1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel83.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setText("Update");
        DevButPar2.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel3.add(DevButPar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 190, 30));

        DevButPar3.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar3.setOpaque(false);
        DevButPar3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Account1.setBackground(new java.awt.Color(255, 255, 255));
        Account1.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Account1.setForeground(new java.awt.Color(255, 255, 255));
        Account1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Account1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Account1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Account1MouseClicked(evt);
            }
        });
        DevButPar3.add(Account1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel84.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("Register");
        DevButPar3.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel3.add(DevButPar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 190, 30));

        DevButPar1.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar1.setOpaque(false);
        DevButPar1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LogOut1.setBackground(new java.awt.Color(255, 255, 255));
        LogOut1.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        LogOut1.setForeground(new java.awt.Color(255, 255, 255));
        LogOut1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogOut1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogOut1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogOut1MouseClicked(evt);
            }
        });
        DevButPar1.add(LogOut1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel81.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText("Archive");
        DevButPar1.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel3.add(DevButPar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 190, 30));

        SidePaneDev.add(kGradientPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 290, 200, 160));

        jLayeredPane1.add(SidePaneDev, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 800, 450));

        jPanel5.add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 440));

        jTabbedPane3.addTab("Developers", jPanel5);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SidePaneDev1.setkEndColor(new java.awt.Color(153, 153, 255));
        SidePaneDev1.setkStartColor(new java.awt.Color(204, 153, 255));
        SidePaneDev1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(153, 153, 153)));
        kGradientPanel4.setkEndColor(new java.awt.Color(153, 153, 255));
        kGradientPanel4.setkStartColor(new java.awt.Color(204, 153, 255));
        kGradientPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DevButPar4.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar4.setOpaque(false);
        DevButPar4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Settings2.setBackground(new java.awt.Color(255, 255, 255));
        Settings2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Settings2.setForeground(new java.awt.Color(255, 255, 255));
        Settings2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Settings2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Settings2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Settings2MouseClicked(evt);
            }
        });
        DevButPar4.add(Settings2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel89.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("Update");
        DevButPar4.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel4.add(DevButPar4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 190, 30));

        DevButPar5.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar5.setOpaque(false);
        DevButPar5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Account2.setBackground(new java.awt.Color(255, 255, 255));
        Account2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Account2.setForeground(new java.awt.Color(255, 255, 255));
        Account2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Account2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Account2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Account2MouseClicked(evt);
            }
        });
        DevButPar5.add(Account2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel90.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("Register");
        DevButPar5.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel4.add(DevButPar5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 190, 30));

        DevButPar6.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar6.setOpaque(false);
        DevButPar6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LogOut2.setBackground(new java.awt.Color(255, 255, 255));
        LogOut2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        LogOut2.setForeground(new java.awt.Color(255, 255, 255));
        LogOut2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogOut2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogOut2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogOut2MouseClicked(evt);
            }
        });
        DevButPar6.add(LogOut2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel91.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(255, 255, 255));
        jLabel91.setText("Archive");
        DevButPar6.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel4.add(DevButPar6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 190, 30));

        DevButPar11.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar11.setOpaque(false);
        DevButPar11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LogOut5.setBackground(new java.awt.Color(255, 255, 255));
        LogOut5.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        LogOut5.setForeground(new java.awt.Color(255, 255, 255));
        LogOut5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogOut5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogOut5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogOut5MouseClicked(evt);
            }
        });
        DevButPar11.add(LogOut5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel95.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(255, 255, 255));
        jLabel95.setText("Cancel Project");
        DevButPar11.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel4.add(DevButPar11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 190, 30));

        DevButPar15.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar15.setOpaque(false);
        DevButPar15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LogOut6.setBackground(new java.awt.Color(255, 255, 255));
        LogOut6.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        LogOut6.setForeground(new java.awt.Color(255, 255, 255));
        LogOut6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogOut6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogOut6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogOut6MouseClicked(evt);
            }
        });
        DevButPar15.add(LogOut6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel108.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(255, 255, 255));
        jLabel108.setText("Finish Project");
        DevButPar15.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel4.add(DevButPar15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 190, 30));

        jLabel6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        kGradientPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 64, 180, 10));

        SidePaneDev1.add(kGradientPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 260, 200, 190));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 0, new java.awt.Color(153, 153, 153)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dev_selector.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dev_selector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        dev_selector.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 2, 1, new java.awt.Color(153, 153, 255)));
        dev_selector.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dev_selectorItemStateChanged(evt);
            }
        });
        jPanel8.add(dev_selector, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 170, 20));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Name");
        jPanel8.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 40, 20));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel20.setText("ID");
        jPanel8.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 40, 20));

        Insert_Project_Name.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Insert_Project_Name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel8.add(Insert_Project_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 190, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Price");
        jPanel8.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 40, 20));

        Insert_Project_Price.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Insert_Project_Price.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel8.add(Insert_Project_Price, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 190, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel5.setText("Developer");
        jPanel8.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 60, 20));

        Insert_Project_ID.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Insert_Project_ID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel8.add(Insert_Project_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 190, -1));

        jPanel9.setBackground(new java.awt.Color(230, 230, 230));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        house_4.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel9.add(house_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 130, 130));

        Upload.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Upload.setText("Upload");
        Upload.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadActionPerformed(evt);
            }
        });
        jPanel9.add(Upload, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 60, 20));

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 150, 160));

        Search_Proj.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Search_Proj.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        Search_Proj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Search_ProjKeyPressed(evt);
            }
        });
        jPanel8.add(Search_Proj, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 190, 20));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel16.setText("Search");
        jPanel8.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 40, 20));

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
        jPanel8.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 250, 10));

        SidePaneDev1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 430, 190));

        Proj_Dev_Tbl.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        Proj_Dev_Tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proj_ID", "Proj_Name", "Proj_Price", "Location", "Dev_Name", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Proj_Dev_Tbl.setSelectionBackground(new java.awt.Color(153, 153, 255));
        Proj_Dev_Tbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Proj_Dev_Tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Proj_Dev_TblMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Proj_Dev_Tbl);

        SidePaneDev1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 770, 240));

        jLayeredPane2.add(SidePaneDev1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 790, 450));

        jPanel1.add(jLayeredPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 440));

        jTabbedPane3.addTab("Projects", jPanel1);

        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SidePaneDev2.setkEndColor(new java.awt.Color(153, 153, 255));
        SidePaneDev2.setkStartColor(new java.awt.Color(204, 153, 255));
        SidePaneDev2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(153, 153, 153)));
        kGradientPanel5.setkEndColor(new java.awt.Color(153, 153, 255));
        kGradientPanel5.setkStartColor(new java.awt.Color(204, 153, 255));
        kGradientPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DevButPar7.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar7.setOpaque(false);
        DevButPar7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Settings3.setBackground(new java.awt.Color(255, 255, 255));
        Settings3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Settings3.setForeground(new java.awt.Color(255, 255, 255));
        Settings3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Settings3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Settings3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Settings3MouseClicked(evt);
            }
        });
        DevButPar7.add(Settings3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel85.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setText("Update");
        DevButPar7.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel5.add(DevButPar7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 190, 30));

        DevButPar8.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar8.setOpaque(false);
        DevButPar8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Account3.setBackground(new java.awt.Color(255, 255, 255));
        Account3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Account3.setForeground(new java.awt.Color(255, 255, 255));
        Account3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Account3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Account3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Account3MouseClicked(evt);
            }
        });
        DevButPar8.add(Account3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel86.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(255, 255, 255));
        jLabel86.setText("Register");
        DevButPar8.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel5.add(DevButPar8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 190, 30));

        DevButPar9.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar9.setOpaque(false);
        DevButPar9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LogOut3.setBackground(new java.awt.Color(255, 255, 255));
        LogOut3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        LogOut3.setForeground(new java.awt.Color(255, 255, 255));
        LogOut3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogOut3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogOut3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogOut3MouseClicked(evt);
            }
        });
        DevButPar9.add(LogOut3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel87.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(255, 255, 255));
        jLabel87.setText("Archive");
        DevButPar9.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel5.add(DevButPar9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 190, 30));

        SidePaneDev2.add(kGradientPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 260, 200, 320));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 0, new java.awt.Color(153, 153, 153)));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Agent_Contact.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Agent_Contact.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        Agent_Contact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Agent_ContactKeyTyped(evt);
            }
        });
        jPanel10.add(Agent_Contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, 270, 20));

        Agent_Rank.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Agent_Rank.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sales Director", "Sales Manager", "Property Executive" }));
        Agent_Rank.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel10.add(Agent_Rank, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 270, 20));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel14.setText("Agent Contact No");
        jPanel10.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 120, 20));

        Agent_ID.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Agent_ID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        Agent_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Agent_IDKeyTyped(evt);
            }
        });
        jPanel10.add(Agent_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 270, 20));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel9.setText("Agent Name");
        jPanel10.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 120, 20));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel19.setText("Agent Email");
        jPanel10.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 120, 20));

        Agent_Age.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Agent_Age.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        Agent_Age.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Agent_AgeKeyTyped(evt);
            }
        });
        jPanel10.add(Agent_Age, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 270, 20));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("Agent Gender");
        jPanel10.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 120, 20));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Agent Rank");
        jPanel10.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 120, 20));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel11.setText("Agent Address");
        jPanel10.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 120, 20));

        Agent_Name.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Agent_Name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel10.add(Agent_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 270, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("Agent ID");
        jPanel10.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 120, 20));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel10.setText("Agent Age");
        jPanel10.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 120, 20));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel13.setText("Agent Location");
        jPanel10.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 120, 20));

        Agent_Email.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Agent_Email.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel10.add(Agent_Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 270, 20));

        Agent_Location.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Agent_Location.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel10.add(Agent_Location, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 270, 20));

        Agent_Address.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Agent_Address.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel10.add(Agent_Address, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 270, 20));

        Agent_Gender.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Agent_Gender.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel10.add(Agent_Gender, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 270, 20));

        Search_Agents.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Search_Agents.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        Search_Agents.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Search_AgentsKeyPressed(evt);
            }
        });
        jPanel10.add(Search_Agents, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 270, 20));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel26.setText("Search");
        jPanel10.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 40, 20));

        SidePaneDev2.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 430, 320));

        jTabbedPane2.setBackground(new java.awt.Color(245, 245, 245));
        jTabbedPane2.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N

        Agent_Real_Tbl.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Agent_Real_Tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Agent_ID", "Name", "Rank", "Address", "Age", "Location", "Gender", "Email", "Contact"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Agent_Real_Tbl.setSelectionBackground(new java.awt.Color(153, 153, 255));
        Agent_Real_Tbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Agent_Real_Tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Agent_Real_TblMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Agent_Real_Tbl);

        jTabbedPane2.addTab("Agent Info", jScrollPane3);

        Agent_Earnings.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Agent_ID", "Agent Name", "Agent Earnings"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane13.setViewportView(Agent_Earnings);

        jTabbedPane2.addTab("Earnings", jScrollPane13);

        SidePaneDev2.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 750, 250));

        jPanel2.add(SidePaneDev2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 580));

        jScrollPane6.setViewportView(jPanel2);

        jTabbedPane3.addTab("Agents", jScrollPane6);

        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SidePaneDev3.setkEndColor(new java.awt.Color(153, 153, 255));
        SidePaneDev3.setkStartColor(new java.awt.Color(204, 153, 255));
        SidePaneDev3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Client_Agent_Tbl.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Client_Agent_Tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Client_ID", "Name", "Address", "Age", "Gender", "Email", "Contact", "Agent_Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Client_Agent_Tbl.setSelectionBackground(new java.awt.Color(153, 153, 255));
        Client_Agent_Tbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane8.setViewportView(Client_Agent_Tbl);

        SidePaneDev3.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 770, 370));

        jPanel26.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel107.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel107.setText("<html>Take a look into your clients!<br>You can not tamper details about the client.");
        jPanel26.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 330, 50));

        SidePaneDev3.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 770, 50));

        jPanel13.add(SidePaneDev3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 430));

        jScrollPane7.setViewportView(jPanel13);

        jTabbedPane3.addTab("Clients", jScrollPane7);

        SidePaneDev4.setkEndColor(new java.awt.Color(153, 153, 255));
        SidePaneDev4.setkStartColor(new java.awt.Color(204, 153, 255));
        SidePaneDev4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Show_Sold_Items.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        Show_Sold_Items.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sales_ID", "Agent", "Client", "Developer", "Project", "Pay Method", "Incentives", "Total", "Realty Com", "Agent Com", "Equity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Show_Sold_Items.setSelectionBackground(new java.awt.Color(153, 153, 255));
        Show_Sold_Items.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(Show_Sold_Items);

        SidePaneDev4.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 770, 380));

        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel101.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel101.setText("<html>Take a look into your sold projects!<br>You might be satisfied on your progress as a Realtor!");
        jPanel15.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 330, 50));

        SidePaneDev4.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 770, 50));

        jTabbedPane3.addTab("Sold Projects", SidePaneDev4);

        Changelogs4.add(jTabbedPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 800, 470));

        TakeAdminRights5.setBackground(new java.awt.Color(204, 153, 255));
        TakeAdminRights5.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        TakeAdminRights5.setText("Data Retrieval");
        TakeAdminRights5.setBorder(new javax.swing.border.MatteBorder(null));
        TakeAdminRights5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TakeAdminRights5ActionPerformed(evt);
            }
        });
        Changelogs4.add(TakeAdminRights5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, 90, 40));

        jLabel27.setFont(new java.awt.Font("Segoe UI Light", 0, 10)); // NOI18N
        jLabel27.setText("<html>CREATE, UPDATE, ARCHIVE data here.\n<br>>Make sure to fill all of the form when manipulating data.\n<br>>Search by ID and ID ONLY");
        jLabel27.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Changelogs4.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 520, 40));

        jTabbedPane1.addTab("Tools", Changelogs4);

        Changelogs2.setBackground(new java.awt.Color(250, 250, 250));
        Changelogs2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel50.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel50.setText("Changelog:");
        jPanel11.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel54.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel54.setText("November 28, 2022:");
        jLabel54.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 220, 20));

        jLabel52.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jLabel52.setText("<html> ~ERD created <br>~ERD checked");
        jLabel52.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 150, 40));

        jLabel53.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jLabel53.setText("<html> ~System created");
        jLabel53.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 150, 20));

        jLabel55.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel55.setText("November 30, 2022:");
        jLabel55.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 220, 20));

        jLabel56.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jLabel56.setText("<html> ~Project created <br>~Connected to mySQL Server");
        jLabel56.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 190, 40));

        jLabel57.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel57.setText("December 1, 2022:");
        jLabel57.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 220, 20));

        jLabel58.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jLabel58.setText("<html> ~Test functions created <br>~Test queries created");
        jLabel58.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, 190, 40));

        jLabel59.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel59.setText("December 3, 2022:");
        jLabel59.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 220, 20));

        jLabel60.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jLabel60.setText("<html> ~GUI finished <br>~Linked elements to SQL database <br>~bugs fixed");
        jLabel60.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 400, 190, 80));

        jLabel61.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel61.setText("December 6, 2022:");
        jLabel61.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, 220, 20));

        jLabel64.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jLabel64.setText("<html> ~Updated Panels <br>~Updated functions <br>~bugs fixed");
        jLabel64.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 520, 190, 60));

        jLabel65.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel65.setText("December 9, 2022:");
        jLabel65.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 490, 220, 20));

        jLabel66.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jLabel66.setText("<html> ~bugs fixed <br>~bugs fixed <br>~bugs fixed");
        jLabel66.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 620, 190, 60));

        jLabel67.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel67.setText("December 10, 2022:");
        jLabel67.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 590, 220, 20));

        jPanel4.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 720));

        jScrollPane1.setViewportView(jPanel4);

        Changelogs2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 350, 540));

        jLabel71.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jLabel71.setText("aparece.kennethjose@cec.edu.ph");
        jLabel71.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Changelogs2.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 210, 20));

        jLabel72.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel72.setText("Or hit me up in my DMs:");
        jLabel72.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Changelogs2.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 250, 30));

        jLabel73.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        jLabel73.setText("Libriaries used:");
        Changelogs2.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 300, -1, -1));

        FB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        FB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FBMouseClicked(evt);
            }
        });
        Changelogs2.add(FB, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, 70, 70));

        Twitter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Twitter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TwitterMouseClicked(evt);
            }
        });
        Changelogs2.add(Twitter, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 120, 70, 70));

        Instagram.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Instagram.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                InstagramMouseClicked(evt);
            }
        });
        Changelogs2.add(Instagram, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 120, 70, 70));

        jLabel75.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel75.setText("Found a bug?");
        Changelogs2.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, -1, -1));

        jLabel76.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel76.setText("Report the bug to this email:");
        jLabel76.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Changelogs2.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 250, 30));

        jLabel77.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jLabel77.setText("<html>~jdbc mysql connector\n<br>~kGradientPanel\n<br>~jcalendar\n<br>~Absolute Layout");
        jLabel77.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Changelogs2.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 330, 220, 80));

        jLabel78.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jLabel78.setText("<html>This is an academic project only. <br>The business's or database's names <br>are fictional only. <br>They are not intended to be sold or  commercialized.");
        jLabel78.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Changelogs2.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 450, 220, 100));

        jLabel74.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        jLabel74.setText("DISCLAIMER");
        Changelogs2.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 420, -1, -1));

        jLabel79.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jLabel79.setText("<html>Lead, UI Design - Russo\n<br>ERD, System - Canama\n<br>Code, UI Design - Aparece");
        jLabel79.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Changelogs2.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 230, 220, 60));

        jLabel88.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        jLabel88.setText("TeamRusso:");
        Changelogs2.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 200, -1, -1));

        jTabbedPane1.addTab("Changelog and More", Changelogs2);

        Tools_Panel.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 600));

        Layers.add(Tools_Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 600));

        Account_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel7.setkEndColor(new java.awt.Color(88, 65, 200));
        kGradientPanel7.setkGradientFocus(200);
        kGradientPanel7.setkStartColor(new java.awt.Color(118, 86, 210));
        kGradientPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ToDate5.setBackground(new java.awt.Color(255, 246, 242));
        ToDate5.setFont(new java.awt.Font("Segoe UI Light", 0, 48)); // NOI18N
        ToDate5.setForeground(new java.awt.Color(255, 255, 255));
        ToDate5.setText("Good");
        kGradientPanel7.add(ToDate5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 60));

        datehome2.setBackground(new java.awt.Color(255, 246, 242));
        datehome2.setFont(new java.awt.Font("Segoe UI Light", 0, 48)); // NOI18N
        datehome2.setForeground(new java.awt.Color(255, 255, 255));
        kGradientPanel7.add(datehome2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 660, 60));

        jLabelTest2.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabelTest2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTest2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kGradientPanel7.add(jLabelTest2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 790, 50));

        jPanel16.add(kGradientPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 820, 130));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(153, 153, 153)));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlabel.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jlabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel.setText("Realty Statistics");
        jPanel25.add(jlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 130, 30));

        jlabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel4.setText("Total Clients Ever:");
        jPanel25.add(jlabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 130, -1));

        Stats_4.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Stats_4.setText("100");
        jPanel25.add(Stats_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 100, -1));

        jlabel10.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel10.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel10.setText("Sold Projects:");
        jPanel25.add(jlabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 130, -1));

        jlabel12.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel12.setText("Realty Location:");
        jPanel25.add(jlabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 100, -1));

        jlabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel8.setText("Gross Earned:");
        jPanel25.add(jlabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 130, -1));

        Stats_7.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Stats_7.setText("100");
        jPanel25.add(Stats_7, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 100, -1));

        Acc_Location.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jPanel25.add(Acc_Location, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 160, 20));

        jlabel11.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel11.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel11.setText("Agent Earnings:");
        jPanel25.add(jlabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 130, -1));

        jlabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel7.setText("Total Agents Ever:");
        jPanel25.add(jlabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 130, -1));

        Acc_Realty_Name.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jPanel25.add(Acc_Realty_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 160, 20));

        Stats_5.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Stats_5.setText("100");
        jPanel25.add(Stats_5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, 100, -1));

        Current_User_Date.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jPanel25.add(Current_User_Date, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 160, 20));

        Stats_9.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Stats_9.setText("100");
        jPanel25.add(Stats_9, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 340, 100, -1));

        Stats_3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Stats_3.setText("100");
        jPanel25.add(Stats_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, 100, -1));

        jlabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel2.setText("Available Developers:");
        jPanel25.add(jlabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 130, -1));

        Stats_8.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Stats_8.setText("100");
        jPanel25.add(Stats_8, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 100, -1));

        jlabel14.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel14.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel14.setText("Realty Name:");
        jPanel25.add(jlabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, -1));

        Stats_12.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Stats_12.setText("100");
        jPanel25.add(Stats_12, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, 100, -1));

        Stats_1.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Stats_1.setText("100");
        jPanel25.add(Stats_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 100, -1));

        Stats_2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Stats_2.setText("100");
        jPanel25.add(Stats_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 100, -1));

        Stats_11.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Stats_11.setText("100");
        jPanel25.add(Stats_11, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 400, 100, -1));

        jlabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel9.setText("Realty Earnings:");
        jPanel25.add(jlabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 130, -1));

        jlabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel1.setText("Available Projects:");
        jPanel25.add(jlabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 130, -1));

        Stats_10.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Stats_10.setText("100");
        jPanel25.add(Stats_10, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, 100, -1));

        jlabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel5.setText("Total Projects Ever:");
        jPanel25.add(jlabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 130, -1));

        jlabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel6.setText("Total Developers Ever:");
        jPanel25.add(jlabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        jlabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel3.setText("Available Agents:");
        jPanel25.add(jlabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 130, -1));

        jlabel15.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel15.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel15.setText("Date Created:");
        jPanel25.add(jlabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, -1));

        Stats_6.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Stats_6.setText("100");
        jPanel25.add(Stats_6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 280, 100, -1));

        jlabel23.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel23.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel23.setText("Net Earned:");
        jPanel25.add(jlabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 130, -1));

        jScrollPane14.setViewportView(jPanel25);

        jPanel17.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 450));

        jPanel16.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 150, 300, 450));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(153, 153, 153)));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlabel13.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel13.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel13.setText("<html>Do you really wish to remove your\n<br>realities account? You may not get\n<br>back your account again.");
        jlabel13.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlabel13.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel12.add(jlabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 210, 70));

        jlabel16.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel16.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel16.setText("Realty Name:");
        jPanel12.add(jlabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 110, 20));

        jlabel17.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel17.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel17.setText("Realty Location:");
        jPanel12.add(jlabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 110, 20));

        jlabel18.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel18.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel18.setText("Change Password:");
        jPanel12.add(jlabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 120, 20));

        Update_Location.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Update_Location.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel12.add(Update_Location, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 240, 20));

        Update_UserName.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Update_UserName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel12.add(Update_UserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 240, 20));

        Update_Password.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Update_Password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel12.add(Update_Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 240, 20));

        kGradientPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(153, 153, 153)));
        kGradientPanel9.setkEndColor(new java.awt.Color(153, 153, 255));
        kGradientPanel9.setkStartColor(new java.awt.Color(102, 102, 255));
        kGradientPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlabel19.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel19.setForeground(new java.awt.Color(255, 255, 255));
        jlabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlabel19.setText("Update");
        jlabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel19MouseClicked(evt);
            }
        });
        kGradientPanel9.add(jlabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 30));

        jPanel12.add(kGradientPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 120, 30));

        jlabel20.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlabel20.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlabel20.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        jPanel12.add(jlabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 380, 10));

        jlabel21.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jlabel21.setText("Remove Realty Account");
        jlabel21.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlabel21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        jPanel12.add(jlabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 380, -1));

        kGradientPanel10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(153, 153, 153)));
        kGradientPanel10.setkEndColor(new java.awt.Color(153, 153, 255));
        kGradientPanel10.setkStartColor(new java.awt.Color(102, 102, 255));
        kGradientPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlabel22.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel22.setForeground(new java.awt.Color(255, 255, 255));
        jlabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlabel22.setText("Remove");
        jlabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel22MouseClicked(evt);
            }
        });
        kGradientPanel10.add(jlabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 30));

        jPanel12.add(kGradientPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 120, 30));

        Update_Password1.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Update_Password1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel12.add(Update_Password1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 240, 20));

        jlabel24.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel24.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel24.setText("Confirm Password:");
        jPanel12.add(jlabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 120, 20));

        Update_Password2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Update_Password2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel12.add(Update_Password2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 240, 20));

        jlabel25.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jlabel25.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel25.setText("Current Password:");
        jPanel12.add(jlabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 120, 20));

        jlabel30.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlabel30.setText("Account Settings");
        jlabel30.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlabel30.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        jPanel12.add(jlabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 400, 40));

        jPanel16.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 440, 450));

        Account_Panel.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 600));

        Layers.add(Account_Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 600));

        Home_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel2.setkEndColor(new java.awt.Color(88, 65, 200));
        kGradientPanel2.setkGradientFocus(200);
        kGradientPanel2.setkStartColor(new java.awt.Color(118, 86, 210));
        kGradientPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ToDate4.setBackground(new java.awt.Color(255, 246, 242));
        ToDate4.setFont(new java.awt.Font("Segoe UI Light", 0, 48)); // NOI18N
        ToDate4.setForeground(new java.awt.Color(255, 255, 255));
        ToDate4.setText("Good");
        kGradientPanel2.add(ToDate4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 60));

        datehome1.setBackground(new java.awt.Color(255, 246, 242));
        datehome1.setFont(new java.awt.Font("Segoe UI Light", 0, 48)); // NOI18N
        datehome1.setForeground(new java.awt.Color(255, 255, 255));
        kGradientPanel2.add(datehome1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 660, 60));

        jLabelTest.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabelTest.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTest.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        kGradientPanel2.add(jLabelTest, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 790, 50));

        Home_Panel.add(kGradientPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 820, 130));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(153, 153, 153)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        count_proj.setFont(new java.awt.Font("Segoe UI Light", 0, 60)); // NOI18N
        count_proj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        count_proj.setText("100");
        count_proj.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(count_proj, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 110, 80));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Overview");
        jLabel49.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel6.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 180, -1));

        count_agents.setFont(new java.awt.Font("Segoe UI Light", 0, 60)); // NOI18N
        count_agents.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        count_agents.setText("100");
        count_agents.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(count_agents, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 110, 80));

        jLabel51.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("More on Accounts Section");
        jLabel51.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel6.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 330, 150, 20));

        Devs_singularplural.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Devs_singularplural.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Devs_singularplural.setText("Developers");
        Devs_singularplural.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel6.add(Devs_singularplural, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 150, 30));

        count_devs.setFont(new java.awt.Font("Segoe UI Light", 0, 60)); // NOI18N
        count_devs.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        count_devs.setText("100");
        count_devs.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(count_devs, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 110, 80));

        jLabelTest1.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jPanel6.add(jLabelTest1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 460, 40));

        Proj_singularplural.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Proj_singularplural.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Proj_singularplural.setText("Projects");
        Proj_singularplural.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel6.add(Proj_singularplural, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 170, 30));

        count_sales.setFont(new java.awt.Font("Segoe UI Light", 0, 60)); // NOI18N
        count_sales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        count_sales.setText("100");
        count_sales.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(count_sales, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 160, 110, 80));

        Sales_singularplural.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Sales_singularplural.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Sales_singularplural.setText("Sales");
        Sales_singularplural.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel6.add(Sales_singularplural, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 240, 150, 30));

        Agents_singularplural.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Agents_singularplural.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Agents_singularplural.setText("Agents");
        Agents_singularplural.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel6.add(Agents_singularplural, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 130, 30));

        Count_Comment.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        Count_Comment.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel6.add(Count_Comment, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, 530, 40));

        jLabel70.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        jPanel6.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 600, 20));

        jlabel31.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jlabel31.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jlabel31.setText("Realty Earnings:");
        jPanel6.add(jlabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 130, 30));

        Stats_17.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        Stats_17.setText("100");
        jPanel6.add(Stats_17, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, 210, 30));

        Home_Panel.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 640, 450));

        BG.setFont(new java.awt.Font("Segoe UI Light", 0, 8)); // NOI18N
        Home_Panel.add(BG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 820, 450));

        Layers.add(Home_Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 600));

        Sales_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel8.setkEndColor(new java.awt.Color(204, 153, 255));
        kGradientPanel8.setkStartColor(new java.awt.Color(153, 153, 255));
        kGradientPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Select a project");
        jPanel18.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 580, -1));

        jPanel3.setBackground(new java.awt.Color(230, 230, 230));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        house.setBackground(new java.awt.Color(255, 255, 255));
        house.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel3.add(house, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, 170));

        jPanel18.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 190, 190));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Name:");
        jPanel18.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 50, 20));

        Selected_Name.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Selected_Name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(Selected_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 150, 20));

        Selected_Price.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Selected_Price.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(Selected_Price, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 150, 20));

        jLabel15.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel15.setText("Price:");
        jPanel18.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 50, 20));

        jLabel17.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel17.setText("Location:");
        jPanel18.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 50, 20));

        Selected_Location.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Selected_Location.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(Selected_Location, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, 150, 20));

        Selected_Developer.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Selected_Developer.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(Selected_Developer, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, 150, 20));

        jLabel18.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel18.setText("Developer:");
        jPanel18.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, 20));

        jLabel40.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel40.setText("Agent:");
        jPanel18.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, 40, 20));

        agent_selector.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        agent_selector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        agent_selector.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        agent_selector.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                agent_selectorItemStateChanged(evt);
            }
        });
        jPanel18.add(agent_selector, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 270, 160, 20));

        JLabel_Email.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        JLabel_Email.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(JLabel_Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 420, 160, 20));

        JLabel_ID.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        JLabel_ID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(JLabel_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 300, 160, 20));

        JLabel_Location.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        JLabel_Location.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(JLabel_Location, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 360, 160, 20));

        JLabel_Contact.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        JLabel_Contact.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(JLabel_Contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 390, 160, 20));

        jLabel35.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel35.setText("Agent ID:");
        jLabel35.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel18.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 300, 60, 20));

        jLabel45.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel45.setText("Agent Location:");
        jLabel45.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel18.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 360, 80, 20));

        jLabel33.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel33.setText("Agent Contact:");
        jLabel33.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel18.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 390, 80, 20));

        jLabel34.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel34.setText("Agent Email:");
        jLabel34.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel18.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 420, -1, 20));

        Client_Name.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Client_Name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(Client_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 270, 160, 20));

        Client_Address.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Client_Address.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(Client_Address, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 300, 160, 20));

        Client_Age.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Client_Age.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(Client_Age, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 330, 160, 20));

        Client_Gender.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Client_Gender.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(Client_Gender, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 360, 160, 20));

        Client_Email.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Client_Email.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(Client_Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 390, 160, 20));

        Client_Contact.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Client_Contact.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(Client_Contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 420, 160, 20));

        Pay_Method.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Pay_Method.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Debit", "Credit" }));
        Pay_Method.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(Pay_Method, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 480, 160, 20));

        Select_incentives.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Select_incentives.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rice", "Cosmetics", "Clothing", "Cash" }));
        Select_incentives.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(Select_incentives, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 450, 160, 20));

        jLabel41.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel41.setText("Client Age:");
        jLabel41.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel18.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 330, 70, 20));

        jLabel37.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel37.setText("Client Name:");
        jLabel37.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel18.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 270, 80, 20));

        jLabel43.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel43.setText("Client Gender:");
        jLabel43.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel18.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 360, 80, 20));

        jLabel44.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel44.setText("Client Email:");
        jLabel44.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel18.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 390, 80, 20));

        jLabel36.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel36.setText("Client Contact:");
        jLabel36.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel18.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 80, 20));

        jLabel39.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel39.setText("Client Address:");
        jLabel39.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel18.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 300, 80, 20));

        jLabel42.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel42.setText("Incentives");
        jLabel42.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel18.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 450, 100, 20));

        jLabel80.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel80.setText("Client Pay Method:");
        jLabel80.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel18.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 480, 100, 20));

        jButton2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jButton2.setText("Submit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel18.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 450, 80, 20));

        Table_for_Houses.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Table_for_Houses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Price", "Location", "Developer"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_for_Houses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_for_HousesMouseClicked(evt);
            }
        });
        Sales_Scroll.setViewportView(Table_for_Houses);
        if (Table_for_Houses.getColumnModel().getColumnCount() > 0) {
            Table_for_Houses.getColumnModel().getColumn(0).setResizable(false);
            Table_for_Houses.getColumnModel().getColumn(0).setHeaderValue("ID");
            Table_for_Houses.getColumnModel().getColumn(1).setResizable(false);
            Table_for_Houses.getColumnModel().getColumn(2).setResizable(false);
            Table_for_Houses.getColumnModel().getColumn(3).setResizable(false);
            Table_for_Houses.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel18.add(Sales_Scroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 580, 220));

        JLabel_Rank.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        JLabel_Rank.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        jPanel18.add(JLabel_Rank, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 330, 160, 20));

        jLabel46.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel46.setText("Agent Rank:");
        jLabel46.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel18.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 330, 80, 20));

        jLabel29.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(204, 204, 204)));
        jPanel18.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 270, 10, 240));

        jLabel30.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(204, 204, 204)));
        jPanel18.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 270, 10, 240));

        kGradientPanel8.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 820, 520));

        Sales_Panel.add(kGradientPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -70, 820, 670));

        sale.setBackground(new java.awt.Color(255, 255, 255));
        sale.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Sales_Panel.add(sale, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        Layers.add(Sales_Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 600));

        Data_Retrieval_Panel.setBackground(new java.awt.Color(255, 255, 255));
        Data_Retrieval_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane4.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane4.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SidePaneDev5.setkEndColor(new java.awt.Color(153, 153, 255));
        SidePaneDev5.setkStartColor(new java.awt.Color(204, 153, 255));
        SidePaneDev5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        kGradientPanel11.setkEndColor(new java.awt.Color(153, 153, 255));
        kGradientPanel11.setkStartColor(new java.awt.Color(204, 153, 255));
        kGradientPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DevButPar13.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar13.setOpaque(false);
        DevButPar13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Account4.setBackground(new java.awt.Color(255, 255, 255));
        Account4.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Account4.setForeground(new java.awt.Color(255, 255, 255));
        Account4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Account4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Account4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Account4MouseClicked(evt);
            }
        });
        DevButPar13.add(Account4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel96.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setText("Retrieve");
        DevButPar13.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel11.add(DevButPar13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 190, 30));

        SidePaneDev5.add(kGradientPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 200, 50));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel31.setText("Project ID:");
        jPanel20.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, 30));

        Retrieve_Proj_ID.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Retrieve_Proj_ID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        Retrieve_Proj_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Retrieve_Proj_IDKeyTyped(evt);
            }
        });
        jPanel20.add(Retrieve_Proj_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 270, 30));

        SidePaneDev5.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 610, 50));

        Archived_Proj_Dev_Tbl.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        Archived_Proj_Dev_Tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proj_ID", "Proj_Name", "Proj_Price", "Location", "Dev_Name", "Dev_ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Archived_Proj_Dev_Tbl.setSelectionBackground(new java.awt.Color(153, 153, 255));
        Archived_Proj_Dev_Tbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Archived_Proj_Dev_Tbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Archived_Proj_Dev_Tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Archived_Proj_Dev_TblMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(Archived_Proj_Dev_Tbl);

        SidePaneDev5.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 770, 420));

        jLayeredPane3.add(SidePaneDev5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 810, 490));

        jPanel19.add(jLayeredPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 560));

        jTabbedPane4.addTab("Projects", jPanel19);

        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SidePaneDev9.setkEndColor(new java.awt.Color(153, 153, 255));
        SidePaneDev9.setkStartColor(new java.awt.Color(204, 153, 255));
        SidePaneDev9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        kGradientPanel15.setkEndColor(new java.awt.Color(153, 153, 255));
        kGradientPanel15.setkStartColor(new java.awt.Color(204, 153, 255));
        kGradientPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DevButPar14.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar14.setOpaque(false);
        DevButPar14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Account7.setBackground(new java.awt.Color(255, 255, 255));
        Account7.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Account7.setForeground(new java.awt.Color(255, 255, 255));
        Account7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Account7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Account7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Account7MouseClicked(evt);
            }
        });
        DevButPar14.add(Account7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel98.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 255, 255));
        jLabel98.setText("Retrieve");
        DevButPar14.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel15.add(DevButPar14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 190, 30));

        SidePaneDev9.add(kGradientPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 200, 50));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Retrieve_Dev_ID.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Retrieve_Dev_ID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        Retrieve_Dev_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Retrieve_Dev_IDKeyTyped(evt);
            }
        });
        jPanel22.add(Retrieve_Dev_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 250, 30));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel32.setText("Developer ID:");
        jPanel22.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 30));

        SidePaneDev9.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 610, 50));

        Archived_Dev_Real_Tbl.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        Archived_Dev_Real_Tbl.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Archived_Dev_Real_Tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dev_Branch_ID", "Dev_Name", "Dev_Location"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Archived_Dev_Real_Tbl.setSelectionBackground(new java.awt.Color(153, 153, 255));
        Archived_Dev_Real_Tbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Archived_Dev_Real_Tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Archived_Dev_Real_TblMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(Archived_Dev_Real_Tbl);

        SidePaneDev9.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 770, 420));

        jLayeredPane5.add(SidePaneDev9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 810, 490));

        jPanel21.add(jLayeredPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 560));

        jTabbedPane4.addTab("Developers", jPanel21);

        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SidePaneDev10.setkEndColor(new java.awt.Color(153, 153, 255));
        SidePaneDev10.setkStartColor(new java.awt.Color(204, 153, 255));
        SidePaneDev10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        kGradientPanel16.setkEndColor(new java.awt.Color(153, 153, 255));
        kGradientPanel16.setkStartColor(new java.awt.Color(204, 153, 255));
        kGradientPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DevButPar23.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar23.setOpaque(false);
        DevButPar23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Account8.setBackground(new java.awt.Color(255, 255, 255));
        Account8.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Account8.setForeground(new java.awt.Color(255, 255, 255));
        Account8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Account8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Account8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Account8MouseClicked(evt);
            }
        });
        DevButPar23.add(Account8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel99.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(255, 255, 255));
        jLabel99.setText("Retrieve");
        DevButPar23.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel16.add(DevButPar23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 190, 30));

        SidePaneDev10.add(kGradientPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 200, 50));

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Retrieve_Agent_ID.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Retrieve_Agent_ID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        Retrieve_Agent_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Retrieve_Agent_IDKeyTyped(evt);
            }
        });
        jPanel23.add(Retrieve_Agent_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 270, 30));

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel47.setText("Agent ID:");
        jPanel23.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 50, 30));

        SidePaneDev10.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 610, 50));

        Archived_Agent_Real_Tbl.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Archived_Agent_Real_Tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Agent_ID", "Name", "Rank", "Address", "Age", "Location", "Gender", "Email", "Contact"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Archived_Agent_Real_Tbl.setSelectionBackground(new java.awt.Color(153, 153, 255));
        Archived_Agent_Real_Tbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Archived_Agent_Real_Tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Archived_Agent_Real_TblMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(Archived_Agent_Real_Tbl);
        if (Archived_Agent_Real_Tbl.getColumnModel().getColumnCount() > 0) {
            Archived_Agent_Real_Tbl.getColumnModel().getColumn(2).setHeaderValue("Rank");
            Archived_Agent_Real_Tbl.getColumnModel().getColumn(5).setHeaderValue("Location");
        }

        SidePaneDev10.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 770, 420));

        jLayeredPane6.add(SidePaneDev10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 810, 490));

        jPanel29.add(jLayeredPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 560));

        jTabbedPane4.addTab("Agents", jPanel29);

        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SidePaneDev11.setkEndColor(new java.awt.Color(153, 153, 255));
        SidePaneDev11.setkStartColor(new java.awt.Color(204, 153, 255));
        SidePaneDev11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        kGradientPanel17.setkEndColor(new java.awt.Color(153, 153, 255));
        kGradientPanel17.setkStartColor(new java.awt.Color(204, 153, 255));
        kGradientPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DevButPar24.setBackground(new java.awt.Color(51, 26, 127));
        DevButPar24.setOpaque(false);
        DevButPar24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Account9.setBackground(new java.awt.Color(255, 255, 255));
        Account9.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Account9.setForeground(new java.awt.Color(255, 255, 255));
        Account9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Account9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Account9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Account9MouseClicked(evt);
            }
        });
        DevButPar24.add(Account9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 30));

        jLabel100.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(255, 255, 255));
        jLabel100.setText("Retrieve");
        DevButPar24.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 160, 30));

        kGradientPanel17.add(DevButPar24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 190, 30));

        SidePaneDev11.add(kGradientPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 200, 50));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Retrieve_Client_ID.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Retrieve_Client_ID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 255)));
        Retrieve_Client_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Retrieve_Client_IDKeyTyped(evt);
            }
        });
        jPanel24.add(Retrieve_Client_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 270, 30));

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel48.setText("Client ID:");
        jPanel24.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 50, 30));

        SidePaneDev11.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 610, 50));

        Archived_Client_Agent_Tbl.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Archived_Client_Agent_Tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Client_ID", "Name", "Address", "Age", "Gender", "Email", "Contact", "Agent_Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Archived_Client_Agent_Tbl.setSelectionBackground(new java.awt.Color(153, 153, 255));
        Archived_Client_Agent_Tbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Archived_Client_Agent_Tbl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Archived_Client_Agent_TblKeyPressed(evt);
            }
        });
        jScrollPane12.setViewportView(Archived_Client_Agent_Tbl);

        SidePaneDev11.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 770, 420));

        jLayeredPane7.add(SidePaneDev11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 810, 490));

        jPanel31.add(jLayeredPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 560));

        jTabbedPane4.addTab("Clients", jPanel31);

        Data_Retrieval_Panel.add(jTabbedPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 820, 520));

        jLabel138.setFont(new java.awt.Font("Segoe UI Light", 0, 11)); // NOI18N
        jLabel138.setText("<html>Retrieve lost data when using this tool:\n<br>>When retrieving data, make sure to click the data you wish to retrieve first.\n<br>>Data showing on the panel above means you have selected a data.\n<br>>Click \"Retrieve\" button to restore data to the data center.");
        Data_Retrieval_Panel.add(jLabel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 520, 70));

        Layers.add(Data_Retrieval_Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 600));

        MAIN.add(Layers, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 820, 600));

        getContentPane().add(MAIN, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, -1));

        getAccessibleContext().setAccessibleName("Testing Program");
        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Table_for_HousesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_for_HousesMouseClicked
        // TODO add your handling code here:
        int index = Table_for_Houses.getSelectedRow();
        TableModel model = Table_for_Houses.getModel();
        selector_1_1("select * from projects where Proj_Name = '" + model.getValueAt(index, 1).toString() + "'", "Dev_Branch_ID");
        Selected_Name.setText(model.getValueAt(index, 1).toString());
        Selected_Price.setText(model.getValueAt(index, 2).toString());
        Selected_Location.setText(model.getValueAt(index, 3).toString());
        Selected_Developer.setText(model.getValueAt(index, 4).toString());
        display_image("select * from developers join projects on developers.Dev_Branch_ID = projects.Dev_Branch_ID where Proj_ID = " + (model.getValueAt(index, 0).toString()), house);
    }//GEN-LAST:event_Table_for_HousesMouseClicked

    private void agent_selectorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_agent_selectorItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            display_agent_data();
            selector_2("select * from agents where Agent_Name = '" + (String) agent_selector.getSelectedItem() + "'", "Agent_ID");
            System.out.println(selector_ID_2);
        }
    }//GEN-LAST:event_agent_selectorItemStateChanged

    private void LogOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogOutMouseClicked
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(this, "Do you really wish to Log out?", "Confirm Exit", 0);
        if (result == 0) {
            log_out();
        }
    }//GEN-LAST:event_LogOutMouseClicked

    private void AccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AccountMouseClicked
        // TODO add your handling code here:
        Set_Visibility(false, false, false, true, false); //home, sales, tools, account, data retrieval
    }//GEN-LAST:event_AccountMouseClicked

    private void ToolsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ToolsMouseClicked
        // TODO add your handling code here:
        Set_Visibility(false, false, true, false, false); //home, sales, tools, account, data retrieval
    }//GEN-LAST:event_ToolsMouseClicked

    private void CreateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CreateMouseClicked
        // TODO add your handling code here:
        Set_Visibility(false, true, false, false, false); //home, sales, tools, account, data retrieval
    }//GEN-LAST:event_CreateMouseClicked

    private void HomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeMouseClicked
        // TODO add your handling code here:
        Set_Visibility(true, false, false, false, false); //home, sales, tools, account, data retrieval
    }//GEN-LAST:event_HomeMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        submit_client();
        selector_client("select * from client where Client_Name = '" + Client_Name.getText() + "'", "Client_ID");
        System.out.println(selector_ID_3);
        submit_Sale();
        update_availability();
        clear_sales();
        display_all();
        put_data();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void InstagramMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InstagramMouseClicked
        // TODO add your handling code here:
        try {
            Desktop.getDesktop().browse(new URL("https://www.instagram.com/kenprce/").toURI());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_InstagramMouseClicked

    private void TwitterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TwitterMouseClicked
        // TODO add your handling code here:
        try {
            Desktop.getDesktop().browse(new URL("https://twitter.com/kenprce/").toURI());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_TwitterMouseClicked

    private void FBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FBMouseClicked
        // TODO add your handling code here:
        try {
            Desktop.getDesktop().browse(new URL("https://www.facebook.com/Kenprce/").toURI());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_FBMouseClicked

    private void TakeAdminRights5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TakeAdminRights5ActionPerformed
        // TODO add your handling code here:
        Re_Log_In overlay = new Re_Log_In(current_id, current_user, database, Data_Retrieval_Panel, Tools_Panel, Account_Panel, Home_Panel, Sales_Panel);
        overlay.show();
    }//GEN-LAST:event_TakeAdminRights5ActionPerformed

    private void Account9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Account9MouseClicked
        // TODO add your handling code here:
        try {
            archive("client", "Client_ID", "No", Retrieve_Client_ID);
            display_all();
            put_data();
            Retrieve_Client_ID.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Account9MouseClicked

    private void Account8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Account8MouseClicked
        // TODO add your handling code here:
        try {
            archive("agents", "Agent_ID", "No", Retrieve_Agent_ID);
            display_all();
            put_data();
            Retrieve_Agent_ID.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Account8MouseClicked

    private void Account7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Account7MouseClicked
        // TODO add your handling code here:
        try {
            archive("developers", "Dev_Branch_ID", "No", Retrieve_Dev_ID);
            display_all();
            put_data();
            Retrieve_Dev_ID.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Account7MouseClicked

    private void Archived_Proj_Dev_TblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Archived_Proj_Dev_TblMouseClicked
        // TODO add your handling code here:
        int index = Archived_Proj_Dev_Tbl.getSelectedRow();
        TableModel model = Archived_Proj_Dev_Tbl.getModel();
        Retrieve_Proj_ID.setText(model.getValueAt(index, 0).toString());
    }//GEN-LAST:event_Archived_Proj_Dev_TblMouseClicked

    private void Account4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Account4MouseClicked
        // TODO add your handling code here:

        try {
            archive("projects", "Proj_ID", "No", Retrieve_Proj_ID);
            display_all();
            put_data();
            Retrieve_Proj_ID.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Account4MouseClicked

    private void jlabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel19MouseClicked
        // TODO add your handling code here:
        if (Update_Password2.getText().equals("")
                || Update_UserName.getText().equals("")
                || Update_Location.getText().equals("")
                || Update_Password.getText().equals("")
                || Update_Password1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "one or more fields are null!");
        } else if (!Update_Password1.getText().equals(Update_Password.getText())) {
            JOptionPane.showMessageDialog(null, "new and confirm passwords do not match!");
        } else if (!Update_Password2.getText().equals(current_password)) {
            JOptionPane.showMessageDialog(null, "Incorrect password!");
        } else {
            try {
                update_realty();
                display_all();
                current_user = Update_UserName.getText();
                current_loc = Update_Location.getText();
                current_password = Update_Password.getText();
                put_data();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jlabel19MouseClicked

    private void jlabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel22MouseClicked
        // TODO add your handling code here:
//        Re_Log_In_Archive overlay = new Re_Log_In_Archive(current_id, current_user, database);
//        overlay.show();
        String input = JOptionPane.showInputDialog("Enter Pasword");
        if (!input.equals(current_password)) {
            JOptionPane.showMessageDialog(null, "Wrong password");
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Do you really wish to remove this account? You will not get this back.", "Confirm Exit", 0);
            if (confirm == 0) {
                archive_realty();
                dispose();
            }
        }
    }//GEN-LAST:event_jlabel22MouseClicked

    private void Retrieve_Proj_IDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Retrieve_Proj_IDKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_Retrieve_Proj_IDKeyTyped

    private void Retrieve_Dev_IDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Retrieve_Dev_IDKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_Retrieve_Dev_IDKeyTyped

    private void Retrieve_Agent_IDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Retrieve_Agent_IDKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_Retrieve_Agent_IDKeyTyped

    private void Retrieve_Client_IDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Retrieve_Client_IDKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_Retrieve_Client_IDKeyTyped

    private void Archived_Dev_Real_TblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Archived_Dev_Real_TblMouseClicked
        // TODO add your handling code here:
        int index = Archived_Dev_Real_Tbl.getSelectedRow();
        TableModel model = Archived_Dev_Real_Tbl.getModel();
        Retrieve_Dev_ID.setText(model.getValueAt(index, 0).toString());
    }//GEN-LAST:event_Archived_Dev_Real_TblMouseClicked

    private void Archived_Agent_Real_TblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Archived_Agent_Real_TblMouseClicked
        // TODO add your handling code here:

        int index = Archived_Agent_Real_Tbl.getSelectedRow();
        TableModel model = Archived_Agent_Real_Tbl.getModel();
        Retrieve_Agent_ID.setText(model.getValueAt(index, 0).toString());
    }//GEN-LAST:event_Archived_Agent_Real_TblMouseClicked

    private void Archived_Client_Agent_TblKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Archived_Client_Agent_TblKeyPressed
        // TODO add your handling code here:
        int index = Archived_Client_Agent_Tbl.getSelectedRow();
        TableModel model = Archived_Client_Agent_Tbl.getModel();
        Retrieve_Client_ID.setText(model.getValueAt(index, 0).toString());
    }//GEN-LAST:event_Archived_Client_Agent_TblKeyPressed

    private void Agent_Real_TblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Agent_Real_TblMouseClicked
        // TODO add your handling code here:
        int index = Agent_Real_Tbl.getSelectedRow();
        TableModel model = Agent_Real_Tbl.getModel();
        Agent_ID.setText(model.getValueAt(index, 0).toString());
        Agent_Name.setText(model.getValueAt(index, 1).toString());
        Agent_Rank.setSelectedItem(model.getValueAt(index, 2).toString());
        Agent_Address.setText(model.getValueAt(index, 3).toString());
        Agent_Age.setText(model.getValueAt(index, 4).toString());
        Agent_Location.setText(model.getValueAt(index, 5).toString());
        Agent_Gender.setText(model.getValueAt(index, 6).toString());
        Agent_Email.setText(model.getValueAt(index, 7).toString());
        Agent_Contact.setText(model.getValueAt(index, 8).toString());
    }//GEN-LAST:event_Agent_Real_TblMouseClicked

    private void Search_AgentsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Search_AgentsKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                search_agents();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Search_AgentsKeyPressed

    private void Agent_AgeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Agent_AgeKeyTyped
        // TODO add your handling code here:
        char i = evt.getKeyChar();
        if (!Character.isDigit(i)) {
            evt.consume();
        }
    }//GEN-LAST:event_Agent_AgeKeyTyped

    private void Agent_IDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Agent_IDKeyTyped
        // TODO add your handling code here:
        char i = evt.getKeyChar();
        if (!Character.isDigit(i)) {
            evt.consume();
        }
    }//GEN-LAST:event_Agent_IDKeyTyped

    private void Agent_ContactKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Agent_ContactKeyTyped
        // TODO add your handling code here:
        char i = evt.getKeyChar();
        if (!Character.isDigit(i)) {
            evt.consume();

        }
        if (Agent_Contact.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_Agent_ContactKeyTyped

    private void LogOut3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogOut3MouseClicked
        // TODO add your handling code here:
        try {
            archive("agents", "Agent_ID", "Yes", Agent_ID);
            clear_agents();
            display_all();
            update_combobox("select * from agents where Realty_ID = " + current_id, agent_selector, "Agent_Name");
            put_data();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_LogOut3MouseClicked

    private void Account3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Account3MouseClicked
        // TODO add your handling code here:
        if (Agent_ID.getText().equals("")
                || Agent_Name.getText().equals("")
                || Agent_Rank.getSelectedIndex() == 0
                || Agent_Age.getText().equals("")
                || Agent_Location.getText().equals("")
                || Agent_Gender.getText().equals("")
                || Agent_Email.getText().equals("")
                || Agent_Contact.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Error: One or more fields are empty.");
            clear_agents();
            //            if (Agent_ID.getText().equals("[a-zA-Z]+")
            //                    || Agent_Age.getText().equals("[a-zA-Z]+")
            //                    || Agent_Contact.getText().equals("[a-zA-Z]+")) {
            //                JOptionPane.showMessageDialog(this, "Error: Please input numbers in the ff: ID, Age, Contact.");
            //                clear();
            //                if (Agent_Contact.getText().length() < 11){
            //                    JOptionPane.showMessageDialog(this, "Error: Contact number length is invalid!");
            //                    clear();
            //                }
            //            }
        } else {
            submit_agents();
            clear_agents();
            update_combobox("select * from agents where Realty_ID = " + current_id, agent_selector, "Agent_Name");
            display_all();
            put_data();
        }
    }//GEN-LAST:event_Account3MouseClicked

    private void Settings3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Settings3MouseClicked
        // TODO add your handling code here:
        if (Agent_ID.getText().equals("") || Agent_Name.getText().equals("")
                || Agent_Rank.getSelectedItem().equals(null) || Agent_Address.getText().equals("")
                || Agent_Age.getText().equals("") || Agent_Location.getText().equals("")
                || Agent_Gender.getText().equals("") || Agent_Email.getText().equals("")
                || Agent_Contact.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ERROR: values must not be null!");
        } else {
            try {
                update_agents();
                clear_agents();
                update_combobox("select * from agents where Realty_ID = " + current_id, agent_selector, "Agent_Name");
                display_all();
                put_data();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Settings3MouseClicked

    private void Proj_Dev_TblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Proj_Dev_TblMouseClicked
        // TODO add your handling code here:
        int index = Proj_Dev_Tbl.getSelectedRow();
        TableModel model = Proj_Dev_Tbl.getModel();
        Insert_Project_ID.setText(model.getValueAt(index, 0).toString());
        Insert_Project_Name.setText(model.getValueAt(index, 1).toString());
        Insert_Project_Price.setText(model.getValueAt(index, 2).toString());
        display_image("select * from developers join projects on developers.Dev_Branch_ID = projects.Dev_Branch_ID where Proj_ID = " + (model.getValueAt(index, 0).toString()), house_4);
        dev_selector.setSelectedItem(model.getValueAt(index, 4).toString());
    }//GEN-LAST:event_Proj_Dev_TblMouseClicked

    private void Search_ProjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Search_ProjKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                search_proj();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Search_ProjKeyPressed

    private void UploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadActionPerformed
        // TODO add your handling code here:
        upload_image();
    }//GEN-LAST:event_UploadActionPerformed

    private void dev_selectorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dev_selectorItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            selector_1("select * from developers where Dev_Name = '" + (String) dev_selector.getSelectedItem() + "'", "Dev_Branch_ID");
            System.out.println(selector_ID_1);
        }
    }//GEN-LAST:event_dev_selectorItemStateChanged

    private void LogOut5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogOut5MouseClicked
        // TODO add your handling code here:
        if (Insert_Project_ID.getText().equals("") || Insert_Project_Name.getText().equals("") || house_4.getIcon().equals(null) || Insert_Project_Price.getText().equals("") || dev_selector.getSelectedItem().equals(null)) {
            JOptionPane.showMessageDialog(null, "ERROR: values must not be null!");
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure the building is cancelled?.", "Confirm Cancellation", 0);
            if (confirm == 0) {
                try {
                    finalize_project("Cancelled");
                    clear_projects();
                    display_all();
                    put_data();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_LogOut5MouseClicked

    private void LogOut2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogOut2MouseClicked
        try {
            archive("projects", "Proj_ID", "Yes", Insert_Project_ID);
            clear_projects();
            display_all();
            put_data();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_LogOut2MouseClicked

    private void Account2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Account2MouseClicked
        // TODO add your handling code here:
        if (Insert_Project_Name.getText().equals(null) || Insert_Project_Price.getText().equals(null) || house_4.getIcon().equals(null) || dev_selector.getSelectedItem().equals(null)) {
            JOptionPane.showMessageDialog(null, "ERROR: values must not be null!");
        } else {
            try {
                submit_project();
                display_all();
                put_data();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Account2MouseClicked

    private void Settings2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Settings2MouseClicked
        // TODO add your handling code here:
        if (Insert_Project_ID.getText().equals("") || Insert_Project_Name.getText().equals("") || Insert_Project_Price.getText().equals("") || dev_selector.getSelectedItem().equals(null) || house_4.getIcon().equals(null)) {
            JOptionPane.showMessageDialog(null, "ERROR: values must not be null!");
        } else {
            try {
                update_project();
                clear_projects();
                display_all();
                put_data();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Settings2MouseClicked

    private void LogOut1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogOut1MouseClicked
        // TODO add your handling code here:
        try {
            archive("developers", "Dev_Branch_ID", "Yes", Developer_ID);
            clear_developers();
            update_combobox("select * from developers where Realty_ID = " + current_id, dev_selector, "Dev_Name");
            display_all();
            put_data();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_LogOut1MouseClicked

    private void Account1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Account1MouseClicked
        // TODO add your handling code here:
        if (Developer_ID.getText().equals("")
                || Developer_Name.getText().equals("")
                || Developer_Location.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Error: One or more fields are empty.");
            clear_agents();
        } else {
            submit_developers();
            clear_developers();
            update_combobox("select * from developers where Realty_ID = " + current_id, dev_selector, "Dev_Name");
            display_all();
            put_data();
        }
    }//GEN-LAST:event_Account1MouseClicked

    private void Settings1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Settings1MouseClicked
        // TODO add your handling code here:
        if (Developer_ID.getText().equals("") || Developer_Name.getText().equals("") || Developer_Location.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ERROR: values must not be null!");
        } else {
            try {
                update_developers();
                clear_developers();
                update_combobox("select * from developers where Realty_ID = " + current_id, dev_selector, "Dev_Name");
                display_all();
                put_data();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Settings1MouseClicked

    private void Search_DevKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Search_DevKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                search_dev();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_Search_DevKeyPressed

    private void Developer_IDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Developer_IDKeyTyped
        // TODO add your handling code here:
        char i = evt.getKeyChar();
        if (!Character.isDigit(i)) {
            evt.consume();
        }
    }//GEN-LAST:event_Developer_IDKeyTyped

    private void Dev_Real_TblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Dev_Real_TblMouseClicked
        // TODO add your handling code here:
        int index = Dev_Real_Tbl.getSelectedRow();
        TableModel model = Dev_Real_Tbl.getModel();
        Developer_ID.setText(model.getValueAt(index, 0).toString());
        Developer_Name.setText(model.getValueAt(index, 1).toString());
        Developer_Location.setText(model.getValueAt(index, 2).toString());
    }//GEN-LAST:event_Dev_Real_TblMouseClicked

    private void LogOut4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogOut4MouseClicked
        // TODO add your handling code here:
        try {
            archive("client", "Client_ID", "Yes", Client_Name1);
            display_all();
            put_data();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_LogOut4MouseClicked

    private void Settings4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Settings4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Settings4MouseClicked

    private void LogOut6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogOut6MouseClicked
        // TODO add your handling code here:
        if (Insert_Project_ID.getText().equals("") || Insert_Project_Name.getText().equals("") || house_4.getIcon().equals(null) || Insert_Project_Price.getText().equals("") || dev_selector.getSelectedItem().equals(null)) {
            JOptionPane.showMessageDialog(null, "ERROR: values must not be null!");
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure the building is finished?.", "Confirm", 0);
            if (confirm == 0) {
                try {
                    finalize_project("Finished");
                    clear_projects();
                    display_all();
                    put_data();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_LogOut6MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Oten.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Oten.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Oten.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Oten.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Oten().setVisible(true);
            }
        });
    }

    private void setIconImage() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("C:\\Users\\user\\Documents\\NetBeansProjects\\JavaApplication1\\src\\assets\\")));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Acc_Location;
    private javax.swing.JLabel Acc_Realty_Name;
    private javax.swing.JLabel Account;
    private javax.swing.JLabel Account1;
    private javax.swing.JLabel Account2;
    private javax.swing.JLabel Account3;
    private javax.swing.JLabel Account4;
    private javax.swing.JLabel Account7;
    private javax.swing.JLabel Account8;
    private javax.swing.JLabel Account9;
    private javax.swing.JPanel Account_Panel;
    private javax.swing.JTextField Agent_Address;
    private javax.swing.JTextField Agent_Age;
    private javax.swing.JTextField Agent_Contact;
    private javax.swing.JTable Agent_Earnings;
    private javax.swing.JTextField Agent_Email;
    private javax.swing.JTextField Agent_Gender;
    private javax.swing.JTextField Agent_ID;
    private javax.swing.JTextField Agent_Location;
    private javax.swing.JTextField Agent_Name;
    private javax.swing.JComboBox<String> Agent_Rank;
    private javax.swing.JTable Agent_Real_Tbl;
    private javax.swing.JLabel Agents_singularplural;
    private javax.swing.JTable Archived_Agent_Real_Tbl;
    private javax.swing.JTable Archived_Client_Agent_Tbl;
    private javax.swing.JTable Archived_Dev_Real_Tbl;
    private javax.swing.JTable Archived_Proj_Dev_Tbl;
    private javax.swing.JLabel BG;
    private javax.swing.JPanel Changelogs2;
    private javax.swing.JPanel Changelogs4;
    private javax.swing.JTextField Client_Address;
    private javax.swing.JTextField Client_Address1;
    private javax.swing.JTextField Client_Age;
    private javax.swing.JTextField Client_Age1;
    private javax.swing.JTable Client_Agent_Tbl;
    private javax.swing.JTextField Client_Contact;
    private javax.swing.JTextField Client_Contact1;
    private javax.swing.JTextField Client_Email;
    private javax.swing.JTextField Client_Email1;
    private javax.swing.JTextField Client_Gender;
    private javax.swing.JTextField Client_Gender1;
    private javax.swing.JTextField Client_Name;
    private javax.swing.JTextField Client_Name1;
    private javax.swing.JLabel Count_Comment;
    private javax.swing.JLabel Create;
    private javax.swing.JLabel Current_User_Date;
    private javax.swing.JPanel Data_Retrieval_Panel;
    private javax.swing.JLabel Date;
    private javax.swing.JPanel DevButPar1;
    private javax.swing.JPanel DevButPar10;
    private javax.swing.JPanel DevButPar11;
    private javax.swing.JPanel DevButPar12;
    private javax.swing.JPanel DevButPar13;
    private javax.swing.JPanel DevButPar14;
    private javax.swing.JPanel DevButPar15;
    private javax.swing.JPanel DevButPar2;
    private javax.swing.JPanel DevButPar23;
    private javax.swing.JPanel DevButPar24;
    private javax.swing.JPanel DevButPar3;
    private javax.swing.JPanel DevButPar4;
    private javax.swing.JPanel DevButPar5;
    private javax.swing.JPanel DevButPar6;
    private javax.swing.JPanel DevButPar7;
    private javax.swing.JPanel DevButPar8;
    private javax.swing.JPanel DevButPar9;
    private javax.swing.JTable Dev_Real_Tbl;
    private javax.swing.JTextField Developer_ID;
    private javax.swing.JTextField Developer_Location;
    private javax.swing.JTextField Developer_Name;
    private javax.swing.JLabel Devs_singularplural;
    private javax.swing.JLabel FB;
    private javax.swing.JLabel Home;
    private javax.swing.JPanel Home_Panel;
    private javax.swing.JTextField Insert_Project_ID;
    private javax.swing.JTextField Insert_Project_ID5;
    private javax.swing.JTextField Insert_Project_Name;
    private javax.swing.JTextField Insert_Project_Price;
    private javax.swing.JLabel Instagram;
    private javax.swing.JLabel JLabel_Contact;
    private javax.swing.JLabel JLabel_Email;
    private javax.swing.JLabel JLabel_ID;
    private javax.swing.JLabel JLabel_Location;
    private javax.swing.JLabel JLabel_Rank;
    private javax.swing.JLayeredPane Layers;
    private javax.swing.JLabel LogOut;
    private javax.swing.JLabel LogOut1;
    private javax.swing.JLabel LogOut2;
    private javax.swing.JLabel LogOut3;
    private javax.swing.JLabel LogOut4;
    private javax.swing.JLabel LogOut5;
    private javax.swing.JLabel LogOut6;
    private javax.swing.JPanel MAIN;
    private javax.swing.JComboBox<String> Pay_Method;
    private javax.swing.JTable Proj_Dev_Tbl;
    private javax.swing.JLabel Proj_singularplural;
    private javax.swing.JTextField Retrieve_Agent_ID;
    private javax.swing.JTextField Retrieve_Client_ID;
    private javax.swing.JTextField Retrieve_Dev_ID;
    private javax.swing.JTextField Retrieve_Proj_ID;
    private javax.swing.JPanel Sales_Panel;
    private javax.swing.JScrollPane Sales_Scroll;
    private javax.swing.JLabel Sales_singularplural;
    private javax.swing.JTextField Search_Agents;
    private javax.swing.JTextField Search_Dev;
    private javax.swing.JTextField Search_Proj;
    private javax.swing.JComboBox<String> Select_incentives;
    private javax.swing.JLabel Selected_Developer;
    private javax.swing.JLabel Selected_Location;
    private javax.swing.JLabel Selected_Name;
    private javax.swing.JLabel Selected_Price;
    private javax.swing.JLabel Settings1;
    private javax.swing.JLabel Settings2;
    private javax.swing.JLabel Settings3;
    private javax.swing.JLabel Settings4;
    private javax.swing.JTable Show_Sold_Items;
    private keeptoo.KGradientPanel SidePaneDev;
    private keeptoo.KGradientPanel SidePaneDev1;
    private keeptoo.KGradientPanel SidePaneDev10;
    private keeptoo.KGradientPanel SidePaneDev11;
    private keeptoo.KGradientPanel SidePaneDev2;
    private keeptoo.KGradientPanel SidePaneDev3;
    private keeptoo.KGradientPanel SidePaneDev4;
    private keeptoo.KGradientPanel SidePaneDev5;
    private keeptoo.KGradientPanel SidePaneDev9;
    private javax.swing.JPanel SidePanel;
    private javax.swing.JLabel Stats_1;
    private javax.swing.JLabel Stats_10;
    private javax.swing.JLabel Stats_11;
    private javax.swing.JLabel Stats_12;
    private javax.swing.JLabel Stats_17;
    private javax.swing.JLabel Stats_2;
    private javax.swing.JLabel Stats_3;
    private javax.swing.JLabel Stats_4;
    private javax.swing.JLabel Stats_5;
    private javax.swing.JLabel Stats_6;
    private javax.swing.JLabel Stats_7;
    private javax.swing.JLabel Stats_8;
    private javax.swing.JLabel Stats_9;
    private javax.swing.JTable Table_for_Houses;
    private javax.swing.JButton TakeAdminRights5;
    private javax.swing.JLabel Time;
    private javax.swing.JLabel ToDate3;
    private javax.swing.JLabel ToDate4;
    private javax.swing.JLabel ToDate5;
    private javax.swing.JLabel Tools;
    private javax.swing.JPanel Tools_Panel;
    private javax.swing.JLabel Twitter;
    private javax.swing.JTextField Update_Location;
    private javax.swing.JPasswordField Update_Password;
    private javax.swing.JPasswordField Update_Password1;
    private javax.swing.JPasswordField Update_Password2;
    private javax.swing.JTextField Update_UserName;
    private javax.swing.JButton Upload;
    private javax.swing.JComboBox<String> agent_selector;
    private javax.swing.JLabel count_agents;
    private javax.swing.JLabel count_devs;
    private javax.swing.JLabel count_proj;
    private javax.swing.JLabel count_sales;
    private javax.swing.JLabel datehome1;
    private javax.swing.JLabel datehome2;
    private javax.swing.JComboBox<String> dev_selector;
    private javax.swing.JLabel footer_id2;
    private javax.swing.JLabel footer_name2;
    private javax.swing.JLabel house;
    private javax.swing.JLabel house_4;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JLabel jLabelTest;
    private javax.swing.JLabel jLabelTest1;
    private javax.swing.JLabel jLabelTest2;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JLayeredPane jLayeredPane6;
    private javax.swing.JLayeredPane jLayeredPane7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JLabel jlabel;
    private javax.swing.JLabel jlabel1;
    private javax.swing.JLabel jlabel10;
    private javax.swing.JLabel jlabel11;
    private javax.swing.JLabel jlabel12;
    private javax.swing.JLabel jlabel13;
    private javax.swing.JLabel jlabel14;
    private javax.swing.JLabel jlabel15;
    private javax.swing.JLabel jlabel16;
    private javax.swing.JLabel jlabel17;
    private javax.swing.JLabel jlabel18;
    private javax.swing.JLabel jlabel19;
    private javax.swing.JLabel jlabel2;
    private javax.swing.JLabel jlabel20;
    private javax.swing.JLabel jlabel21;
    private javax.swing.JLabel jlabel22;
    private javax.swing.JLabel jlabel23;
    private javax.swing.JLabel jlabel24;
    private javax.swing.JLabel jlabel25;
    private javax.swing.JLabel jlabel3;
    private javax.swing.JLabel jlabel30;
    private javax.swing.JLabel jlabel31;
    private javax.swing.JLabel jlabel4;
    private javax.swing.JLabel jlabel5;
    private javax.swing.JLabel jlabel6;
    private javax.swing.JLabel jlabel7;
    private javax.swing.JLabel jlabel8;
    private javax.swing.JLabel jlabel9;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel10;
    private keeptoo.KGradientPanel kGradientPanel11;
    private keeptoo.KGradientPanel kGradientPanel15;
    private keeptoo.KGradientPanel kGradientPanel16;
    private keeptoo.KGradientPanel kGradientPanel17;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private keeptoo.KGradientPanel kGradientPanel4;
    private keeptoo.KGradientPanel kGradientPanel5;
    private keeptoo.KGradientPanel kGradientPanel6;
    private keeptoo.KGradientPanel kGradientPanel7;
    private keeptoo.KGradientPanel kGradientPanel8;
    private keeptoo.KGradientPanel kGradientPanel9;
    private javax.swing.JPanel sale;
    // End of variables declaration//GEN-END:variables

}
