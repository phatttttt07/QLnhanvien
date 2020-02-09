package QLNhanVien;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import java.sql.*;
public class changePassword extends JFrame implements ActionListener {
    Container container = getContentPane();
    JLabel title = new JLabel("Đổi mật khẩu");
    JLabel oldpw = new JLabel("Mật khẩu cũ: ");
    JLabel newpw = new JLabel("Mật khẩu mới: ");
    JLabel renewpw = new JLabel("Nhập lại: ");
    JTextField oldpwtf = new JTextField();
    JTextField newpwtf = new JTextField();
    JTextField renewpwtf = new JTextField();
    JButton submit = new JButton("Xác nhận");
    JButton back = new JButton("Quay lại");
    String manv = new String();
    public void setLocationAndSize() {
        title.setFont(title.getFont().deriveFont(20.0f));
        title.setBounds(100,0,300,70);
        oldpw.setBounds(40,65,100,30);
        oldpwtf.setBounds(140,60,130,30);
        newpw.setBounds(40,105,100,30);
        newpwtf.setBounds(140,100,130,30);
        renewpw.setBounds(40,145,100,30);
        renewpwtf.setBounds(140,140,130,30);
        submit.setBounds(40,190,100,30);
        back.setBounds(150,190,100,30);
    }

    public static void main(String[] args) {
    }

    changePassword(String id) {
        manv = id;
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void addComponentsToContainer() {
        container.add(title);
        container.add(oldpw);
        container.add(oldpwtf);
        container.add(newpw);
        container.add(newpwtf);
        container.add(renewpw);
        container.add(renewpwtf);
        container.add(submit);
        container.add(back);
    }

    public void addActionEvent()
    {
        submit.addActionListener(this);
        back.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==submit)
        {
            if(oldpwtf.getText().isEmpty() || newpwtf.getText().isEmpty()||renewpwtf.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null,"Vui lòng nhập đầy đủ thông tin");
            }
            else if(!newpwtf.getText().equals(renewpwtf.getText()))
            {
                JOptionPane.showMessageDialog(null,"Nhập lại mật khẩu không chính xác");
            }
            else
            {
                Connection   conn = null;
                String URL ="jdbc:sqlserver://localhost\\MSSQLSERVER01:12345;database=QLNHANVIEN;integratedSecurity=true;";
                CallableStatement cs = null;
                try {
                    conn = DriverManager.getConnection(URL);
                    cs =conn.prepareCall("{call CHANGE_PW(?,?,?,?)}");
                    cs.setString(1, manv);
                    cs.setString(2, oldpwtf.getText());
                    cs.setString(3, newpwtf.getText());
                    cs.registerOutParameter(4, Types.INTEGER);
                    cs.execute();
                    int result = cs.getInt(4);
                    if(result == 0)
                    {
                        JOptionPane.showMessageDialog(null, "Đổi mật khẩu thất bại", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(result == 1)
                    {
                        JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công");
                        this.setVisible(false);
                        this.dispose();
                    }
                    else if(result == 2)
                    {
                        JOptionPane.showMessageDialog(null, "Sai mật khẩu cũ", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if(actionEvent.getSource() == back)
        {
            this.setVisible(false);
            this.dispose();
        }
    }
}
