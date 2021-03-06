package com.sendfriend.data;

import com.sendfriend.models.Beta;
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
