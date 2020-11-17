package TransportLogic;

public enum NumberOfDoors {
    three,
    four,
    five;

    public static NumberOfDoors getNumber(int number) {
        switch (number) {
            case 0:
                return NumberOfDoors.three;
            case 1:
                return NumberOfDoors.four;
            case 2:
                return  NumberOfDoors.five;
        }
        return null;
    }
}