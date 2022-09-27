package com.iacsd.demo.cron;

import com.iacsd.demo.model.CheckIn;
import com.iacsd.demo.repository.CheckinRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResetEmailRemider {
	@Autowired
	private CheckinRepo checkinRepo;
	
	//@Scheduled(cron = "0 */1 * * * * ")//for every minute
	@Scheduled(cron = "0 0 0 * * *")
	public void resetSentCount() {
		List<CheckIn> checkIns=checkinRepo.findAll();
		checkIns.forEach(ch->{
			ch.setSentCount(0);
		});
		checkinRepo.saveAll(checkIns);
	}
}
