package com.yemre.charging.model;

/**
 * Charging session summary model
 * 
 * @author YunusEmre
 *
 */
public class ChargingSessionSummary {
    private int totalCount;
    private int startedCount;
    private int stoppedCount;

    public ChargingSessionSummary() {
	super();
    }

    public ChargingSessionSummary(int startedCount, int stoppedCount) {
	this.startedCount = startedCount;
	this.stoppedCount = stoppedCount;
	this.totalCount = startedCount + stoppedCount;
    }

    public int getTotalCount() {
	return totalCount;
    }

    public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
    }

    public int getStartedCount() {
	return startedCount;
    }

    public void setStartedCount(int startedCount) {
	this.startedCount = startedCount;
    }

    public int getStoppedCount() {
	return stoppedCount;
    }

    public void setStoppedCount(int stoppedCount) {
	this.stoppedCount = stoppedCount;
    }

}
