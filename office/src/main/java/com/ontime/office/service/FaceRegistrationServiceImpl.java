package com.ontime.office.service;

import com.ontime.office.model.FaceRecord;
import com.ontime.office.repository.FaceRecordRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FaceRegistrationServiceImpl implements FaceRegistrationService {

    private final String flaskApiUrl = "https://facepythonbackend.onrender.com/faces";

    @Autowired
    private FaceRecordRepository faceRecordRepository;

    @Override
    public boolean registerFace(Long employeeId, MultipartFile imageFile) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource byteArrayResource = new ByteArrayResource(imageFile.getBytes()) {
            @Override
            public String getFilename() {
                return imageFile.getOriginalFilename();
            }
        };

        body.add("file", byteArrayResource);
        body.add("action", "register");
        body.add("employeeId", employeeId.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(flaskApiUrl, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // Save a record in DB (optional)
            FaceRecord existingRecord = faceRecordRepository.findByEmployeeId(employeeId);
            if (existingRecord == null) {
                FaceRecord record = new FaceRecord(employeeId, null); // save path if you want
                faceRecordRepository.save(record);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String checkInFace(MultipartFile imageFile) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource byteArrayResource = new ByteArrayResource(imageFile.getBytes()) {
            @Override
            public String getFilename() {
                return imageFile.getOriginalFilename();
            }
        };

        body.add("file", byteArrayResource);
        body.add("action", "checkin");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(flaskApiUrl, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String json = response.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            String message = root.path("message").asText();

            if (message.contains("recognized")) {
                return "Matched";
            } else {
                return "Not Matched";
            }
        } else {
            throw new Exception("Check-in failed: " + response.getStatusCode());
        }
    }
}
