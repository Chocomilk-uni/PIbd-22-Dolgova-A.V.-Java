package Forms;

import TransportLogic.AdditionalElems;
import TransportLogic.Bus;
import TransportLogic.BusStation;
import TransportLogic.DoubleBus;

import javax.swing.*;
import javax.swing.border.Border;

public class FrameBusStation {

    private final JFrame frame;
    private final JTextField textFieldPlaceNumber;
    private final BusStation<Bus, AdditionalElems> busStation;

    public FrameBusStation() {
        frame = new JFrame("Автовокзал");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(null);

        busStation = new BusStation<>(700, 450);

        PanelBusStation panelBusStation = new PanelBusStation(busStation);
        JButton buttonParkBus = new JButton("Припарковать автобус");
        JButton buttonParkDoubleBus = new JButton("Припарковать  двухэтажный автобус");
        JButton buttonPickBus = new JButton("Забрать автобус");
        JLabel labelPlace = new JLabel("Место:");

        JPanel groupBoxPickBus = new JPanel();
        Border centerBorder = BorderFactory.createTitledBorder("Забрать автобус:");
        groupBoxPickBus.setBorder(centerBorder);
        textFieldPlaceNumber = new JFormattedTextField();

        frame.getContentPane().add(buttonParkBus);
        frame.getContentPane().add(buttonParkDoubleBus);
        frame.getContentPane().add(buttonPickBus);
        frame.getContentPane().add(panelBusStation);
        groupBoxPickBus.add(textFieldPlaceNumber);
        groupBoxPickBus.add(buttonPickBus);
        groupBoxPickBus.add(labelPlace);
        frame.getContentPane().add(groupBoxPickBus);

        panelBusStation.setBounds(5, 5, 650, 450);
        buttonParkBus.setBounds(50, 420, 200, 30);
        buttonParkDoubleBus.setBounds(260, 420, 300, 30);

        groupBoxPickBus.setLayout(null);
        groupBoxPickBus.setBounds(670, 20, 200, 130);
        labelPlace.setBounds(10, 20, 50, 30);
        textFieldPlaceNumber.setBounds(70, 30, 30, 20);
        buttonPickBus.setBounds(25, 70, 150, 30);

        buttonParkBus.addActionListener(e -> parkBus());
        buttonParkDoubleBus.addActionListener(e -> parkDoubleBus());
        buttonPickBus.addActionListener(e -> pickBus());

        frame.repaint();
    }

    private void parkBus() {
        JColorChooser colorDialog = new JColorChooser();
        JOptionPane.showMessageDialog(frame, colorDialog);
        if (colorDialog.getColor() != null) {
            Bus bus = new Bus(1000, 5000, 40, colorDialog.getColor());
            if (busStation.add(bus)) {
                frame.repaint();
            }
            else {
                JOptionPane.showMessageDialog(frame, "Автовокзал переполнен");
            }
        }
    }

    private void parkDoubleBus() {
        JColorChooser colorDialog = new JColorChooser();
        JOptionPane.showMessageDialog(frame, colorDialog);
        if (colorDialog.getColor() != null) {
            JColorChooser additionalColorDialog = new JColorChooser();
            JOptionPane.showMessageDialog(frame, additionalColorDialog);
            if (additionalColorDialog.getColor() != null) {
                Bus bus = new DoubleBus(1000, 5000, 40, colorDialog.getColor(), additionalColorDialog.getColor(), true, false, true, 0, 0);
                if (busStation.add(bus)) {
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "Автовокзал переполнен");
                }
            }
        }
    }

    private void pickBus() {
        if (!textFieldPlaceNumber.getText().equals("")) {
            try {
                Bus bus = busStation.remove(Integer.parseInt(textFieldPlaceNumber.getText()));
                if (bus != null) {
                    FrameBus frameBus = new FrameBus();
                    frameBus.initBus(bus);
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "На этом месте нет автобуса");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "На этом месте нет автобуса");
            }
        }
    }
}