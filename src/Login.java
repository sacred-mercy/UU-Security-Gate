import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
public class Login extends JDialog {

    private JPanel loginMain;
    private JTextField tfusername;
    private JPasswordField tfpassword;
    private JButton loginBtn;
    private JLabel error;

    public Login(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(loginMain);
        setMinimumSize(new Dimension(1400, 800));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    final String dbURL = "jdbc:mysql://localhost:3306/uu_security_system";
                    final String dbUsername = "root";
                    final String dbPassword = "";
                    Connection con= DriverManager.getConnection(dbURL, dbUsername,dbPassword);
                    String username = tfusername.getText();
                    String password = String.valueOf(tfpassword.getPassword());
                    Statement stmt = con.createStatement();
                    String sql = "SELECT * FROM admin WHERE username='"+username+"' AND password='"+password+"'";
                    ResultSet resultSet = stmt.executeQuery(sql);
                    if(resultSet.next()){
                        dispose(); //close login page if logged in
                        Index index = new Index(null);
                        index.getAccessibleContext();
                    }else {
                        JOptionPane.showMessageDialog(Login.this,
                                "username and password are invalid",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                        tfusername.setText("");
                        tfpassword.setText("");
                        con.close();
                    }
                }catch (Exception error){
                    System.out.println(error.getMessage());
                }
            }
        });
        setVisible(true);

        loginBtn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }
        });
    }





}
class Main{
    public static void main(String[] args) {
        Login form = new Login(null);

    }
}
