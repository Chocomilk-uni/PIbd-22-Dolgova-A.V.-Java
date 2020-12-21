package TransportLogic;

import java.awt.*;
import java.util.Iterator;

public class DoubleBus extends Bus implements Iterator<Object>, Iterable<Object>, Comparable<Bus> {
    public Color additionalColor;
    public boolean hasSecondFloor;
    public boolean hasAdditionalDoor;
    public boolean hasFrontPlatform;

    //Закрытое поле от ИнтерДоп
    private AdditionalElems additionalElems;

    public Color getAdditionalColor() {
        return additionalColor;
    }

    public boolean isHasSecondFloor() {
        return hasSecondFloor;
    }

    public boolean isHasAdditionalDoor() {
        return hasAdditionalDoor;
    }

    public boolean isHasFrontPlatform() {
        return hasFrontPlatform;
    }

    public void setAdditionalColor(Color additionalColor) {
        this.additionalColor = additionalColor;
    }

    private void setHasSecondFloor(boolean hasSecondFloor) {
        this.hasSecondFloor = hasSecondFloor;
    }

    private void setHasAdditionalDoor(boolean hasAdditionalDoor) {
        this.hasAdditionalDoor = hasAdditionalDoor;
    }

    private void setHasFrontPlatform(boolean hasFrontPlatform) {
        this.hasFrontPlatform = hasFrontPlatform;
    }

    public void setAdditionalElems(AdditionalElems additionalElems) {
        this.additionalElems = additionalElems;
    }

    public DoubleBus(int averageSpeed, float weight, int seats, Color mainColor, Color additColor, boolean hasFrontPlatform, boolean hasAdditionalDoor, boolean hasSecondFloor) {
        super(averageSpeed, weight, seats, mainColor);
        this.additionalColor = additColor;
        listConfig.add(additColor);
        this.hasAdditionalDoor = hasAdditionalDoor;
        listConfig.add(hasAdditionalDoor);
        this.hasFrontPlatform = hasFrontPlatform;
        listConfig.add(hasFrontPlatform);
        this.hasSecondFloor = hasSecondFloor;
        listConfig.add(hasSecondFloor);
    }

    public DoubleBus(String info) {
        String[] args = info.split(separator);
        if (args.length == 9) {
            averageSpeed = Integer.parseInt(args[0]);
            listConfig.add(averageSpeed);
            weight = Float.parseFloat(args[1]);
            listConfig.add(weight);
            seats = Integer.parseInt(args[2]);
            listConfig.add(seats);
            mainColor = new Color(Integer.parseInt(args[3]));
            listConfig.add(mainColor);
            additionalColor = new Color(Integer.parseInt(args[4]));
            listConfig.add(additionalColor);
            hasAdditionalDoor = Boolean.parseBoolean(args[5]);
            listConfig.add(hasAdditionalDoor);
            hasFrontPlatform = Boolean.parseBoolean(args[6]);
            listConfig.add(hasFrontPlatform);
            hasSecondFloor = Boolean.parseBoolean(args[7]);
            listConfig.add(hasSecondFloor);
            if (args[8].contains("null")) {
                additionalElems = null;
                listConfig.add(additionalElems);
            } else {
                String[] argsAdditionalElems = args[8].split("\\.");
                int number = Integer.parseInt(argsAdditionalElems[1]);
                switch (argsAdditionalElems[0]) {
                    case "RectangleDoors":
                        additionalElems = new RectangleDoors(number);
                        break;
                    case "RoundedDoors":
                        additionalElems = new RoundedDoors(number);
                        break;
                    case "TriangularDoors":
                        additionalElems = new TriangularDoors(number);
                        break;
                }
                listConfig.add(argsAdditionalElems[0]);
                listConfig.add(number);
            }
        }
    }

    @Override
    public void drawTransport(Graphics g) {
        super.drawTransport(g);

        if (hasFrontPlatform) {
            g.setColor(Color.BLACK);
            g.drawRect(startPosX, startPosY + 55, 40, 15);
            g.setColor(Color.BLACK);
            g.fillOval(startPosX + 10, startPosY + 60, 22, 22);
            g.fillRect(startPosX + 5, startPosY + 35, 25, 15);
            g.setColor(mainColor);
            g.fillRect(startPosX, startPosY + 50, 40, 20);
        }

        if (hasSecondFloor) {
            g.drawRect(startPosX, startPosY + 5, busWidth + 5, 25);
            g.setColor(mainColor);
            g.fillRect(startPosX, startPosY + 5, busWidth + 5, 25);

            //Окна 2-го этажа
            g.setColor(Color.BLACK);
            g.fillRect(startPosX + 2, startPosY + 10, 20, 10);
            g.fillRect(startPosX + 32, startPosY + 10, 20, 10);
            g.fillRect(startPosX + 62, startPosY + 10, 20, 10);
            g.fillRect(startPosX + 92, startPosY + 10, 14, 10);

            //Полоса
            g.drawRect(startPosX, startPosY + 24, busWidth + 5, 3);
            g.setColor(additionalColor);
            g.fillRect(startPosX, startPosY + 24, busWidth + 5, 3);
        }

        if (hasAdditionalDoor) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(startPosX + 37, startPosY + 40, 15, 30);
            g.setColor(Color.WHITE);
            g.drawLine(startPosX + 44, startPosY + 40, startPosX + 44, startPosY + 70);
        }

        //Отрисовка дополнительных элементов (форма дверей)
        if (additionalElems != null) {
            additionalElems.draw(g, Color.DARK_GRAY, startPosX, startPosY);
        }
    }

    public String toString() {
        return averageSpeed + separator + weight + separator + seats + separator +
                mainColor.getRGB() + separator + additionalColor.getRGB() + separator +
                hasAdditionalDoor + separator + hasFrontPlatform + separator + hasSecondFloor +
                separator + additionalElems;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DoubleBus doubleBusObject)) {
            return false;
        }
        return equals(doubleBusObject);
    }

    public boolean equals(DoubleBus other) {
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
        if (additionalColor.getRGB() != other.additionalColor.getRGB()) {
            return false;
        }
        if (hasAdditionalDoor != other.hasAdditionalDoor) {
            return false;
        }
        if (hasFrontPlatform != other.hasFrontPlatform) {
            return false;
        }
        if (hasSecondFloor != other.hasSecondFloor) {
            return false;
        }
        if (additionalElems != null && other.additionalElems != null && !(additionalElems.toString().equals(other.additionalElems.toString()))) {
            return false;
        }
        if ((additionalElems == null && other.additionalElems != null) || (additionalElems != null && other.additionalElems == null)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Bus bus) {
        DoubleBus doubleBus = (DoubleBus) bus;
        if (additionalColor.getRGB() != doubleBus.additionalColor.getRGB()) {
            return Integer.compare(additionalColor.getRGB(), doubleBus.getAdditionalColor().getRGB());
        }
        if (hasAdditionalDoor != doubleBus.hasAdditionalDoor) {
            return Boolean.compare(hasAdditionalDoor, doubleBus.hasAdditionalDoor);
        }
        if (hasFrontPlatform != doubleBus.hasFrontPlatform) {
            return Boolean.compare(hasFrontPlatform, doubleBus.hasFrontPlatform);
        }
        if (hasSecondFloor != doubleBus.hasSecondFloor) {
            return Boolean.compare(hasSecondFloor, doubleBus.hasSecondFloor);
        }
        if (additionalElems == null && doubleBus.additionalElems != null) {
            return 1;
        }
        if (additionalElems != null && doubleBus.additionalElems == null) {
            return -1;
        }
        return 0;
    }

    public AdditionalElems getAdditionalElems() {
        return  additionalElems;
    }
}