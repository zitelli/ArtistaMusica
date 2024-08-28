package br.com.alura.artistaMusica.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.github.cdimascio.dotenv.Dotenv;

public class ConsumoAPIMariTalkAI {
	    
    public static String sendChatRequest(String nome) {
        RestTemplate restTemplate = new RestTemplate();
        String question = "Informe somente os nomes das músicas do artista " + nome;
        
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("url");
        String apiKey = dotenv.get("API_KEY");

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        
        // Create request body
        Map<String, Object> requestBody = new HashMap<>();
        
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", question);
        messages.add(message);
        
        requestBody.put("messages", messages);
        requestBody.put("do_sample", "True");
        requestBody.put("max_tokens", 200);
        requestBody.put("temperature", 0.7);
        requestBody.put("top_p", 0.95);
        requestBody.put("model", "sabia-3");
        
        // Combine headers and body into an HttpEntity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        
        // Send POST request
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        
        // Handle response
        if (response.getStatusCode().is2xxSuccessful()) {
//            System.out.println("Response: " + response.getBody());
        	return response.getBody();
        } else {
            System.out.println("Error occurred: " + response.getStatusCode());
        }
        return null;
    }
	
}
