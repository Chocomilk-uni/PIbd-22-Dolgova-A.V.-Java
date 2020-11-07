package TransportLogic;

import java.awt.*;

public abstract class PublicTransport implements Transport{
    protected int startPosX;
    protected int startPosY;

    protected int pictureWidth;
    protected int pictureHeight;

    protected int averageSpeed;
    protected float weight;
    protected int seats;
    protected Color mainColor;

    public int getAverageSpeed() {
        return averageSpeed;
    }

    protected void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public float getWeight() {
        return  weight;
    }

    protected void setWeight(float weight) {
        this.weight = weight;
    }

    public int getSeats() {
        return  seats;
    }

    protected void setSeats(int seats) {
        this.seats = seats;
    }

    public Color getMainColor() {
        return mainColor;
    }

    protected void setMainColor(Color mainColor) {
        this.mainColor = mainColor;
    }

    @Override
    public void setPosition(int x, int y, int width, int height)
    {
        this.pictureWidth = width;
        this.pictureHeight = height;
        if (startPosX >= 0 && startPosX < pictureWidth &&
                startPosY >= 0 && startPosY < pictureHeight) {
            startPosX = x;
            startPosY = y;
        }
    }

    @Override
    public abstract void drawTransport(Graphics g);

    @Override
    public abstract void moveTransport(Direction direction);
}
