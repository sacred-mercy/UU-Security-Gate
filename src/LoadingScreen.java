import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JDialog implements Runnable {
    private JPanel panel1;
    private JProgressBar progressBar1;

    public LoadingScreen(JFrame parent) {
        super(parent);
        setTitle("Loading...");
        setContentPane(panel1);
        setMinimumSize(new Dimension(1400, 800));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        progressBar1.setValue(0);
        progressBar1.setStringPainted(true);
        setVisible(true);
    }
    public void iterate(){
        int i = 0;
        while(i<=100){
            progressBar1.setValue(i);
            i++;
            try{Thread.sleep(10);}catch(Exception e){}
        }
    }

    @Override
    public void run() {
        iterate();
        TabbedFrame index = new TabbedFrame(null);
        index.getAccessibleContext();

        for (int i = 0; i<=2; i++) {
            if (i == 2) {
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
