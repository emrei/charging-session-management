package com.yemre.charging.dao;

import java.util.List;
import java.util.UUID;

import com.yemre.charging.model.ChargingSession;
import com.yemre.charging.model.ChargingSessionRequestDTO;
import com.yemre.charging.model.ChargingSessionSummary;
/**
 * Charging Session Repository class
 * @author YunusEmre
 *
 */
public interface ChargingSessionRepository {
    /**
     * return all charging sessions
     * 
     * @return
     */
    List<ChargingSession> getAll();

    /**
     * return charging session with given id
     * 
     * @param id
     * @return
     */
    ChargingSession getById(UUID id);

    /**
     * return charging session summary of last minute
     * 
     * @return
     */
    ChargingSessionSummary getChargingSessionsOfLastMinute();

    /**
     * create a new charging session with given request. If there is already same
     * session returns previously created one
     * 
     * @param chargingSession
     * @return
     */
    ChargingSession create(ChargingSessionRequestDTO chargingSession);

    /**
     * update charging session
     * @param chargingSession
     * @return
     */
    ChargingSession update(ChargingSession chargingSession);

}
