import javax.swing.*;

public class Frame extends JFrame {
    Panel panel;

    public Frame() {
        this.setTitle("Maze Project");
        panel = new Panel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
