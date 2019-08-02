package com.yemre.charging.web;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.yemre.charging.enums.StatusEnum;
import com.yemre.charging.model.ChargingSession;
import com.yemre.charging.model.ChargingSessionRequestDTO;
import com.yemre.charging.model.ChargingSessionResponseDTO;
import com.yemre.charging.model.ChargingSessionSummary;

/**
 * Charging Session Rest Controller integration tests
 * 
 * @author YunusEmre
 *
 */
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ChargingSessionControllerTests {

    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
	restTemplate = new TestRestTemplate();
    }

    @Test
    public void testCreate() {
	ChargingSessionRequestDTO chargingSessionRequestDTO = new ChargingSessionRequestDTO("ABC-12345",
		LocalDateTime.parse("2019-05-06T19:00:20.529"));
	ResponseEntity<ChargingSessionResponseDTO> responseEntity = restTemplate.postForEntity(
		"http://localhost:8080/chargingSessions", chargingSessionRequestDTO, ChargingSessionResponseDTO.class);
	MatcherAssert.assertThat(responseEntity.getBody().getStationId(),
		Matchers.equalTo(chargingSessionRequestDTO.getStationId()));
	MatcherAssert.assertThat(responseEntity.getBody().getStartedAt(),
		Matchers.equalTo(chargingSessionRequestDTO.getStartedAt()));
	assertNotNull(responseEntity.getBody().getId());
    }

    @Test
    public void testGetAllChargingSessions() {
	ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/chargingSessions", List.class);
	MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
	List<ChargingSession> body = response.getBody();
	MatcherAssert.assertThat(body, Matchers.empty());
    }

    @Test
    public void testUpdate() {
	ChargingSessionRequestDTO chargingSessionRequestDTO = new ChargingSessionRequestDTO("ABC-12345",
		LocalDateTime.parse("2019-05-06T19:00:20.529"));
	ResponseEntity<ChargingSessionResponseDTO> responseEntity = restTemplate.postForEntity(
		"http://localhost:8080/chargingSessions", chargingSessionRequestDTO, ChargingSessionResponseDTO.class);
	ResponseEntity<ChargingSession> responseUpdate = restTemplate.exchange(
		"http://localhost:8080/chargingSessions/" + responseEntity.getBody().getId(), HttpMethod.PUT, null,
		ChargingSession.class);
	MatcherAssert.assertThat(responseUpdate.getStatusCodeValue(), Matchers.equalTo(200));
	MatcherAssert.assertThat(responseUpdate.getBody().getStatus(), Matchers.equalTo(StatusEnum.FINISHED));
    }

    @Test
    public void testUpdate_SessionNotFound() {
	ResponseEntity<ChargingSession> responseUpdate = restTemplate.exchange(
		"http://localhost:8080/chargingSessions/d9bb7458-d5d9-4de7-87f7-7f39edd51d18", HttpMethod.PUT, null,
		ChargingSession.class);
	MatcherAssert.assertThat(responseUpdate.getStatusCodeValue(), Matchers.equalTo(404));
    }

    @Test
    public void testGetChargingSessionSummary() {
	ChargingSessionRequestDTO chargingSessionRequestDTO1 = new ChargingSessionRequestDTO("ABC-12345",
		LocalDateTime.now());
	ResponseEntity<ChargingSessionResponseDTO> responseEntity1 = restTemplate.postForEntity(
		"http://localhost:8080/chargingSessions", chargingSessionRequestDTO1, ChargingSessionResponseDTO.class);
	restTemplate.exchange("http://localhost:8080/chargingSessions/" + responseEntity1.getBody().getId(),
		HttpMethod.PUT, null, ChargingSession.class);
	ChargingSessionRequestDTO chargingSessionRequestDTO2 = new ChargingSessionRequestDTO("ABC-12345",
		LocalDateTime.now());
	restTemplate.postForEntity("http://localhost:8080/chargingSessions", chargingSessionRequestDTO2,
		ChargingSessionResponseDTO.class);
	ResponseEntity<ChargingSessionSummary> response = restTemplate
		.getForEntity("http://localhost:8080/chargingSessions/summary", ChargingSessionSummary.class);
	MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
	MatcherAssert.assertThat(response.getBody().getStartedCount(), Matchers.equalTo(1));
	MatcherAssert.assertThat(response.getBody().getStoppedCount(), Matchers.equalTo(1));
	MatcherAssert.assertThat(response.getBody().getTotalCount(), Matchers.equalTo(2));
    }

}
