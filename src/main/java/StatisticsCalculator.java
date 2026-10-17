import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StatisticsCalculator {

    public void printStatistics(List<Address> addresses, long timeSpentMs) {
        printDuplicates(addresses);
        printFloorsStats(addresses);
        System.out.println("\nВремя обработки файла: " + timeSpentMs + " мс.");
    }

    private void printDuplicates(List<Address> addresses) {
        System.out.println("\n--- Дублирующиеся записи ---");
        
        // Считаем, сколько раз встречается каждый уникальный адрес
        Map<Address, Integer> counts = new HashMap<>();
        for (Address address : addresses) {
            counts.put(address, counts.getOrDefault(address, 0) + 1);
        }

        boolean found = false;
        for (Map.Entry<Address, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 1) {
                Address a = entry.getKey();
                System.out.println("Город: " + a.getCity() + ", Улица: " + a.getStreet() + 
                                   ", Дом: " + a.getHouse() + " | Повторений: " + entry.getValue());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("Дубликатов не найдено.");
        }
    }

    private void printFloorsStats(List<Address> addresses) {
        System.out.println("\n--- Этажность зданий по городам ---");
        
        // TreeMap хранит ключи (города) в отсортированном виде (по алфавиту)
        // Внутри храним еще одну Map: Этаж -> Количество
        Map<String, Map<Integer, Integer>> cityFloors = new TreeMap<>();

        for (Address address : addresses) {
            cityFloors.putIfAbsent(address.getCity(), new HashMap<>());
            Map<Integer, Integer> floors = cityFloors.get(address.getCity());
            floors.put(address.getFloor(), floors.getOrDefault(address.getFloor(), 0) + 1);
        }

        for (Map.Entry<String, Map<Integer, Integer>> entry : cityFloors.entrySet()) {
            String city = entry.getKey();
            Map<Integer, Integer> floors = entry.getValue();
            
            System.out.print(city + ": ");
            // Выводим этажи от 1 до 5, как просили в задании
            for (int i = 1; i <= 5; i++) {
                int count = floors.getOrDefault(i, 0);
                System.out.print(i + "-эт: " + count + "; ");
            }
            System.out.println(); // Перенос строки для следующего города
        }
    }
}