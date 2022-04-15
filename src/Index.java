import javax.swing.*;
import java.awt.*;

public class Index extends JDialog {
    private JPanel indexPannel;
    private JTextField textField1;
    private JTextField textField2;
    private JTable table1;
    private JButton enterButton;
    private JTextField textField3;
    private JTextField textField4;
    private JTable table2;

    public Index(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(indexPannel);
        setMinimumSize(new Dimension(1400, 800));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}

class IndexMain{
    public static void main(String[] args) {
        Index show = new Index(null);

    }
}
