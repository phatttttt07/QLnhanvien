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

public class MyEmployee extends JFrame implements ActionListener{
    Container container = getContentPane();
    JLabel title = new JLabel("Quản lý nhân viên");
    JPanel namepn = new JPanel();
    String []header ={"Tên", "ID", "Chức Vụ"};
    DefaultTableModel tableModel = new DefaultTableModel();
    JTable table = new JTable() {
        private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    JScrollPane jScrollPane = null;
    JButton info = new JButton("Thông tin chi tiết");
    JButton job = new JButton("Quản lý công việc");
    JButton deActivate = new JButton("Vô hiệu hóa");
    JButton check = new JButton("Điểm danh");
    JButton back = new JButton("Quay lại");
    JButton salary = new JButton("Thêm phúc lợi");
    String manv = new String();
    public void tableProcess()
    {
        tableModel.setColumnIdentifiers(header);
        table.setModel(tableModel);
        TableColumnModel tableColumnModel = table.getColumnModel();
        for(int i=0;i<3;i++)
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
        cs = conn.prepareCall("{call MY_EMPLOYEE(?)}");
        cs.setString(1, manv);
        rs = cs.executeQuery();
        while (rs.next())
        {
            String row[] = new String[3];
            for(int i=0;i<3;i++)
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
        //namepn.add(jScrollPane);
        info.setBounds(40,335,135,30);
        salary.setBounds(40,370,135,30);
        job.setBounds(180,335,135,30);
        deActivate.setBounds(180,370,135,30);
        check.setBounds(320,335,135,30);
        back.setBounds(320,370,135,30);
       // add.setBounds(40,405,135,30);
    }
    MyEmployee(String id) throws SQLException {
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
        container.add(info);
        container.add(salary);
        container.add(job);
        container.add(deActivate);
        container.add(check);
        container.add(back);
       // container.add(add);
    }
    public void addActionEvent()
    {
        info.addActionListener(this);
        salary.addActionListener(this);
        job.addActionListener(this);
        deActivate.addActionListener(this);
        check.addActionListener(this);
        back.addActionListener(this);
      //  add.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()== info)
        {
            int selectedIndex = table.getSelectedRow();
            String pID = tableModel.getValueAt(selectedIndex, 1).toString();
            InfoFR info = new InfoFR(pID);
            info.setTitle("Thông tin cá nhân.");
            info.setVisible(true);
            info.setBounds(10,10,475,425);
            info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            info.setResizable(false);
            info.setLocationRelativeTo(null);
        }
        if(actionEvent.getSource()==job)
        {
            int selectedIndex = table.getSelectedRow();
            String pID = tableModel.getValueAt(selectedIndex, 1).toString();
            JobFR job = null;
            try {
                job = new JobFR(pID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            job.setTitle("Quản lý công việc");
            job.setVisible(true);
            job.setBounds(10,10,485,380);
            job.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            job.setResizable(false);
            job.setLocationRelativeTo(null);
        }
        if (actionEvent.getSource()==deActivate)
        {
            int selectedIndex = table.getSelectedRow();
            String pID = tableModel.getValueAt(selectedIndex, 1).toString();
            int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn vô hiệu hóa tài khoản?", "WARNING", JOptionPane.WARNING_MESSAGE);
            if(i == JOptionPane.YES_OPTION)
            {
                Connection   conn = null;
                String URL ="jdbc:sqlserver://localhost\\MSSQLSERVER01:12345;database=QLNHANVIEN;integratedSecurity=true;";
                CallableStatement cs = null;
                try {
                    conn = DriverManager.getConnection(URL);
                    cs = conn.prepareCall("{call DEACTIVATE(?,?)}");
                    cs.setString(1, pID);
                    cs.registerOutParameter(2, Types.INTEGER);
                    cs.execute();
                    int result = cs.getInt(2);
                    if(result == 1)
                    {
                        JOptionPane.showMessageDialog(null, "Vô hiệu hóa thành công");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Vô hiệu hóa thất bại", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if(actionEvent.getSource()==check)
        {
            int selectedIndex = table.getSelectedRow();
            String pID = tableModel.getValueAt(selectedIndex, 1).toString();
            Connection   conn = null;
            String URL ="jdbc:sqlserver://localhost\\MSSQLSERVER01:12345;database=QLNHANVIEN;integratedSecurity=true;";
            CallableStatement cs = null;
            JPanel myPanel = new JPanel();
            String cbb[] = {"Đi làm","Vắng"};
            JComboBox comboBox = new JComboBox(cbb);
            myPanel.add(comboBox);
            int result = JOptionPane.showConfirmDialog(null, myPanel, "Điểm danh", JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.OK_OPTION)
            {
                Integer k = (Integer)comboBox.getSelectedIndex();
                switch(k)
                {
                    case 1: {
                        try {
                            conn = DriverManager.getConnection(URL);
                            cs = conn.prepareCall("{call CHECK_work(?)}");
                            cs.setString(1, pID);
                            cs.execute();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }break;
                    case 0:{
                    }
                }
            }
        }
        if(actionEvent.getSource()==back)
        {
            this.setVisible(false);
            this.dispose();
        }
        if(actionEvent.getSource()==salary)
        {

        }
    }
}
