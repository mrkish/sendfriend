package com.sendfriend.repository;

import com.sendfriend.domain.Crag;
import com.sendfriend.domain.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CragDao extends CrudRepository<Crag, Integer> {
    List<Crag> findByName(String name);
    List<Crag> findByNameIgnoreCaseContaining(String name);
    List<Crag> findByAreaId(int areaId);
    Crag findById(int id);
    Crag findByRoutes(List<Route> routes);
}