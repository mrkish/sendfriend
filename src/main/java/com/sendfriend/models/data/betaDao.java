package com.sendfriend.models.data;

import com.sendfriend.models.Beta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface betaDao extends CrudRepository<Beta, Integer> {
}
