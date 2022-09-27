package com.iacsd.demo.cron;

import com.iacsd.demo.model.AppUser;
import com.iacsd.demo.model.CheckIn;
import com.iacsd.demo.repository.CheckinRepo;
import com.iacsd.demo.util.dto.EmailNotification;
import com.iacsd.demo.util.dto.Recipient;
import com.iacsd.demo.util.dto.RecipientType;
import com.iacsd.demo.util.impl.JavaEmailUtilImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableScheduling
@Component
public class CheckinExpireEmailScheduler {
	@Autowired
	private CheckinRepo checkinRepo;
	@Autowired
	private JavaEmailUtilImpl javaEmailUtilImpl;

	//@Scheduled(cron = "*/3 * * * * * ")
	@Scheduled(cron = "0 */1 * * * * ")
	//@Scheduled(cron = "0 0 */3 * * *")
	public void productExpireScheduler() {
		System.out.println("Inside Checkin Email reminder Scheduler");
		Instant today=Instant.now();
		//get check ins which are not expired yet
		List<CheckIn> checkIns=checkinRepo.findByExpireOnAfter(today);
		
		checkIns.forEach(ch->{
			if(ch.getSentCount()<ch.getRepeatReminder()&&ch.getRemainingQuantity()>0&&
			  (ch.getExpireOn().isBefore(today.plus(ch.getRemindBefore(),ChronoUnit.DAYS)))) {
				AppUser user= ch.getAccount().getUser();
				Recipient recipient=new Recipient(user.getEmail(), RecipientType.TO);
				String msg="Hi "+user.getUsername()+", <br> Your checkin is going to expire on "+getLocalDateFormatter(ch.getExpireOn())+" product name "+ch.getProduct().getName()+" product code "+ch.getProduct().getCode()+" which was checked in on "+getLocalDateFormatter(ch.getCheckedInOn())+". checkin still have product with quantity "+ch.getRemainingQuantity()+" "+ch.getUnit()+"!";
				Map<String, byte[]> attachments= new HashMap<>();
				List<Recipient> recipients=new ArrayList<>();
				recipients.add(recipient);
				EmailNotification notification=EmailNotification.from(recipients, msg,"Product Expiring Soon!",attachments);
				javaEmailUtilImpl.sendEmail(notification);
				ch.setSentCount(ch.getSentCount()+1);
				checkinRepo.save(ch);
			}
		});
	}

	    public String getLocalDateFormatter(Instant insDate) {
        //Instant instant = Instant.now();
        //System.out.println(instant);
        ZonedDateTime zonedDateTime = insDate.atZone(ZoneId.of("Asia/Kolkata"));
        System.out.println("ZonedDateTime : " + zonedDateTime);
        String dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(zonedDateTime);
        //System.out.println(dateTimeFormatter);
        return dateTimeFormatter;
    }
}
