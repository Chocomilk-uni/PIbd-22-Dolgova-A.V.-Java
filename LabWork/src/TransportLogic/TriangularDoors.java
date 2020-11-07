package TransportLogic;

import java.awt.*;

public class TriangularDoors implements AdditionalElems {
    //Закрытое поле от перечисления TransportLogic.NumberOfDoors
    private NumberOfDoors doorsNumber;

    public TriangularDoors(int number) {
        setNumber(number);
    }

    //Открытое числовое свойство, через к-рое можно в закрытое поле занести значение
    @Override
    public void setNumber(int number) {
        this.doorsNumber = NumberOfDoors.getNumber(number);
    }

    @Override
    public void draw(Graphics g, Color color, Color additionalColor, int posX, int posY) {
        int[] yPoints = new int[]{posY + 40, posY + 35, posY + 40};
        int[] xFirstPoints = new int[]{posX + 91, posX + 98, posX + 106};
        int[] xSecondPoints = new int[]{posX + 28, posX + 35, posX + 43};
        int[] xThirdPoints = new int[]{posX + 48, posX + 55, posX + 63};
        g.setColor(color);
        g.fillPolygon(xFirstPoints, yPoints, 3);
        g.fillRect(posX + 28, posY + 40, 15, 30);
        g.fillRect(posX + 48, posY + 40, 15, 30);
        g.fillPolygon(xSecondPoints, yPoints, 3);
        g.fillPolygon(xThirdPoints, yPoints, 3);
        g.setColor(additionalColor);
        g.drawLine(posX + 98, posY + 37, posX + 98, posY + 40);
        g.drawLine(posX + 35, posY + 37, posX + 35, posY + 70);
        g.drawLine(posX + 55, posY + 37, posX + 55, posY + 70);

        if (doorsNumber == NumberOfDoors.four || doorsNumber == NumberOfDoors.five) {
            int[] xFourthPoints = new int[]{posX + 8, posX + 15, posX + 23};
            g.setColor(color);
            g.fillRect(posX + 8, posY + 40, 15, 30);
            g.fillPolygon(xFourthPoints, yPoints, 3);
            g.setColor(additionalColor);
            g.drawLine(posX + 15, posY + 37, posX + 15, posY + 70);
            if (doorsNumber == NumberOfDoors.five) {
                int[] xFifthPoints = new int[]{posX + 68, posX + 75, posX + 83};
                g.setColor(color);
                g.fillRect(posX + 68, posY + 40, 15, 30);
                g.fillPolygon(xFifthPoints, yPoints, 3);
                g.setColor(additionalColor);
                g.drawLine(posX + 75, posY + 37, posX + 75, posY + 70);
            }
        }
    }
}