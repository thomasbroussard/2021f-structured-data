package fr.epita.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class XMLFilesExtractionElia {


    public static void main(String[] args) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        URL url = new URL("https://thomas-broussard.fr/presentation/data-structuration-and-transportation/xml/ml.zip");

        ZipInputStream zis = new ZipInputStream(url.openStream());

        ZipEntry ze = zis.getNextEntry();
        while (ze != null) {
            String filePath = "structured-data/src/main/resources/file" + File.separator + ze.getName();
            if (!ze.isDirectory()) {
                try {
                    fileContent.append(extractFile(zis))
                            .append(System.getProperty("line.separator"));
                }catch(Exception e){
                    System.out.println("error occurred while processing this file " + ze.getName());
                }
            }
            ze = zis.getNextEntry();
        }
        zis.close();
        Files.write(new File("output.csv").toPath(), fileContent.toString().getBytes(), StandardOpenOption.CREATE);
    }


    private static String extractFile(ZipInputStream zipEntry) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {




        InputStream stream = new ByteArrayInputStream();
        Document doc = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(stream);

        String xpathForMonth = "//article/front/article-meta/pub-date/month/text()";
        String xpathForYear = "//article/front/article-meta/pub-date/year/text()";
        String xpathForTitle = "/article/front/article-meta/title-group/article-title/text()";


        return getTextFromXpath(doc, xpathForTitle)
                + "," + getTextFromXpath(doc, xpathForYear)
                + "," + getTextFromXpath(doc, xpathForMonth)
                + ", MACHINE LEARNING";
    }

    private static String getTextFromXpath(Document doc, String xpathExpr) throws XPathExpressionException {
        XPathExpression xPathExpression = XPathFactory.newInstance().newXPath().compile(xpathExpr);
        return (String) xPathExpression.evaluate(doc, XPathConstants.STRING);

    }

}
