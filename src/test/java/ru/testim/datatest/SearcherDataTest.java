package ru.testim.datatest;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashSet;
import java.util.Set;

public class SearcherDataTest {

    public static final String generatorAndDescription = "generator:yandex.ru/blogs description:Результаты поиска Яндекса по блогам и форумам по запросу: «java»";

    public static final Set<String> linksTestSet = new HashSet<>();

    static {
        linksTestSet.add("http://vk.com/wall113331570_2095");
        linksTestSet.add("http://www.cyberforum.ru/java-database/thread1821076.html");
        linksTestSet.add("http://vk.com/wall113331570_2095");
        linksTestSet.add("http://twitter.com/TsapukNikita/statuses/784307416355573761");
        linksTestSet.add("http://vk.com/wall996311_1564");
    }


    public static Document getTestDocument() throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element root = doc.createElement("rss");
        doc.appendChild(root);

        Element parent = doc.createElement("item");
        root.appendChild(parent);

        Element childElement = doc.createElement("link");
        childElement.setTextContent("http://vk.com/wall113331570_2095");
        parent.appendChild(childElement);


        Element parent2 = doc.createElement("item");
        root.appendChild(parent2);

        Element childElement2 = doc.createElement("link");
        childElement2.setTextContent("http://www.cyberforum.ru/java-database/thread1821076.html");
        parent2.appendChild(childElement2);


        Element parent3 = doc.createElement("item");
        root.appendChild(parent3);

        Element childElement3 = doc.createElement("link");
        childElement3.setTextContent("http://vk.com/wall113331570_2095");
        parent3.appendChild(childElement3);


        Element parent4 = doc.createElement("item");
        root.appendChild(parent4);

        Element childElement4 = doc.createElement("link");
        childElement4.setTextContent("http://twitter.com/TsapukNikita/statuses/784307416355573761");
        parent4.appendChild(childElement4);

        Element parent5 = doc.createElement("item");
        root.appendChild(parent5);

        Element childElement5 = doc.createElement("link");
        childElement5.setTextContent("http://vk.com/wall996311_1564");
        parent5.appendChild(childElement5);


        return doc;

    }

    private SearcherDataTest(){}
}
