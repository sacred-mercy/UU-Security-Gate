import com.mysql.jdbc.Connection;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TabbedFrame extends JDialog {
    private JPanel indexPanel;
    private JTabbedPane tabbedPane1;
    private JTextField employeeIdInsert;
    private JTextField employeeVehicleInsert;
    private JButton insertEmployeeDataBtn;
    private JTextField employeeDataSearch;
    private JTable employeeDataSearchTable;
    private JTextField employeeIdHistorySorting;
    private JTextField employeeNameHistorySorting;
    private JTable EmployeeHistoryRecordsTable;
    private JPanel EmployeeDataInsert;
    private JPanel EmployeeDataSearch;
    private JPanel EmployeeHistoryRecordSearch;
    private JTextField visitorNameInsertTextField;
    private JTextField visitorVehicleInsertTextField;
    private JTextArea visitorReasonInsertTextarea;
    private JTextField visitorMobileInsertTextField;
    private JTextField visitorSearchNameTextField;
    private JTable visitorHistoryRecordTable;
    private JButton InsertVisitorDataBtn;
    private JTable table1;

    public TabbedFrame(JFrame parent) {
        super(parent);
        setTitle("Employee Entry");
        setContentPane(indexPanel);
        setMinimumSize(new Dimension(1400, 800));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        showEmployeeDataTableData();
        showEmployeeHistoryRecordTableData();

        insertEmployeeDataBtn.addActionListener(e -> {
            try {
                Database db = new Database();
                Connection con = db.con;
                String id = employeeIdInsert.getText();
                String vehicleNo = employeeVehicleInsert.getText();
                DateTimeFormatter dateObject = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeObject = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String date = dateObject.format(now);
                String time = timeObject.format(now);
                Statement stmt = con.createStatement();
                String query = "SELECT * FROM faculty_details WHERE faculty_id='"+id+"'";
                ResultSet employeeDataDb = stmt.executeQuery(query);
                if (employeeDataDb.next()){
                    String name = employeeDataDb.getString(2);
                    String department = employeeDataDb.getString(3);
                    query = "INSERT INTO faculty_record VALUES(?,?,?,?,?,?)" ;
                    String[] employeeInformation = new String[6];
                    employeeInformation[0] = id;
                    employeeInformation[1] = name;
                    employeeInformation[2] = department;
                    employeeInformation[3] = time;
                    employeeInformation[4] = date;
                    employeeInformation[5] = vehicleNo;
                    db.dataInsert(query,6,employeeInformation);
                    employeeIdInsert.setText(" ");
                    employeeVehicleInsert.setText(" ");
                    con.close();
                } else {
                    JOptionPane.showMessageDialog(TabbedFrame.this,
                            "Invalid Employee ID",
                            "Try Again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }catch (Exception error){
                System.out.println(error.getMessage());
            }

        });
        InsertVisitorDataBtn.addActionListener(e -> {
            try {
                Database db = new Database();
                Connection con = db.con;
                String name = visitorNameInsertTextField.getText();
                String vehicleNo = visitorVehicleInsertTextField.getText();
                String mobileNo = visitorMobileInsertTextField.getText();
                String reason = visitorReasonInsertTextarea.getText();
                DateTimeFormatter dateObject = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeObject = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String date = dateObject.format(now);
                String time = timeObject.format(now);
                String query = "INSERT INTO visitor_records VALUES(?,?,?,?,?,?)" ;
                String[] visitorInformation = new String[6];
                visitorInformation[0] = name;
                visitorInformation[1] = mobileNo;
                visitorInformation[2] = vehicleNo;
                visitorInformation[3] = reason;
                visitorInformation[4] = time;
                visitorInformation[5] = date;
                db.dataInsert(query,6,visitorInformation);
                con.close();
            }catch (Exception error){
                System.out.println(error.getMessage());
            }
        });
        setVisible(true);
    }

    private void showEmployeeHistoryRecordTableData() {

    }

    private void showEmployeeDataTableData() {
    }
}
