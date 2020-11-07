package TransportLogic;

import java.awt.*;

public class DoubleBus extends Bus{
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

    private void setAdditionalColor(Color additionalColor) {
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

    public DoubleBus(int averageSpeed, float weight, int seats, Color mainColor, Color additColor, boolean hasFrontPlatform, boolean hasAdditionalDoor, boolean hasSecondFloor, int number, int addition) {
        super(averageSpeed, weight, seats, mainColor);
        this.additionalColor = additColor;
        this.hasAdditionalDoor = hasAdditionalDoor;
        this.hasFrontPlatform = hasFrontPlatform;
        this.hasSecondFloor = hasSecondFloor;
        //Инициализация поля от ИнтерДоп
        switch (addition) {
            case 0:
                additionalElems = new RectangleDoors(number);
                break;
            case 1:
                additionalElems = new TriangularDoors(number);
                break;
            case 2:
                additionalElems = new RoundedDoors(number);
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
            g.fillRect(startPosX + 2,startPosY + 10, 20, 10);
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
        additionalElems.draw(g, Color.DARK_GRAY, getAdditionalColor(), startPosX, startPosY);
    }
}