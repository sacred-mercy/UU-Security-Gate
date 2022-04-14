import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setMinimumSize(new Dimension(900, 648));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con= DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/mini_project","root","");
                    String username = tfusername.getText();
                    String password = String.valueOf(tfpassword.getPassword());
                    Statement stmt = con.createStatement();
                    String sql = "SELECT * FROM admin WHERE email='"+username+"' AND password='"+password+"'";
                    ResultSet resultSet = stmt.executeQuery(sql);
                    if(resultSet.next()){
                        dispose(); //close login page if logged in
                        index indexPage = new index(null);
                        indexPage.show();
                    }else {
                        JOptionPane.showMessageDialog(null, "username and password are invalid");
//                        username.setText();
//                        password.setText();
                        con.close();
                    }
                }catch (Exception error){
                    System.out.println(error.getMessage());
                }
            }
        });
    }





}
class Main{
    public static void main(String[] args) {
        Login form = new Login(null);

    }
}

//class MysqlCon{
//    public static void main(String[] args){
//        try{
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con= DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/mini_project","root","");
//            Statement stmt=con.createStatement();
//            ResultSet rs=stmt.executeQuery("SELECT * FROM admin");
//            while(rs.next())
//                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
//            con.close();
//        }catch(Exception e){ System.out.println(e);}
//    }
//}
