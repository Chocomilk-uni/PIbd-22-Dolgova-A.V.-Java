import javax.swing.*;
import java.awt.*;

public class BusPanel extends JPanel {

    private Bus doubleBus;

    public void paintComponent(Graphics g) {
        if (doubleBus != null)
            doubleBus.drawTransport(g);
    }

    public void initBus(Bus doubleBus) {
        this.doubleBus = doubleBus;
    }

    public Bus getBus() {
        return doubleBus;
    }
}