package com.yemre.charging.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.yemre.charging.dao.ChargingSessionRepository;
import com.yemre.charging.enums.StatusEnum;
import com.yemre.charging.exception.ChargingSessionNotFoundException;
import com.yemre.charging.model.ChargingSession;
import com.yemre.charging.model.ChargingSessionRequestDTO;
import com.yemre.charging.model.ChargingSessionResponseDTO;
import com.yemre.charging.model.ChargingSessionSummary;
import com.yemre.charging.service.impl.ChargingSessionServiceImpl;

/**
 * Charging SEssion Service mock tests
 * 
 * @author YunusEmre
 *
 */
@RunWith(SpringRunner.class)
public class ChargingSessionServiceTests {
    @InjectMocks
    private ChargingSessionServiceImpl chargingSessionService;

    @Mock
    private ChargingSessionRepository chargingSessionRepository;

    List<ChargingSession> expectedList = new ArrayList<ChargingSession>();
    ChargingSession chargingSession1;
    ChargingSession chargingSession2;
    ChargingSessionRequestDTO chargingSessionRequestDTO;
    ChargingSessionResponseDTO chargingSessionResponseDTO;
    ChargingSessionSummary chargingSessionSummary;

    @Before
    public void setup() {
	chargingSession1 = new ChargingSession(UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51d18"), "ABC-12345",
		LocalDateTime.parse("2019-05-06T19:00:20.529"), StatusEnum.IN_PROGESS);
	expectedList.add(chargingSession1);
	chargingSession2 = new ChargingSession(UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51587"), "ABC-12345",
		LocalDateTime.parse("2019-05-06T19:00:25.529"), StatusEnum.FINISHED);
	expectedList.add(chargingSession2);
	chargingSessionRequestDTO = new ChargingSessionRequestDTO("ABC-12345",
		LocalDateTime.parse("2019-05-06T19:00:20.529"));
	chargingSessionResponseDTO = new ChargingSessionResponseDTO(
		UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51d18"), "ABC-12345",
		LocalDateTime.parse("2019-05-06T19:00:20.529"));
	chargingSessionSummary = new ChargingSessionSummary(1, 1);
    }

    @Test
    public void testGetAll() {
	Mockito.when(chargingSessionRepository.getAll()).thenReturn(expectedList);
	List<ChargingSession> actualList = chargingSessionService.getAll();
	assertEquals(expectedList.size(), actualList.size());
    }

    @Test
    public void testGetById_Found() {
	Mockito.when(chargingSessionRepository.getById(UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51587")))
		.thenReturn(chargingSession2);
	ChargingSession actual = chargingSessionService
		.getById(UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51587"));
	assertEquals(chargingSession2.getId(), actual.getId());
	assertEquals(chargingSession2.getStatus(), actual.getStatus());
	assertEquals(chargingSession2.getStartedAt(), actual.getStartedAt());
	assertEquals(chargingSession2.getStationId(), actual.getStationId());
    }

    @Test(expected = ChargingSessionNotFoundException.class)
    public void testGetById_NotFound() {
	Mockito.when(chargingSessionRepository.getById(UUID.fromString("a9bb7458-d5d9-4de7-87f7-7f39edd51587")))
		.thenReturn(null);
	chargingSessionService.getById(UUID.fromString("a9bb7458-d5d9-4de7-87f7-7f39edd51587"));
    }

    @Test
    public void testGetSummaryOfLastMinute() {
	Mockito.when(chargingSessionRepository.getChargingSessionsOfLastMinute()).thenReturn(chargingSessionSummary);
	ChargingSessionSummary summaryOfLastMinute = chargingSessionService.getSummaryOfLastMinute();
	assertEquals(1, summaryOfLastMinute.getStartedCount());
	assertEquals(1, summaryOfLastMinute.getStoppedCount());
	assertEquals(2, summaryOfLastMinute.getTotalCount());
    }

    @Test
    public void testCreate() {
	Mockito.when(chargingSessionRepository.create(chargingSessionRequestDTO)).thenReturn(chargingSession1);
	ChargingSessionResponseDTO response = chargingSessionService.create(chargingSessionRequestDTO);
	assertEquals(chargingSessionResponseDTO.getId(), response.getId());
	assertEquals(chargingSessionResponseDTO.getStartedAt(), response.getStartedAt());
	assertEquals(chargingSessionResponseDTO.getStationId(), response.getStationId());
    }

    @Test
    public void testUpdate() {
	Mockito.when(chargingSessionRepository.update(chargingSession1)).thenReturn(chargingSession1);
	ChargingSession actual = chargingSessionService.update(chargingSession1);
	assertEquals(chargingSession1.getId(), actual.getId());
	assertEquals(chargingSession1.getStatus(), actual.getStatus());
    }

}
