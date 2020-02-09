package QLNhanVien;
import javax.swing.*;


public class Main {
    public static void main(String []args)
    {
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
