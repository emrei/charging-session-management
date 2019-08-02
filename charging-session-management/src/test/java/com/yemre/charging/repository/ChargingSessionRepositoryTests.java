package com.yemre.charging.repository;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.yemre.charging.dao.ChargingSessionRepository;
import com.yemre.charging.enums.StatusEnum;
import com.yemre.charging.model.ChargingSession;
import com.yemre.charging.model.ChargingSessionRequestDTO;
import com.yemre.charging.model.ChargingSessionResponseDTO;
import com.yemre.charging.model.ChargingSessionSummary;

/**
 * Charging Session Repository integration tests.
 * 
 * @author YunusEmre
 *
 */
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class ChargingSessionRepositoryTests {

    @Autowired
    ChargingSessionRepository chargingSessionrepository;

    List<ChargingSession> expectedList = new ArrayList<ChargingSession>();
    ChargingSession chargingSession1;
    ChargingSession chargingSession2;
    ChargingSessionRequestDTO chargingSessionRequestDTO;
    ChargingSessionResponseDTO chargingSessionResponseDTO;

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
    }

    @Test
    public void testCreate() {
	chargingSessionrepository.create(chargingSessionRequestDTO);
	List<ChargingSession> list = chargingSessionrepository.getAll();
	assertEquals(1, list.size());
	assertEquals("ABC-12345", list.get(0).getStationId());
	assertEquals(LocalDateTime.parse("2019-05-06T19:00:20.529"), list.get(0).getStartedAt());
    }

    @Test
    public void testUpdate() {
	chargingSessionrepository.create(chargingSessionRequestDTO);
	ChargingSession updated = chargingSessionrepository.update(chargingSession1);
	assertEquals(chargingSession1.getId(), updated.getId());
	assertEquals(chargingSession1.getStatus(), updated.getStatus());
    }

    @Test
    public void testGetChargingSessionsOfLastMinute_NoSession() {
	chargingSessionrepository.create(chargingSessionRequestDTO);
	ChargingSessionSummary summary = chargingSessionrepository.getChargingSessionsOfLastMinute();
	assertEquals(0, summary.getTotalCount());
    }

    @Test
    public void testGetChargingSessionsOfLastMinute_WithSession() {
	chargingSessionRequestDTO.setStartedAt(LocalDateTime.now());
	chargingSessionrepository.create(chargingSessionRequestDTO);
	ChargingSessionSummary summary = chargingSessionrepository.getChargingSessionsOfLastMinute();
	assertEquals(1, summary.getTotalCount());
	assertEquals(1, summary.getStartedCount());
	assertEquals(0, summary.getStoppedCount());
    }

    @Test
    public void testGetAll() {
	chargingSessionrepository.create(chargingSessionRequestDTO);
	chargingSessionRequestDTO.setStartedAt(LocalDateTime.now());
	chargingSessionrepository.create(chargingSessionRequestDTO);
	List<ChargingSession> list = chargingSessionrepository.getAll();
	assertEquals(2, list.size());
    }

}
