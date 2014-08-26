package processor;

import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;


/**
 * Created by tharik on 7/7/14.
 */
public class XMLProcessor {
    public static void main(String[] args)
    {
        //Create Files for XML and XSD
        File xmlFile  = new File("src/main/resources/xml/automation.xml");
        File xsdFile  = new File("src/main/resources/xml/automation.xsd");

        //Validated against given XSD
        validateXml(xsdFile, xmlFile);
    }

    /**
     * Validate given xml against given xsd
     * @param xsd schema for xml
     * @param xml xml which needs to be validated
     */
    public static void validateXml(File xsd, File xml) {
        try {

            Schema schema = loadSchema(xsd);

            // creating a Validator instance
            Validator validator = schema.newValidator();
            System.out.println();
            System.out.println("Validator Class: "
                    + validator.getClass().getName());

            // preparing the XML file as a SAX source
            SAXSource source = new SAXSource(
                    new InputSource(new java.io.FileInputStream(xml)));

            // validating the SAX source against the schema
            validator.validate(source);
            System.out.println();
            System.out.println("XML has been Validated against XSD successfully.");

        } catch (Exception ex) {
            // catching all validation exceptions
            System.out.println();
            System.out.println(ex.toString());
        }
    }

    /**
     * Load schema from given xsd file
     * @param xsdFile
     * @return
     */
    public static Schema loadSchema(File xsdFile) {
        Schema schema = null;
        try {

            //Load language using XML constant
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;

            //Create schema factory using the language
            SchemaFactory factory = SchemaFactory.newInstance(language);

            //create schema using schema factory and given XSD
            schema = factory.newSchema(xsdFile);

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return schema;
    }
}
