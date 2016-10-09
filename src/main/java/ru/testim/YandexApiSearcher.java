package ru.testim;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class YandexApiSearcher {

    private static final Logger LOG = LoggerFactory.getLogger(YandexApiSearcher.class);
    private static final String URL_FORMAT = "http://blogs.yandex.ru/search.rss?text=%s&numdoc=%s";
    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private static int numdoc = 10;

    private YandexApiSearcher(){}


    public static Map<String, Integer> getLinksStatistics(Set<String> keywordsSet) throws IOException{

        Set<String> links = new HashSet<>();
        for (String keyword : keywordsSet) {
            Document document = getDocument(keyword);
            links.addAll(getLinks(document));
        }

        Map<String, Integer> linkStatistics = getLinksCount(links);
        return linkStatistics;
    }

    public static Document getDocument(String keyword) throws IOException {

        String url = String.format(URL_FORMAT, keyword, numdoc);

        HttpClient client = HttpClientBuilder.create().disableCookieManagement().build();
        HttpUriRequest request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        Document document = null;
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                document = builder.parse(entity.getContent());
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
        return document;
    }

    public static Set<String> getLinks(Document document) {
        Set<String> links = new HashSet<>();
        NodeList nList = document.getElementsByTagName("item");
        for (int i = 0; i < nList.getLength(); i++) {
            Element e = (Element) nList.item(i);
            links.add(e.getElementsByTagName("link").item(0).getTextContent());
        }
        return links;
    }


    public static Map<String, Integer> getLinksCount(Set<String> links) throws MalformedURLException {
        Map<String, Integer> linksStatistics = new HashMap<>();
        URL url;
        for (String link : links) {
            url = new URL(link);
            String domain = url.getHost();
            domain = (domain.startsWith("www.") ? domain.substring(4) : domain);
            linksStatistics.merge(domain, 1, (oldValue, newValue) -> newValue = oldValue + 1);
        }
        return linksStatistics;
    }

    public static int getNumdoc() {
        return numdoc;
    }

    public static void setNumdoc(int numdoc) {
        if(numdoc <= 0){
            LOG.debug("значение numdoc должно быть положительным");
            throw new IllegalArgumentException("значение numdoc должно быть положительным");
        }
        YandexApiSearcher.numdoc = numdoc;
    }
}