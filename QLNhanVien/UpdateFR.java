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

public class UpdateFR extends JFrame implements ActionListener {
    Container container = getContentPane();
    JLabel title = new JLabel("Chỉnh sửa thông tin");
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
    JButton save = new JButton("Lưu");
    JButton back = new JButton("Quay Lại");
    String manv = new String();
    public void setLocationAndSize()
    {
        title.setFont(title.getFont().deriveFont(20.0f));
        title.setBounds(40,3,300,70);
        name.setBounds(10,70,100,30);
        nametf.setBounds(75, 70,145,25);
        hometown.setBounds(10,110,100,30);
        hometowntf.setBounds(75,110,145,25);
        education.setBounds(10,150,100,30);
        educationtf.setBounds(75,150,145,25);
        email.setBounds(10,190,100,30);
        emailtf.setBounds(75,190,145,25);
        birth.setBounds(10,230,100,30);
        Class.setBounds(260,70,100,30);
        Classtf.setBounds(315,70,145,25);
        Classtf.setEditable(false);
        CMND.setBounds(260,110,100,30);
        CMNDtf.setBounds(315,110,145,25);
        address.setBounds(260,150,100,30);
        addresstf.setBounds(315,150,145,25);
        phone.setBounds(260,190,100,30);
        phonetf.setBounds(315,190,145,25);
        editor.setEditable(false);
        dateChooser.setBounds(145,230,100,25);
        editor.setBounds(75,230,145,25);
        save.setBounds(315,230,120,30);
        back.setBounds(315,265,120,30);
    }
    public static void main(String[] args)
    {
    }
    UpdateFR(String id)
    {
        manv = id;
        setLayoutManager();
        setLocationAndSize();
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
        container.add(dateChooser);
        container.add(editor);
        container.add(save);
        container.add(back);
    }
    public void addActionEvent()
    {
        save.addActionListener(this);
        back.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==save)
        {
            if(nametf.getText().isEmpty()||hometowntf.getText().isEmpty()||educationtf.getText().isEmpty()||
                emailtf.getText().isEmpty()||CMNDtf.getText().isEmpty()||addresstf.getText().isEmpty()||
            phonetf.getText().isEmpty()||editor.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                Connection   conn = null;
                String URL ="jdbc:sqlserver://localhost\\MSSQLSERVER01:12345;database=QLNHANVIEN;integratedSecurity=true;";
                CallableStatement cs = null;
                try {
                    conn = DriverManager.getConnection(URL);
                    cs = conn.prepareCall("{call UPDATE_INFO(?,?,?,?,?,?,?,?,?,?)}");
                    cs.setString(1, manv);
                    cs.setString(2, nametf.getText());
                    cs.setString(3,hometowntf.getText());
                    cs.setString(4, educationtf.getText());
                    cs.setString(5, emailtf.getText());
                    cs.setString(6, editor.getText());
                    cs.setString(7,CMNDtf.getText());
                    cs.setString(8, addresstf.getText());
                    cs.setString(9, phonetf.getText());
                    cs.registerOutParameter(10, Types.INTEGER);
                    cs.execute();
                    int result = cs.getInt(10);
                    if(result == 1)
                    {
                        JOptionPane.showMessageDialog(null, "Cập nhật thông tin thành công");
                        this.setVisible(false);
                        this.dispose();
                    }
                    else if(result == 0)
                    {
                        JOptionPane.showMessageDialog(null, "Cập nhật thông tin thất bại, vui lòng nhập lại", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if(actionEvent.getSource()==back)
        {
            this.setVisible(false);
            this.dispose();
        }
    }
}
