import javax.swing.*;

public class test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.setContentPane(new test().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel indexPanel;
    private JTextField textField3;
    private JTextField textField4;
    private JTable employeeRecords;
    private JTextField tfEmployeeId;
    private JTextField tfEmployeeVehicle;
    private JButton enterButton;
    private JTable employeeData;
    private JTextField textField1;
}
