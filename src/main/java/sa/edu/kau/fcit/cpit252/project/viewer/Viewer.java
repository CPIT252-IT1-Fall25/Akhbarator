package sa.edu.kau.fcit.cpit252.project.viewer;

import javax.swing.*;

public class Viewer {
    public void run() {
        JFrame frame = new JFrame("Akhbarator");

        JLabel label = new JLabel("Welcome user!");
        label.setBounds(10, 10, 200, 20);
        JButton button = new JButton("News button");
        button.setBounds(200, 200, 150, 200);

        frame.add(label);
        frame.add(button);

        frame.setSize(500, 600);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
