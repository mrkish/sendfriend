package com.sendfriend.controllers;

import com.sendfriend.models.Area;
import com.sendfriend.models.Crag;
import com.sendfriend.models.Route;
import com.sendfriend.models.User;
import com.sendfriend.models.data.*;
import com.sendfriend.models.forms.SearchFieldType;
import com.sendfriend.models.forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "search")
public class SearchController extends AbstractController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private CragDao cragDao;

    @Autowired
    private AreaDao areaDao;

    @RequestMapping(value = "")
    public String search(Model model) {

        model.addAttribute("title", "Sendfriend | Search");
        model.addAttribute(new SearchForm());
        return "search";
    }

    @RequestMapping(value = "results")
    public String search(Model model, String searchTerm, @ModelAttribute SearchForm searchForm) {

        String keyword;
        HashMap<String, List<Object>> results = new HashMap<>();

        if (searchForm.getKeyword() != null) {
            if (searchForm.getKeyword().isEmpty() && searchTerm.isEmpty()) {
                model.addAttribute("results", results);
                return "search";
            }
        }

        if (searchTerm == null) {
            keyword = searchForm.getKeyword();
        } else {
            keyword = searchTerm;
            searchForm.setSearchField(SearchFieldType.ALL);
        }

        if (searchForm.getSearchField().equals(SearchFieldType.ALL) || searchForm.getSearchField().equals(SearchFieldType.USER)) {
            List<User> userResults = userDao.findByUsernameIgnoreCaseContaining(keyword);
            if (!userResults.isEmpty()) {
                results.put("user", Collections.singletonList(userResults));
            }
        }

        if (searchForm.getSearchField().equals(SearchFieldType.ALL) || searchForm.getSearchField().equals(SearchFieldType.ROUTE)) {
            List<Route> routeResults = routeDao.findByNameIgnoreCaseContaining(keyword);
            if (!routeResults.isEmpty()) {
                results.put("route", Collections.singletonList(routeResults));
            }
        }

        if (searchForm.getSearchField().equals(SearchFieldType.ALL) || searchForm.getSearchField().equals(SearchFieldType.CRAG)) {
            List<Crag> cragResults = cragDao.findByNameIgnoreCaseContaining(keyword);
            if (!cragResults.isEmpty()) {
                results.put("crag", Collections.singletonList(cragResults));
            }
        }

        if (searchForm.getSearchField().equals(SearchFieldType.ALL) || searchForm.getSearchField().equals(SearchFieldType.AREA)) {
            List<Area> areaResults = areaDao.findByNameIgnoreCaseContaining(keyword);
            if (!areaResults.isEmpty()) {
                results.put("area", Collections.singletonList(areaResults));
            }
        }

        model.addAttribute("title", "Sendfriend | Search results for: " + keyword);
        model.addAttribute("results", results);

        return "search";
    }
}
