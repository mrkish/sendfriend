package com.sendfriend.util;

import java.util.Arrays;
import java.util.List;

public class AppConstants {

    // Templates
    public static final String SEARCH = "search";
    public static final String USER = "user";
    public static final String ROUTE = "route";
    public static final String BETA = "beta";
    public static final String CRAG = "crag";
    public static final String AREA = "area";
    // Misc
    public static final String TITLE = "title";
    public static final String USERS = "Users";
    public static final String AREAS = "Areas";
    public static final String CRAGS = "Crags";
    public static final String BETAS = "Betas";
    // Security
    public static final String HTPASSWD = ".htpasswd";
    public static final String HTACCESS = ".htaccess";
    public static final String CROSSDOMAIN_XML = "crossdomain.xml";
    public static final String CLIENT_ACCESS_POLICY_XML = "clientaccesspolicy.xml";
    public static final List<String> DISALLOWD_FILE_TYPES = Arrays.asList(HTACCESS,
                                                                                   HTPASSWD,
                                                                                   CROSSDOMAIN_XML,
                                                                                   CLIENT_ACCESS_POLICY_XML);

    private AppConstants() {
        throw new UnsupportedOperationException("Class cannot be instantiated");
    }
}
