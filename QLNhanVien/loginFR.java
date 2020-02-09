package QLNhanVien;
import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.sql.*;

public class loginFR extends JFrame implements ActionListener{
    Container container = getContentPane();
    JLabel mainLabel = new JLabel("QUẢN LÝ NHÂN VIÊN");
    JLabel userLabel = new JLabel("TÀI KHOẢN: ");
    JLabel passwordLabel = new JLabel("MẬT KHẨU: ");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("ĐĂNG NHẬP");
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("Hiện mật khẩu");
   // JButton register = new JButton("Register");
    loginFR()
    {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }
    public void setLayoutManager()
    {
        container.setLayout(null);
    }
    public void setLocationAndSize()
    {
        mainLabel.setFont(mainLabel.getFont().deriveFont(20.0f));
        mainLabel.setBounds(80,30,300,50);
        userLabel.setBounds(50,80,100,30);
        passwordLabel.setBounds(50,110,100,30);
        userTextField.setBounds(150,80,150,30);
        passwordField.setBounds(150,110,150,30);
        showPassword.setBounds(145,140,150,30);
        loginButton.setBounds(70,190,120,30);
        resetButton.setBounds(195,190,120,30);
    }
    public void addComponentsToContainer()
    {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
        container.add(mainLabel);
    }
    public void addActionEvent()
    {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
        passwordField.addActionListener(this);
        userTextField.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Connection   conn = null;
        String URL ="jdbc:sqlserver://localhost\\MSSQLSERVER01:12345;database=QLNHANVIEN;integratedSecurity=true;";
        CallableStatement cs = null;
        if((actionEvent.getSource() == loginButton)||(actionEvent.getSource() == passwordField)||(actionEvent.getSource() == userTextField))
        {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = passwordField.getText();
            if (userText.isEmpty() || pwdText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui vui lòng điền đầy đủ thông tin", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else
            {
                try {
                    conn = DriverManager.getConnection(URL);
                    cs = conn.prepareCall("{call LOG_IN(?,?,?)}");
                    cs.setString(1, userText);
                    cs.setString(2, pwdText);
                    cs.registerOutParameter(3, Types.INTEGER);
                    cs.execute();
                    int result = cs.getInt(3);
                    switch (result) {
                        case (0):
                            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu, vui lòng nhập lại", "ERROR", JOptionPane.ERROR_MESSAGE);
                            break;
                        case (2):
                            JOptionPane.showMessageDialog(this, "Tài khoản của bạn đã bị khóa", "ERROR", JOptionPane.ERROR_MESSAGE);
                            break;
                        default: {
                            JOptionPane.showMessageDialog(this, "Đăng nhập thành công");
                            this.setVisible(false);
                            this.dispose();
                            UserFR usr = new UserFR(userText, result);
                            usr.setTitle("Nhân Viên");
                            usr.setVisible(true);
                            usr.setBounds(10,10,450,440);
                            usr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            usr.setResizable(false);
                            usr.setLocationRelativeTo(null);
                        }
                    }
                }catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (actionEvent.getSource() == resetButton)
        {
            userTextField.setText("");
            passwordField.setText("");
        }
        if(actionEvent.getSource() == showPassword)
        {
            if(showPassword.isSelected())
            {
                passwordField.setEchoChar((char) 0);
            }
            else
            {
                passwordField.setEchoChar('*');
            }
        }
    }
}
