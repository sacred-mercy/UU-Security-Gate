import java.awt.event.*;
import javax.swing.*;

public class TestKeys {
    private static void createAndShowGUI() {
        final JFrame frame = new JFrame("Keys");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.ALT_MASK);

        Action testAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Alt-H pressed");
            }
        };

        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "TestAction");
        frame.getRootPane().getActionMap().put("TestAction", testAction);

        JLabel label = new JLabel("Hit Alt-H");
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        frame.add(label);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}