package Forms;

import TransportLogic.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
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

        frame = new JFrame("Автовокзалы");
        frame.setSize(900, 530);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);

        busStationCollection = new BusStationCollection(700, 450);

        panelBusStation = new PanelBusStation(busStationCollection);
        JButton buttonParkBus = new JButton("Припарковать автобус");
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
        buttonParkBus.setBounds(670, 265, 200, 40);

        groupBoxBusStation.setLayout(null);
        groupBoxBusStation.setBounds(670, 10, 200, 240);
        labelBusStationName.setBounds(10, 20, 180, 30);
        textFieldBusStationName.setBounds(10, 50, 180, 20);
        buttonAddBusStation.setBounds(10, 80, 180, 30);
        listBoxBusStation.setBounds(10, 120, 180, 60);
        buttonDeleteBusStatiom.setBounds(10, 190, 180, 30);

        groupBoxPickBus.setLayout(null);
        groupBoxPickBus.setBounds(670, 320, 200, 140);
        labelPlace.setBounds(10, 20, 50, 30);
        textFieldPlaceNumber.setBounds(70, 30, 30, 20);
        buttonAddToQueue.setBounds(10, 60, 180, 30);
        buttonGetFromQueue.setBounds(10, 100, 180, 30);

        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);
        JMenu menuFile = new JMenu("Файл");
        JMenuItem fileItemSave = new JMenuItem("Сохранить");
        JMenuItem fileItemLoad = new JMenuItem("Загрузить");
        JMenu menuBusStation = new JMenu("Автовокзал");
        JMenuItem busStationItemSave = new JMenuItem("Сохранить автовокзал");
        JMenuItem busStationItemLoad = new JMenuItem("Загрузить автовокзал");
        menuFile.add(fileItemSave);
        menuFile.add(fileItemLoad);
        menuBusStation.add(busStationItemSave);
        menuBusStation.add(busStationItemLoad);
        menu.add(menuFile);
        menu.add(menuBusStation);

        buttonParkBus.addActionListener(e -> parkBus());
        buttonAddToQueue.addActionListener(e -> pickBus());
        buttonGetFromQueue.addActionListener(e -> getBus());
        buttonAddBusStation.addActionListener(e -> addBusStation());
        buttonDeleteBusStatiom.addActionListener(e -> deleteBusStation());
        listBoxBusStation.addListSelectionListener(e -> listListener());
        fileItemSave.addActionListener(e -> save());
        fileItemLoad.addActionListener(e -> load());
        busStationItemSave.addActionListener(e -> saveBusStation());
        busStationItemLoad.addActionListener(e -> loadBusStation());

        frame.repaint();
    }

    private void parkBus() {
        if (listBoxBusStation.getSelectedIndex() >= 0) {
            PanelBusConfig configPanel = new PanelBusConfig(frame);

            //Получаем автобус из панельки диалогового окна
            Bus bus = configPanel.getBus();

            if (bus == null) return;
            if (busStationCollection.get(listBoxBusStation.getSelectedValue()).add(bus)) {
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Автовокзал переполнен");
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
                        //Автобусы, изымаемые с формы, помещаются в очередь
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
            //Автобусы на вторую форму передаются из очереди
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

    private void save() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        int result = fileDialog.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            if (busStationCollection.saveData(fileDialog.getSelectedFile().getPath())) {
                JOptionPane.showMessageDialog(frame, "Сохранение прошло успешно");
            }
            else {
                JOptionPane.showMessageDialog(frame, "Не удалось сохранить");
            }
        }

    }

    private void load() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        int result = fileDialog.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            if (busStationCollection.loadData(fileDialog.getSelectedFile().getPath())) {
                JOptionPane.showMessageDialog(frame, "Загрузка прошла успешно");
                reloadLevels();
                frame.repaint();
            }
            else {
                JOptionPane.showMessageDialog(frame,"Не удалось загрузить файл");
            }
        }
    }

    private void saveBusStation() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        if (listBoxBusStation.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(frame, "Перед сохранением необходимо выбрать автовокзал");
            return;
        }
        int result = fileDialog.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            if (busStationCollection.saveBusStation(fileDialog.getSelectedFile().getPath(), listBoxBusStation.getSelectedValue())) {
                JOptionPane.showMessageDialog(frame, "Сохранение прошло успешно", "Результат", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Не удалось сохранить", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadBusStation() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        int result = fileDialog.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            if (busStationCollection.loadBusStation(fileDialog.getSelectedFile().getPath())) {
                JOptionPane.showMessageDialog(frame, "Загрузка прошла успешно", "Результат", JOptionPane.INFORMATION_MESSAGE);
                reloadLevels();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Не удалось загрузить файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}