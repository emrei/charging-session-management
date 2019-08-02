package com.yemre.charging.exception;

/**
 * Charging session not found exception
 * @author YunusEmre
 *
 */
public class ChargingSessionNotFoundException extends RuntimeException {

    public ChargingSessionNotFoundException(String message) {
	super(message);
    }

}
