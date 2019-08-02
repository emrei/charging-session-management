package com.yemre.charging.repository;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.yemre.charging.dao.ChargingSessionRepository;
import com.yemre.charging.model.ChargingSessionRequestDTO;

/**
 * Test class for creating sessions concurrently
 * @author YunusEmre
 *
 */
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class ChargingSessionRepositoryConcurrencyTests {

    @Autowired
    ChargingSessionRepository chargingSessionRepository;

    ChargingSessionRequestDTO chargingSessionRequestDTO;

    @Before
    public void setup() {
	chargingSessionRequestDTO = new ChargingSessionRequestDTO("ABC-12345",
		LocalDateTime.parse("2019-05-06T19:00:20.529"));
    }

    @Test
    public void testConcurrentCreate() throws InterruptedException {
	runMultithreaded(new Runnable() {
	    public void run() {
		chargingSessionRepository.create(chargingSessionRequestDTO);
	    }
	}, 1000);
	assertEquals(1000, chargingSessionRepository.getAll().size());
    }

    private void runMultithreaded(Runnable runnable, int threadCount) throws InterruptedException {
	List<Thread> threadList = new LinkedList<Thread>();
	for (int i = 0; i < threadCount; i++) {
	    threadList.add(new Thread(runnable));
	}
	for (Thread t : threadList) {
	    t.start();
	}
	for (Thread t : threadList) {
	    t.join();
	}
    }
}
