import javax.swing.*;
import java.awt.*;

public class Login extends JDialog {

    private JPanel loginMain;
    private JTextField username;
    private JPasswordField password;
    private JButton loginBtn;
    private JLabel error;

    public Login(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(loginMain);
        setMinimumSize(new Dimension(900, 648));
        setModal(true);
        setLocationRelativeTo(parent);
        setVisible(true);
    }


    public static void main(String[] args) {
        Login form = new Login(null);
    }
}
