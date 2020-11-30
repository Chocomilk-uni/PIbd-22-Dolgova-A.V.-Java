package Forms;

import TransportLogic.Bus;
import TransportLogic.Direction;
import TransportLogic.DoubleBus;
import TransportLogic.Transport;

import javax.swing.*;
import java.awt.*;

public class FrameBus {
    private final JFrame frame;
    private PanelBus panelBus;
    private final JComboBox<String> listNumberOfDoors;
    private final JComboBox<String> listAdditionalElems;

    public void initBus(Transport bus) {
        bus.setPosition((int)(Math.random() * 100 + 10), (int) (Math.random() * (150 - 130) + 130), frame.getWidth(), frame.getHeight());
        panelBus.initBus(bus);
        frame.repaint();
    }

    public FrameBus() {
        frame = new JFrame("Автобус");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        JButton buttonCreateBus = new JButton("Создать автобус");
        JButton buttonCreateDoubleBus = new JButton("Создать двухэтажный автобус");
        JButton buttonUp = new JButton(new ImageIcon(getClass().getResource("Pics/up.png")));
        buttonUp.setName("buttonUp");
        JButton buttonDown = new JButton(new ImageIcon(getClass().getResource("Pics/down.png")));
        buttonDown.setName("buttonDown");
        JButton buttonLeft = new JButton(new ImageIcon(getClass().getResource("Pics/left.png")));
        buttonLeft.setName("buttonLeft");
        JButton buttonRight = new JButton(new ImageIcon(getClass().getResource("Pics/right.png")));
        buttonRight.setName("buttonRight");

        frame.getContentPane().add(buttonCreateBus);
        frame.getContentPane().add(buttonCreateDoubleBus);
        frame.getContentPane().add(buttonUp);
        frame.getContentPane().add(buttonDown);
        frame.getContentPane().add(buttonLeft);
        frame.getContentPane().add(buttonRight);

        buttonCreateBus.setBounds(240, 12, 250, 33);
        buttonCreateDoubleBus.setBounds(12, 12, 220, 33);
        buttonUp.setBounds(797, 383, 30, 30);
        buttonDown.setBounds(797, 419, 30, 30);
        buttonLeft.setBounds(761, 419, 30, 30);
        buttonRight.setBounds(833, 419, 30, 30);

        buttonCreateBus.addActionListener(e -> initBus());
        buttonCreateDoubleBus.addActionListener(e -> initDoubleBus());
        buttonUp.addActionListener(e -> buttonMove_Click(buttonUp));
        buttonDown.addActionListener(e -> buttonMove_Click(buttonDown));
        buttonLeft.addActionListener(e -> buttonMove_Click(buttonLeft));
        buttonRight.addActionListener(e -> buttonMove_Click(buttonRight));

        listNumberOfDoors = new JComboBox<>(new String[]{"3 двери", "4 двери", "5 дверей"});
        frame.getContentPane().add(listNumberOfDoors);
        listNumberOfDoors.setBounds(12, 45, 150, 30);

        listAdditionalElems = new JComboBox<>(new String[]{"Прямоугольная форма", "Треугольная форма", "Загруглённая форма"});
        frame.getContentPane().add(listAdditionalElems);
        listAdditionalElems.setBounds(12, 75, 150, 30);

        panelBus = new PanelBus();
        frame.getContentPane().add(panelBus);
        panelBus.setBounds(0, 0, 900, 500);
        frame.repaint();
    }

    private void buttonMove_Click(JButton button) {
        String name = button.getName();
        switch (name) {
            case "upButton":
                panelBus.getBus().moveTransport(Direction.Up);
                break;
            case "downButton":
                panelBus.getBus().moveTransport(Direction.Down);
                break;
            case "rightButton":
                panelBus.getBus().moveTransport(Direction.Right);
                break;
            case "leftButton":
                panelBus.getBus().moveTransport(Direction.Left);
                break;
        }
        frame.repaint();
    }

    //Скорость не очень правдоподобная, чтобы передвижение было более заметно (изначально стояла от 40 до 50)
    private void initBus() {
        panelBus.initBus(new Bus((int) (Math.random() * (1500 - 1000) + 1000), (float) (Math.random() * (8500 - 5500) + 5500), (int) (Math.random() * (40 - 20) + 20), Color.RED));
        panelBus.getBus().setPosition((int) (Math.random() * 100 + 10), (int) (Math.random() * (150 - 130) + 130), 850, 450);
        frame.repaint();
    }

    private void initDoubleBus() {
        panelBus.initBus(new DoubleBus((int) (Math.random() * (1500 - 1000) + 1000), (float) (Math.random() * (8500 - 5500) + 5500), (int) (Math.random() * (40 - 20) + 20), Color.RED, Color.WHITE,true, false, true));
        panelBus.getBus().setPosition((int) (Math.random() * 100 + 10), (int) (Math.random() * (150 - 130) + 130), 850, 450);
        frame.repaint();
    }
}