package TransportLogic;

public class BusStationPlaceNotFoundException extends Exception {
    public BusStationPlaceNotFoundException(int i) {
        super("Автобус по месту " + i +  " не найден");
    }
}