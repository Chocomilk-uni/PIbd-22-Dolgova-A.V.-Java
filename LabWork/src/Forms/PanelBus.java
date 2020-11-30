package Forms;

import TransportLogic.Transport;

import javax.swing.*;
import java.awt.*;

public class PanelBus extends JPanel {

    private Transport bus;

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (bus != null) bus.drawTransport(g2);
    }

    public void initBus(Transport bus) {
        this.bus = bus;
    }

    public Transport getBus() {
        return bus;
    }
}