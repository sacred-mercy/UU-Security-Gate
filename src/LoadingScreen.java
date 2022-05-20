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
//        progressBar1.setStringPainted(true);
        setVisible(true);
    }
    public void iterate(){
        int i = 35;
        while(i<=100){
            progressBar1.setValue(i);
            i++;
            try{Thread.sleep(3);}catch(Exception e){}
        }
    }

    @Override
    public void run() {
        progressBar1.setValue(5);
        progressBar1.setValue(10);
        try{Thread.sleep(20);}catch(Exception e){}
        progressBar1.setValue(20);
        progressBar1.setValue(25);
        try{Thread.sleep(20);}catch(Exception e){}
        progressBar1.setValue(30);
        progressBar1.setValue(35);
        TabbedFrame index = new TabbedFrame(null);
        iterate();
        index.getAccessibleContext();
        index.setVisible(true);

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
