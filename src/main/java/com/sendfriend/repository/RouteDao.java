<<<<<<< HEAD:src/main/java/com/sendfriend/repository/RouteDao.java
package com.sendfriend.repository;

import com.sendfriend.domain.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RouteDao extends CrudRepository<Route, Integer> {
    List<Route> findByName(String name);
    List<Route> findByNameIgnoreCaseContaining(String name);
    List<Route> findByCrag(String crag);
    List<Route> findByCragId(int cragId);
    Route findById(int id);
=======
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
    List<Route> findByNameIgnoreCaseContaining(String name);
    List<Route> findByCrag(String crag);
    List<Route> findByCragId(int cragId);
    Route findById(int id);
>>>>>>> develop:src/main/java/com/sendfriend/models/data/RouteDao.java
}