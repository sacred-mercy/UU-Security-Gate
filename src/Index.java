import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Index extends JDialog {
    private JPanel indexPanel;
    private JTextField tfEmployeeId;
    private JTextField tfEmployeeVehicle;
    private JButton enterButton;
    private JTextField textField3;
    private JTextField textField4;
    private JTable employeeRecords;
    private JTable employeeDataTable;
    private JTextField textField1;

    public Index(JFrame parent){
        super(parent);
        setTitle("Employee Entry");
        setContentPane(indexPanel);
        setMinimumSize(new Dimension(1400, 800));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    final String dbURL = "jdbc:mysql://localhost:3306/uu_security_system";
                    final String dbUsername = "root";
                    final String dbPassword = "";
                    Connection con= DriverManager.getConnection(dbURL, dbUsername,dbPassword);
                    String id = tfEmployeeId.getText();
                    String vehicleNo = tfEmployeeVehicle.getText();
                    DateTimeFormatter dateObject = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    DateTimeFormatter timeObject = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String date = dateObject.format(now);
                    String time = timeObject.format(now);
                    Statement stmt = con.createStatement();
                    ResultSet employeeInformation = stmt.executeQuery("SELECT * FROM uu_security_system.faculty_details WHERE faculty_id='"+id+"'");
                    String name = employeeInformation.getString(2);
                    String department = employeeInformation.getString(3);
                    System.out.println(name);
                    System.out.println(department);
                    System.out.println(id);
                    System.out.println(time);
                    System.out.println(date );
                    System.out.println(vehicleNo);
                    String query = "INSERT INTO uu_security_system.faculty_record ('id', 'name', 'department', 'time', 'date','vehicleNo')" +
                            "VALUES ('"+id+"', '"+name+"', '"+department+"', '"+time+"','"+date+"','"+vehicleNo+"')";
                    ResultSet resultSet = stmt.executeQuery(query);
                    if(resultSet.next()){
                    }else {
                        JOptionPane.showMessageDialog(Index.this,
                                "Employee ID does not exist",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                        con.close();
                    }
                }catch (Exception error){
                    System.out.println(error.getMessage());
                }
            }
        });


        employeeDataTable.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    final String dbURL = "jdbc:mysql://localhost:3306/uu_security_system";
                    final String dbUsername = "root";
                    final String dbPassword = "";
                    Connection con= DriverManager.getConnection(dbURL, dbUsername,dbPassword);
                    String id = tfEmployeeId.getText();
                    String vehicleNo = tfEmployeeVehicle.getText();
                    DateTimeFormatter dateObject = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    DateTimeFormatter timeObject = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String date = dateObject.format(now);
                    String time = timeObject.format(now);
                    Statement stmt = con.createStatement();
                    String query = "SELECT * FROM mini-project.faculty_details";
                    ResultSet rs = stmt.executeQuery(query);
                    ResultSetMetaData rsmd = rs.getMetaData();
                    DefaultTableModel model = (DefaultTableModel) employeeDataTable.getModel();
                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i=0; i<cols; i++)
                        colName[i] = rsmd.getColumnName(i+1);
                    model.setColumnIdentifiers(colName);

                }catch (Exception error){
                    System.out.println(error.getMessage());
                }
            }
        });
        setVisible(true);

        employeeDataTable.addContainerListener(new ContainerAdapter() {
        });
    }
}