package javaapplication1;

import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class Log_In extends javax.swing.JFrame {

    String current_user_id;
    String current_user_name, current_user_pass;
    String current_user_loc, current_user_date_created;
    String database;

    public Log_In() {
        initComponents();
    }

    Log_In(String database) {
        initComponents();
        this.database = database;
        System.out.println("Current database: " + database);
        scaleImage("C:\\Users\\user\\Documents\\NetBeansProjects\\JavaApplication1\\src\\assets\\Log_In_Ico.png", Icon_Image);
        jPanel1.setVisible(true);
        jPanel2.setVisible(false);
    }

    /**
     * Creates new form Log_In
     *
     * @param name
     * @param pass
     * @param location
     */
    private void scaleImage(String location, JLabel label) {
        ImageIcon icon = new ImageIcon(location);
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        label.setIcon(scaledIcon);
    }

    public void clear(JTextField name, JPasswordField pass, JTextField location) {
        name.setText(null);
        pass.setText(null);
        location.setText(null);
    }

    public void Login() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, "root", "");
            String username1 = UserName.getText();
            String password1 = Password.getText();
            Statement stm = con.createStatement();
            String sql = "select * from realty where Realty_Name='" + username1 + "' and Realty_Password='" + password1 + "' and Is_Archived='No'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                current_user_id = String.valueOf(rs.getString("Realty_ID"));
                current_user_pass = String.valueOf(rs.getString("Realty_Password"));
                current_user_name = String.valueOf(rs.getString("Realty_Name"));
                current_user_loc = String.valueOf(rs.getString("Realty_Location"));
                current_user_date_created = String.valueOf(rs.getString("Date_Created"));
                JOptionPane.showMessageDialog(this, "Welcome " + (String.valueOf(rs.getString("Realty_Name"))) + "!!!");
                new Oten(current_user_id, current_user_name, database, current_user_loc, current_user_date_created, current_user_pass).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "ID or Password is incorrect.");
                clear(UserName, Password, null);
            }
            con.close();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Register() {
        String Name = Reg_UserName.getText();
        String Location = Reg_Location.getText();
        String Password = Reg_Password.getText();

        try {
            String date = new SimpleDateFormat("MMMM-dd-YYYY HH:mm:ss").format(Calendar.getInstance().getTime()).toUpperCase();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, "root", "");
            String sql = "insert into realty (Realty_Name, Realty_Location, Realty_Password, Is_Archived, Date_Created, Date_Modified) values (?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Name);
            pst.setString(2, Location);
            pst.setString(3, Password);
            pst.setString(4, "No");
            pst.setString(5, date);
            pst.setString(6, date);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "User Successfully created!");
            con.close();
            clear(Reg_UserName, Reg_Password, Reg_Location);
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void checkbox(JCheckBox checkbox, JPasswordField passwordbox) {
        if (checkbox.isSelected()) {
            passwordbox.setEchoChar((char) 0);
        } else {
            passwordbox.setEchoChar('•');
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

        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        SignUpButton = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Password = new javax.swing.JPasswordField();
        jCheckBox1 = new javax.swing.JCheckBox();
        UserName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        Reg_Location = new javax.swing.JTextField();
        Reg_Password = new javax.swing.JPasswordField();
        Reg_UserName = new javax.swing.JTextField();
        kGradientPanel3 = new keeptoo.KGradientPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel17 = new javax.swing.JLabel();
        to_SignIn = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Icon_Image = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Log In");
        setMaximumSize(new java.awt.Dimension(800, 500));
        setMinimumSize(new java.awt.Dimension(800, 500));
        setName("Login"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 500));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel1.setkEndColor(new java.awt.Color(89, 89, 188));
        kGradientPanel1.setkStartColor(new java.awt.Color(225, 131, 225));
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SignUpButton.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        SignUpButton.setForeground(new java.awt.Color(0, 102, 255));
        SignUpButton.setText("here.");
        SignUpButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SignUpButton.setFocusable(false);
        SignUpButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SignUpButtonMouseClicked(evt);
            }
        });
        jPanel1.add(SignUpButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, 30, 20));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(107, 74, 237));
        jLabel1.setText("Name");
        jLabel1.setFocusable(false);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 80, 20));

        Password.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(70, 39, 195)));
        Password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PasswordKeyPressed(evt);
            }
        });
        jPanel1.add(Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 240, 20));

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        jCheckBox1.setText("Show");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 200, 70, -1));

        UserName.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        UserName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(70, 39, 195)));
        UserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UserNameKeyPressed(evt);
            }
        });
        jPanel1.add(UserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 240, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(107, 74, 237));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Sign In");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 350, 40));

        kGradientPanel2.setkEndColor(new java.awt.Color(107, 107, 255));
        kGradientPanel2.setkGradientFocus(250);
        kGradientPanel2.setkStartColor(new java.awt.Color(54, 40, 216));
        kGradientPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Log In");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        kGradientPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 40));

        jPanel1.add(kGradientPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 100, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI Light", 0, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(107, 74, 237));
        jLabel11.setText("Password");
        jLabel11.setFocusable(false);
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 80, 20));

        jLabel12.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        jLabel12.setText("New here? Create an account ");
        jLabel12.setFocusable(false);
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 150, 20));

        jLayeredPane1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 410));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Reg_Location.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Reg_Location.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(70, 39, 195)));
        jPanel2.add(Reg_Location, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 240, 20));

        Reg_Password.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Reg_Password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(70, 39, 195)));
        jPanel2.add(Reg_Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 240, 20));

        Reg_UserName.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        Reg_UserName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(70, 39, 195)));
        jPanel2.add(Reg_UserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 240, 20));

        kGradientPanel3.setkEndColor(new java.awt.Color(107, 107, 255));
        kGradientPanel3.setkGradientFocus(250);
        kGradientPanel3.setkStartColor(new java.awt.Color(54, 40, 216));
        kGradientPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Register");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        kGradientPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 40));

        jPanel2.add(kGradientPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 100, 40));

        jLabel14.setFont(new java.awt.Font("Segoe UI Light", 0, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(107, 74, 237));
        jLabel14.setText("Name");
        jLabel14.setFocusable(false);
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 80, 20));

        jLabel15.setFont(new java.awt.Font("Segoe UI Light", 0, 15)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(107, 74, 237));
        jLabel15.setText("Password");
        jLabel15.setFocusable(false);
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 80, 20));

        jLabel16.setFont(new java.awt.Font("Segoe UI Semilight", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(107, 74, 237));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Sign Up");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 350, 40));

        jCheckBox3.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox3.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        jCheckBox3.setText("Show");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        jPanel2.add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 70, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI Light", 0, 15)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(107, 74, 237));
        jLabel17.setText("Location");
        jLabel17.setFocusable(false);
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 80, 20));

        to_SignIn.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        to_SignIn.setForeground(new java.awt.Color(0, 102, 255));
        to_SignIn.setText(" here.");
        to_SignIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        to_SignIn.setFocusable(false);
        to_SignIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                to_SignInMouseClicked(evt);
            }
        });
        jPanel2.add(to_SignIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 370, 30, 20));

        jLabel18.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        jLabel18.setText("Already have an account? Click ");
        jLabel18.setFocusable(false);
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, 160, 20));

        jLayeredPane1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 410));

        kGradientPanel1.add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 390, 410));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("________________________");
        kGradientPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 240, 40));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("RealManager DBM");
        kGradientPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 230, 40));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("<html> REALTY DATA MANAGER <br> FOR FREE");
        kGradientPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, 250, 70));
        kGradientPanel1.add(Icon_Image, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 150, 160));

        getContentPane().add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 460));

        getAccessibleContext().setAccessibleName("Login");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        checkbox(jCheckBox1, Password);
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void PasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Login();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_PasswordKeyPressed

    private void UserNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UserNameKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Login();
        }
    }//GEN-LAST:event_UserNameKeyPressed

    private void SignUpButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SignUpButtonMouseClicked
        // TODO add your handling code here:
        jPanel1.setVisible(false);
        jPanel2.setVisible(true);
    }//GEN-LAST:event_SignUpButtonMouseClicked

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
        checkbox(jCheckBox3, Reg_Password);
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void to_SignInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_to_SignInMouseClicked
        // TODO add your handling code here:
        jPanel1.setVisible(true);
        jPanel2.setVisible(false);
    }//GEN-LAST:event_to_SignInMouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
        Login();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:

        Register();
    }//GEN-LAST:event_jLabel13MouseClicked

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
            java.util.logging.Logger.getLogger(Log_In.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Log_In.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Log_In.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Log_In.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Log_In().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Icon_Image;
    private javax.swing.JPasswordField Password;
    private javax.swing.JTextField Reg_Location;
    private javax.swing.JPasswordField Reg_Password;
    private javax.swing.JTextField Reg_UserName;
    private javax.swing.JLabel SignUpButton;
    private javax.swing.JTextField UserName;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private javax.swing.JLabel to_SignIn;
    // End of variables declaration//GEN-END:variables
}
