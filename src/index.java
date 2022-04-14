import javax.swing.*;
import java.awt.*;

public class index extends JDialog {
    private JPanel indexPannel;
    private JTextField facaultyId;
    private JTextField vechileNo;
    private JTextField textField3;
    private JButton submitBtn;
    private JTextField facaultyName;
    private JTextField textField5;
    private JButton showAllButton;
    private JTable entryRecord;
    
    public index(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(indexPannel);
        setMinimumSize(new Dimension(1200, 648));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
