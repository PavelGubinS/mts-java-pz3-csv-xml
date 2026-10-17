import java.util.List;

public interface AddressParser {
    // Любой парсер (CSV или XML) обязан уметь читать файл и возвращать список адресов
    List<Address> parse(String filePath) throws Exception;
}