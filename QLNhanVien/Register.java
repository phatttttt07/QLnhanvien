package QLNhanVien;

import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import java.sql.*;
public class Register extends JFrame implements ActionListener{
    Container container = getContentPane();
    JLabel Title = new JLabel("Đăng kí: ");
    JLabel name = new JLabel("Họ Tên: ");
    JTextField nametf = new JTextField();
    JLabel address = new JLabel("Địa chỉ: ");
    JTextField addresstf = new JTextField();
    JLabel password = new JLabel("Mật Khẩu: ");
    JPasswordField passwordtf = new JPasswordField();
    JLabel rePassword = new JLabel("Nhập Lại Mật Khẩu: ");
    JPasswordField rePasswordtf = new JPasswordField();
    JLabel phone = new JLabel("Số Điện Thoại: ");
    JTextField phonetf = new JTextField();
    JLabel email = new JLabel("Email: ");
    JTextField emailtf = new JTextField();
    JCheckBox showPassword = new JCheckBox("Show Password");
    JButton register = new JButton("Đăng Kí");
    JButton back = new JButton("Quay Lại");
    JButton reset = new JButton("Làm Mới");
    public static void main(String []args)
    {
    }
    Register(String id)
    {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }
    public void setLayoutManager(){ container.setLayout(null); }
    public void setLocationAndSize()
    {
        Title.setFont(Title.getFont().deriveFont(50.0f));
        Title.setBounds(90,20,300,70);
        name.setBounds(40,90,150,50);
        nametf.setBounds(155,95,170,30);
        phone.setBounds(40,130,150,50);
        phonetf.setBounds(155,135,170,30);
        email.setBounds(40,170,150,50);
        emailtf.setBounds(155,175,170,30);
        address.setBounds(40,210,150,50);
        addresstf.setBounds(155,215,170,30);
        password.setBounds(40,250,150,50);
        passwordtf.setBounds(155,255,170,30);
        rePassword.setBounds(40,290,150,50);
        rePasswordtf.setBounds(155,295,170,30);
        showPassword.setBounds(151,325,150,25);
        register.setBounds(120,350,100,30);
        back.setBounds(225,350,100,30);
        reset.setBounds(225,385,100,30);
    }
    public void addComponentsToContainer()
    {
        container.add(Title);
        container.add(name);
        container.add(address);
        container.add(password);
        container.add(rePassword);
        container.add(phone);
        container.add(email);
        container.add(nametf);
        container.add(addresstf);
        container.add(passwordtf);
        container.add(rePasswordtf);
        container.add(phonetf);
        container.add(emailtf);
        container.add(register);
        container.add(back);
        container.add(reset);
        container.add(showPassword);
    }
    public void addActionEvent()
    {
        register.addActionListener(this);
        back.addActionListener(this);
        reset.addActionListener(this);
        showPassword.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == register)
        {
            if(nametf.getText().isEmpty()||passwordtf.getText().isEmpty()||rePasswordtf.getText().isEmpty()||
            phonetf.getText().isEmpty()||emailtf.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null,"Vui lòng nhập đầy đủ thông tin", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                if(passwordtf.getText() != rePasswordtf.getText())
                {
                    JOptionPane.showMessageDialog(null, "Mật khẩu nhập lại không trùng khớp");
                }
                else{
                Connection   conn = null;
                String URL ="jdbc:sqlserver://localhost\\MSSQLSERVER01:12345;database=QLNHANVIEN;integratedSecurity=true;";
                CallableStatement cs = null;
                try {
                    conn = DriverManager.getConnection(URL);
                    cs = conn.prepareCall("{call REGISTER(?,?,?,?,?,?)}");
                    cs.setString(1, nametf.getText());
                    cs.setString(2, passwordtf.getText());
                    cs.setString(3, emailtf.getText());
                    cs.setString(4, addresstf.getText());
                    cs.setString(5, passwordtf.getText());
                    cs.registerOutParameter(6, Types.VARCHAR);
                    cs.execute();
                    String result = cs.getString(6);
                    if(result == "null")
                    {
                        JOptionPane.showMessageDialog(null, "Đăng kí thất bại", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Đăng kí thành công, mã nhân viên là: "+result);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            }
        }
        if(actionEvent.getSource()==back)
        {
            this.setVisible(false);
            this.dispose();
        }
        if(actionEvent.getSource()==reset)
        {
            nametf.setText("");
            emailtf.setText("");
            rePasswordtf.setText("");
            passwordtf.setText("");
            phonetf.setText("");
            addresstf.setText("");
        }
        if(actionEvent.getSource() == showPassword)
        {
            if(showPassword.isSelected())
            {
                passwordtf.setEchoChar((char) 0);
                rePasswordtf.setEchoChar((char) 0);
            }
            else
            {
                passwordtf.setEchoChar('*');
                rePasswordtf.setEchoChar('*');
            }
        }
    }
}
/*container.add(nametf);
        container.add(addresstf);
        container.add(passwordtf);
        container.add(rePasswordtf);
        container.add(phonetf);
        container.add(emailtf);
        container.add(register);
        container.add(back);
        container.add(reset);
        container.add(showPassword);*/