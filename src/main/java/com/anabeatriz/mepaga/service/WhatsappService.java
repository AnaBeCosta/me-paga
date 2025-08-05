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
        String mensagem = "💰 A sua conta *" + bill.description() + "* está próxima de vencer!" +
                "\n📅 Vencimento: " + bill.dueDate() +
                "\nPague o quanto antes para evitar multas.";

        Map<String, String> payload = new HashMap<>();

        payload.put("numero", "55" + phone);
        payload.put("mensagem", mensagem);

        try {
            String json = objectMapper.writeValueAsString(payload);

            rabbitTemplate.convertAndSend("me-paga-rabbit", json);

            System.out.println("✅ Mensagem enviada para o RabbitMQ");
        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar mensagem para RabbitMQ: " + e.getMessage());
        }
    }
}