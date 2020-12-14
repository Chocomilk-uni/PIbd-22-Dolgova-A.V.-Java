package Forms;

import TransportLogic.*;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyException;
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

    private Logger logger;

    public FrameBusStation() {
        logger = LogManager.getLogger(FrameBusStation.class);
        PropertyConfigurator.configure("C:\\Users\\Chocomilk\\IdeaProjects\\PIbd-22-Dolgova-A.V.-Java\\LabWork\\src\\TransportLogic\\log4j2.properties");

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
        if (listBoxBusStation.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(frame, "Автовокзал не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            PanelBusConfig configPanel = new PanelBusConfig(frame);

            //Получаем автобус из панельки диалогового окна
            Bus bus = configPanel.getBus();

            if (bus == null) return;
            if (busStationCollection.get(listBoxBusStation.getSelectedValue()).add(bus)) {
                logger.info("На автовокзал " + listBoxBusStation.getSelectedValue() + " был добавлен автобус " + bus);
                frame.repaint();
            }
        } catch (BusStationOverflowException e) {
            JOptionPane.showMessageDialog(frame, "Автовокзал переполнен", "Ошибка", JOptionPane.ERROR_MESSAGE);
            logger.warn(e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Неизвестная ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            logger.fatal(e.getMessage());
        }
    }

    private void pickBus() {
        if (listBoxBusStation.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(frame, "Автовокзал не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (textFieldPlaceNumber.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "Перед изъятием автобуса необходимо ввести номер места", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Bus bus = busStationCollection.get(listBoxBusStation.getSelectedValue()).remove(Integer.parseInt(textFieldPlaceNumber.getText()));
            if (bus != null) {
                //Автобусы, изымаемые с формы, помещаются в очередь
                busQueue.add(bus);
                logger.info("С автовокзала " + listBoxBusStation.getSelectedValue() + " был изъят автобус " + bus + " и помещен в очередь");
                frame.repaint();
            }
        } catch (BusStationPlaceNotFoundException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Не найдено", JOptionPane.ERROR_MESSAGE);
            logger.warn(e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Неизвестная ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            logger.warn(e.getMessage());
        }
    }

    private void getBus() {
        if (!busQueue.isEmpty()) {
            FrameBus frameBus = new FrameBus();
            //Автобусы на вторую форму передаются из очереди
            Bus bus = busQueue.poll();
            frameBus.initBus(Objects.requireNonNull(bus));
            logger.info("Автобус " + bus + " был изъят из очереди");
            frame.repaint();
        } else {
            JOptionPane.showMessageDialog(frame, "Очередь пуста", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
        } else if (index >= 0 && index < itemsCount) {
            listBoxBusStation.setSelectedIndex(index);
        }
    }

    private void addBusStation() {
        if (!textFieldBusStationName.getText().equals("")) {
            busStationCollection.addBusStation(textFieldBusStationName.getText());
            reloadLevels();
            logger.info("Добавлен автовокзал " + textFieldBusStationName.getText());
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
                logger.info("Автовокзал " + listBoxBusStation.getSelectedValue() + " удалён");
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
        if (listBoxBusStation.getSelectedValue() != null) {
            logger.info("Выбран автовокзал " + listBoxBusStation.getSelectedValue());
        }
        frame.repaint();
    }

    private void save() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        int result = fileDialog.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                busStationCollection.saveData(fileDialog.getSelectedFile().getPath());
                JOptionPane.showMessageDialog(frame, "Сохранение прошло успешно");
                logger.info("Данные сохранены в файл " + fileDialog.getSelectedFile().getPath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Ошибка при работе с файлом", JOptionPane.ERROR_MESSAGE);
                logger.error(e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Неизвестная ошибка при сохранении", JOptionPane.ERROR_MESSAGE);
                logger.fatal(e.getMessage());
            }
        }

    }

    private void load() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        int result = fileDialog.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                busStationCollection.loadData(fileDialog.getSelectedFile().getPath());
                JOptionPane.showMessageDialog(frame, "Загрузка прошла успешно");
                reloadLevels();
                frame.repaint();
                logger.info("Данные были загружены из файла " + fileDialog.getSelectedFile().getPath());
            } catch (BusStationOverflowException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Переполнение автовокзала", JOptionPane.ERROR_MESSAGE);
                logger.warn(e.getMessage());
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Файл не найден", JOptionPane.ERROR_MESSAGE);
                logger.error(e.getMessage());
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Неверный формат файла", JOptionPane.ERROR_MESSAGE);
                logger.error(e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Неизвестная ошибка при загрузке", JOptionPane.ERROR_MESSAGE);
                logger.fatal(e.getMessage());
            }
        }
    }

    private void saveBusStation() {
        if (listBoxBusStation.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(frame, "Автовокзал не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        if (listBoxBusStation.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(frame, "Перед сохранением необходимо выбрать автовокзал");
            return;
        }
        int result = fileDialog.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                busStationCollection.saveBusStation(fileDialog.getSelectedFile().getPath(), listBoxBusStation.getSelectedValue());
                JOptionPane.showMessageDialog(frame, "Сохранение прошло успешно", "Результат", JOptionPane.INFORMATION_MESSAGE);
                logger.info("Автовокзал " + listBoxBusStation.getSelectedValue() + " был записан в файл " + fileDialog.getSelectedFile().getPath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Ошибка при работе с файлом", JOptionPane.ERROR_MESSAGE);
                logger.error(e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Неизвестная ошибка при сохранении", JOptionPane.ERROR_MESSAGE);
                logger.fatal(e.getMessage());
            }
        }
    }

    private void loadBusStation() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        int result = fileDialog.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                busStationCollection.loadBusStation(fileDialog.getSelectedFile().getPath());
                JOptionPane.showMessageDialog(frame, "Загрузка прошла успешно", "Результат", JOptionPane.INFORMATION_MESSAGE);
                reloadLevels();
                frame.repaint();
                logger.info("Из файла " + fileDialog.getSelectedFile().getPath() + " был загружен автовокзал");
            } catch (BusStationOverflowException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Переполнение автовокзала", JOptionPane.ERROR_MESSAGE);
                logger.warn(e.getMessage());
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Файл не найден", JOptionPane.ERROR_MESSAGE);
                logger.error(e.getMessage());
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Неверный формат файла", JOptionPane.ERROR_MESSAGE);
                logger.error(e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Неизвестная ошибка при загрузке", JOptionPane.ERROR_MESSAGE);
                logger.fatal(e.getMessage());
            }
        }
    }
}