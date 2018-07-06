package com.sendfriend.models.data;

import com.sendfriend.models.Area;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AreaDao  extends CrudRepository<Area, Integer> {
}
