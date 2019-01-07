package com.sendfriend.models.data;

import com.sendfriend.models.Beta;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class BetaDaoImpl implements BetaDaoCustom {

    @Autowired
    BetaDao betaDao;

    public List<Beta> findPublicBetasByRouteId(int routeId) {
        List<Beta> results = new ArrayList<>();
        List<Beta> allBetas = betaDao.findByRouteId(routeId);
        allBetas.stream().forEach(beta -> {
            if (beta.getIsPublic()) {
                results.add(beta);
            }
        });
        return results;
    }
}
