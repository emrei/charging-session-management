package com.yemre.charging.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response body object for charging session
 * 
 * @author YunusEmre
 *
 */
public class ChargingSessionResponseDTO {
    private UUID id;
    private String stationId;
    private LocalDateTime startedAt;

    public ChargingSessionResponseDTO() {
	super();
    }

    public ChargingSessionResponseDTO(UUID id, String stationId, LocalDateTime startedAt) {
	this.id = id;
	this.stationId = stationId;
	this.startedAt = startedAt;
    }

    public UUID getId() {
	return id;
    }

    public void setId(UUID id) {
	this.id = id;
    }

    public String getStationId() {
	return stationId;
    }

    public void setStationId(String stationId) {
	this.stationId = stationId;
    }

    public LocalDateTime getStartedAt() {
	return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
	this.startedAt = startedAt;
    }

}
