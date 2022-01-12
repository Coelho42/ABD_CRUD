package com.escoteiros.abd.views;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.html.ImageView;

import com.escoteiros.abd.Main;

public class MainFrame extends JFrame {

    private JButton iniciarButton;
    private JPanel mainPanel;
    private JLabel icon;

    public MainFrame() {
        setContentPane(mainPanel);
        setTitle("Escoteiros");
        setSize(1024,768);
        icon.setIcon(new ImageIcon(getClass().getResource("/mainFrameBg.png")));
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
