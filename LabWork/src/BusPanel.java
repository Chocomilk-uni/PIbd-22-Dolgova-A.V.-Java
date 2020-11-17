import javax.swing.*;
import java.awt.*;

public class BusPanel extends JPanel {

    private PublicTransport bus;

    public void paintComponent(Graphics g) {
        if (bus != null)
            bus.drawTransport(g);
    }

    public void initBus(PublicTransport bus) {
        this.bus = bus;
    }

    public PublicTransport getBus() {
        return bus;
    }
}