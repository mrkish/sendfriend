package com.sendfriend.models.data;

import com.sendfriend.models.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RouteDao extends CrudRepository<Route, Integer> {
    List<Route> findByName(String name);
    Route findById(int id);
}