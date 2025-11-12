package sa.edu.kau.fcit.cpit252.project.viewer;

import javax.swing.*;
import java.awt.*;

public class Viewer {
    public void run() {
        JFrame frame = new JFrame("Akhbarator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 300);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.black);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new java.awt.Insets(15, 15, 15, 15); // Padding
        JLabel label = new JLabel("Welcome user!");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        panel.add(label, gbc);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton button = new JButton("News");
        button.setPreferredSize(new Dimension(180, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(button, gbc);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
