package com.sendfriend.models.data;

import com.sendfriend.models.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RouteDao extends CrudRepository<Route, Integer> {

}