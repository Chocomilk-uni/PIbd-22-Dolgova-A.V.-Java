package TransportLogic;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Bus extends PublicTransport implements Iterator<Object>, Iterable<Object>, Comparable<Bus> {
    //Размеры автобуса
    protected int busHeight = 60;
    protected int busWidth = 100;
    protected double changeHeight = 1.4;

    protected String separator = ";";

    protected LinkedList<Object> listConfig = new LinkedList<>();
    protected int currentIndex = -1;

    public Bus(int averageSpeed, float weight, int seats, Color mainColor) {
        this.averageSpeed = averageSpeed;
        this.weight = weight;
        this.seats = seats;
        this.mainColor = mainColor;
    }

    protected Bus(int averageSpeed, float weight, int seats, Color mainColor, int busHeight, int busWidth, double changeHeight) {
        this.averageSpeed = averageSpeed;
        listConfig.add(averageSpeed);
        this.weight = weight;
        listConfig.add(weight);
        this.seats = seats;
        listConfig.add(seats);
        this.mainColor = mainColor;
        listConfig.add(mainColor);
        this.busHeight = busHeight;
        this.busWidth = busWidth;
        this.changeHeight = changeHeight;
    }

    public Bus(String info) {
        String[] args = info.split(separator);
        if (args.length == 4) {
            averageSpeed = Integer.parseInt(args[0]);
            listConfig.add(averageSpeed);
            weight = Float.parseFloat(args[1]);
            listConfig.add(weight);
            seats = Integer.parseInt(args[2]);
            listConfig.add(seats);
            mainColor = new Color(Integer.parseInt(args[3]));
            listConfig.add(mainColor);
        }
    }

    public Bus() {
    }

    @Override
    public void moveTransport(Direction direction) {
        float step = averageSpeed * 100 / weight;
        switch (direction) {
            case Right:
                if (startPosX + step < pictureWidth - busWidth) {
                    startPosX += step;
                }
                break;
            case Left:
                if (startPosX - step > 0) {
                    startPosX -= step;
                }
                break;
            case Up:
                if (startPosY - step > 0) {
                    startPosY -= step;
                }
                break;
            case Down:
                if (startPosY + step < pictureHeight - changeHeight * busHeight) {
                    startPosY += step;
                }
                break;
        }
    }

    @Override
    public void drawTransport(Graphics g) {
        //Колёса
        g.setColor(Color.BLACK);
        g.fillOval(startPosX + 10, startPosY + 60, 22, 22);
        g.fillOval(startPosX + 65, startPosY + 60, 22, 22);

        //Основной кузов
        g.setColor(Color.BLACK);
        g.drawRect(startPosX + 5, startPosY + 30, busWidth, 40);
        g.setColor(mainColor);
        g.fillRect(startPosX + 5, startPosY + 30, busWidth, 40);

        //Окна
        g.setColor(Color.BLACK);
        g.fillRect(startPosX + 12, startPosY + 35, 20, 15);
        g.fillRect(startPosX + 62, startPosY + 35, 20, 15);

        //Дверь
        g.setColor(Color.DARK_GRAY);
        g.fillRect(startPosX + 91, startPosY + 40, 15, 30);
        g.setColor(Color.WHITE);
        g.drawLine(startPosX + 98, startPosY + 40, startPosX + 98, startPosY + 70);
    }

    public String toString() {
        return averageSpeed + separator + weight + separator + seats + separator + mainColor.getRGB();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Bus busObject)) {
            return false;
        }
        return equals(busObject);
    }

    public boolean equals(Bus other) {
        if (other == null) {
            return false;
        }
        if (!this.getClass().getSimpleName().equals(other.getClass().getSimpleName())) {
            return false;
        }
        if (averageSpeed != other.averageSpeed) {
            return false;
        }
        if (weight != other.weight) {
            return false;
        }
        if (seats != other.seats) {
            return false;
        }
        if (mainColor.getRGB() != other.mainColor.getRGB()) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Bus bus) {
        if (averageSpeed != bus.averageSpeed) {
            return Integer.compare(averageSpeed, bus.averageSpeed);
        }
        if (weight != bus.weight) {
            return Float.compare(weight, bus.weight);
        }
        if (seats != bus.seats) {
            return Integer.compare(seats, bus.seats);
        }
        if (mainColor.getRGB() != bus.mainColor.getRGB()) {
            return Integer.compare(mainColor.getRGB(), bus.getMainColor().getRGB());
        }
        return 0;
    }

    @Override
    public Iterator<Object> iterator() {
        currentIndex = -1;
        return listConfig.iterator();
    }

    @Override
    public boolean hasNext() {
        return (currentIndex + 1 < listConfig.size());
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        currentIndex++;
        return listConfig.get(currentIndex);
    }
}