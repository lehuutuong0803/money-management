package com.tiuon.moneymanager.service.impl;

import com.tiuon.moneymanager.entity.ProfileEntity;
import com.tiuon.moneymanager.repository.ProfileRepository;
import com.tiuon.moneymanager.service.IEmailService;
import com.tiuon.moneymanager.service.IExpenseService;
import com.tiuon.moneymanager.service.INotificationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements INotificationService {

    private final ProfileRepository profileRepository;
    private final IEmailService iEmailService;
    private final IExpenseService iExpenseService;

    @Value("${money.manager.frontend.url}")
    private String frontendUrl;

    @Scheduled(cron = "0 0 22 * * *", zone = "Europe/Berlin")
//    @Scheduled(cron = "0 * * * * *", zone = "Europe/Berlin")
    public void sendDailyIncomeExpenseReminder() {
        log.info("Job started: sendDailyIncomeExpenseReminder");
        List<ProfileEntity> profileEntities = profileRepository.findAll();
        for(ProfileEntity profile : profileEntities) {
            String body = "Hi " + profile.getFullName() + ", <br></br>"
                    + "This is a friendly reminder to add your incomes and expenses for today in Money Management.<br></br>"
                    + "<a href="+frontendUrl+" style='display:inline-block;padding:10px 20px;background-color:#4CAF50;color:#fff;text-decoration:none;border-radius:5px;font-weight:bold;'>Go to Money Manager</a>"
                    + "<br><bn>Best regards, <br>Money Manager Team";
            iEmailService.sendEmail(profile.getEmail(),"Daily reminder: Add your income and expenses", body);
        }
    }


}
