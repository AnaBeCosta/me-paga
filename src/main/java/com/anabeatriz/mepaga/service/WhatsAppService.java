package com.anabeatriz.mepaga.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;

@Service
public class WhatsAppService {

    public void enviarMensagem(String telefone, String mensagem) {
        // Chamar API como Twilio, Z-API, etc.
        // Exemplo fictício:
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.whatsapp.com/send"))
                .header("Authorization", "Bearer SUA_CHAVE")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"phone\":\"" + telefone + "\", \"message\":\"" + mensagem + "\"}"
                ))
                .build();

        // Enviar a requisição com HttpClient
    }
}