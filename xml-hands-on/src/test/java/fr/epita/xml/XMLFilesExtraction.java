package fr.epita.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class XMLFilesExtraction {

//    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
//
//        URL url = new URL("https://thomas-broussard.fr/presentation/data-structuration-and-transportation/xml/ml.zip")
//        InputStream zipFileInputStream = url.openStream();
//        //TODO for each element inputstream of the zip file, do
//        Document doc = parseDoc(inputstream) ;//
//
//        String title = extractStringFromXml(doc, "/article/front/article-meta/title-group/article-title/text()");
//        //pubdate/year
//        //pudate/month
//
//        String line = title + "," + year + "," + month + "," + "Machine Learning";
//
//    }
//
//
//    //TODO adapt this
//    public static void unzipFolder(Path source, Path target) throws IOException {
//
//        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source.toFile()))) {
//
//            // list files in zip
//            ZipEntry zipEntry = zis.getNextEntry();
//
//            while (zipEntry != null) {
//
//                boolean isDirectory = false;
//                // example 1.1
//                // some zip stored files and folders separately
//                // e.g data/
//                //     data/folder/
//                //     data/folder/file.txt
//                if (zipEntry.getName().endsWith(File.separator)) {
//                    isDirectory = true;
//                }
//
//                Path newPath = zipSlipProtect(zipEntry, target);
//
//                if (isDirectory) {
//                    Files.createDirectories(newPath);
//                } else {
//
//                    // example 1.2
//                    // some zip stored file path only, need create parent directories
//                    // e.g data/folder/file.txt
//                    if (newPath.getParent() != null) {
//                        if (Files.notExists(newPath.getParent())) {
//                            Files.createDirectories(newPath.getParent());
//                        }
//                    }
//
//                    // copy files, nio
//                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
//
//                    // copy files, classic
//                    /*try (FileOutputStream fos = new FileOutputStream(newPath.toFile())) {
//                        byte[] buffer = new byte[1024];
//                        int len;
//                        while ((len = zis.read(buffer)) > 0) {
//                            fos.write(buffer, 0, len);
//                        }
//                    }*/
//                }
//
//                zipEntry = zis.getNextEntry();
//
//            }
//            zis.closeEntry();
//
//        }
//
//    }
//
//    private static void display(NodeList list) {
//        for (int i = 0; i < list.getLength(); i++) {
//            Node item = list.item(i);
//            System.out.println(item.getTextContent());
//        }
//    }
//
//    private static NodeList extractListFromXml(Document doc, String expression) throws XPathExpressionException {
//        XPathExpression xPathExpression = XPathFactory
//                .newInstance()
//                .newXPath()
//                .compile(expression);
//
//
//        NodeList list = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
//        return list;
//    }
//
//    private static String extractStringFromXml(Document doc, String expression) throws XPathExpressionException {
//        XPathExpression xPathExpression = XPathFactory
//                .newInstance()
//                .newXPath()
//                .compile(expression);
//
//
//        return (String) xPathExpression.evaluate(doc, XPathConstants.STRING);
//    }
//
//    private static Document parseDoc(InputStream ips) throws ParserConfigurationException, SAXException, IOException {
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document doc = db.parse(ips);
//        return doc;
//    }
}
