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

import java.util.ArrayList;
import java.util.Collections;
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
    public List<List<Object>> search(Model model, @ModelAttribute SearchForm searchForm) {

        List<List<Object>> results = new ArrayList<>();
        List<User> userResults = new ArrayList<>();
        List<Route> routeResults = new ArrayList<>();
        List<Crag> cragResults = new ArrayList<>();
        List<Area> areaResults = new ArrayList<>();

        if (searchForm.getSearchField().equals(SearchFieldType.ALL) || searchForm.getSearchField().equals(SearchFieldType.USER)) {
            userResults.add(userDao.findByUsername(searchForm.getKeyword()));
            results.add(Collections.singletonList(userResults));
        }

        if (searchForm.getSearchField().equals(SearchFieldType.ALL) || searchForm.getSearchField().equals(SearchFieldType.ROUTE)) {
            routeResults.add((Route) routeDao.findByName(searchForm.getKeyword()));
            results.add(Collections.singletonList(routeResults));
        }

        if (searchForm.getSearchField().equals(SearchFieldType.ALL) || searchForm.getSearchField().equals(SearchFieldType.CRAG)) {
            cragResults.add((Crag) cragDao.findByName(searchForm.getKeyword()));
            results.add(Collections.singletonList(cragResults));
        }

        if (searchForm.getSearchField().equals(SearchFieldType.ALL) || searchForm.getSearchField().equals(SearchFieldType.AREA)) {
            areaResults.add((Area) areaDao.findByName(searchForm.getKeyword()));
            results.add(Collections.singletonList(areaResults));
        }

        return results;
    }
}
