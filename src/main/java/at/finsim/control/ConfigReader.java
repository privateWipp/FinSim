package at.finsim.control;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ConfigReader {
    public static String getDefaultDirectoryPath(String xmlFilePath) {
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            return root.getElementsByTagName("defaultDirectory").item(0).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
            return System.getProperty("user.home");
        }
    }
}

