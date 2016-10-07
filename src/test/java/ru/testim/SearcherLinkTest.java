package ru.testim;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import ru.testim.datatest.KeywordsDataTest;
import ru.testim.model.KeywordList;
import ru.testim.model.LinksStatistic;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static ru.testim.datatest.LinksStatisticsDataTest.liksStatisticsTestMap;
import static ru.testim.datatest.SearcherDataTest.*;


public class SearcherLinkTest {

    @Test
    public void testGetDocument() throws Exception {
        Document document = SearcherLink.getDocument("java");

        Node generator  = document.getElementsByTagName("generator").item(0);
        Node description  = document.getElementsByTagName("description").item(0);

        String actual = String.format("generator:%s description:%s",
                generator.getTextContent(),
                description.getTextContent());
        assertEquals(generatorAndDescription, actual);

    }

    @Test
    public void testGetLinks() throws Exception {
        Set<String> actual = SearcherLink.getLinks(getTestDocument());
        assertEquals(4, actual.size());
        assertEquals(linksTestSet, actual);
    }

    @Test
    public void testGetLinksStatistics() throws Exception {
        KeywordList keywords = new KeywordList();
        keywords.setKeywords(KeywordsDataTest.keywordsTestSet);

        LinksStatistic actual = SearcherLink.getLinksStatistics(keywords);

        assertEquals(false, actual.isEmpty());
    }

    @Test
    public void testGetLinksCount() throws Exception {
        Map<String, Integer> actual = SearcherLink.getLinksCount(linksTestSet);
        assertEquals(liksStatisticsTestMap, actual);
    }

    @Test
    public void getNumdoc() throws Exception {
        int actual = SearcherLink.getNumdoc();
        assertEquals(10, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNumdocNegativeNumber() throws Exception {
        SearcherLink.setNumdoc(-1);
    }

}