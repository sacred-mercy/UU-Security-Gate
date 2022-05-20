import com.mysql.jdbc.Connection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TabbedFrame extends JDialog {
    //Swing components in GUI

    JTextField empIdInsert;
    JTextField empVehicleInsert;
    JButton insertEmpDataBtn;
    private JPanel indexPanel;
    private JTextField empDataSearch;
    private JTable empDataSearchTable;
    private JTextField empIdHistorySorting;
    private JTable EmpHistoryRecordsTable;
    private JTextField visitorNameInsertTextField;
    private JTextField visitorVehicleInsertTextField;
    private JTextField visitorMobileInsertTextField;
    private JTextField visitorSearchNameTextField;
    private JTable visitorHistoryRecordTable;
    private JButton InsertVisitorDataBtn;
    private JButton empDataSearchButton;
    private JButton empDataClearButton;
    private JTextField visitorReasonInsertTextField;
    private JButton empHistorySortingButton;
    private JButton empHistorySortingClearButton;
    private JButton insertEmpDataClearButton;
    private JButton visitorRecordSearchButton;
    private JButton visitorRecordClearButton;
    private JButton empExportCSVBtn;
    private JButton visitorExportToCSVButton;
    private JPanel Employee;
    private JPanel Visitor;

    public TabbedFrame(JFrame parent) {
        //Creating a view
        super(parent);
        setTitle("UU Gate Entry Manager");
        setContentPane(indexPanel);
        setMinimumSize(new Dimension(1400, 800));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        requestFocusInWindow();


        //Button to insert Employee Entry Data into Database
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
                String query = "SELECT * FROM faculty_details WHERE faculty_id='" + id + "'";
                ResultSet empDataDb = stmt.executeQuery(query);
                if (empDataDb.next()) {
                    String name = empDataDb.getString(2);
                    String department = empDataDb.getString(3);
                    query = "INSERT INTO faculty_record VALUES(?,?,?,?,?,?)";
                    String[] empInformation = new String[6];
                    empInformation[0] = id;
                    empInformation[1] = name;
                    empInformation[2] = department;
                    empInformation[3] = time;
                    empInformation[4] = date;
                    empInformation[5] = vehicleNo;
                    db.dataInsert(query, empInformation);
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
                empIdInsert.requestFocusInWindow();
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
        });

        //Button to insert Visitor Entry Data into Database
        InsertVisitorDataBtn.addActionListener(e -> {
            try {
                Database db = new Database();
                Connection con = db.con;
                Validation validation = new Validation();
                String name = validation.validateTrim(visitorNameInsertTextField.getText());
                String vehicleNo = validation.validate(visitorVehicleInsertTextField.getText());
                String mobileNo = validation.validateTrim(visitorMobileInsertTextField.getText());
                String reason = validation.validateTrim(visitorReasonInsertTextField.getText());
                if (name.isEmpty() || mobileNo.isEmpty() || reason.isEmpty()) {
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
                String query = "INSERT INTO visitor_records VALUES(?,?,?,?,?,?)";
                String[] visitorInformation = new String[6];
                visitorInformation[0] = name;
                visitorInformation[1] = mobileNo;
                visitorInformation[2] = vehicleNo;
                visitorInformation[3] = reason;
                visitorInformation[4] = time;
                visitorInformation[5] = date;
                db.dataInsert(query, visitorInformation);
                visitorNameInsertTextField.setText("");
                visitorVehicleInsertTextField.setText("");
                visitorMobileInsertTextField.setText("");
                visitorReasonInsertTextField.setText("");
                con.close();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(TabbedFrame.this,
                        "Please recheck all the entries",
                        "Invalid Entry",
                        JOptionPane.ERROR_MESSAGE);
            }
            showVisitorHistoryRecordTableData();
            visitorNameInsertTextField.requestFocusInWindow();
        });

        //Button to Fetch Searched employee data from Database
        empDataSearchButton.addActionListener(e -> {
            try {
                Database db = new Database();
                Connection con = db.con;
                Validation validation = new Validation();
                String empName = validation.validateTrim(empDataSearch.getText());
                if (empName.isEmpty()) {
                    JOptionPane.showMessageDialog(TabbedFrame.this,
                            "Please Enter a Employee Name to search",
                            "Employee Name Empty",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Statement statement = con.createStatement();
                String query = "SELECT * FROM faculty_details WHERE faculty_name LIKE '%" + empName + "%'";
                statement.executeQuery(query);
                ResultSet resultSet = statement.executeQuery(query);
                empDataSearchTable.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) empDataSearchTable.getModel();
                String[] colName = {"Id", "Name", "Department"};
                model.setColumnIdentifiers(colName);
                String id, name, department;
                while (resultSet.next()) {
                    id = resultSet.getString(1);
                    name = resultSet.getString(2);
                    department = resultSet.getString(3);
                    String[] row = {id, name, department};
                    model.addRow(row);
                }
                statement.close();
                con.close();
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
        });

        // Button to clear Searched data from Table in which employee data is displayed
        empDataClearButton.addActionListener(e -> {
            showEmpDataTableData();
            empDataSearch.setText("");
        });

        // Button to clear Searched data from Table in which employee's records are displayed
        empHistorySortingClearButton.addActionListener(e -> {
            showEmpHistoryRecordTableData();
            empIdHistorySorting.setText("");
        });

        // Button to search employee Records with name or ID
        empHistorySortingButton.addActionListener(e -> {
            try {
                Database db = new Database();
                Connection con = db.con;
                Validation validation = new Validation();
                String emp = validation.validateTrim(empIdHistorySorting.getText());
                if (emp.isEmpty()) {
                    JOptionPane.showMessageDialog(TabbedFrame.this,
                            "Please Enter an Employee Name or ID to search",
                            "Employee Name/Id Empty",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Statement statement = con.createStatement();
                String query = "SELECT * FROM faculty_record WHERE name LIKE '%" + emp + "%' OR id LIKE '%" + emp + "%' ORDER BY date DESC, time DESC";
                statement.executeQuery(query);
                ResultSet resultSet = statement.executeQuery(query);
                EmpHistoryRecordsTable.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) EmpHistoryRecordsTable.getModel();
                String[] colName = {"Id", "Name", "Department", "Time", "Date", "Vehicle Number"};
                model.setColumnIdentifiers(colName);
                String id, name, department, time, date, vehicleNo;
                while (resultSet.next()) {
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
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
        });

        // Button to clear inserted text of employee entry inserting textField
        insertEmpDataClearButton.addActionListener(e -> {
            empIdInsert.setText("");
            empVehicleInsert.setText("");
        });

        // Search visitor by name or vehicle in table
        visitorRecordSearchButton.addActionListener(e -> {
            try {
                Database db = new Database();
                Connection con = db.con;
                Validation validation = new Validation();
                String visitor = validation.validateTrim(visitorSearchNameTextField.getText());
                if (visitor.isEmpty()) {
                    JOptionPane.showMessageDialog(TabbedFrame.this,
                            "Please Enter visitor Name or Vehicle Number to search",
                            "Visitor Name/Vehicle No. Empty",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Statement statement = con.createStatement();
                String query = "SELECT * FROM visitor_records WHERE name LIKE '%" + visitor + "%' OR vehicleNo LIKE '%" + visitor + "%' ORDER BY date DESC, time DESC";
                statement.executeQuery(query);
                ResultSet resultSet = statement.executeQuery(query);
                visitorHistoryRecordTable.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) visitorHistoryRecordTable.getModel();
                String[] colName = {"Name", "Mobile Number", "Reason", "Time", "Date", "Vehicle Number"};
                model.setColumnIdentifiers(colName);
                String reason, name, mobileNo, time, date, vehicleNo;
                while (resultSet.next()) {
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
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
        });

        // Button to clear searched data in visitor record table
        visitorRecordClearButton.addActionListener(e -> {
            showVisitorHistoryRecordTableData();
            visitorSearchNameTextField.setText("");
        });

        // to add focus for text field to enter key
        empIdInsert.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                getRootPane().setDefaultButton(insertEmpDataBtn);
            }
        });
        empVehicleInsert.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                getRootPane().setDefaultButton(insertEmpDataBtn);
            }
        });
        visitorReasonInsertTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                getRootPane().setDefaultButton(InsertVisitorDataBtn);
            }
        });
        empDataSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                getRootPane().setDefaultButton(empDataSearchButton);
            }
        });
        empIdHistorySorting.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                getRootPane().setDefaultButton(empHistorySortingButton);
            }
        });
        visitorSearchNameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                getRootPane().setDefaultButton(visitorRecordSearchButton);
            }
        });

        // Button to export data from table to excel document
        empExportCSVBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            int option = fc.showSaveDialog(TabbedFrame.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                String filename = fc.getSelectedFile().getName();
                String path = fc.getSelectedFile().getParentFile().getPath();
                int len = filename.length();
                String ext = "";
                String file;
                if (len > 4) {
                    ext = filename.substring(len - 4, len);
                }
                if (ext.equals(".xls")) {
                    file = path + "\\" + filename;
                } else {
                    file = path + "\\" + filename + ".xls";
                }
                toExcel(EmpHistoryRecordsTable, new File(file));
            }
        });
        visitorExportToCSVButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            int option = fc.showSaveDialog(TabbedFrame.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                String filename = fc.getSelectedFile().getName();
                String path = fc.getSelectedFile().getParentFile().getPath();
                int len = filename.length();
                String ext = "";
                String file;
                if (len > 4) {
                    ext = filename.substring(len - 4, len);
                }
                if (ext.equals(".xls")) {
                    file = path + "\\" + filename;
                } else {
                    file = path + "\\" + filename + ".xls";
                }
                toExcel(visitorHistoryRecordTable, new File(file));
            }
        });

//        setVisible(true);

        // functions call for all the tables used
        showEmpDataTableData();
        showEmpHistoryRecordTableData();
        showVisitorHistoryRecordTableData();
    }

    //function to display visitor entry record in Table
    public void showVisitorHistoryRecordTableData() {
        try {
            Database db = new Database();
            Connection con = db.con;
            Statement statement = con.createStatement();
            String query = "SELECT * FROM visitor_records ORDER BY date DESC, time DESC LIMIT 501";
            statement.executeQuery(query);
            ResultSet resultSet = statement.executeQuery(query);
            visitorHistoryRecordTable.setModel(new DefaultTableModel());
            DefaultTableModel model = (DefaultTableModel) visitorHistoryRecordTable.getModel();
            String[] colName = {"Name", "Mobile Number", "Reason", "Time", "Date", "Vehicle Number"};
            model.setColumnIdentifiers(colName);
            String reason, name, mobileNo, time, date, vehicleNo;
            while (resultSet.next()) {
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
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    //function to display employee entry record in Table
    public void showEmpHistoryRecordTableData() {
        try {
            Database db = new Database();
            Connection con = db.con;
            Statement statement = con.createStatement();
            String query = "SELECT * FROM faculty_record ORDER BY date DESC, time DESC LIMIT 501";
            statement.executeQuery(query);
            ResultSet resultSet = statement.executeQuery(query);
            EmpHistoryRecordsTable.setModel(new DefaultTableModel());
            DefaultTableModel model = (DefaultTableModel) EmpHistoryRecordsTable.getModel();
            String[] colName = {"Id", "Name", "Department", "Time", "Date", "Vehicle Number"};
            model.setColumnIdentifiers(colName);
            String id, name, department, time, date, vehicleNo;
            while (resultSet.next()) {
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
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    //function to display Employee Search Data in Table
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
            while (resultSet.next()) {
                id = resultSet.getString(1);
                name = resultSet.getString(2);
                department = resultSet.getString(3);
                String[] row = {id, name, department};
                model.addRow(row);
            }
            statement.close();
            con.close();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    // function to convert JTable to Excel document
    public void toExcel(JTable table, File file) {
        try {
            TableModel model = table.getModel();
            FileWriter excel = new FileWriter(file);
            for (int i = 0; i < model.getColumnCount(); i++) {
                excel.write(model.getColumnName(i) + "\t");
            }
            excel.write("\n");
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    excel.write(model.getValueAt(i, j).toString() + "\t");
                }
                excel.write("\n");
            }
            excel.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}