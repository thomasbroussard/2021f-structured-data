package fr.epita.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

public class XPathPlayground {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        Document doc = parseDoc();

        String expression = "/breakfast_menu/food[starts-with(./name/text(), 'B')]/name";

        NodeList list = extractListFromXml(doc, expression);

        display(list);


    }

    private static void display(NodeList list) {
        for (int i = 0; i < list.getLength(); i++){
            Node item = list.item(i);
            System.out.println(item.getTextContent());
        }
    }

    private static NodeList extractListFromXml(Document doc, String expression) throws XPathExpressionException {
        XPathExpression xPathExpression = XPathFactory
                .newInstance()
                .newXPath()
                .compile(expression);


        NodeList list = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
        return list;
    }

    private static Document parseDoc() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File("xml-hands-on/menu.xml"));
        return doc;
    }
}
