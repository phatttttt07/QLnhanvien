package QLNhanVien;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.font.TextAttribute;
import java.sql.*;
import java.util.Map;

public class managerFR extends JFrame implements ActionListener{
    Container container = getContentPane();
    JLabel title = new JLabel("Quản lý nhân viên");
    JPanel namepn = new JPanel();
    String []header ={"Tên", "ID", "Chức Vụ", "Người quản lý"};
    DefaultTableModel tableModel = new DefaultTableModel();
    JTable table = new JTable() {
        private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    JScrollPane jScrollPane = null;
    JButton myemployee = new JButton("Nhân viên của tôi");
    JButton back = new JButton("Quay lại");
    JButton add = new JButton("Thêm");
    String manv = new String();
    public void tableProcess()
    {
        tableModel.setColumnIdentifiers(header);
        table.setModel(tableModel);
        TableColumnModel tableColumnModel = table.getColumnModel();
        for(int i=0;i<4;i++)
        {
            tableColumnModel.getColumn(i).setPreferredWidth(105);
        }
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        jScrollPane = new JScrollPane(table);
    }
    public void defaults() throws SQLException {
        Connection   conn = null;
        String URL ="jdbc:sqlserver://localhost\\MSSQLSERVER01:12345;database=QLNHANVIEN;integratedSecurity=true;";
        CallableStatement cs = null;
        ResultSet rs = null;
        conn = DriverManager.getConnection(URL);
        cs = conn.prepareCall("{call EMPLOYEE()}");
        rs = cs.executeQuery();
        while (rs.next())
        {
            String row[] = new String[4];
            for(int i=0;i<4;i++)
            {
                row[i] = rs.getString(i+1);
            }
            tableModel.addRow(row);
        }
    }
    public void setLocationAndSize()
    {
        title.setFont(title.getFont().deriveFont(20.0f));
        title.setBounds(20,3,300,70);
        namepn.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Danh sách nhân viên", TitledBorder.CENTER, TitledBorder.TOP));
        namepn.setBounds(23,65,450,260);
        namepn.setBackground(Color.LIGHT_GRAY);
        jScrollPane.setBounds(37,85,420,220);

        myemployee.setBounds(40,370,135,30);
        back.setBounds(320,370,135,30);
        add.setBounds(40,405,135,30);
    }
    managerFR(String id) throws SQLException {
        manv = id;
        setLayoutManager();
        tableProcess();
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
        container.add(jScrollPane);
        container.add(namepn);
        container.add(myemployee);
        container.add(back);
        container.add(add);
    }
    public void addActionEvent()
    {
        myemployee.addActionListener(this);
        back.addActionListener(this);
        add.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()== myemployee)
        {
            MyEmployee myEmployee = null;
            try {
                myEmployee = new MyEmployee(manv);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            myEmployee.setTitle("Nhân viên của tôi");
            myEmployee.setVisible(true);
            myEmployee.setBounds(10,10,500,470);
            myEmployee.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            myEmployee.setResizable(false);
            myEmployee.setLocationRelativeTo(null);
        }
        if(actionEvent.getSource()==back)
        {
            this.setVisible(false);
            this.dispose();
        }
        if(actionEvent.getSource()==add)
        {
            int selectedIndex = table.getSelectedRow();
            String pID = tableModel.getValueAt(selectedIndex, 1).toString();
                Connection conn = null;
                String URL = "jdbc:sqlserver://localhost\\MSSQLSERVER01:12345;database=QLNHANVIEN;integratedSecurity=true;";
                CallableStatement cs = null;
                try {
                    conn = DriverManager.getConnection(URL);
                    cs = conn.prepareCall("{call ADD_MANAGEMENT(?,?,?)}");
                    cs.setString(1, pID);
                    cs.setString(2, manv);
                    cs.registerOutParameter(3, Types.INTEGER);
                    cs.execute();
                    int result = cs.getInt(3);
                    if (result == 1) {
                        JOptionPane.showMessageDialog(null, "Thêm thành công");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm thất bại", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }
}
