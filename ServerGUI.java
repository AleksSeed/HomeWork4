package ru.geekbrains.HomeWork4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {
    private static final int POS_X = 100;
    private static final int POS_Y = 100;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;

    private final ChatServer server = new ChatServer();
    private final JButton btnStart = new JButton("Старт");
    private final JButton btnStop = new JButton("Стоп");

    private ServerGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Чат сервер");
        setAlwaysOnTop(true);
        setLayout(new GridLayout(1, 2));
        btnStart.addActionListener(this);
        btnStop.addActionListener(this);
        add(btnStart);
        add(btnStop);
        setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("main started");//основной запущен
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerGUI();
            }
        });
        System.out.println("main ended");//основной остановлен
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnStart) {
            server.start(80);
        } else if (src == btnStop) {
            server.stop();
        } else {
            throw new RuntimeException("Действие для неосуществленного компонента");
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg = "Исключение в потоке " + t.getName() +
                " " + e.getClass().getCanonicalName() +
                ": " + e.getMessage() +
                "\n\t" + e.getStackTrace()[0];
        JOptionPane.showMessageDialog(null, msg,
                "Исключение", JOptionPane.ERROR_MESSAGE);
    }
}
