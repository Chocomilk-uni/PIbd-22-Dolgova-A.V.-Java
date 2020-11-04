import java.awt.*;

public class Bus {
    //Координаты отрисовки
    private int _startPosX;
    private int _startPosY;

    //Поле отрисовки
    private int _pictureWidth;
    private int _pictureHeight;

    //Размеры автобуса
    private final int busHeight = 60;
    private final int busWidth = 100;
    private final double changeHeight = 1.4;

    private int averageSpeed;
    private float weight;
    private int seats;
    private Color mainColor;
    private Color additionalColor;
    private boolean hasSecondFloor;
    private boolean hasAdditionalDoor;
    private boolean hasFrontPlatform;
    //Поле от классаДоп.
    private Doors doors;

    public int getAverageSpeed() {
        return averageSpeed;
    }

    private void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public float getWeight() {
        return  weight;
    }

    private void setWeight(float weight) {
        this.weight = weight;
    }

    public int getSeats() {
        return  seats;
    }

    private void setSeats(int seats) {
        this.seats = seats;
    }

    public Color getMainColor() {
        return mainColor;
    }

    private void setMainColor(Color mainColor) {
        this.mainColor = mainColor;
    }

    public Color getAdditionalColor() {
        return additionalColor;
    }

    private void setAdditionalColor(Color additionalColor) {
        this.additionalColor = additionalColor;
    }

    public boolean isHasSecondFloor() {
        return  hasSecondFloor;
    }

    private void setHasSecondFloor(boolean hasSecondFloor) {
        this.hasSecondFloor = hasSecondFloor;
    }

    public boolean isHasAdditionalDoor() {
        return  hasAdditionalDoor;
    }

    private void setHasAdditionalDoor(boolean hasAdditionalDoor) {
        this.hasAdditionalDoor = hasAdditionalDoor;
    }

    public boolean isHasFrontPlatform() {
        return  hasFrontPlatform;
    }

    private void setHasFrontPlatform(boolean hasFrontPlatform) {
        this.hasFrontPlatform = hasFrontPlatform;
    }

    public Bus(int averageSpeed, float weight, int seats, Color mainColor, Color additColor, boolean hasSecondFloor, boolean hasAdditionalDoor, boolean hasFrontPlatform, int number) {
        this.averageSpeed = averageSpeed;
        this.weight = weight;
        this.seats = seats;
        this.mainColor = mainColor;
        this.additionalColor = additColor;
        this.hasSecondFloor = hasSecondFloor;
        this.hasAdditionalDoor = hasAdditionalDoor;
        this.hasFrontPlatform = hasFrontPlatform;
        this.doors = new Doors(number);
    }

    public void setPosition(int x, int y, int width, int height) {
        this._pictureWidth = width;
        this._pictureHeight = height;
        if (_startPosX >= 0 && _startPosX + busWidth < _pictureWidth &&
                _startPosY >= 0 && _startPosY + busHeight < _pictureHeight) {
            _startPosX = x;
            _startPosY = y;
        }
    }

    public void moveTransport(Direction direction)
    {
        float step = averageSpeed * 100 / weight;
        switch (direction)
        {
            case Right:
                if (_startPosX + step < _pictureWidth - busWidth) {
                    _startPosX += step;
                }
                break;
            case Left:
                if (_startPosX - step > 0) {
                    _startPosX -= step;
                }
                break;
            case Up:
                if (_startPosY - step > 0) {
                    _startPosY -= step;
                }
                break;
            case Down:
                if (_startPosY + step < _pictureHeight - changeHeight * busHeight) {
                    _startPosY += step;
                }
                break;
        }
    }

    public void drawTransport(Graphics g)
    {
        //Основной кузов
        g.setColor(Color.BLACK);
        g.drawRect(_startPosX + 5, _startPosY + 30, busWidth, 40);
        g.setColor(mainColor);
        g.fillRect(_startPosX + 5, _startPosY + 30, busWidth, 40);

        //Окна
        g.setColor(Color.BLACK);
        g.fillRect(_startPosX + 12, _startPosY + 35, 20, 15);
        g.fillRect(_startPosX + 62, _startPosY + 35, 20, 15);

        //Дверь
        g.setColor(Color.DARK_GRAY);
        g.fillRect(_startPosX + 91, _startPosY + 35, 15, 35);
        g.setColor(additionalColor);
        g.drawLine(_startPosX + 98, _startPosY + 35, _startPosX + 98, _startPosY + 70);

        if (hasFrontPlatform)
        {
            g.setColor(Color.BLACK);
            g.drawRect(_startPosX, _startPosY + 55, 40, 15);
            g.setColor(mainColor);
            g.fillRect(_startPosX, _startPosY + 50, 40, 20);
            g.setColor(Color.BLACK);
            g.fillRect(_startPosX + 5, _startPosY + 35, 25, 15);
        }

        //Метод отрисовки классаДоп.
        doors.drawDoors(g, Color.DARK_GRAY, additionalColor, _startPosX, _startPosY);

        //Колёса
        g.setColor(Color.BLACK);
        g.fillOval(_startPosX + 10, _startPosY + 60, 22, 22);
        g.fillOval(_startPosX + 65, _startPosY + 60, 22, 22);

        if (hasSecondFloor)
        {
            g.drawRect(_startPosX, _startPosY + 5, busWidth + 5, 25);
            g.setColor(mainColor);
            g.fillRect(_startPosX, _startPosY + 5, busWidth + 5, 25);

            //Окна 2-го этажа
            g.setColor(Color.BLACK);
            g.fillRect(_startPosX + 2, _startPosY + 10, 20, 10);
            g.fillRect(_startPosX + 32, _startPosY + 10, 20, 10);
            g.fillRect(_startPosX + 62, _startPosY + 10, 20, 10);
            g.fillRect(_startPosX + 92, _startPosY + 10, 14, 10);

            //Полоса
            g.drawRect(_startPosX, _startPosY + 24, busWidth + 5, 3);
            g.setColor(additionalColor);
            g.fillRect(_startPosX, _startPosY + 24, busWidth + 5, 3);
        }

        if (hasAdditionalDoor) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(_startPosX + 37, _startPosY + 40, 15, 32);
        }
    }
}