package com.anabeatriz.mepaga.service;

import com.anabeatriz.mepaga.dto.BillRepositoryDTO;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.cdimascio.dotenv.Dotenv;

public class WhatsappService {
    Dotenv dotenv = Dotenv.load();

    String sid = dotenv.get("TWILIO_ACCOUNT_SID");
    String token = dotenv.get("TWILIO_AUTH_TOKEN");

    public void sendWhatsappReminder(String phone, BillRepositoryDTO bill) {
        Twilio.init(sid, token);
        Message message = Message.creator(
                        new PhoneNumber("whatsapp:+55" + phone),
                        new PhoneNumber("whatsapp:+14155238886"),
                        "A sua conta " + bill.description() + " está próximo de vencer!!" +
                        "\n vencimento:" + bill.dueDate()+
                        "\n pague o quanto antes")
                .create();

        System.out.println(message.getSid());
    }
}