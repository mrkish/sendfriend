package com.sendfriend.models.forms;

public class SearchForm {

    private SearchFieldType[] fields = SearchFieldType.values();

    private SearchFieldType searchField = SearchFieldType.ALL;

    private String keyword;

    public SearchFieldType getSearchField() { return searchField; }

    public void setSearchField(SearchFieldType searchField) { this.searchField = searchField; }

    public SearchFieldType[] getFields() { return fields; }

    public String getKeyword() { return keyword; }

    public void setKeyword(String keyword) { this.keyword = keyword; }
}
