import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;
    private final StatisticsCalculator statsCalculator;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.statsCalculator = new StatisticsCalculator();
    }

    public void start() {
        System.out.println("=== Программа для работы со справочником городов ===");
        System.out.println("Введите путь к файлу (.csv или .xml) или команду 'exit' для выхода.");

        while (true) {
            System.out.print("\nПуть к файлу (или 'exit'): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Завершение работы программы...");
                break;
            }

            if (input.isEmpty()) {
                continue;
            }

            // Определяем парсер в зависимости от расширения файла
            AddressParser parser = getParser(input);
            if (parser == null) {
                System.out.println("Ошибка: Неизвестный формат файла. Поддерживаются только .csv и .xml");
                continue;
            }

            // Обрабатываем файл и ловим ошибки (файл не найден и т.д.)
            try {
                long startTime = System.currentTimeMillis();
                List<Address> addresses = parser.parse(input);
                long endTime = System.currentTimeMillis();

                statsCalculator.printStatistics(addresses, endTime - startTime);

            } catch (Exception e) {
                System.out.println("Ошибка при чтении файла. Проверьте путь и формат данных.");
                System.out.println("Детали: " + e.getMessage());
            }
        }
    }

    // Вспомогательный метод для выбора нужного парсера
    private AddressParser getParser(String filePath) {
        String lowerCasePath = filePath.toLowerCase();
        if (lowerCasePath.endsWith(".csv")) {
            return new CsvAddressParser();
        } else if (lowerCasePath.endsWith(".xml")) {
            return new XmlAddressParser();
        }
        return null;
    }
}