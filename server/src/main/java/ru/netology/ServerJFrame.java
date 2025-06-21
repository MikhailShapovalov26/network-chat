package ru.netology;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerJFrame {


   private JFrame frame = new JFrame("Server chat");

    public void startJFrameServer() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        // кнопка старта
        JPanel panel = new JPanel();
        JButton button = new JButton("Старт сервера");
        panel.add(button);
        Image icon = new javax.swing.ImageIcon("chatLabel.png").getImage();

        JTextArea jTextArea = new JTextArea();
        Color backgroundColor = new Color(240, 240, 240);
        Color textColor = new Color(50, 50, 50);
        Font textFont = new Font("Arial", Font.PLAIN, 14);
        jTextArea.setEditable(false);
        jTextArea.setBackground(backgroundColor);
        jTextArea.setForeground(textColor);
        jTextArea.setFont(textFont);
        JScrollPane scrollPane = new JScrollPane(jTextArea);




        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Button Clicked!");
            }
        });


        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel,BorderLayout.CENTER);
        frame.setIconImage(icon);
        frame.setVisible(true);
    }
}
