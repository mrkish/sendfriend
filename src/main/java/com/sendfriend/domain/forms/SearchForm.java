<<<<<<< HEAD:src/main/java/com/sendfriend/domain/forms/SearchForm.java
package com.sendfriend.domain.forms;

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
=======
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
>>>>>>> develop:src/main/java/com/sendfriend/models/forms/SearchForm.java
