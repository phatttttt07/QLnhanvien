package QLNhanVien;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class UserFR extends JFrame implements ActionListener{
    Container container = getContentPane();
    JLabel Name = null;
    JLabel Class = null;
    JPanel Info = new JPanel();
    JButton InfoButton = new JButton("Thông tin cá nhân");
    JPanel Work = new JPanel();
    JButton WorkButton = new JButton("Công việc");
    JPanel manage = new JPanel();
    JButton manageButton = new JButton("Quản lý nhân viên");
    JButton ChangePassword = new JButton("Đổi mật khẩu");
    JButton LogOut = new JButton("Đăng xuất");
    JButton register = new JButton("Đăng kí");
    String manv = new String();
    String name = new String();
    String Classs = new String();
    int loai;
    public void name_classProcess(String id) throws SQLException {
        Connection   conn = null;
        String URL ="jdbc:sqlserver://localhost\\MSSQLSERVER01:12345;database=QLNHANVIEN;integratedSecurity=true;";
        CallableStatement cs = null;
        conn = java.sql.DriverManager.getConnection(URL);
        cs = conn.prepareCall("{call GET_NAME(?,?)}");
        cs.setString(1, id);
        cs.registerOutParameter(2, Types.NVARCHAR);
        cs.execute();
        name = cs.getString(2);
        Name = new JLabel(name);
        cs = conn.prepareCall("{call GET_CLASS(?,?)}");
        cs.setString(1, id);
        cs.registerOutParameter(2, Types.NVARCHAR);
        cs.execute();
        Classs = cs.getString(2);
        Class= new JLabel(Classs);
       }

    UserFR(String id, int clas) throws IOException, SQLException {
        manv = id;
        loai = clas;
        name_classProcess(id);
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }
    public void setLayoutManager(){container.setLayout(null);}
    public void setLocationAndSize() throws IOException {
        Name.setFont(Name.getFont().deriveFont(20.0f));
        Name.setBounds(10,35,200,50);
        Class.setFont(Class.getFont().deriveFont(15.0f));
        Class.setBounds(10,55,200,50);
        ImageIcon icon = new ImageIcon("C:\\Users\\nkang\\IdeaProjects\\QLNhanVien\\src\\QLNhanVien\\icons8-analyzing-skill-96.png");
        ImageIcon icon2 = new ImageIcon("C:\\Users\\nkang\\IdeaProjects\\QLNhanVien\\src\\QLNhanVien\\icons8-to-do-96.png");
        ImageIcon icon3 = new ImageIcon("C:\\Users\\nkang\\IdeaProjects\\QLNhanVien\\src\\QLNhanVien\\baseline_supervised_user_circle_black_48dp.png");
        InfoButton.setSize(60,60);
        JLabel iconLabel = new JLabel(icon);
        JLabel iconLabel2 = new JLabel(icon2);
        JLabel iconLabel3 = new JLabel(icon3);
        Info.add(iconLabel);
        Info.add(InfoButton);
        Info.setBounds(35,170,175,135);
        Info.setBackground(Color.gray);
        WorkButton.setSize(60,60);
        Work.add(iconLabel2);
        Work.add(WorkButton);
        Work.setBounds(225,170,185,135);
        Work.setBackground(Color.gray);
        manage.add(iconLabel3);
        manage.add(manageButton);
        manage.setBounds(225,25,185,135);
        manage.setBackground(Color.gray);
        ChangePassword.setBounds(130,325,120,30);
        LogOut.setBounds(260,325,120,30);
        register.setBounds(130,360,120, 30);
    }
    public void addComponentsToContainer()
    {
        if(loai == 3)
        {
            container.add(manage);
            container.add(register);
        }
        container.add(Name);
        container.add(Class);
        container.add(Info);
        container.add(Work);
        container.add(ChangePassword);
        container.add(LogOut);
       }
    public void addActionEvent()
    {
        InfoButton.addActionListener(this);
        WorkButton.addActionListener(this);
        manageButton.addActionListener(this);
        LogOut.addActionListener(this);
        ChangePassword.addActionListener(this);
        register.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == InfoButton)
        {

            InfoFR info = new InfoFR(manv);
            info.setTitle("Thông tin cá nhân.");
            info.setVisible(true);
            info.setBounds(10,10,475,425);
            info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            info.setResizable(false);
            info.setLocationRelativeTo(null);
        }
        if(actionEvent.getSource()==WorkButton)
        {
            JobFR job = null;
            try {
                job = new JobFR(manv);
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
        if(actionEvent.getSource()==manageButton)
        {

            managerFR manager = null;
            try {
                manager = new managerFR(manv);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            manager.setTitle("Quản lý");
            manager.setVisible(true);
            manager.setBounds(10,10,500,470);
            manager.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            manager.setResizable(false);
            manager.setLocationRelativeTo(null);
        }
        if(actionEvent.getSource()==LogOut)
        {
            int i = JOptionPane.showConfirmDialog(null, "Would you like to Log out?","Exit", JOptionPane.YES_NO_OPTION);
            if(i == JOptionPane.YES_OPTION)
            {
                this.setVisible(false);
                this.dispose();
                loginFR lF = new loginFR();
                lF.setTitle("QUẢN LÝ NHÂN VIÊN");
                // lF.getContentPane().setBackground(Color.getHSBColor(600,300, 300));
                lF.setVisible(true);
                lF.setBounds(10,10,370,300);
                lF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                lF.setResizable(false);
                lF.setLocationRelativeTo(null);
            }
        }
        if(actionEvent.getSource() == ChangePassword)
        {
            changePassword change = new changePassword(manv);
            change.setTitle("Đổi mật khẩu");
            change.setVisible(true);
            change.setBounds(10,10, 325,300);
            change.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            change.setResizable(false);
            change.setLocationRelativeTo(null);
        }
        if(actionEvent.getSource() == register)
        {
            Register rs = new Register(manv);
            rs.setTitle("Đăng Kí ");
            rs.setVisible(true);
            rs.setBounds(10,10,400,550);
            rs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            rs.setResizable(false);
            rs.setLocationRelativeTo(null);
        }
    }
}
