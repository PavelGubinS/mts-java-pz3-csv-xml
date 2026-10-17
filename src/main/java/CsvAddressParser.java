import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvAddressParser implements AddressParser {

    @Override
    public List<Address> parse(String filePath) throws Exception {
        List<Address> addresses = new ArrayList<>();
        
        // try-with-resources автоматически закроет файл после чтения
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                // Пропускаем заголовок таблицы (первую строку)
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                // Пропускаем пустые строки
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Разделяем строку по точке с запятой
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    try {
                        // Убираем лишние кавычки и пробелы
                        String city = parts[0].replace("\"", "").trim();
                        String street = parts[1].replace("\"", "").trim();
                        int house = Integer.parseInt(parts[2].replace("\"", "").trim());
                        int floor = Integer.parseInt(parts[3].replace("\"", "").trim());

                        addresses.add(new Address(city, street, house, floor));
                    } catch (NumberFormatException e) {
                        // Если в номере дома или этажа оказался текст, просто пропускаем эту строку
                        System.err.println("Пропущена некорректная строка CSV: " + line);
                    }
                }
            }
        }
        return addresses;
    }
}