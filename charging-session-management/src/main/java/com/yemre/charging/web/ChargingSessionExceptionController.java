package com.yemre.charging.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.yemre.charging.exception.ChargingSessionNotFoundException;
import com.yemre.charging.model.ChargingSession;
/**
 * Charging Session Exception Controller Advice
 * @author YunusEmre
 *
 */
@ControllerAdvice
public class ChargingSessionExceptionController {
    
    Logger logger = LoggerFactory.getLogger(ChargingSessionExceptionController.class);

    @ExceptionHandler(value = ChargingSessionNotFoundException.class)
    public ResponseEntity<ChargingSession> handleNotFound(ChargingSessionNotFoundException exception) {
	return new ResponseEntity<ChargingSession>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleServerError(Exception exception) {
	logger.error("big error", exception);
	return new ResponseEntity<>("Server has a problem", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
