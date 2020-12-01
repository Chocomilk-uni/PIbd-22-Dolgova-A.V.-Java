package TransportLogic;

import java.awt.*;

public class RectangleDoors implements AdditionalElems {
    //Закрытое поле от перечисления TransportLogic.NumberOfDoors
    private NumberOfDoors doorsNumber;

    public RectangleDoors(int number) {
        setNumber(number);
    }

    //Открытое числовое свойство, через к-рое можно в закрытое поле занести значение
    @Override
    public void setNumber(int number) {
        this.doorsNumber = NumberOfDoors.getNumber(number);
    }

    @Override
    public void draw(Graphics g, Color color, int posX, int posY) {
        g.setColor(color);
        g.fillRect(posX + 91, posY + 35, 15, 5);
        g.fillRect(posX + 28, posY + 35, 15, 35);
        g.fillRect(posX + 48, posY + 35, 15, 35);
        g.setColor(Color.white);
        g.drawLine(posX + 98, posY + 35, posX + 98, posY + 40);
        g.drawLine(posX + 35, posY + 35, posX + 35, posY + 70);
        g.drawLine(posX + 55, posY + 35, posX + 55, posY + 70);

        if (doorsNumber == NumberOfDoors.four || doorsNumber == NumberOfDoors.five) {
            g.setColor(color);
            g.fillRect(posX + 8, posY + 35, 15, 35);
            g.setColor(Color.white);
            g.drawLine(posX + 15, posY + 35, posX + 15, posY + 70);
            if (doorsNumber == NumberOfDoors.five) {
                g.setColor(color);
                g.fillRect(posX + 68, posY + 35, 15, 35);
                g.setColor(Color.white);
                g.drawLine(posX + 75, posY + 35, posX + 75, posY + 70);
            }
        }
    }

    public String toString() {
        return this.getClass().getSimpleName() + '.' + doorsNumber.ordinal();
    }
}