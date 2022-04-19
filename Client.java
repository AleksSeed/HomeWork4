
    private final JTextArea log = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("80");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Поверх окон");
    private final JTextField tfLogin = new JTextField("Aleks_Seed");
    private final JPasswordField tfPassword = new JPasswordField("123456");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("Разъеденить");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Отправить");
    private final JList<String> userList = new JList<>();

    private Log log1 = new Log("log.txt"); // Структура записи в логе


    private Client() throws IOException {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //посреди экрана
        setSize(WIDTH, HEIGHT);
        setTitle("Клиент чата");
        log.setEditable(false);
        JScrollPane spLog = new JScrollPane(log);
        JScrollPane spUsers = new JScrollPane(userList);
        String[] users = {"user1", "user2",
                "user3", "user4", "user5", "user6",
                "user7", "user8", "user9",
                "user10_with_a_exceptionally_long_nickname",};
        userList.setListData(users);
        spUsers.setPreferredSize(new Dimension(100, 0));
        cbAlwaysOnTop.addActionListener(this);

        btnSend.addActionListener(this);
        tfMessage.addKeyListener(this);

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);  //Edit
        panelBottom.add(btnSend, BorderLayout.EAST); //Button Send

        add(panelBottom, BorderLayout.SOUTH); // кнопка "разъеденить"
        add(panelTop, BorderLayout.NORTH);
        add(spLog, BorderLayout.CENTER);
        add(spUsers, BorderLayout.EAST);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Client();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend) {
            sendMess();
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


    /**
     * -1. Сообщение должно отсылаться либо по нажатию кнопки на форме, либо по нажатию кнопки Enter.
     * -При «отсылке» сообщение перекидывается из нижнего поля в центральное.
     * -2. Отправлять сообщения в лог по нажатию кнопки или по нажатию клавиши Enter.
     * Создать лог в файле (показать комментарием, где и как Вы планируете писать сообщение в файловый журнал).
     */
//----------------------------------------------------------------------изменения--------------------------------------
    private void sendMess() {        //Пишем в консоль, то что отправили
        String msg = tfMessage.getText();
        if (msg.isEmpty() || msg.isEmpty()) return;
        String user = userList.getSelectedValue();
        if (user == null) return;
        if (sendMessage(msg, user)) logUpdate(String.format("Пользователь %s : %s ", user, msg));

        tfMessage.setText("");
    }

    private boolean sendMessage(String msg, String user) {       //Отправка сообщения
        System.out.printf("Сообщение от пользователя %s: %s \n", user, msg);
        return true;
    }

    private String logUpdate(String msg) {     //добавление строки в log
        log.append(msg + "\n");
        Log();
        return msg;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Object src = e.getSource();
        if (src == tfMessage && e.getKeyCode() == 10) {
            sendMess();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

                 /**     Запись в файловый журнал     */
    private void Log() {
        String lineSeparator = System.getProperty("line.separator");
        String msg = tfMessage.getText();
        log1.append(msg + " \n");
        tfMessage.setText(" ");
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write(" \n" + lineSeparator);
            writer.flush();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



}





