package com.yemre.charging.dao.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.yemre.charging.dao.ChargingSessionRepository;
import com.yemre.charging.enums.StatusEnum;
import com.yemre.charging.model.ChargingSession;
import com.yemre.charging.model.ChargingSessionRequestDTO;
import com.yemre.charging.model.ChargingSessionSummary;

/**
 * Charging session repository in memory implementation
 * 
 * @author YunusEmre
 *
 */
@Repository
public class ChargingSessionRepositoryImpl implements ChargingSessionRepository {

    /**
     * It stores charging session lists with localDateTime key. It keeps sorted in
     * order to retrieve summary in a more efficient way. It is a concurrent map and
     * keeps data like tree map.
     */
    private final ConcurrentSkipListMap<LocalDateTime, CopyOnWriteArrayList<ChargingSession>> chargingSessionTreeMap = new ConcurrentSkipListMap<LocalDateTime, CopyOnWriteArrayList<ChargingSession>>();

    @Override
    public List<ChargingSession> getAll() {
	return chargingSessionTreeMap.values().stream().flatMap(list -> list.stream()).collect(Collectors.toList());
    }

    @Override
    public ChargingSession getById(UUID id) {
	List<ChargingSession> sessionList = chargingSessionTreeMap.values().stream().flatMap(list -> list.stream())
		.collect(Collectors.toList());
	for (ChargingSession chargingSession : sessionList) {
	    if (chargingSession.getId().equals(id)) {
		return chargingSession;
	    }
	}
	return null;
    }

    @Override
    public ChargingSessionSummary getChargingSessionsOfLastMinute() {
	LocalDateTime lastMinute = LocalDateTime.now().minusMinutes(1);
	List<StatusEnum> statusList = chargingSessionTreeMap.tailMap(lastMinute, true).values().stream()
		.flatMap(list -> list.stream()).map(cs -> cs.getStatus()).collect(Collectors.toList());
	int startedCount = (int) statusList.stream().filter(e -> e == StatusEnum.IN_PROGESS).count();
	int stoppedCount = (int) statusList.stream().filter(e -> e == StatusEnum.FINISHED).count();
	return new ChargingSessionSummary(startedCount, stoppedCount);
    }

    @Override
    public ChargingSession create(ChargingSessionRequestDTO chargingSessionDTO) {

	ChargingSession chargingSession = createNewSession(chargingSessionDTO);
	chargingSessionTreeMap.computeIfAbsent(chargingSession.getStartedAt(), k -> new CopyOnWriteArrayList<>())
		.add(chargingSession);
	return chargingSession;
    }

    private ChargingSession createNewSession(ChargingSessionRequestDTO chargingSessionDTO) {
	return new ChargingSession(UUID.randomUUID(), chargingSessionDTO.getStationId(),
		chargingSessionDTO.getStartedAt(), StatusEnum.IN_PROGESS);
    }

    @Override
    public ChargingSession update(ChargingSession chargingSession) {
	return updateTreeMap(chargingSession);

    }

    private ChargingSession updateTreeMap(ChargingSession chargingSession) {
	List<ChargingSession> list = chargingSessionTreeMap.get(chargingSession.getStartedAt());
	for (ChargingSession session : list) {
	    if (session.getId().equals(chargingSession.getId())) {
		session.setStatus(StatusEnum.FINISHED);
		chargingSession = session;
	    }
	}
	return chargingSession;
    }

}
