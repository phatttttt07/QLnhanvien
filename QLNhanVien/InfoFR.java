package QLNhanVien;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import java.sql.*;

public class InfoFR extends JFrame implements ActionListener {
    Container container = getContentPane();
    JLabel title = new JLabel("Thông Tin Cá Nhân");
    JLabel name = new JLabel("Họ tên ");
    JTextField nametf = new JTextField();
    JLabel hometown = new JLabel("Quê quán ");
    JTextField hometowntf = new JTextField();
    JLabel education = new JLabel("Trình độ ");
    JTextField educationtf = new JTextField();
    JLabel email = new JLabel("Email ");
    JTextField emailtf = new JTextField();
    JLabel birth = new JLabel("Ngày sinh ");
   // JTextField birthtf = new JTextField();
    JLabel Class = new JLabel("Chức vụ ");
    JTextField Classtf = new JTextField();
    JLabel CMND = new JLabel("CMND ");
    JTextField CMNDtf = new JTextField();
    JLabel address = new JLabel("Địa chỉ ");
    JTextField addresstf = new JTextField();
    JLabel phone = new JLabel("SĐT ");
    JTextField phonetf = new JTextField();
    JDateChooser dateChooser = new JDateChooser();
    JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
    JButton update = new JButton("Chỉnh Sửa");
    JButton back = new JButton("Quay Lại");
    String manv = new String();
    public void defaults()
    {
        Connection   conn = null;
        String URL ="jdbc:sqlserver://localhost\\MSSQLSERVER01:12345;database=QLNHANVIEN;integratedSecurity=true;";
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(URL);
            cs = conn.prepareCall("{call INFO(?)}");
            cs.setString(1, manv);
            rs = cs.executeQuery();
            rs.next();
            nametf.setText(rs.getString(1));
            hometowntf.setText(rs.getString(2));
            educationtf.setText(rs.getString(3));
            emailtf.setText(rs.getString(4));
            editor.setText(rs.getString(5));
            Classtf.setText(rs.getString(6));
            CMNDtf.setText(rs.getString(7));
            addresstf.setText(rs.getString(8));
            phonetf.setText(rs.getString(9));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setLocationAndSize(String id)
    {
        title.setFont(title.getFont().deriveFont(20.0f));
        title.setBounds(40,3,300,70);
        name.setBounds(10,70,100,30);
        nametf.setBounds(75, 70,145,25);
        nametf.setEditable(false);
        hometown.setBounds(10,110,100,30);
        hometowntf.setBounds(75,110,145,25);
        hometowntf.setEditable(false);
        education.setBounds(10,150,100,30);
        educationtf.setBounds(75,150,145,25);
        educationtf.setEditable(false);
        email.setBounds(10,190,100,30);
        emailtf.setBounds(75,190,145,25);
        emailtf.setEditable(false);
        birth.setBounds(10,230,100,30);
       // birthtf.setBounds(75,230,135,25);
        Class.setBounds(260,70,100,30);
        Classtf.setBounds(315,70,145,25);
        Classtf.setEditable(false);
        CMND.setBounds(260,110,100,30);
        CMNDtf.setBounds(315,110,145,25);
        CMNDtf.setEditable(false);
        address.setBounds(260,150,100,30);
        addresstf.setBounds(315,150,145,25);
        addresstf.setEditable(false);
        phone.setBounds(260,190,100,30);
        phonetf.setBounds(315,190,145,25);
        phonetf.setEditable(false);
        editor.setEditable(false);
        dateChooser.setBounds(135,230,100,25);
        editor.setBounds(75,230,145,25);
        update.setBounds(315,230,120,30);
        back.setBounds(315,265,120,30);
        }
    InfoFR(String id)
    {
        manv = id;
        defaults();
        setLayoutManager();
        setLocationAndSize(id);
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManager()
    {
        container.setLayout(null);
    }

    public void addComponentsToContainer()
    {
        container.add(title);
        container.add(name);
        container.add(nametf);
        container.add(hometown);
        container.add(hometowntf);
        container.add(education);
        container.add(educationtf);
        container.add(email);
        container.add(emailtf);
        container.add(birth);
       // container.add(birthtf);
        container.add(Class);
        container.add(Classtf);
        container.add(CMND);
        container.add(CMNDtf);
        container.add(address);
        container.add(addresstf);
        container.add(phone);
        container.add(phonetf);
     //   container.add(dateChooser);
        container.add(editor);
        container.add(update);
        container.add(back);
    }
    public void addActionEvent()
    {
        update.addActionListener(this);
        back.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
    if(actionEvent.getSource() == update)
    {
        UpdateFR update = new UpdateFR(manv);
        update.setTitle("Cập nhật thông tin");
        update.setVisible(true);
        update.setBounds(10,10,475,425);
        update.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        update.setResizable(false);
        update.setLocationRelativeTo(null);
    }
    if(actionEvent.getSource() == back)
    {
        this.setVisible(false);
        this.dispose();
    }
    }
}
