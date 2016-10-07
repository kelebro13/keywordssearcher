package ru.testim.model;


import java.util.Set;

public class KeywordList {

    private Set<String> keywords;

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public boolean isEmpty() {
        return keywords.size() == 0 ? true : false;
    }
}
