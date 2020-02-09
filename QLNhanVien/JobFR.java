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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.sql.*;

public class JobFR extends JFrame implements ActionListener {
    Container container = getContentPane();
    JLabel title = new JLabel("Thông Tin Công Việc");
    JLabel name = new JLabel("Họ tên ");
    JTextField nametf = new JTextField();
    JLabel ID = new JLabel("ID ");
    JTextField IDtf = new JTextField();
    JLabel date = new JLabel("Ngày vào làm ");
    JTextField datetf = new JTextField();
    JLabel manager = new JLabel("Quản lý ");
    JTextField managertf = new JTextField();
    // JTextField birthtf = new JTextField();
    JLabel Class = new JLabel("Chức vụ ");
    JTextField Classtf = new JTextField();
    JLabel room = new JLabel("Phòng Ban ");
    JTextField roomtf = new JTextField();
    JDateChooser dateChooser = new JDateChooser();
    JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
    JButton back = new JButton("Quay Lại");
    JButton salary = new JButton("Lương");
    JButton dayoff = new JButton("Ngày nghỉ");
    String manv = new String();

    public void defaults() throws SQLException {
        Connection   conn = null;
        String URL ="jdbc:sqlserver://localhost\\MSSQLSERVER01:12345;database=QLNHANVIEN;integratedSecurity=true;";
        CallableStatement cs = null;
        ResultSet rs = null;
        conn = java.sql.DriverManager.getConnection(URL);
        cs = conn.prepareCall("{call JOB(?)}");
        cs.setString(1, manv);
        rs = cs.executeQuery();
        rs.next();
        nametf.setText(rs.getString(1));
        IDtf.setText(rs.getString(2));
        editor.setText(rs.getString(3));
        managertf.setText(rs.getString(4));
        Classtf.setText(rs.getString(5));
        roomtf.setText(rs.getString(6));
        conn.close();
    }
    public void setLocationAndSize()
    {
        title.setFont(title.getFont().deriveFont(20.0f));
        title.setBounds(40,3,300,70);
        name.setBounds(10,70,100,30);
        nametf.setBounds(90, 70,135,25);
        nametf.setEditable(false);
        ID.setBounds(10,110,100,30);
        IDtf.setBounds(90,110,135,25);
        IDtf.setEditable(false);
        date.setBounds(10,150,100,30);
        manager.setBounds(10,190,100,30);
        managertf.setBounds(90,190,135,25);
        managertf.setEditable(false);
        // birthtf.setBounds(75,230,135,25);
        Class.setBounds(260,70,100,30);
        Classtf.setBounds(330,70,135,25);
        Classtf.setEditable(false);
        room.setBounds(260,110,100,30);
        roomtf.setBounds(330,110,135,25);
        roomtf.setEditable(false);
        editor.setEditable(false);
        //dateChooser.setBounds(126,150,100,25);
        editor.setBounds(90,150,120,25);
        salary.setBounds(330,160,121,40);
        dayoff.setBounds(330,205,120,40);
        back.setBounds(330,250,120,40);
    }
    public static void main(String[] args)
    {

    }
    JobFR(String id) throws SQLException {
        manv = id;
        setLayoutManager();
        defaults();
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
        container.add(ID);
        container.add(IDtf);
        container.add(date);
        container.add(datetf);
        container.add(manager);
        container.add(managertf);
        // container.add(birthtf);
        container.add(Class);
        container.add(Classtf);
        container.add(room);
        container.add(roomtf);
        container.add(dateChooser);
        container.add(editor);
        container.add(salary);
        container.add(dayoff);
        container.add(back);
    }
    public void addActionEvent()
    {
        salary.addActionListener(this);
        dayoff.addActionListener(this);
        back.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==salary)
        {
            Connection   conn = null;
            String URL ="jdbc:sqlserver://localhost\\MSSQLSERVER01:12345;database=QLNHANVIEN;integratedSecurity=true;";
            CallableStatement cs = null;
            try {
                conn = DriverManager.getConnection(URL);
                cs = conn.prepareCall("{call TINHLUONG(?,?)}");
                cs.setString(1, manv);
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.execute();
                String money = cs.getString(2);
                JOptionPane.showMessageDialog(null, "Lương tháng này của bạn là: "+money);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if(actionEvent.getSource()==dayoff)
        {
            String []header ={"Mã nhân viên","Ngày nghỉ"};
            DefaultTableModel tableModel = new DefaultTableModel();
            JTable table = new JTable() {
                private static final long serialVersionUID = 1L;
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JScrollPane jScrollPane = null;
            tableModel.setColumnIdentifiers(header);
            table.setModel(tableModel);
            TableColumnModel tableColumnModel = table.getColumnModel();
            tableColumnModel.getColumn(0).setPreferredWidth(105);
            for(int i=0;i<2;i++)
            {
                tableColumnModel.getColumn(i).setPreferredWidth(120);
            }
            table.setPreferredScrollableViewportSize(table.getPreferredSize());
            table.setFillsViewportHeight(true);
            jScrollPane = new JScrollPane(table);
            Connection   conn = null;
            String URL ="jdbc:sqlserver://localhost\\MSSQLSERVER01:12345;database=QLNHANVIEN;integratedSecurity=true;";
            CallableStatement cs = null;
            ResultSet rs = null;
            jScrollPane = new JScrollPane(table);
            try {
                conn = DriverManager.getConnection(URL);
                cs = conn.prepareCall("{call DAYOFF(?)}");
                cs.setString(1, manv);
                rs = cs.executeQuery();
                while (rs.next())
                {
                    String row[] = new String[2];
                    for(int i=0;i<2;i++)
                    {
                        row[i] = rs.getString(i+1);
                    }
                    tableModel.addRow(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            jScrollPane.setPreferredSize(new Dimension(240,200));
            JOptionPane.showConfirmDialog(null, jScrollPane, "Sô ngày nghỉ tháng này", JOptionPane.OK_CANCEL_OPTION);
        }
        if(actionEvent.getSource()==back)
        {
            this.setVisible(false);
            this.dispose();
        }
    }
}
