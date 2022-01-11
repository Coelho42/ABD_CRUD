package views;

import db.*;
import models.*;

import javax.swing.*;
import java.util.Locale;

public class MainFrame extends JFrame {

    private JButton iniciarButton;
    private JPanel mainPanel;

    public MainFrame() {
        setContentPane(mainPanel);
        setTitle("Escoteiros");
        setSize(1024,768);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        iniciarButton.addActionListener(e -> {
            MenuFrame menuFrame = new MenuFrame();
            dispose();
        });
    }
}
