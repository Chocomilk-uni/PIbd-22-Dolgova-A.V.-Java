package TransportLogic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class BusStationCollection {

    private final Map<String, BusStation<Bus, AdditionalElems>> busStationStages;
    private final int pictureWidth;
    private final int pictureHeight;

    private final String separator = ":";

    public BusStationCollection(int pictureWidth, int pictureHeight) {
        busStationStages = new HashMap<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public Set<String> keys() {
        return busStationStages.keySet();
    }

    public void addBusStation(String name) {
        if (!busStationStages.containsKey(name)) {
            busStationStages.put(name, new BusStation<>(pictureWidth, pictureHeight));
        }
    }

    public void deleteBusStation(String name) {
        busStationStages.remove(name);
    }

    public BusStation<Bus, AdditionalElems> get(String name) {
        if (busStationStages.containsKey(name)) {
            return busStationStages.get(name);
        }
        return null;
    }

    /*
    Метод как замена индексатора с двумя параметрами,
    первый выбирает элемент из словаря, второй - из списка параметризованного класа.
     */
    public Bus get(String name, int index) {
        if (busStationStages.containsKey(name)) {
            return busStationStages.get(name).get(index);
        }
        return null;
    }

    public boolean saveData(String filename) {
        if (!filename.contains(".txt")) {
            filename += ".txt";
        }
        try (FileWriter fileWriter = new FileWriter(filename, false)) {
            fileWriter.write("BusStationCollection\n");
            for (Map.Entry<String, BusStation<Bus, AdditionalElems>> level : busStationStages.entrySet()) {
                fileWriter.write("BusStation" + separator + level.getKey() + '\n');

                Bus bus;
                for (int i = 0; (bus = level.getValue().get(i)) != null; i++) {
                    if (bus.getClass().getSimpleName().equals("Bus")) {
                        fileWriter.write("Bus" + separator);
                    } else if (bus.getClass().getSimpleName().equals("DoubleBus")) {
                        fileWriter.write("DoubleBus" + separator);
                    }
                    fileWriter.write(bus.toString() + '\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean loadData(String filename) {
        if (!(new File(filename).exists())) {
            return false;
        }
        try (FileReader fileReader = new FileReader(filename)) {
            Scanner scanner = new Scanner(fileReader);
            if (scanner.nextLine().contains("BusStationCollection")) {
                busStationStages.clear();
            } else {
                return false;
            }

            Bus bus = null;
            String key = "";
            String line;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains("BusStation")) {
                    key = line.split(separator)[1];
                    busStationStages.put(key, new BusStation<>(pictureWidth, pictureHeight));
                } else if (line.contains(separator)) {
                    if (line.contains("DoubleBus")) {
                        bus = new DoubleBus(line.split(separator)[1]);
                    } else if (line.contains("Bus")) {
                        bus = new Bus(line.split(separator)[1]);
                    }
                    if (!(busStationStages.get(key).add(bus))) {
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean saveBusStation(String filename, String key) {
        if (busStationStages.containsKey(key)) {
            if (!filename.contains(".txt")) {
                filename += ".txt";
            }
            try (FileWriter fileWriter = new FileWriter(filename, false)) {
                fileWriter.write("BusStation" + separator + key + '\n');

                Bus bus;
                for (int i = 0; (bus = busStationStages.get(key).get(i)) != null; i++) {
                    if (bus.getClass().getSimpleName().equals("Bus")) {
                        fileWriter.write("Bus" + separator);
                    } else if (bus.getClass().getSimpleName().equals("DoubleBus")) {
                        fileWriter.write("DoubleBus" + separator);
                    }
                    fileWriter.write(bus.toString() + '\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public boolean loadBusStation(String filename) {
        try (FileReader fileReader = new FileReader(filename)) {
            Scanner scanner = new Scanner(fileReader);
            String key;
            String line;

            line = scanner.nextLine();
            if (line.contains("BusStation:")) {
                key = line.split(separator)[1];
                if (busStationStages.containsKey(key)) {
                    busStationStages.get(key).clear();
                } else {
                    busStationStages.put(key, new BusStation<>(pictureWidth, pictureHeight));
                }
            } else {
                return false;
            }

            Bus bus = null;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(separator)) {
                    if (line.contains("DoubleBus")) {
                        bus = new DoubleBus(line.split(separator)[1]);
                    } else if (line.contains("Bus")) {
                        bus = new Bus(line.split(separator)[1]);
                    }
                    if (!(busStationStages.get(key).add(bus))) {
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}