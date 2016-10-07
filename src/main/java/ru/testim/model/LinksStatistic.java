package ru.testim.model;

import java.util.Map;

public class LinksStatistic {

    private Map<String, Integer> links;

    public Map<String, Integer> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Integer> links) {
        this.links = links;
    }

    public boolean isEmpty() {
        return links.size() == 0 ? true : false;
    }
}
