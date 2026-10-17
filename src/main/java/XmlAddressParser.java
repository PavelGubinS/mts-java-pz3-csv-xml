import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlAddressParser implements AddressParser {

    @Override
    public List<Address> parse(String filePath) throws Exception {
        List<Address> addresses = new ArrayList<>();
        
        // Подготавливаем инструменты для чтения XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        // Читаем файл
        Document document = builder.parse(new File(filePath));
        
        // Получаем все теги <item>
        NodeList nodeList = document.getElementsByTagName("item");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            
            try {
                // Достаем значения из атрибутов тега
                String city = element.getAttribute("cite"); // В XML используется "cite"
                String street = element.getAttribute("street");
                int house = Integer.parseInt(element.getAttribute("house"));
                int floor = Integer.parseInt(element.getAttribute("floor"));

                addresses.add(new Address(city, street, house, floor));
            } catch (NumberFormatException e) {
                // Если в атрибутах дома или этажа попался текст, не даем программе упасть
                System.err.println("Пропущен некорректный элемент XML.");
            }
        }
        
        return addresses;
    }
}