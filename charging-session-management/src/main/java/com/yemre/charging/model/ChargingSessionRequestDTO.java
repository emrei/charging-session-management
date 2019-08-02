package com.yemre.charging.model;

import java.time.LocalDateTime;

/**
 * Request body object for charging session
 * 
 * @author YunusEmre
 *
 */
public class ChargingSessionRequestDTO {
    private String stationId;
    private LocalDateTime startedAt;

    public ChargingSessionRequestDTO(String stationId, LocalDateTime startedAt) {
	this.stationId = stationId;
	this.startedAt = startedAt;
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
