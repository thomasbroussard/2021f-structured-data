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
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class XMLFilesExtractionMultithread {


    public static void main(String[] args) throws IOException {


        StringBuilder fileContent = new StringBuilder();
        URL url = new URL("https://thomas-broussard.fr/presentation/data-structuration-and-transportation/xml/ml.zip");


        ZipInputStream zis = new ZipInputStream(url.openStream());

        Date start = new Date();
        ZipEntry ze = zis.getNextEntry();

        ExecutorService executor = Executors.newFixedThreadPool(4);

        while (ze != null) {
              if (!ze.isDirectory()) {
                try {
                    ByteArrayInputStream copiedInputStream = new ByteArrayInputStream(toByteArray(zis));
                    executor.execute( () -> {
                        try {
                            fileContent.append(extractFile(copiedInputStream))
                                    .append(System.getProperty("line.separator"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (XPathExpressionException e) {
                            e.printStackTrace();
                        }
                    });
                }catch(Exception e){
                    System.out.println("error occurred while processing this file " + ze.getName());
                }
            }
            ze = zis.getNextEntry();
        }

        zis.close();
        Files.write(new File("output.csv").toPath(), fileContent.toString().getBytes(), StandardOpenOption.CREATE);
        Date end = new Date();
        System.out.println("processed in " + (end.getTime() - start.getTime()) + "ms");

        executor.shutdown();
    }


    private static String extractFile(InputStream ips) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {;


        Document doc = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(ips);

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
