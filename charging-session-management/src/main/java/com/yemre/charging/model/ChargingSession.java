package com.yemre.charging.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.yemre.charging.enums.StatusEnum;

/**
 * Charging Session Model class
 * 
 * @author YunusEmre
 *
 */
public class ChargingSession {
    private UUID id;
    private String stationId;
    private LocalDateTime startedAt;
    private StatusEnum status;

    public ChargingSession() {
	super();
    }

    public ChargingSession(UUID id, String stationId, LocalDateTime startedAt, StatusEnum status) {
	this.id = id;
	this.stationId = stationId;
	this.startedAt = startedAt;
	this.status = status;
    }

    public UUID getId() {
	return id;
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

    public StatusEnum getStatus() {
	return status;
    }

    public void setStatus(StatusEnum status) {
	this.status = status;
    }

    @Override
    public int hashCode() {
	return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof ChargingSession))
	    return false;
	ChargingSession other = (ChargingSession) obj;
	return Objects.equals(getId(), other.getId());
    }

}
