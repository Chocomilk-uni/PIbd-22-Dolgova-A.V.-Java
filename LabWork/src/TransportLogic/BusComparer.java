package TransportLogic;

import java.util.Comparator;

public class BusComparer implements Comparator<PublicTransport> {

    @Override
    public int compare(PublicTransport x, PublicTransport y) {
        if (!x.getClass().getSimpleName().equals(y.getClass().getSimpleName())) {
            return x.getClass().getSimpleName().compareTo(y.getClass().getSimpleName());
        }
        int result;
        switch (x.getClass().getSimpleName()) {
            case "Bus" -> {
                result = comparerBus((Bus) x, (Bus) y);
                return result;
            }
            case "DoubleBus" -> {
                result = comparerDoubleBus((DoubleBus) x, (DoubleBus) y);
                return result;
            }
        }
        return 100;
    }

    private int comparerBus(Bus x, Bus y) {
        if (x.getAverageSpeed() != y.getAverageSpeed()) {
            return Integer.compare(x.getAverageSpeed(), y.getAverageSpeed());
        }
        if (x.getWeight() != y.getWeight()) {
            return Float.compare(x.getWeight(), y.getWeight());
        }
        if (x.getSeats() != y.getSeats()) {
            return Integer.compare(x.getSeats(), y.getSeats());
        }
        if (x.getMainColor() != y.getMainColor()) {
            return Integer.compare(x.getMainColor().getRGB(), y.getMainColor().getRGB());
        }
        return 0;
    }

    private int comparerDoubleBus(DoubleBus x, DoubleBus y) {
        int result = comparerBus(x, y);
        if (result == 0) {
            return result;
        }

        if (x.getAdditionalColor() != y.getAdditionalColor()) {
            return Integer.compare(x.getAdditionalColor().getRGB(), y.getAdditionalColor().getRGB());
        }
        if (x.isHasAdditionalDoor() != y.isHasAdditionalDoor()) {
            return Boolean.compare(x.isHasAdditionalDoor(), y.isHasAdditionalDoor());
        }
        if (x.isHasSecondFloor() != y.isHasSecondFloor()) {
            return Boolean.compare(x.isHasSecondFloor(), y.isHasSecondFloor());
        }
        if (x.isHasFrontPlatform() != y.isHasFrontPlatform()) {
            return Boolean.compare(x.isHasFrontPlatform(), y.isHasFrontPlatform());
        }
        if (x.getAdditionalElems() != null && y.getAdditionalElems() != null
                && !(x.getAdditionalElems().toString().equals(y.getAdditionalElems().toString()))) {
            return x.getAdditionalElems().toString().compareTo(y.getAdditionalElems().toString());
        }
        if (x.getAdditionalElems() == null && y.getAdditionalElems() != null) {
            return 1;
        }
        if (x.getAdditionalElems() != null && y.getAdditionalElems() == null) {
            return -1;
        }
        return 0;
    }
}