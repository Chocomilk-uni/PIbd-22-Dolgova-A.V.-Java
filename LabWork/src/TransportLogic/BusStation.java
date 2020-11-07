package TransportLogic;

import java.awt.*;

public class BusStation<T extends Transport, H extends AdditionalElems> {
    private final Object[] places;

    private final int pictureWidth;
    private final int pictureHeight;

    private final int placeSizeWidth = 230;
    private final int placeSizeHeight = 100;

    public BusStation(int pictureWidth, int pictureHeight) {
        int columnsNumber = pictureWidth / placeSizeWidth;
        int rowsNumber = pictureHeight / placeSizeHeight;
        places = new Object[columnsNumber * rowsNumber];
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    //Метод, заменяющий перегруженный оператор сложения в лабораторной на C#
    public boolean add(T transport) {
        int rowsNumber = pictureHeight / placeSizeHeight;
        int margin = 10;
        for (int i = 0; i < places.length; i++) {
            if (places[i] == null) {
                transport.setPosition(margin + placeSizeWidth * (i / rowsNumber), margin + placeSizeHeight * (i % rowsNumber), pictureWidth, pictureHeight);
                places[i] = transport;
                return true;
            }
        }
        return false;
    }

    //Метод, заменяющий перегруженный оператор вычитания в лабораторной на C#
    public T remove(int index) {
        if (index >= 0 && index < places.length && places[index] != null) {
            Object temp = places[index];
            places[index] = null;
            return (T) (temp);
        } else {
            return null;
        }
    }

    //Метод как замена перегрузки оператора ">="
    public boolean moreOrEqual(int number) {
        int placesNumber = 0;
        for (Object object : places) {
            if (object != null) {
                placesNumber++;
            }
        }
        return placesNumber >= number;
    }

    //Метод как замена перегрузки оператора "<="
    public boolean lessOrEqual(int number) {
        int placesNumber = 0;
        for (Object object : places) {
            if (object != null) {
                placesNumber++;
            }
        }
        return placesNumber <= number;
    }

    public void drawBusStation(Graphics2D g) {
        drawMarking(g);
        g.setStroke(new BasicStroke(1));
        for (Object place : places) {
            if (place != null) {
                T placeT = (T) place;
                placeT.drawTransport(g);
            }
        }
    }

    public void drawMarking(Graphics2D g) {
        g.setStroke(new BasicStroke(3));
        for (int i = 0; i < pictureWidth / placeSizeWidth; i++) {
            for (int j = 0; j < pictureHeight / placeSizeHeight + 1; j++) {
                g.drawLine(i * placeSizeWidth, j * placeSizeHeight, i * placeSizeWidth + placeSizeWidth / 2, j * placeSizeHeight);
            }
            g.drawLine(i * placeSizeWidth,0, i * placeSizeWidth, (pictureHeight / placeSizeHeight) * placeSizeHeight);
        }
    }
}