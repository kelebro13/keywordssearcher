package ru.testim;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
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
import ru.testim.util.ConsoleHelper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class YandexApiSearcher {

    private static final Logger LOG = LoggerFactory.getLogger(YandexApiSearcher.class);
    private static final String URL_FORMAT = "http://blogs.yandex.ru/search.rss?text=%s&numdoc=%s";
    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private static int numdoc = 10;
    private Queue<String> keywords = new ConcurrentLinkedQueue<>();
    private static Set<String> links = new CopyOnWriteArraySet<>(); // все ссылки по ключевым словам

    private URL urlProxy;
    private Integer portProxy;

    public Map<String, Integer> getLinksStatistics(Set<String> keywordsSet) throws IOException {

        keywords = keywordsSet.stream().collect(Collectors.toCollection(ConcurrentLinkedQueue::new)); // все ключевые слова
        ExecutorService service = Executors.newFixedThreadPool(5);

        while (keywords.size() != 0) {
            try {
                links.addAll((Set<String>) service.submit(new LinkSearcher()).get());
            } catch (InterruptedException e) {
                LOG.debug(e.getLocalizedMessage());
            } catch (ExecutionException e) {
                LOG.debug(e.getLocalizedMessage());
            }
        }

        service.shutdown();
        while (!service.isTerminated()) {
            //wait
        }
        Map<String, Integer> linkStatistics = getLinksCount(links);
        return linkStatistics;
    }


    public Map<String, Integer> getLinksCount(Set<String> links) throws MalformedURLException {

        if (links.size() == 0) {
            return Collections.emptyMap();
        }

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

    public int getNumdoc() {
        return numdoc;
    }

    public void setNumdoc(int numdoc) {
        if (numdoc <= 0) {
            LOG.debug("значение numdoc должно быть положительным");
            throw new IllegalArgumentException("значение numdoc должно быть положительным");
        }
        YandexApiSearcher.numdoc = numdoc;
    }

    public URL getUrlProxy() {
        return urlProxy;
    }

    public void setUrlProxy(URL urlProxy) {
        this.urlProxy = urlProxy;
    }

    public Integer getPortProxy() {
        return portProxy;
    }

    public void setPortProxy(Integer portProxy) {
        this.portProxy = portProxy;
    }

    public class LinkSearcher implements Callable {


        @Override
        public Set<String> call() throws Exception {
            Set<String> links = new HashSet<>();
            String keyword = keywords.poll();
            ConsoleHelper.writeMessage("Поиск по слову: " + keyword);
            Document document = getDocument(keyword);
            links.addAll(getLinks(document));
            ConsoleHelper.writeMessage("Поиск заверщен по слову: " + keyword);
            return links;
        }

        public Document getDocument(String keyword) {

            String url = String.format(URL_FORMAT, keyword, numdoc);
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            clientBuilder.disableCookieManagement();
            if(urlProxy != null) {
                clientBuilder.setProxy(new HttpHost(urlProxy.getHost(), portProxy));
            }
            HttpClient client = clientBuilder.build();
            HttpUriRequest request = new HttpGet(url);
            HttpResponse response;
            Document document = null;
            try {
                response = client.execute(request);
            } catch (IOException e) {
                LOG.debug("Не удалось подключится к серверу. Ошибка: " + e.getLocalizedMessage());
                return document;
            }
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                try {
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    try {
                        document = builder.parse(entity.getContent());
                    } catch (IOException e) {
                        LOG.debug(e.getLocalizedMessage());
                    }
                } catch (ParserConfigurationException e) {
                    LOG.debug(e.getLocalizedMessage());
                } catch (SAXException e) {
                    LOG.debug(e.getLocalizedMessage());
                }
            } else {
                LOG.debug("Сервер вернул код ошибки= " + statusCode);
            }
            return document;
        }

        public Set<String> getLinks(Document document) {

            if (document == null) {
                return Collections.EMPTY_SET;
            }

            Set<String> links = new HashSet<>();
            NodeList nList = document.getElementsByTagName("item");
            for (int i = 0; i < nList.getLength(); i++) {
                Element e = (Element) nList.item(i);
                links.add(e.getElementsByTagName("link").item(0).getTextContent());
            }
            return links;
        }

    }

}
