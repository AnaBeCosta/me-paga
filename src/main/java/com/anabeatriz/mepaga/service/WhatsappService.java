package com.anabeatriz.mepaga.service;

import com.anabeatriz.mepaga.dto.BillRepositoryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WhatsappService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public WhatsappService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendWhatsappReminder(String phone, BillRepositoryDTO bill) {
        String mensagem = "\uD83D\uDCB0 A sua conta *" + bill.description() + "* está próxima de vencer!" +
                "\n📅 Vencimento: " + bill.dueDate() +
                "\nPague o quanto antes para evitar multas." +
                "\n\nEnvie a mensagem 'Pago' caso já tenha feito o pagamento";

        Map<String, String> payload = new HashMap<>();

        payload.put("numero", "55" + phone);
        payload.put("mensagem", mensagem);

        try {
            rabbitTemplate.convertAndSend("me-paga-rabbit", payload);

            System.out.println("✅ Mensagem enviada para o RabbitMQ");
        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar mensagem para RabbitMQ: " + e.getMessage());
        }
    }
}