<<<<<<< HEAD:src/main/java/com/sendfriend/repository/BetaDao.java
package com.sendfriend.repository;

import com.sendfriend.domain.Beta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BetaDao extends CrudRepository<Beta, Integer>, BetaDaoCustom {
    Beta findById(int id);
    List<Beta> findByUserId(int id);
    List<Beta> findByRouteId(int routeId);
    List<Beta> findPublicBetasByRouteId(int routeId);

    void deleteById(int betaId);
}
=======
package com.sendfriend.models.data;

import com.sendfriend.models.Beta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BetaDao extends CrudRepository<Beta, Integer> {
    Beta findById(int id);
    List<Beta> findByUserId(int id);
    List<Beta> findPublicBetasByRouteId(int routeId);

    void deleteById(int betaId);
}
>>>>>>> develop:src/main/java/com/sendfriend/models/data/BetaDao.java
