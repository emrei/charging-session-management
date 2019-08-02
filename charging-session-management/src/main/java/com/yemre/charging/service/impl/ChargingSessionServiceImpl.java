package com.yemre.charging.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yemre.charging.dao.ChargingSessionRepository;
import com.yemre.charging.exception.ChargingSessionNotFoundException;
import com.yemre.charging.model.ChargingSession;
import com.yemre.charging.model.ChargingSessionRequestDTO;
import com.yemre.charging.model.ChargingSessionResponseDTO;
import com.yemre.charging.model.ChargingSessionSummary;
import com.yemre.charging.service.ChargingSessionService;

/**
 * Charging session service implementation
 * 
 * @author YunusEmre
 *
 */
@Service
public class ChargingSessionServiceImpl implements ChargingSessionService {

    @Autowired
    ChargingSessionRepository chargingSessionRepository;

    @Override
    public List<ChargingSession> getAll() {
	return chargingSessionRepository.getAll();
    }

    @Override
    public ChargingSession getById(UUID id) {
	ChargingSession session = chargingSessionRepository.getById(id);
	if (session == null) {
	    throw new ChargingSessionNotFoundException("Charging session not found with id: " + id);
	}
	return session;
    }

    @Override
    public ChargingSessionSummary getSummaryOfLastMinute() {
	return chargingSessionRepository.getChargingSessionsOfLastMinute();
    }

    @Override
    public ChargingSessionResponseDTO create(ChargingSessionRequestDTO chargingSessionDTO) {
	ChargingSession newSession = chargingSessionRepository.create(chargingSessionDTO);
	ChargingSessionResponseDTO chargingSessionResponseDTO = new ChargingSessionResponseDTO(newSession.getId(),
		newSession.getStationId(), newSession.getStartedAt());
	return chargingSessionResponseDTO;
    }

    @Override
    public ChargingSession update(ChargingSession chargingSession) {
	return chargingSessionRepository.update(chargingSession);
    }

}
