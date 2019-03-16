package com.sendfriend.repository;

import com.sendfriend.domain.Beta;

import java.util.List;

public interface BetaDaoCustom {
    List<Beta> findPublicBetasByRouteId(int routeId);
}
