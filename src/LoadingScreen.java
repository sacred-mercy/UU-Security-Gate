import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JDialog implements Runnable {
    private JPanel panel1;

    public LoadingScreen(JFrame parent) {
        super(parent);
        setTitle("Loading...");
        setContentPane(panel1);
        setMinimumSize(new Dimension(1400, 800));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void run() {
            TabbedFrame index = new TabbedFrame(null);
            index.getAccessibleContext();
        for (int i = 0; i<=4; i++) {
            if (i == 4) {
                dispose();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }
}
