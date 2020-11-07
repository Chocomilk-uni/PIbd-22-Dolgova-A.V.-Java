package TransportLogic;

import java.awt.*;

public class RoundedDoors implements AdditionalElems {
    //Закрытое поле от перечисления TransportLogic.NumberOfDoors
    private NumberOfDoors doorsNumber;

    public RoundedDoors(int number) {
        setNumber(number);
    }

    //Открытое числовое свойство, через к-рое можно в закрытое поле занести значение
    @Override
    public void setNumber(int number) {
        this.doorsNumber = NumberOfDoors.getNumber(number);
    }

    @Override
    public void draw(Graphics g, Color color, Color additionalColor, int posX, int posY) {
        g.setColor(color);
        g.fillOval(posX + 91, posY + 35, 15, 15);
        g.fillRect(posX + 28, posY + 40, 15, 30);
        g.fillRect(posX + 48, posY + 40, 15, 30);
        g.fillOval(posX + 28, posY + 35, 15, 15);
        g.fillOval(posX + 48, posY + 35, 15, 15);
        g.setColor(additionalColor);
        g.drawLine(posX + 98, posY + 37, posX + 98, posY + 50);
        g.drawLine(posX + 35, posY + 37, posX + 35, posY + 70);
        g.drawLine(posX + 55, posY + 37, posX + 55, posY + 70);

        if (doorsNumber == NumberOfDoors.four || doorsNumber == NumberOfDoors.five) {
            g.setColor(color);
            g.fillRect(posX + 8, posY + 40, 15, 30);
            g.fillOval(posX + 8, posY + 35, 15, 15);
            g.setColor(additionalColor);
            g.drawLine(posX + 15, posY + 37, posX + 15, posY + 70);
            if (doorsNumber == NumberOfDoors.five) {
                g.setColor(color);
                g.fillRect(posX + 68, posY + 40, 15, 30);
                g.fillOval(posX + 68, posY + 35, 15, 15);
                g.setColor(additionalColor);
                g.drawLine(posX + 75, posY + 37, posX + 75, posY + 70);
            }
        }
    }
}