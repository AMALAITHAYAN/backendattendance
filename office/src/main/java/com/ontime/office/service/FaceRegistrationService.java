package com.ontime.office.service;

import org.springframework.web.multipart.MultipartFile;

public interface FaceRegistrationService {

    boolean registerFace(Long employeeId, MultipartFile imageFile) throws Exception;

    String checkInFace(MultipartFile imageFile) throws Exception;
}
