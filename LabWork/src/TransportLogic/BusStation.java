package TransportLogic;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BusStation<T extends Transport, H extends AdditionalElems> implements Iterator<T>, Iterable<T>{
    private final List<T> places;
    private final int maxCount;

    private final int pictureWidth;
    private final int pictureHeight;

    private final int placeSizeWidth = 230;
    private final int placeSizeHeight = 100;

    private int currentIndex;

    public BusStation(int pictureWidth, int pictureHeight) {
        int columnsNumber = pictureWidth / placeSizeWidth;
        int rowsNumber = pictureHeight / placeSizeHeight;
        maxCount = columnsNumber * rowsNumber;
        currentIndex = -1;
        places = new ArrayList<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    //Метод, заменяющий перегруженный оператор сложения в лабораторной на C#
    public boolean add(T bus) throws BusStationOverflowException, BusStationAlreadyHaveException {
        if (places.size() >= maxCount) {
            throw new BusStationOverflowException();
        }
        if (places.contains(bus)) {
            throw new BusStationAlreadyHaveException();
        }
        places.add(bus);
        return true;
    }

    //Метод, заменяющий перегруженный оператор вычитания в лабораторной на C#
    public T remove(int index) throws BusStationPlaceNotFoundException{
        if (index < 0 || index >= places.size()) {
            throw new BusStationPlaceNotFoundException(index);
        }
        T truck = places.get(index);
        places.remove(index);
        return truck;
    }

    //Подсчёт занятых мест
    public int countOccupiedPlaces() {
        int placesNumber = 0;
        for (Object object : places) {
            if (object != null) {
                placesNumber++;
            }
        }
        return placesNumber;
    }

    //Метод как замена перегрузки оператора ">="
    public boolean moreOrEqual(int number) {
        return countOccupiedPlaces() >= number;
    }

    //Метод как замена перегрузки оператора "<="
    public boolean lessOrEqual(int number) {
        return countOccupiedPlaces() <= number;
    }

    public void drawBusStation(Graphics2D g) {
        int rowsNumber = pictureHeight / placeSizeHeight;
        int margin = 10;

        drawMarking(g);
        g.setStroke(new BasicStroke(1));

        for (int i = 0; i < places.size(); i++)
        {
            places.get(i).setPosition(margin + placeSizeWidth * (i / rowsNumber), margin + placeSizeHeight * (i % rowsNumber), pictureWidth, pictureHeight);
            places.get(i).drawTransport(g);
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

    //Индексатор для получения элементов из списка
    public T get(int index) {
        if (index >= 0 && index < places.size()) {
            return places.get(index);
        }
        return null;
    }

    public void clear() {
        places.clear();
    }

    public void sort() {
        places.sort((Comparator<? super T>) new BusComparer());
    }

    @Override
    public boolean hasNext() {
        return currentIndex < places.size() - 1;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        currentIndex++;
        return places.get(currentIndex);
    }

    @Override
    public Iterator<T> iterator() {
        currentIndex = -1;
        return this;
    }

    public void printInfo() {
        for (T truck : places) {
            System.out.println(truck);
        }
    }
}