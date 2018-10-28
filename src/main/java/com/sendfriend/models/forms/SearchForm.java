package com.sendfriend.models.forms;

public class SearchForm {

    private SearchFieldType[] fields = SearchFieldType.values();

    private SearchFieldType searchField = SearchFieldType.ALL;

    private StringBuilder keyword;

    public SearchFieldType getSearchField() { return searchField; }

    public void setSearchField(SearchFieldType searchField) { this.searchField = searchField; }

    public SearchFieldType[] getFields() { return fields; }

    public StringBuilder getKeyword() { return keyword; }

    public void setKeyword(StringBuilder keyword) { this.keyword = keyword; }
}
