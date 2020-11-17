package Forms;

import TransportLogic.Transport;

import javax.swing.*;
import java.awt.*;

public class PanelBus extends JPanel {

    private Transport bus;

    public void paintComponent(Graphics g) {
        if (bus != null)
            bus.drawTransport(g);
    }

    public void initBus(Transport bus) {
        this.bus = bus;
    }

    public Transport getBus() {
        return bus;
    }
}