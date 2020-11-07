package Forms;

import TransportLogic.AdditionalElems;
import TransportLogic.Bus;
import TransportLogic.BusStation;

import javax.swing.*;
import java.awt.*;

public class PanelBusStation extends JPanel {

    private final BusStation<Bus, AdditionalElems> busStation;

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (busStation != null) {
            busStation.drawBusStation(g2);
        }
    }

    public BusStation<Bus, AdditionalElems> getBusStation() {
        return busStation;
    }

    public PanelBusStation(BusStation<Bus, AdditionalElems> busStation) {
        this.busStation = busStation;
    }
}