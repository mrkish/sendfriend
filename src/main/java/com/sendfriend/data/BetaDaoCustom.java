package com.sendfriend.data;

import com.sendfriend.models.Beta;

import java.util.List;

public interface BetaDaoCustom {
    List<Beta> findPublicBetasByRouteId(int routeId);
}
