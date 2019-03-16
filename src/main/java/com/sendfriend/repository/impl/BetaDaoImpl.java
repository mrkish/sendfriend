package com.sendfriend.repository.impl;

import com.sendfriend.domain.Beta;
import com.sendfriend.repository.BetaDao;
import com.sendfriend.repository.BetaDaoCustom;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
