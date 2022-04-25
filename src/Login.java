import com.mysql.jdbc.Connection;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends JDialog {
    private JPanel loginMain;
    private JTextField textFieldUsername;
    private JPasswordField textFieldPassword;
    private JButton loginBtn;

    public Login(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginMain);
        setMinimumSize(new Dimension(550, 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        loginBtn.addActionListener(e -> {
            try {
                Database db = new Database();
                Connection con = db.con;
                String username = textFieldUsername.getText();
                String password = String.valueOf(textFieldPassword.getPassword());
                Statement stmt = con.createStatement();
                String query = "SELECT * FROM admin WHERE username='" + username + "' AND password='" + password + "'";
                ResultSet resultSet = stmt.executeQuery(query);
                if (resultSet.next()) {
                    dispose();
                    TabbedFrame index = new TabbedFrame(null);
                    index.getAccessibleContext();
                } else {
                    JOptionPane.showMessageDialog(Login.this,
                            "username and password are invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    textFieldUsername.setText("");
                    textFieldPassword.setText("");
                    con.close();
                }
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
        });
        setVisible(true);
    }
}

