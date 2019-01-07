package com.sendfriend.models.data;

import com.sendfriend.models.Beta;

import java.util.List;

public interface BetaDaoCustom {
    List<Beta> findPublicBetasByRouteId(int routeId);
}
