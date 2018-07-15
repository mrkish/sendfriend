package com.sendfriend.models.data;

import com.sendfriend.models.Crag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CragDao extends CrudRepository<Crag, Integer> {
}
