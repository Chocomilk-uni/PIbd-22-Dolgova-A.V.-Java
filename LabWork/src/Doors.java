import java.awt.*;

public class Doors {
    //Закрытое поле от перечисления NumberOfDoors
    private NumberOfDoors doorsNumber;

    //Открытое числовое свойство, через к-рое можно в закрытое поле занести значение
    public void setDoorsNumber(int number) {
        this.doorsNumber = NumberOfDoors.getNumber(number);
    }

    public Doors(int number) {
        setDoorsNumber(number);
    }

    public void drawDoors(Graphics g, Color color, Color additionalColor, int posX, int posY) {
        if (doorsNumber != null) {
            switch (doorsNumber) {
                case three:
                    g.setColor(color);
                    g.fillRect(posX + 18, posY + 35, 15, 35);
                    g.fillRect(posX + 40, posY + 35, 15, 35);
                    g.setColor(additionalColor);
                    g.drawLine(posX + 25, posY + 35, posX + 25, posY + 70);
                    g.drawLine(posX + 47, posY + 35, posX + 47, posY + 70);
                    break;
                case four:
                    g.setColor(color);
                    g.fillRect(posX + 18, posY + 35, 15, 35);
                    g.fillRect(posX + 38, posY + 35, 15, 35);
                    g.fillRect(posX + 58, posY + 35, 15, 35);
                    g.setColor(additionalColor);
                    g.drawLine(posX + 25, posY + 35, posX + 25, posY + 70);
                    g.drawLine(posX + 45, posY + 35, posX + 45, posY + 70);
                    g.drawLine(posX + 65, posY + 35, posX + 65, posY + 70);
                    break;
                case five:
                    g.setColor(color);
                    g.fillRect(posX + 8, posY + 35, 15, 35);
                    g.fillRect(posX + 28, posY + 35, 15, 35);
                    g.fillRect(posX + 48, posY + 35, 15, 35);
                    g.fillRect(posX + 68, posY + 35, 15, 35);
                    g.setColor(additionalColor);
                    g.drawLine(posX + 15, posY + 35, posX + 15, posY + 70);
                    g.drawLine(posX + 35, posY + 35, posX + 35, posY + 70);
                    g.drawLine(posX + 55, posY + 35, posX + 55, posY + 70);
                    g.drawLine(posX + 75, posY + 35, posX + 75, posY + 70);
                    break;
            }
        }
    }
}