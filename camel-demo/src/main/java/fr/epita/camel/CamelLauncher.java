package fr.epita.camel;


import org.apache.camel.*;
import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.ZipFileDataFormat;
import org.xml.sax.SAXException;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class CamelLauncher {

    public static void main(String[] args) throws Exception {
        ZipFileDataFormat zipFile = new ZipFileDataFormat();
        zipFile.setUsingIterator("true");

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder(){
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .to("https://thomas-broussard.fr/presentation/data-structuration-and-transportation/xml/ml.zip")
                        .log("fetched zip file")
                        .unmarshal(zipFile)
                        .split(bodyAs(Iterator.class), AggregationStrategies.string("\n"))
                            .streaming()
                            .process(exchange -> {
                                InputStream in = exchange.getIn().getBody(InputStream.class);
                                try {
                                    String line =  getCsvLineFromInputStream(in);
                                    exchange.getIn().setBody(line);
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            })
                        .end()
                        .to("file://destination?fileName=test.csv");


            }
        });

        context.start();

        ProducerTemplate template = context.createProducerTemplate();

        template.sendBody("direct:start","some message");

    }


    private static String getCsvLineFromInputStream(InputStream ips) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        org.w3c.dom.Document doc = DocumentBuilderFactory
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

    private static String getTextFromXpath(org.w3c.dom.Document doc, String xpathExpr) throws XPathExpressionException {
        XPathExpression xPathExpression = XPathFactory.newInstance().newXPath().compile(xpathExpr);
        return (String) xPathExpression.evaluate(doc, XPathConstants.STRING);

    }

}
