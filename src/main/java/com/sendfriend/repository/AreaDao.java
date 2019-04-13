<<<<<<< HEAD:src/main/java/com/sendfriend/repository/AreaDao.java
package com.sendfriend.repository;

import com.sendfriend.domain.Area;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AreaDao extends CrudRepository<Area, Integer> {
    List<Area> findByName(String name);
    List<Area> findByNameIgnoreCaseContaining(String name);
    Area findById(int id);
=======
package com.sendfriend.models.data;

import com.sendfriend.models.Area;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AreaDao extends CrudRepository<Area, Integer> {
    List<Area> findByName(String name);
    List<Area> findByNameIgnoreCaseContaining(String name);
    Area findById(int id);
>>>>>>> develop:src/main/java/com/sendfriend/models/data/AreaDao.java
}