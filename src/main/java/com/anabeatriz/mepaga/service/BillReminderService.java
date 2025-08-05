package com.anabeatriz.mepaga.service;

import com.anabeatriz.mepaga.dto.BillRepositoryDTO;
import com.anabeatriz.mepaga.model.Bill;
import com.anabeatriz.mepaga.repository.BillRepository;
import com.anabeatriz.mepaga.service.WhatsappService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillReminderService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private WhatsappService service;

    @Scheduled(cron = "0 * * * * *") // a cada minuto
    //@Scheduled(cron = "0 0 8,20 * * *")
    public void checkBills() {
        LocalDate today = LocalDate.now();
        LocalDate upcoming = today.plusDays(2);

        List<BillRepositoryDTO> bills = billRepository.findByDueDateBetween(today, upcoming);

       for (BillRepositoryDTO bill : bills) {
           service.sendWhatsappReminder(bill.userPhone(), bill);
       }
    }
}
