import javax.swing.*;
import java.awt.*;

public class BusFrame {
    private final JFrame frame;
    private BusPanel busPanel;
    private final JComboBox<String> list;

    public BusFrame() {
        frame = new JFrame("Автобус");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        JButton createButton = new JButton("Создать");
        JButton upButton = new JButton(new ImageIcon(getClass().getResource("Pics/up.png")));
        upButton.setName("upButton");
        JButton downButton = new JButton(new ImageIcon(getClass().getResource("Pics/down.png")));
        downButton.setName("downButton");
        JButton leftButton = new JButton(new ImageIcon(getClass().getResource("Pics/left.png")));
        leftButton.setName("leftButton");
        JButton rightButton = new JButton(new ImageIcon(getClass().getResource("Pics/right.png")));
        rightButton.setName("rightButton");

        frame.getContentPane().add(createButton);
        frame.getContentPane().add(upButton);
        frame.getContentPane().add(downButton);
        frame.getContentPane().add(leftButton);
        frame.getContentPane().add(rightButton);

        createButton.setBounds(12, 12, 85, 23);
        upButton.setBounds(797, 383, 30, 30);
        downButton.setBounds(797, 419, 30, 30);
        leftButton.setBounds(761, 419, 30, 30);
        rightButton.setBounds(833, 419, 30, 30);

        createButton.addActionListener(e -> initBus());
        upButton.addActionListener(e -> buttonMove_Click(upButton));
        downButton.addActionListener(e -> buttonMove_Click(downButton));
        leftButton.addActionListener(e -> buttonMove_Click(leftButton));
        rightButton.addActionListener(e -> buttonMove_Click(rightButton));

        list = new JComboBox<>(new String[]{"3 двери", "4 двери", "5 дверей"});
        frame.getContentPane().add(list);
        list.setBounds(12, 45, 90, 30);
    }

    public void addBusPanel(BusPanel panel) {
        busPanel = panel;
        frame.getContentPane().add(busPanel);
        busPanel.setBounds(0, 0, 900, 500);
        frame.repaint();
    }

    private void buttonMove_Click(JButton button) {
        String name = button.getName();
        switch (name) {
            case "upButton":
                busPanel.getBus().moveTransport(Direction.Up);
                break;
            case "downButton":
                busPanel.getBus().moveTransport(Direction.Down);
                break;
            case "rightButton":
                busPanel.getBus().moveTransport(Direction.Right);
                break;
            case "leftButton":
                busPanel.getBus().moveTransport(Direction.Left);
                break;
        }
        frame.repaint();
    }

    //Скорость не очень правдоподобная, чтобы передвижение было более заметно (изначально стояла от 40 до 50)
    private void initBus() {
        busPanel.initBus(new Bus((int) (Math.random() * (1500 - 1000) + 1000), (float) (Math.random() * (8500 - 5500) + 5500), (int) (Math.random() * (40 - 20) + 20), Color.RED, Color.WHITE,true, false, true, list.getSelectedIndex() + 3));
        busPanel.getBus().setPosition((int) (Math.random() * 100 + 10), (int) (Math.random() * (100 - 70) + 70), 850, 450);
        frame.repaint();
    }
}