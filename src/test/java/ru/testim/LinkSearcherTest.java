package ru.testim;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static ru.testim.datatest.SearcherDataTest.*;


public class LinkSearcherTest {

    @Test
    public void testGetDocument() throws Exception {
        YandexApiSearcher yandexApiSearcher = new YandexApiSearcher();
        YandexApiSearcher.LinkSearcher searcher = yandexApiSearcher.new LinkSearcher();
        Document document = searcher.getDocument("java");
        Node generator  = document.getElementsByTagName("generator").item(0);
        Node description  = document.getElementsByTagName("description").item(0);

        String actual = String.format("generator:%s description:%s",
                generator.getTextContent(),
                description.getTextContent());
        assertEquals(generatorAndDescription, actual);
    }

    @Test
    public void testGetLinks() throws Exception {
        YandexApiSearcher yandexApiSearcher = new YandexApiSearcher();
        YandexApiSearcher.LinkSearcher searcher = yandexApiSearcher.new LinkSearcher();
        Set<String> actual = searcher.getLinks(getTestDocument());
        assertEquals(4, actual.size());
        assertEquals(linksTestSet, actual);
    }

    @Test
    public void testGetLinksFromEmptySet() throws Exception {
        YandexApiSearcher yandexApiSearcher = new YandexApiSearcher();
        YandexApiSearcher.LinkSearcher searcher = yandexApiSearcher.new LinkSearcher();
        Set<String> actual = searcher.getLinks(null);
        assertEquals(0, actual.size());
        assertEquals(Collections.emptySet(), actual);
    }

}