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

public class XMLFilesExtractionMultithread {


    public static void main(String[] args) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        URL url = new URL("https://thomas-broussard.fr/presentation/data-structuration-and-transportation/xml/ml.zip");

        ZipInputStream zis = new ZipInputStream(url.openStream());

        ZipEntry ze = zis.getNextEntry();


        while (ze != null) {
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

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(toByteArray(zipEntry));


        Document doc = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(byteArrayInputStream);

        String xpathForMonth = "//article/front/article-meta/pub-date/month/text()";
        String xpathForYear = "//article/front/article-meta/pub-date/year/text()";
        String xpathForTitle = "/article/front/article-meta/title-group/article-title/text()";


        return getTextFromXpath(doc, xpathForTitle)
                + "," + getTextFromXpath(doc, xpathForYear)
                + "," + getTextFromXpath(doc, xpathForMonth)
                + ", MACHINE LEARNING";
    }

    private static byte[] toByteArray(InputStream ips) throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] buff = new byte[8000];

        int bytesRead = 0;

        while((bytesRead = ips.read(buff)) != -1) {
            bao.write(buff, 0, bytesRead);
        }

        return bao.toByteArray();
    }

    private static String getTextFromXpath(Document doc, String xpathExpr) throws XPathExpressionException {
        XPathExpression xPathExpression = XPathFactory.newInstance().newXPath().compile(xpathExpr);
        return (String) xPathExpression.evaluate(doc, XPathConstants.STRING);

    }

}
