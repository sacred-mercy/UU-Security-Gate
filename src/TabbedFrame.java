import com.mysql.jdbc.Connection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TabbedFrame extends JDialog {
    private JPanel indexPanel;
    private JTabbedPane tabbedPane1;
    JTextField empIdInsert;
    JTextField empVehicleInsert;
    JButton insertEmpDataBtn;
    private JTextField empDataSearch;
    private JTable empDataSearchTable;
    private JTextField empIdHistorySorting;
    private JTextField empNameHistorySorting;
    private JTable EmpHistoryRecordsTable;
    private JPanel EmpDataInsert;
    private JPanel EmpDataSearch;
    private JPanel EmpHistoryRecordSearch;
    private JTextField visitorNameInsertTextField;
    private JTextField visitorVehicleInsertTextField;
    private JTextArea visitorReasonInsertTextarea;
    private JTextField visitorMobileInsertTextField;
    private JTextField visitorSearchNameTextField;
    private JTable visitorHistoryRecordTable;
    private JButton InsertVisitorDataBtn;
    private JTable completeHistoryTable;
    private JButton empDataSearchButton;
    private JButton clearButton;
    private JTextField vehicleSearchNoTextField;
    private JTextField vehicleSearchNameTextField;
    private JTextField vehicleSearchMobileNo;
    private JTextArea vehicleSearchReasonTextArea;
    private JButton vehicleSearchButton;

    public TabbedFrame(JFrame parent) {
        super(parent);
        setTitle("Employee Entry");
        setContentPane(indexPanel);
        setMinimumSize(new Dimension(1400, 800));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        showEmpDataTableData();
        showEmpHistoryRecordTableData();
        showVisitorHistoryRecordTableData();

        insertEmpDataBtn.addActionListener(e -> {
            try {
                Database db = new Database();
                Connection con = db.con;
                Validation validation = new Validation();
                String id = validation.validate(empIdInsert.getText());
                String vehicleNo = validation.validate(empVehicleInsert.getText());
                DateTimeFormatter dateObject = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeObject = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String date = dateObject.format(now);
                String time = timeObject.format(now);
                Statement stmt = con.createStatement();
                String query = "SELECT * FROM faculty_details WHERE faculty_id='"+id+"'";
                ResultSet empDataDb = stmt.executeQuery(query);
                if (empDataDb.next()){
                    String name = empDataDb.getString(2);
                    String department = empDataDb.getString(3);
                    query = "INSERT INTO faculty_record VALUES(?,?,?,?,?,?)" ;
                    String[] empInformation = new String[6];
                    empInformation[0] = id;
                    empInformation[1] = name;
                    empInformation[2] = department;
                    empInformation[3] = time;
                    empInformation[4] = date;
                    empInformation[5] = vehicleNo;
                    db.dataInsert(query,6,empInformation);
                    empIdInsert.setText(" ");
                    empVehicleInsert.setText(" ");
                    showEmpHistoryRecordTableData();
                    showEmpDataTableData();
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
                Validation validation = new Validation();
                String name = validation.validateTrim(visitorNameInsertTextField.getText());
                String vehicleNo = validation.validate(visitorVehicleInsertTextField.getText());
                String mobileNo = validation.validateTrim(visitorMobileInsertTextField.getText());
                String reason = validation.validateTrim(visitorReasonInsertTextarea.getText());
                if (name.isEmpty() || mobileNo.isEmpty() || reason.isEmpty()){
                    JOptionPane.showMessageDialog(TabbedFrame.this,
                            "Please fill all the required entries",
                            "Blank Entry",
                            JOptionPane.ERROR_MESSAGE);
                }
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
                visitorNameInsertTextField.setText("");
                visitorVehicleInsertTextField.setText("");
                visitorMobileInsertTextField.setText("");
                visitorReasonInsertTextarea.setText("");
                con.close();
            }catch (Exception error){
                JOptionPane.showMessageDialog(TabbedFrame.this,
                        "Please recheck all the entries",
                        "Invalid Entry",
                        JOptionPane.ERROR_MESSAGE);
            }
            showVisitorHistoryRecordTableData();
        });
        empDataSearchButton.addActionListener(e -> {
            try {
                Database db = new Database();
                Connection con = db.con;
                Validation validation = new Validation();
                String empName = validation.validateTrim(empDataSearch.getText());
                if (empName.isEmpty()){
                    JOptionPane.showMessageDialog(TabbedFrame.this,
                            "Please Enter a Employee Name to search",
                            "Employee Name Empty",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Statement statement = con.createStatement();
                String query = "SELECT * FROM faculty_details WHERE faculty_name LIKE '%"+empName+"%'";
                statement.executeQuery(query);
                ResultSet resultSet = statement.executeQuery(query);
                empDataSearchTable.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) empDataSearchTable.getModel();
                String[] colName = {"Id", "Name", "Department"};
                model.setColumnIdentifiers(colName);
                String id, name, department;
                while (resultSet.next()){
                    id = resultSet.getString(1);
                    name = resultSet.getString(2);
                    department = resultSet.getString(3);
                    String[] row = {id, name, department};
                    model.addRow(row);
                }
                statement.close();
                con.close();
            } catch (Exception error){
                System.out.println(error.getMessage());
            }
        });


        setVisible(true);
    }

    public void showVisitorHistoryRecordTableData() {
        try {
            Database db = new Database();
            Connection con = db.con;
            Statement statement = con.createStatement();
            String query = "SELECT * FROM visitor_records ORDER BY date DESC, time DESC";
            statement.executeQuery(query);
            ResultSet resultSet = statement.executeQuery(query);
            visitorHistoryRecordTable.setModel(new DefaultTableModel());
            DefaultTableModel model = (DefaultTableModel) visitorHistoryRecordTable.getModel();
            String[] colName = {"Name", "Mobile Number", "Reason", "Time", "Date", "Vehicle Number"};
            model.setColumnIdentifiers(colName);
            String reason, name, mobileNo, time, date, vehicleNo;
            while (resultSet.next()){
                name = resultSet.getString(1);
                mobileNo = resultSet.getString(2);
                vehicleNo = resultSet.getString(3);
                reason = resultSet.getString(4);
                time = resultSet.getString(5);
                date = resultSet.getString(6);
                String[] row = {name, mobileNo, reason, time, date, vehicleNo};
                model.addRow(row);
            }
            statement.close();
            con.close();
        } catch (Exception error){
            System.out.println(error.getMessage());
        }
    }

    public void showEmpHistoryRecordTableData() {
        try {
            Database db = new Database();
            Connection con = db.con;
            Statement statement = con.createStatement();
            String query = "SELECT * FROM faculty_record ORDER BY date DESC, time DESC";
            statement.executeQuery(query);
            ResultSet resultSet = statement.executeQuery(query);
            EmpHistoryRecordsTable.setModel(new DefaultTableModel());
            DefaultTableModel model = (DefaultTableModel) EmpHistoryRecordsTable.getModel();
            String[] colName = {"Id", "Name", "Department", "Time", "Date", "Vehicle Number"};
            model.setColumnIdentifiers(colName);
            String id, name, department, time, date, vehicleNo;
            while (resultSet.next()){
                id = resultSet.getString(1);
                name = resultSet.getString(2);
                department = resultSet.getString(3);
                time = resultSet.getString(4);
                date = resultSet.getString(5);
                vehicleNo = resultSet.getString(6);
                String[] row = {id, name, department, time, date, vehicleNo};
                model.addRow(row);
            }
            statement.close();
            con.close();
        } catch (Exception error){
            System.out.println(error.getMessage());
        }
    }

    private void showEmpDataTableData() {
        try {
            Database db = new Database();
            Connection con = db.con;
            Statement statement = con.createStatement();
            String query = "SELECT * FROM faculty_details";
            statement.executeQuery(query);
            ResultSet resultSet = statement.executeQuery(query);
            empDataSearchTable.setModel(new DefaultTableModel());
            DefaultTableModel model = (DefaultTableModel) empDataSearchTable.getModel();
            String[] colName = {"Id", "Name", "Department"};
            model.setColumnIdentifiers(colName);
            String id, name, department;
            while (resultSet.next()){
                id = resultSet.getString(1);
                name = resultSet.getString(2);
                department = resultSet.getString(3);
                String[] row = {id, name, department};
                model.addRow(row);
            }
            statement.close();
            con.close();
        } catch (Exception error){
            System.out.println(error.getMessage());
        }
    }
}