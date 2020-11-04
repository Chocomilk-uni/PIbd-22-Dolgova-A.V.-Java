public enum NumberOfDoors {
    three,
    four,
    five;

    public static NumberOfDoors getNumber(int number) {
        switch (number) {
            case 3:
                return NumberOfDoors.three;
            case 4:
                return NumberOfDoors.four;
            case 5:
                return  NumberOfDoors.five;
        }
        return null;
    }
}