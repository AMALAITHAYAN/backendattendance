package com.ontime.office.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class FaceService {

    public String sendImageToPython(MultipartFile imageFile) throws IOException {
        // Convert to byte array
        byte[] imageBytes = imageFile.getBytes();

        // Send to Python server
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://facepythonbackend.onrender.com/api/face/process")) // Adjust to your Python server URL
                .header("Content-Type", "application/octet-stream")
                .POST(HttpRequest.BodyPublishers.ofByteArray(imageBytes))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();  // Python response (e.g., "Face matched", etc.)
            } else {
                throw new RuntimeException("Python server error: " + response.body());
            }

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException("Failed to send image to Python server", e);
        }
    }
}

