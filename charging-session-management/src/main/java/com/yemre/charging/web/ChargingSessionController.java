package com.yemre.charging.web;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yemre.charging.enums.StatusEnum;
import com.yemre.charging.model.ChargingSession;
import com.yemre.charging.model.ChargingSessionRequestDTO;
import com.yemre.charging.model.ChargingSessionResponseDTO;
import com.yemre.charging.model.ChargingSessionSummary;
import com.yemre.charging.service.ChargingSessionService;

/**
 * Rest Controller for Charging Session Rest API
 * @author YunusEmre
 *
 */
@RestController
@RequestMapping("/chargingSessions")
public class ChargingSessionController {

    @Autowired
    ChargingSessionService chargingSessionService;

    @PostMapping
    public ResponseEntity<ChargingSessionResponseDTO> create(
	    @RequestBody ChargingSessionRequestDTO chargingSessionRequestDTO) {
	ChargingSessionResponseDTO newSession = chargingSessionService.create(chargingSessionRequestDTO);
	return new ResponseEntity<ChargingSessionResponseDTO>(newSession, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargingSession> update(@PathVariable("id") UUID id) {
	ChargingSession chargingSession = chargingSessionService.getById(id);
	chargingSession.setStatus(StatusEnum.FINISHED);
	chargingSessionService.update(chargingSession);
	return ResponseEntity.ok(chargingSession);
    }

    @GetMapping
    public ResponseEntity<List<ChargingSession>> getAllChargingSessions() {
	List<ChargingSession> allSessions = chargingSessionService.getAll();
	if(allSessions.isEmpty()) {
	    return new ResponseEntity<List<ChargingSession>>(HttpStatus.NO_CONTENT);
	}
	return ResponseEntity.ok(allSessions);
    }

    @GetMapping("/summary")
    public ResponseEntity<ChargingSessionSummary> getChargingSessionSummary() {
	ChargingSessionSummary summaryOfLastMinute = chargingSessionService.getSummaryOfLastMinute();
	return ResponseEntity.ok(summaryOfLastMinute);
    }

}
