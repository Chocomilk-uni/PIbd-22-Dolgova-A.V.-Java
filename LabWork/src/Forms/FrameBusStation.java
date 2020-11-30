package Forms;

import TransportLogic.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class FrameBusStation {
    //Коллекция (очередь), куда помещаются объекты, изымаемые с формы
    private final Queue<Bus> busQueue;
    private final JFrame frame;
    private final JTextField textFieldPlaceNumber;
    private final BusStationCollection busStationCollection;
    private final DefaultListModel<String> busStationList;
    private final JList<String> listBoxBusStation;
    private final JTextField textFieldBusStationName;
    private final PanelBusStation panelBusStation;

    public FrameBusStation() {
        busQueue = new LinkedList<>();

        frame = new JFrame("Автовокзал");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(null);

        busStationCollection = new BusStationCollection(700, 450);

        panelBusStation = new PanelBusStation(busStationCollection);
        JButton buttonParkBus = new JButton("Припарковать автобус");
        JButton buttonParkDoubleBus = new JButton("Припарковать  двухэтажный автобус");
        JLabel labelPlace = new JLabel("Место:");
        JButton buttonAddToQueue = new JButton("Добавить в очередь");
        JButton buttonGetFromQueue = new JButton("Получить из очереди");

        JPanel groupBoxPickBus = new JPanel();
        Border centerBorder = BorderFactory.createTitledBorder("Забрать автобус:");
        groupBoxPickBus.setBorder(centerBorder);
        textFieldPlaceNumber = new JFormattedTextField();

        JPanel groupBoxBusStation = new JPanel();
        centerBorder = BorderFactory.createTitledBorder("Автовокзалы");
        groupBoxBusStation.setBorder(centerBorder);
        JLabel labelBusStationName = new JLabel("Название автовокзала:");
        busStationList = new DefaultListModel<>();
        listBoxBusStation = new JList<>(busStationList);
        textFieldBusStationName = new JTextField();
        JButton buttonAddBusStation = new JButton("Добавить автовокзал");
        JButton buttonDeleteBusStatiom = new JButton("Удалить автовокзал");

        frame.getContentPane().add(buttonParkBus);
        frame.getContentPane().add(buttonParkDoubleBus);
        frame.getContentPane().add(panelBusStation);

        groupBoxPickBus.add(textFieldPlaceNumber);
        groupBoxPickBus.add(labelPlace);
        groupBoxPickBus.add(buttonAddToQueue);
        groupBoxPickBus.add(buttonGetFromQueue);
        frame.getContentPane().add(groupBoxPickBus);

        groupBoxBusStation.add(labelBusStationName);
        groupBoxBusStation.add(listBoxBusStation);
        groupBoxBusStation.add(textFieldBusStationName);
        groupBoxBusStation.add(buttonAddBusStation);
        groupBoxBusStation.add(buttonDeleteBusStatiom);
        frame.getContentPane().add(groupBoxBusStation);

        panelBusStation.setBounds(5, 5, 650, 450);
        buttonParkBus.setBounds(50, 420, 200, 30);
        buttonParkDoubleBus.setBounds(260, 420, 300, 30);

        groupBoxBusStation.setLayout(null);
        groupBoxBusStation.setBounds(670, 20, 200, 240);
        labelBusStationName.setBounds(10, 20, 180, 30);
        textFieldBusStationName.setBounds(10, 50, 180, 20);
        buttonAddBusStation.setBounds(10, 80, 180, 30);
        listBoxBusStation.setBounds(10, 120, 180, 60);
        buttonDeleteBusStatiom.setBounds(10, 190, 180, 30);

        groupBoxPickBus.setLayout(null);
        groupBoxPickBus.setBounds(670, 280, 200, 150);
        labelPlace.setBounds(10, 20, 50, 30);
        textFieldPlaceNumber.setBounds(70, 30, 30, 20);
        buttonAddToQueue.setBounds(10, 70, 180, 30);
        buttonGetFromQueue.setBounds(10, 110, 180, 30);

        buttonParkBus.addActionListener(e -> parkBus());
        buttonParkDoubleBus.addActionListener(e -> parkDoubleBus());
        buttonAddToQueue.addActionListener(e -> pickBus());
        buttonGetFromQueue.addActionListener(e -> getBus());
        buttonAddBusStation.addActionListener(e -> addBusStation());
        buttonDeleteBusStatiom.addActionListener(e -> deleteBusStation());
        listBoxBusStation.addListSelectionListener(e -> listListener());

        frame.repaint();
    }

    private void parkBus() {
        if (listBoxBusStation.getSelectedIndex() >= 0) {
            JColorChooser colorDialog = new JColorChooser();
            JOptionPane.showMessageDialog(frame, colorDialog);
            if (colorDialog.getColor() != null) {
                Bus bus = new Bus(1000, 5000, 40, colorDialog.getColor());
                if (busStationCollection.get(listBoxBusStation.getSelectedValue()).add(bus)) {
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "Автовокзал переполнен");
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Автовокзал не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void parkDoubleBus() {
        if (listBoxBusStation.getSelectedIndex() >= 0) {
            JColorChooser colorDialog = new JColorChooser();
            JOptionPane.showMessageDialog(frame, colorDialog);
            if (colorDialog.getColor() != null) {
                JColorChooser additionalColorDialog = new JColorChooser();
                JOptionPane.showMessageDialog(frame, additionalColorDialog);
                if (additionalColorDialog.getColor() != null) {
                    Bus bus = new DoubleBus(1000, 5000, 40, colorDialog.getColor(), additionalColorDialog.getColor(), true, false, true, 0, 0);
                    if (busStationCollection.get(listBoxBusStation.getSelectedValue()).add(bus)) {
                        frame.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Автовокзал переполнен");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Автовокзал не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pickBus() {
        if (listBoxBusStation.getSelectedIndex() >= 0) {
            if (!textFieldPlaceNumber.getText().equals("")) {
                try {
                    Bus bus = busStationCollection.get(listBoxBusStation.getSelectedValue()).remove(Integer.parseInt(textFieldPlaceNumber.getText()));
                    if (bus != null) {
                        /*
                        Автобусы, изымаемые с формы, помещаются в очередь
                         */
                        busQueue.add(bus);
                        frame.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Автобус с таким индексом отсутствует", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Автобус с таким индексом отсутствует", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Автовокзал не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getBus() {
        if (!busQueue.isEmpty()) {
            FrameBus frameBus = new FrameBus();
            /*
            Автобусы на вторую форму передаются из очереди
             */
            frameBus.initBus(Objects.requireNonNull(busQueue.poll()));
            frame.repaint();
        }
    }

    private void reloadLevels() {
        int index = listBoxBusStation.getSelectedIndex();
        busStationList.removeAllElements();

        int i = 0;
        for (String name : busStationCollection.keys()) {
            busStationList.add(i, name);
            i++;
        }

        int itemsCount = busStationList.size();
        if (itemsCount > 0 && (index < 0 || index >= itemsCount)) {
            listBoxBusStation.setSelectedIndex(0);
        }
        else if (index >= 0 && index < itemsCount) {
            listBoxBusStation.setSelectedIndex(index);
        }
    }

    private void addBusStation() {
        if (!textFieldBusStationName.getText().equals("")) {
            busStationCollection.addBusStation(textFieldBusStationName.getText());
            reloadLevels();
            frame.repaint();
        } else {
            JOptionPane.showMessageDialog(frame, "Введите название автовокзала", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBusStation() {
        if (listBoxBusStation.getSelectedIndex() >= 0) {
            int result = JOptionPane.showConfirmDialog(frame, "Удалить автовокзал " + listBoxBusStation.getSelectedValue() + "?", "Удаление",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                busStationCollection.deleteBusStation(listBoxBusStation.getSelectedValue());
                reloadLevels();
                frame.repaint();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Автовокзал не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listListener() {
        panelBusStation.setSelectedItem(listBoxBusStation.getSelectedValue());
        frame.repaint();
    }
}