package main.java.Main;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XsdValidator {

    public static final Path xsdPath = MyProperties.getXsdPath();

    public void validate(File xmlFile) {

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder parser = documentBuilderFactory.newDocumentBuilder();
            File xsdFile = xsdPath.toFile();
            Document document = parser.parse(xmlFile);
            Schema schema = schemaFactory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));

        } catch (SAXException e) {
            Interactive.errorReport(String.format("Фаил %s не прошел валидацию.", xmlFile.getName()));
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            Interactive.errorReport(String.format("Фаил %s не прошел валидацию.", xmlFile.getName()));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}