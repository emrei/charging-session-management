package com.yemre.charging.service;

import java.util.List;
import java.util.UUID;

import com.yemre.charging.model.ChargingSession;
import com.yemre.charging.model.ChargingSessionRequestDTO;
import com.yemre.charging.model.ChargingSessionResponseDTO;
import com.yemre.charging.model.ChargingSessionSummary;
/**
 * Charging Session Service 
 * @author YunusEmre
 *
 */
public interface ChargingSessionService {
    /**
     * get all charging sessions
     * @return
     */
    List<ChargingSession> getAll();
    /**
     * get charging session with given id
     * @param id
     * @return
     */
    ChargingSession getById(UUID id);
    /**
     * get charging session summary of last minute
     * @return
     */
    ChargingSessionSummary getSummaryOfLastMinute();
    /**
     * create a new charging session
     * @param chargingSessionDTO
     * @return
     */
    ChargingSessionResponseDTO create(ChargingSessionRequestDTO chargingSessionDTO);
    
    /**
     * update charging session with given value
     * @param chargingSession
     * @return
     */
    ChargingSession update(ChargingSession chargingSession);
}
